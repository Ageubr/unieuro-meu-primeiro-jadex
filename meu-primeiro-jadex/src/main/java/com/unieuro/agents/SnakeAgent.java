package com.unieuro.agents;

import com.unieuro.services.IGameService;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.OnStart;
import jadex.bridge.service.search.ServiceQuery;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

@Agent
@RequiredServices(@RequiredService(name="gameservice", type=IGameService.class))
public class SnakeAgent {

    @OnStart
    void start(IInternalAccess me) {
        System.out.println("SnakeAgent: Iniciando loop do jogo...");
        me.repeatStep(0, 200, dummy -> {
            // Tentar descoberta nativa primeiro
            me.searchService(new ServiceQuery<>(IGameService.class))
                .addResultListener(new IResultListener<IGameService>() {
                    @Override
                    public void resultAvailable(IGameService gameService) {
                        if (gameService != null) {
                            gameService.step();
                        }
                    }
                    
                    @Override
                    public void exceptionOccurred(Exception exception) {
                        // Fallback para AgentBridge
                        IGameService bridgeService = AgentBridge.getGameService();
                        if (bridgeService != null) {
                            bridgeService.step();
                        } else {
                            System.out.println("SnakeAgent: Nenhum GameService disponível");
                        }
                    }
                });
            return IFuture.DONE;
        });
    }
}
