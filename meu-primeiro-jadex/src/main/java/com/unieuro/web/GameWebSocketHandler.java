package com.unieuro.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unieuro.agents.AgentBridge;
import com.unieuro.game.Direction;
import com.unieuro.services.GameState;
import com.unieuro.services.IGameService;
import jadex.bridge.IExternalAccess;
import jadex.bridge.service.search.ServiceQuery;
import jadex.commons.future.IResultListener;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class GameWebSocketHandler extends TextWebSocketHandler {
    
    private static final ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private static IExternalAccess platform;
    private static IGameService gameService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    // Fallback game model para quando os agentes não estão disponíveis
    private static com.unieuro.game.GameModel fallbackGame = new com.unieuro.game.GameModel(30, 20);

    public static void setPlatform(IExternalAccess platform) {
        GameWebSocketHandler.platform = platform;
        if (platform != null) {
            // Tentar buscar o serviço de jogo com retry
            connectToGameService();
        }
    }

    private static void connectToGameService() {
        if (platform == null) return;
        
        platform.searchService(new ServiceQuery<>(IGameService.class))
            .addResultListener(new IResultListener<IGameService>() {
                @Override
                public void resultAvailable(IGameService result) {
                    gameService = result;
                    System.out.println("WebSocket: GameService connected successfully");
                }
                
                @Override
                public void exceptionOccurred(Exception exception) {
                    System.out.println("WebSocket: GameService not ready yet, trying AgentBridge...");
                    
                    // Tentar usar o AgentBridge como fallback
                    IGameService bridgeService = AgentBridge.getGameService();
                    if (bridgeService != null) {
                        gameService = bridgeService;
                        System.out.println("WebSocket: GameService connected via AgentBridge");
                        return;
                    }
                    
                    // Tentar novamente após 2 segundos
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            connectToGameService();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }).start();
                }
            });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        System.out.println("WebSocket connected: " + session.getId());
        
        // Enviar estado inicial
        sendGameState(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("WebSocket disconnected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (gameService == null) {
            // Tentar reconectar ao serviço
            connectToGameService();
            // Em vez de falhar, vamos usar um jogo básico
            handleBasicGame(session, message);
            return;
        }

        try {
            JsonNode json = objectMapper.readTree(message.getPayload());
            String action = json.get("action").asText();
            
            switch (action) {
                case "move":
                    String direction = json.get("direction").asText();
                    handleMove(direction);
                    break;
                case "reset":
                    gameService.resetGame();
                    break;
                case "getState":
                    sendGameState(session);
                    break;
                default:
                    session.sendMessage(new TextMessage("{\"error\":\"Unknown action: " + action + "\"}"));
            }
        } catch (Exception e) {
            session.sendMessage(new TextMessage("{\"error\":\"Invalid message format\"}"));
        }
    }

    private void handleMove(String directionStr) {
        System.out.println("WebSocket: Recebido comando de movimento: " + directionStr);
        
        if (gameService == null) {
            System.out.println("WebSocket: ERRO - gameService é null!");
            return;
        }
        
        Direction direction = switch (directionStr.toLowerCase()) {
            case "up" -> Direction.UP;
            case "down" -> Direction.DOWN;
            case "left" -> Direction.LEFT;
            case "right" -> Direction.RIGHT;
            default -> null;
        };
        
        if (direction != null) {
            System.out.println("WebSocket: Enviando direção " + direction + " para gameService");
            gameService.setSnakeDirection(direction);
            System.out.println("WebSocket: Comando enviado com sucesso");
        } else {
            System.out.println("WebSocket: ERRO - Direção inválida: " + directionStr);
        }
    }

    private void sendGameState(WebSocketSession session) {
        if (gameService == null) {
            // Tentar reconectar ao serviço
            connectToGameService();
            // Usar jogo básico como fallback
            sendBasicGameState(session);
            return;
        }
        
        gameService.getGameState().addResultListener(new IResultListener<GameState>() {
            @Override
            public void resultAvailable(GameState state) {
                try {
                    String json = convertGameStateToJson(state);
                    session.sendMessage(new TextMessage(json));
                } catch (Exception e) {
                    System.err.println("Error sending game state: " + e.getMessage());
                }
            }
            
            @Override
            public void exceptionOccurred(Exception exception) {
                // ignore
            }
        });
    }

    public static void broadcastGameState() {
        if (gameService == null || sessions.isEmpty()) return;
        
        gameService.getGameState().addResultListener(new IResultListener<GameState>() {
            @Override
            public void resultAvailable(GameState state) {
                String json = convertGameStateToJson(state);
                TextMessage message = new TextMessage(json);
                
                sessions.values().forEach(session -> {
                    try {
                        if (session.isOpen()) {
                            session.sendMessage(message);
                        }
                    } catch (Exception e) {
                        System.err.println("Error broadcasting to session: " + e.getMessage());
                    }
                });
            }
            
            @Override
            public void exceptionOccurred(Exception exception) {
                // ignore
            }
        });
    }

    private static String convertGameStateToJson(GameState state) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"type\":\"gameState\",");
        json.append("\"width\":").append(state.width).append(",");
        json.append("\"height\":").append(state.height).append(",");
        json.append("\"score\":").append(state.score).append(",");
        json.append("\"gameOver\":").append(state.gameOver).append(",");
        
        // Apple
        json.append("\"apple\":{\"x\":").append(state.apple.x).append(",\"y\":").append(state.apple.y).append("},");
        
        // Snake
        json.append("\"snake\":[");
        boolean first = true;
        for (var point : state.snake) {
            if (!first) json.append(",");
            json.append("{\"x\":").append(point.x).append(",\"y\":").append(point.y).append("}");
            first = false;
        }
        json.append("]");
        json.append("}");
        
        return json.toString();
    }

    private void handleBasicGame(WebSocketSession session, TextMessage message) throws Exception {
        try {
            JsonNode json = objectMapper.readTree(message.getPayload());
            String action = json.get("action").asText();
            
            switch (action) {
                case "move":
                    String direction = json.get("direction").asText();
                    handleBasicMove(direction);
                    break;
                case "reset":
                    fallbackGame = new com.unieuro.game.GameModel(30, 20);
                    break;
                case "getState":
                    sendBasicGameState(session);
                    return;
            }
            
            // Atualizar jogo e enviar estado
            fallbackGame.step();
            sendBasicGameState(session);
            
        } catch (Exception e) {
            session.sendMessage(new TextMessage("{\"error\":\"Invalid message format\"}"));
        }
    }

    private void handleBasicMove(String directionStr) {
        Direction direction = switch (directionStr.toLowerCase()) {
            case "up" -> Direction.UP;
            case "down" -> Direction.DOWN;
            case "left" -> Direction.LEFT;
            case "right" -> Direction.RIGHT;
            default -> null;
        };
        
        if (direction != null) {
            fallbackGame.setDirection(direction);
        }
    }

    private void sendBasicGameState(WebSocketSession session) {
        try {
            String json = convertBasicGameStateToJson();
            session.sendMessage(new TextMessage(json));
        } catch (Exception e) {
            System.err.println("Error sending basic game state: " + e.getMessage());
        }
    }

    private static String convertBasicGameStateToJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"type\":\"gameState\",");
        json.append("\"width\":").append(fallbackGame.width).append(",");
        json.append("\"height\":").append(fallbackGame.height).append(",");
        json.append("\"score\":").append(fallbackGame.getScore()).append(",");
        json.append("\"gameOver\":").append(fallbackGame.isGameOver()).append(",");
        
        // Apple
        var apple = fallbackGame.getApple();
        json.append("\"apple\":{\"x\":").append(apple.x).append(",\"y\":").append(apple.y).append("},");
        
        // Snake
        json.append("\"snake\":[");
        boolean first = true;
        for (var point : fallbackGame.getSnake()) {
            if (!first) json.append(",");
            json.append("{\"x\":").append(point.x).append(",\"y\":").append(point.y).append("}");
            first = false;
        }
        json.append("]");
        json.append("}");
        
        return json.toString();
    }
}