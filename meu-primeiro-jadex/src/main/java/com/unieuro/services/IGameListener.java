package com.unieuro.services;

/**
 * Interface for receiving game events
 */
public interface IGameListener {
    /**
     * Called when apple is eaten
     */
    void onAppleEaten(int newScore);
    
    /**
     * Called when game ends
     */
    void onGameOver();
    
    /**
     * Called when game state changes
     */
    void onGameStep();
}