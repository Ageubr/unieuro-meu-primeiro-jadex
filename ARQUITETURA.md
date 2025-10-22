# 🎯 ARQUITETURA DO PROJETO - JOGO DA COBRINHA COM JADEX

## 🤖 FUNÇÃO DE CADA AGENTE

### **1. GameManagerAgent** 🎮
**Função**: Coordenador central do jogo
- **Responsabilidades**:
  - Gerencia o estado geral do jogo (GameModel)
  - Registra o serviço `IGameService` para outros agentes encontrarem
  - Processa comandos de direção vindos do WebSocket
  - Coordena a comunicação entre todos os componentes
- **Por que é importante**: É o "cérebro" que conecta a interface web com os agentes

### **2. SnakeAgent** 🐍
**Função**: Motor principal do jogo
- **Responsabilidades**:
  - Executa o loop principal do jogo (a cada 200ms)
  - Move a cobrinha baseado na direção atual
  - Verifica colisões (paredes, próprio corpo)
  - Detecta quando a cobrinha come maçãs
  - Atualiza o estado do jogo continuamente
- **Por que é importante**: Sem ele, o jogo não se move! É o "coração" que faz tudo acontecer

### **3. AppleAgent** 🍎
**Função**: Gerenciador de maçãs
- **Responsabilidades**:
  - Gera novas maçãs em posições aleatórias
  - Remove maçãs quando a cobrinha as come
  - Garante que sempre há maçãs disponíveis no tabuleiro
- **Por que é importante**: Sem maçãs, não há objetivo no jogo!

### **4. ScoreAgent** 📊
**Função**: Sistema de pontuação
- **Responsabilidades**:
  - Calcula e mantém a pontuação do jogador
  - Atualiza score quando maçãs são comidas
  - Pode implementar sistema de high scores
- **Por que é importante**: Gamificação - torna o jogo mais interessante

---

## 🔧 PARA QUE SERVIU O JADEX

### **🌟 Jadex = Plataforma Multi-Agente**
O Jadex é um framework que permite criar **sistemas distribuídos** onde diferentes "agentes" (pequenos programas independentes) trabalham juntos:

**Vantagens no nosso projeto**:
1. **Modularidade**: Cada agente tem uma responsabilidade específica
2. **Comunicação**: Agentes se comunicam através de serviços
3. **Concorrência**: Múltiplos agentes executam simultaneamente
4. **Flexibilidade**: Fácil adicionar novos agentes (PowerUpAgent, MultiplayerAgent, etc.)
5. **Simulação de mundo real**: Como uma empresa onde cada departamento tem sua função

**Exemplo prático**:
```
🎮 GameManagerAgent: "Recebi comando para ir à direita"
🐍 SnakeAgent: "Ok, na próxima iteração vou para a direita"
🍎 AppleAgent: "Ah, a cobra comeu minha maçã! Vou gerar outra"
📊 ScoreAgent: "Maçã comida = +10 pontos!"
```

---

## 🛠️ OUTRAS FERRAMENTAS UTILIZADAS

### **1. Spring Boot** 🌱
**Função**: Framework web principal
- **Por que usamos**:
  - Cria servidor web automaticamente
  - Gerencia injeção de dependências
  - Facilita configuração de WebSockets
  - Serve páginas HTML e recursos estáticos

### **2. WebSocket** 🔌
**Função**: Comunicação em tempo real
- **Por que usamos**:
  - Permite comunicação bidirecional instantânea
  - Browser → Servidor: comandos de movimento
  - Servidor → Browser: atualizações do jogo
  - Sem refresh da página!

### **3. HTML5 Canvas + JavaScript** 🎨
**Função**: Interface gráfica do jogo
- **Por que usamos**:
  - Canvas permite desenhar gráficos 2D
  - JavaScript captura teclas pressionadas
  - Renderização suave e responsiva
  - Compatível com qualquer browser moderno

### **4. Maven** 📦
**Função**: Gerenciador de dependências e build
- **Por que usamos**:
  - Gerencia bibliotecas automaticamente
  - Compila o projeto com um comando
  - Executa testes e empacota a aplicação
  - Padrão da indústria Java

---

## 🏗️ ARQUITETURA GERAL

```
🌐 BROWSER (HTML5 Canvas + JS)
         ↕️ WebSocket
🌱 SPRING BOOT (Servidor Web)
         ↕️ AgentBridge
🤖 JADEX PLATFORM (Multi-Agente)
    ├── 🎮 GameManagerAgent
    ├── 🐍 SnakeAgent  
    ├── 🍎 AppleAgent
    └── 📊 ScoreAgent
```

---

## 💡 POR QUE ESSA ARQUITETURA?

