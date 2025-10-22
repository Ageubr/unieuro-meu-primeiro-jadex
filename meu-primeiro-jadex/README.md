# 🐍 Jogo da Cobrinha com Jadex

Sistema Multi-Agente desenvolvido com **Jadex** e **Spring Boot** que implementa o clássico jogo da cobrinha usando agentes inteligentes.

## ✨ Características

- 🤖 **Sistema Multi-Agente** com Jadex
- 🌐 **Interface Web** com Spring Boot
- 🔄 **Comunicação em Tempo Real** via WebSocket
- 🎮 **Controles Intuitivos** (teclado e botões)
- 📊 **Sistema de Pontuação**

## 🏗️ Arquitetura

### Agentes Jadex
- **GameManagerAgent** 🎮 - Gerencia o estado e lógica do jogo
- **SnakeAgent** 🐍 - Controla o comportamento da cobra
- **AppleAgent** 🍎 - Gerencia a geração e consumo de maçãs
- **ScoreAgent** 📊 - Sistema de pontuação e estatísticas
- **HelloAgent** 👋 - Agente de demonstração

### Componentes Spring Boot
- **WebApplication** - Aplicação principal Spring Boot
- **GameController** - Controlador web para páginas
- **GameWebSocketHandler** - Comunicação WebSocket em tempo real
- **WebSocketConfig** - Configuração do WebSocket

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+

### Método 1: Script Automatizado
```bash
chmod +x start-game.sh
./start-game.sh
```

### Método 2: Comando Maven
```bash
mvn exec:java -Dexec.mainClass="com.unieuro.Main"
```

### Método 3: Spring Boot
```bash
mvn spring-boot:run
```

## 🌐 Acessando o Jogo

Após inicializar, acesse:
- **Página Principal**: http://localhost:8080
- **Jogo**: http://localhost:8080/game
- **Diagnóstico**: http://localhost:8080/diagnostic

## 🎮 Controles

- **Setas do Teclado**: ⬆️⬇️⬅️➡️ para mover a cobra
- **Botões na Tela**: Clique nos botões direcionais
- **Tecla R**: Reiniciar o jogo
- **Botão Reiniciar**: Reset via interface

## 🛠️ Estrutura do Projeto

```
src/main/java/com/unieuro/
├── Main.java                    # Classe principal que inicia Jadex + Spring
├── WebApplication.java          # Aplicação Spring Boot
├── agents/                      # Agentes Jadex
│   ├── GameManagerAgent.java
│   ├── SnakeAgent.java
│   ├── AppleAgent.java
│   ├── ScoreAgent.java
│   └── HelloAgent.java
├── config/                      # Configurações
├── game/                        # Lógica do jogo
├── services/                    # Interfaces de serviços
└── web/                         # Controllers e WebSocket
```

## 🔧 Configuração

### application.properties
```properties
server.port=8080
server.servlet.context-path=/
logging.level.com.unieuro=DEBUG
```

## 🐛 Solução de Problemas

### Porta em Uso
Se a porta 8080 estiver ocupada, altere em `application.properties`:
```properties
server.port=8081
```

### Processos Anteriores
Para limpar processos anteriores:
```bash
pkill -f "meu-primeiro-jadex"
```

### Logs de Debug
Para ver mais informações, ative logs detalhados:
```properties
logging.level.jadex=DEBUG
logging.level.com.unieuro=DEBUG
```

## 📋 Status dos Componentes

✅ Spring Boot Web Server  
✅ WebSocket Handler  
✅ Jadex Platform  
✅ GameManagerAgent  
✅ Multi-Agent Communication  
✅ Real-time Game Updates  

## 🔄 Sistema Multi-Agente

O jogo utiliza uma arquitetura de agentes onde cada componente tem responsabilidades específicas:

1. **GameManagerAgent** centraliza o estado do jogo
2. **Agentes especializados** lidam com aspectos específicos
3. **Comunicação assíncrona** entre agentes via Jadex
4. **Interface web** conecta via WebSocket

## 📝 Logs do Sistema

Durante a execução, você verá logs como:
```
🌐 Spring Boot iniciado em http://localhost:8080
🎮 Acesse o jogo em: http://localhost:8080/game
🤖 Iniciando agentes Jadex...
GameManagerAgent started with game model
AppleAgent started
ScoreAgent started
✅ Sistema iniciado!
```