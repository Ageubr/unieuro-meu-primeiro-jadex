package com.unieuro.game;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class GameModel {
    public final int width;
    public final int height;

    private final Deque<Point> snake = new ArrayDeque<>();
    private Direction direction = Direction.RIGHT;
    private Point apple;
    private final Random rnd = new Random();
    private int score = 0;
    private boolean gameOver = false;

    public GameModel(int width, int height) {
        this.width = width;
        this.height = height;
        int startX = width / 4;
        int startY = height / 2;
        snake.addFirst(new Point(startX, startY));
        snake.addLast(new Point(startX - 1, startY));
        snake.addLast(new Point(startX - 2, startY));
        placeApple();
    }

    public synchronized void setDirection(Direction d) {
        System.out.println("GameModel: Tentando mudar direção de " + this.direction + " para " + d);
        if (!d.isOpposite(this.direction)) {
            this.direction = d;
            System.out.println("GameModel: Direção alterada com sucesso para " + d);
        } else {
            System.out.println("GameModel: Direção rejeitada - é oposta à atual");
        }
    }

    public synchronized void step() {
        if (gameOver) return;
        Point head = snake.peekFirst();
        Point next = new Point(head.x + direction.dx, head.y + direction.dy);
        // wrap-around
        if (next.x < 0) next.x = width - 1;
        if (next.y < 0) next.y = height - 1;
        if (next.x >= width) next.x = 0;
        if (next.y >= height) next.y = 0;

        // collision with self
        for (Point p : snake) {
            if (p.equals(next)) {
                gameOver = true;
                return;
            }
        }

        snake.addFirst(next);
        if (next.equals(apple)) {
            score++;
            placeApple();
        } else {
            snake.removeLast();
        }
    }

    private void placeApple() {
        Point p;
        do {
            p = new Point(rnd.nextInt(width), rnd.nextInt(height));
        } while (snake.contains(p));
        apple = p;
    }

    public synchronized Deque<Point> getSnake() { return new ArrayDeque<>(snake); }
    public synchronized Point getApple() { return new Point(apple); }
    public synchronized int getScore() { return score; }
    public synchronized boolean isGameOver() { return gameOver; }
}