### **Benefícios**:
1. **Escalabilidade**: Fácil adicionar novos recursos
2. **Manutenibilidade**: Cada componente é independente
3. **Testabilidade**: Pode testar cada agente separadamente
4. **Aprendizado**: Demonstra conceitos avançados de programação
5. **Flexibilidade**: Pode trocar implementações sem afetar outros componentes

### **Cenários de expansão**:
- **MultiplayerAgent**: Para jogos online
- **AIAgent**: Para IA que joga sozinha
- **PowerUpAgent**: Para power-ups especiais
- **LeaderboardAgent**: Para ranking global
- **SoundAgent**: Para efeitos sonoros

---

## 🔄 FLUXO DE EXECUÇÃO

### **1. Inicialização**
```
1. Spring Boot inicia o servidor web (porta 8080)
2. Jadex Platform é inicializada
3. Agentes são criados e registrados:
   - GameManagerAgent registra IGameService
   - SnakeAgent procura IGameService e inicia loop
   - AppleAgent e ScoreAgent se conectam ao sistema
```

### **2. Durante o Jogo**
```
1. Usuário pressiona tecla no browser (WASD/setas)
2. JavaScript captura evento e envia via WebSocket
3. GameWebSocketHandler recebe comando
4. GameManagerAgent processa mudança de direção
5. SnakeAgent (loop 200ms) move a cobrinha
6. AppleAgent verifica se maçã foi comida
7. ScoreAgent atualiza pontuação
8. Estado atualizado é renderizado no Canvas
```

### **3. Sistema de Fallback**
```
Se descoberta de serviços falhar:
SnakeAgent → AgentBridge → GameManagerAgent
(Comunicação direta quando Jadex services não funcionam)
```

---

## 📁 ESTRUTURA DE ARQUIVOS

```
src/main/java/com/unieuro/
├── agents/           # Agentes Jadex
│   ├── GameManagerAgent.java
│   ├── SnakeAgent.java
│   ├── AppleAgent.java
│   ├── ScoreAgent.java
│   └── AgentBridge.java
├── config/           # Configurações Spring
│   └── WebConfig.java
├── game/             # Lógica do jogo
│   ├── GameModel.java
│   ├── Direction.java
│   └── GameWindow.java
├── services/         # Interfaces de serviços
│   ├── IGameService.java
│   ├── IGameListener.java
│   └── GameState.java
├── web/              # Controladores web
│   ├── GameController.java
│   ├── GameWebSocketHandler.java
│   ├── WebSocketConfig.java
│   └── DiagnosticController.java
├── Main.java         # Ponto de entrada Jadex
└── WebApplication.java # Ponto de entrada Spring Boot
```

---

## 🎓 CONCEITOS APRENDIDOS

### **Programação**
1. **Sistemas Multi-Agente** (Jadex)
2. **Programação Web** (Spring Boot)
3. **Comunicação em Tempo Real** (WebSocket)
4. **Programação Orientada a Eventos** (JavaScript)
5. **Arquitetura de Software** (Separação de responsabilidades)

### **DevOps**
1. **Controle de Versão** (Git)
2. **Gerenciamento de Dependências** (Maven)
3. **Documentação Técnica** (Markdown)
4. **Containerização** (Conceitos Docker)

### **Boas Práticas**
1. **Clean Code**: Código limpo e bem estruturado
2. **SOLID Principles**: Separação de responsabilidades
3. **Design Patterns**: Observer, Service Locator, Bridge
4. **Error Handling**: Sistema de fallback robusto

---

## 🚀 POSSÍVEIS EXPANSÕES

### **Funcionalidades**
- [ ] Sistema de níveis de dificuldade
- [ ] Power-ups especiais (velocidade, invencibilidade)
- [ ] Multiplayer local/online
- [ ] IA para jogar automaticamente
- [ ] Sistema de achievement/conquistas
- [ ] Efeitos sonoros e música
- [ ] Temas visuais personalizáveis

### **Técnicas**
- [ ] Persistência de dados (banco de dados)
- [ ] API REST para estatísticas
- [ ] Autenticação de usuários
- [ ] Deploy em cloud (AWS, Azure, Heroku)
- [ ] Testes automatizados (JUnit, Selenium)
- [ ] CI/CD pipeline (GitHub Actions)
- [ ] Monitoramento e métricas

---

## 📚 RECURSOS PARA ESTUDO

### **Jadex**
- [Documentação Oficial](https://www.activecomponents.org/)
- [Tutoriais Multi-Agente](https://www.activecomponents.org/forward.html?type=tutorial)

### **Spring Boot**
- [Spring Boot Guide](https://spring.io/guides/gs/spring-boot/)
- [WebSocket Tutorial](https://spring.io/guides/gs/messaging-stomp-websocket/)

### **Arquitetura de Software**
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Microservices Patterns](https://microservices.io/patterns/)

---

**💡 Este projeto demonstra uma implementação profissional de sistemas distribuídos aplicados a jogos, combinando tecnologias modernas de forma elegante e escalável.**