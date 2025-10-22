package com.unieuro.services;

import com.unieuro.game.Direction;
import jadex.commons.future.IFuture;

/**
 * Service interface for game state management
 */
public interface IGameService {
    /**
     * Get current game state as a snapshot
     */
    IFuture<GameState> getGameState();
    
    /**
     * Set snake direction
     */
    IFuture<Void> setSnakeDirection(Direction direction);
    
    /**
     * Advance game by one step
     */
    IFuture<Void> step();
    
    /**
     * Check if game is over
     */
    IFuture<Boolean> isGameOver();
    
    /**
     * Reset the game
     */
    IFuture<Void> resetGame();
    
    /**
     * Register for game events
     */
    IFuture<Void> addGameListener(IGameListener listener);
}