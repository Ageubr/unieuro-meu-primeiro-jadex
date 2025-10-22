package com.unieuro.agents;

import com.unieuro.game.Direction;
import com.unieuro.game.GameModel;
import com.unieuro.services.GameState;
import com.unieuro.services.IGameListener;
import com.unieuro.services.IGameService;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.OnStart;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.component.IProvidedServicesFeature;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;

import java.util.ArrayList;
import java.util.List;

@Agent
@Service
@ProvidedServices(@ProvidedService(type = IGameService.class))
public class GameManagerAgent implements IGameService {
    
    private GameModel model;
    private List<IGameListener> listeners = new ArrayList<>();
    private int lastScore = 0;
    
    @OnStart
    void start(IInternalAccess me) {
        model = new GameModel(30, 20);
        System.out.println("GameManagerAgent started with game model");
        
        // Registrar no AgentBridge para acesso direto
        AgentBridge.setModel(model);
        AgentBridge.setGameService(this);
        
        // Registrar o serviço manualmente
        try {
            System.out.println("GameManagerAgent: Attempting to register service " + IGameService.class.getName());
            me.getFeature(IProvidedServicesFeature.class)
              .addService(IGameService.class.getName(), IGameService.class, this, (String)null);
            System.out.println("GameManagerAgent: Service registered successfully as " + IGameService.class.getSimpleName());
            
            // Aguardar um pouco e notificar a plataforma
            Thread.sleep(1000);
            System.out.println("GameManagerAgent: Service should now be discoverable");
        } catch (Exception e) {
            System.err.println("GameManagerAgent: Failed to register service: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public IFuture<GameState> getGameState() {
        if (model == null) return new Future<>(null);
        
        GameState state = new GameState(
            model.getSnake(),
            model.getApple(),
            model.getScore(),
            model.isGameOver(),
            model.width,
            model.height
        );
        return new Future<>(state);
    }
    
    @Override
    public IFuture<Void> setSnakeDirection(Direction direction) {
        System.out.println("GameManagerAgent: Recebida nova direção: " + direction);
        if (model != null) {
            model.setDirection(direction);
            System.out.println("GameManagerAgent: Direção atualizada no modelo");
        } else {
            System.out.println("GameManagerAgent: ERRO - model é null!");
        }
        return IFuture.DONE;
    }
    
    @Override
    public IFuture<Void> step() {
        if (model != null && !model.isGameOver()) {
            int oldScore = model.getScore();
            model.step();
            
            // notify listeners
            if (model.getScore() > oldScore) {
                notifyAppleEaten(model.getScore());
            }
            if (model.isGameOver()) {
                notifyGameOver();
            }
            notifyGameStep();
        }
        return IFuture.DONE;
    }
    
    @Override
    public IFuture<Boolean> isGameOver() {
        return new Future<>(model != null ? model.isGameOver() : true);
    }
    
    @Override
    public IFuture<Void> resetGame() {
        model = new GameModel(30, 20);
        lastScore = 0;
        return IFuture.DONE;
    }
    
    @Override
    public IFuture<Void> addGameListener(IGameListener listener) {
        listeners.add(listener);
        return IFuture.DONE;
    }
    
    private void notifyAppleEaten(int newScore) {
        for (IGameListener listener : listeners) {
            try {
                listener.onAppleEaten(newScore);
            } catch (Exception e) {
                // ignore listener errors
            }
        }
    }
    
    private void notifyGameOver() {
        for (IGameListener listener : listeners) {
            try {
                listener.onGameOver();
            } catch (Exception e) {
                // ignore listener errors
            }
        }
    }
    
    private void notifyGameStep() {
        for (IGameListener listener : listeners) {
            try {
                listener.onGameStep();
            } catch (Exception e) {
                // ignore listener errors
            }
        }
        
        // Notificar WebSocket
        try {
            com.unieuro.web.GameWebSocketHandler.broadcastGameState();
        } catch (Exception e) {
            // ignore se WebSocket não estiver disponível
        }
    }
    
    /**
     * Get the model for direct access (for GUI)
     */
    public GameModel getModel() {
        return model;
    }
}