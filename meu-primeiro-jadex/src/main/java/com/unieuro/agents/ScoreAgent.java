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
public class ScoreAgent implements IGameListener {

    private int currentScore = 0;

    @OnStart
    void start(IInternalAccess me) {
        System.out.println("ScoreAgent started");
        
        // Register as listener for score events
        me.searchService(new ServiceQuery<>(IGameService.class))
            .addResultListener(new IResultListener<IGameService>() {
                @Override
                public void resultAvailable(IGameService gameService) {
                    if (gameService != null) {
                        gameService.addGameListener(ScoreAgent.this);
                        System.out.println("ScoreAgent registered as game listener");
                    }
                }
                
                @Override
                public void exceptionOccurred(Exception exception) {
                    System.out.println("ScoreAgent: Failed to find game service");
                }
            });
    }

    @Override
    public void onAppleEaten(int newScore) {
        int increase = newScore - currentScore;
        currentScore = newScore;
        System.out.println("ScoreAgent: Score increased by " + increase + " to " + currentScore);
        
        // Could implement achievements, high scores, etc.
        if (currentScore > 0 && currentScore % 5 == 0) {
            System.out.println("ScoreAgent: Milestone reached! Score: " + currentScore);
        }
    }

    @Override
    public void onGameOver() {
        System.out.println("ScoreAgent: Final score was " + currentScore);
        currentScore = 0; // reset for next game
    }

    @Override
    public void onGameStep() {
        // Could track time-based scoring, etc.
    }
}