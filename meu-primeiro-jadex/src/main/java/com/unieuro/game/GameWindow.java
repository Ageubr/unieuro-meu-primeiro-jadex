package com.unieuro.game;

import com.unieuro.services.GameState;
import com.unieuro.services.IGameService;
import jadex.bridge.IExternalAccess;
import jadex.bridge.service.search.ServiceQuery;
import jadex.commons.future.IResultListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public class GameWindow extends JFrame {
    private final IExternalAccess platform;
    private final int cellSize = 20;
    private GameState currentState;
    private IGameService gameService;

    public GameWindow(IExternalAccess platform) {
        super("Jadex Snake");
        this.platform = platform;
        
        // Initialize with default size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(30 * cellSize + 16, 20 * cellSize + 39 + 30);
        setResizable(false);

        // Get game service
        platform.searchService(new ServiceQuery<>(IGameService.class))
            .addResultListener(new IResultListener<IGameService>() {
                @Override
                public void resultAvailable(IGameService service) {
                    gameService = service;
                    updateGameState();
                }
                
                @Override
                public void exceptionOccurred(Exception exception) {
                    System.out.println("GameWindow: Failed to find game service");
                }
            });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameService == null) return;
                
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> gameService.setSnakeDirection(Direction.UP);
                    case KeyEvent.VK_DOWN -> gameService.setSnakeDirection(Direction.DOWN);
                    case KeyEvent.VK_LEFT -> gameService.setSnakeDirection(Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> gameService.setSnakeDirection(Direction.RIGHT);
                    case KeyEvent.VK_R -> {
                        if (currentState != null && currentState.gameOver) {
                            gameService.resetGame();
                        }
                    }
                }
            }
        });

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentState == null) return;
                
                Graphics2D g2 = (Graphics2D) g;
                // background
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // draw grid (optional)
                g2.setColor(Color.DARK_GRAY);
                for (int x = 0; x < currentState.width; x++) {
                    for (int y = 0; y < currentState.height; y++) {
                        Rectangle2D r = new Rectangle2D.Double(x * cellSize, y * cellSize, cellSize - 1, cellSize - 1);
                        g2.draw(r);
                    }
                }

                // draw apple
                g2.setColor(Color.RED);
                var apple = currentState.apple;
                g2.fillRect(apple.x * cellSize, apple.y * cellSize, cellSize - 1, cellSize - 1);

                // draw snake
                g2.setColor(Color.GREEN);
                for (var p : currentState.snake) {
                    g2.fillRect(p.x * cellSize, p.y * cellSize, cellSize - 1, cellSize - 1);
                }

                // score
                g2.setColor(Color.WHITE);
                g2.drawString("Score: " + currentState.score, 5, currentState.height * cellSize + 15);

                if (currentState.gameOver) {
                    g2.setColor(Color.WHITE);
                    g2.drawString("Game Over - press R to restart", 120, currentState.height * cellSize + 15);
                }
            }
        };
        panel.setPreferredSize(new Dimension(30 * cellSize, 20 * cellSize + 30));
        add(panel);
        pack();

        // timer for repainting and updating state
        Timer t = new Timer(150, ev -> {
            updateGameState();
            panel.repaint();
        });
        t.start();
    }
    
    private void updateGameState() {
        if (gameService != null) {
            gameService.getGameState().addResultListener(new IResultListener<GameState>() {
                @Override
                public void resultAvailable(GameState state) {
                    currentState = state;
                }
                
                @Override
                public void exceptionOccurred(Exception exception) {
                    // ignore
                }
            });
        }
    }
}
