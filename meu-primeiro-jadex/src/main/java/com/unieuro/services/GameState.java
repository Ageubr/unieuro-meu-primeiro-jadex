package com.unieuro.services;

import java.awt.Point;
import java.util.Deque;

/**
 * Immutable snapshot of game state
 */
public class GameState {
    public final Deque<Point> snake;
    public final Point apple;
    public final int score;
    public final boolean gameOver;
    public final int width;
    public final int height;

    public GameState(Deque<Point> snake, Point apple, int score, boolean gameOver, int width, int height) {
        this.snake = snake;
        this.apple = apple;
        this.score = score;
        this.gameOver = gameOver;
        this.width = width;
        this.height = height;
    }
}