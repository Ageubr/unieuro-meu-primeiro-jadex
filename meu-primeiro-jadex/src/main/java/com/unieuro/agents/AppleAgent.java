package com.unieuro.agents;

import com.unieuro.services.IGameListener;
import com.unieuro.services.IGameService;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.annotation.OnStart;
import jadex.bridge.service.search.ServiceQuery;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;

@Agent
@RequiredServices(@RequiredService(name="gameservice", type=IGameService.class))
public class AppleAgent implements IGameListener {

    @OnStart
    void start(IInternalAccess me) {
        System.out.println("AppleAgent started");
        
        // Register as listener for apple events
        me.searchService(new ServiceQuery<>(IGameService.class))
            .addResultListener(new IResultListener<IGameService>() {
                @Override
                public void resultAvailable(IGameService gameService) {
                    if (gameService != null) {
                        gameService.addGameListener(AppleAgent.this);
                        System.out.println("AppleAgent registered as game listener");
                    }
                }
                
                @Override
                public void exceptionOccurred(Exception exception) {
                    System.out.println("AppleAgent: Failed to find game service");
                }
            });
    }

    @Override
    public void onAppleEaten(int newScore) {
        System.out.println("AppleAgent: Apple eaten! New score: " + newScore);
    }

    @Override
    public void onGameOver() {
        System.out.println("AppleAgent: Game over detected");
    }

    @Override
    public void onGameStep() {
        // Could track apple position changes, etc.
    }
}
