package com.unieuro.agents;

import com.unieuro.game.GameModel;
import com.unieuro.services.IGameService;

/**
 * Small bridge to share the model instance between agents and the UI in this demo.
 * In a production Jadex system you'd use services/messages; this keeps the demo simple.
 */
public class AgentBridge {
    private static GameModel model;
    private static IGameService gameService;

    public static void setModel(GameModel m) { model = m; }
    public static GameModel getModel() { return model; }
    
    public static void setGameService(IGameService service) { gameService = service; }
    public static IGameService getGameService() { return gameService; }
}
