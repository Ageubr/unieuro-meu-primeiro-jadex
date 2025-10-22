package com.unieuro;

import com.unieuro.agents.AppleAgent;
import com.unieuro.agents.GameManagerAgent;
import com.unieuro.agents.HelloAgent;
import com.unieuro.agents.ScoreAgent;
import com.unieuro.agents.SnakeAgent;
import com.unieuro.web.GameWebSocketHandler;
import jadex.base.IPlatformConfiguration;
import jadex.base.PlatformConfigurationHandler;
import jadex.base.Starter;
import jadex.bridge.IExternalAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class Main {
    public static void main(String[] args) {
        // Iniciar Spring Boot em uma thread separada
        Thread springThread = new Thread(() -> {
            ConfigurableApplicationContext context = SpringApplication.run(WebApplication.class, args);
            System.out.println("🌐 Spring Boot iniciado em http://localhost:8080");
            System.out.println("🎮 Acesse o jogo em: http://localhost:8080/game");
        });
        springThread.setDaemon(false);
        springThread.start();

        // Aguardar um pouco para Spring Boot iniciar
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Iniciar plataforma Jadex
        System.out.println("🤖 Iniciando agentes Jadex...");
        IPlatformConfiguration config = PlatformConfigurationHandler.getMinimal();
        config.addComponent(HelloAgent.class);
        config.addComponent(GameManagerAgent.class);
        config.addComponent(SnakeAgent.class);
        config.addComponent(AppleAgent.class);
        config.addComponent(ScoreAgent.class);

        IExternalAccess platform = Starter.createPlatform(config).get();
        
        // Aguardar os agentes se registrarem
        try {
            Thread.sleep(5000);
            System.out.println("🔍 Aguardando registro dos serviços...");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Conectar WebSocket ao platform Jadex
        GameWebSocketHandler.setPlatform(platform);
        
        System.out.println("✅ Sistema iniciado!");
        System.out.println("🌐 Interface web: http://localhost:8080");
        System.out.println("🎮 Jogo: http://localhost:8080/game");
        
        // Manter a aplicação rodando
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}