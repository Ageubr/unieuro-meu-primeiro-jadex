# 📋 Changelog - Jogo da Cobrinha com Jadex

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

## [1.1.0] - 2025-10-22

### 🎉 Adicionado
- **Comunicação Híbrida**: Sistema robusto com descoberta nativa + AgentBridge fallback
- **Registro Duplo de Serviços**: Compatibilidade máxima entre anotações e registro manual
- **AgentBridge**: Classe bridge para comunicação direta entre agentes e WebSocket
- **Logs Detalhados**: Sistema de logging estruturado para debug
- **Script de Inicialização**: `start-game.sh` com verificações e instruções
- **README Completo**: Documentação técnica detalhada com troubleshooting

### 🔧 Corrigido
- **Descoberta de Serviços**: Problema de timing na descoberta de serviços Jadex
- **WebSocket Connectivity**: Loop infinito "GameService not ready yet"
- **Service Registration**: Registro manual usando `IProvidedServicesFeature`
- **Thread Safety**: Sincronização aprimorada no GameModel

### ⚡ Melhorado
- **Confiabilidade**: Sistema funciona mesmo com falhas na descoberta de serviços
- **Performance**: Otimização na comunicação inter-agente
- **Debugging**: Mensagens de log mais claras e informativas
- **Documentação**: README com seções detalhadas de arquitetura e troubleshooting

### 🔄 Alterado
- **GameManagerAgent**: Adicionado registro manual de serviços com error handling
- **GameWebSocketHandler**: Implementado fallback via AgentBridge
- **AgentBridge**: Expandido para incluir referência ao IGameService
- **Logging**: Mensagens mais descritivas para facilitar debug

## [1.0.0] - 2025-10-21

### 🎉 Inicial
- **Sistema Multi-Agente**: Arquitetura com 4 agentes Jadex especializados
- **Interface Web**: HTML5 Canvas com WebSocket para comunicação real-time
- **Spring Boot Integration**: Framework web integrado com Jadex Platform
- **Game Engine**: Lógica completa do jogo Snake com detecção de colisões
- **Responsive Design**: Interface adaptável para desktop e mobile
- **WebSocket Communication**: Comunicação bidirecional para controles e estado

### 🤖 Agentes Implementados
- **GameManagerAgent**: Coordenador central e provedor do IGameService
- **SnakeAgent**: Motor do jogo com loop automático a cada 200ms
- **AppleAgent**: Gerenciador de eventos de consumo de maçãs
- **ScoreAgent**: Sistema de pontuação com detecção de marcos especiais

### 🌐 Interface Web
- **Homepage**: Página inicial com informações do projeto
- **Game Page**: Interface principal do jogo com Canvas HTML5
- **Diagnostic Page**: Página de debug e diagnósticos
- **WebSocket Handler**: Comunicação real-time com agentes

### 🔧 Infraestrutura
- **Maven Build**: Sistema de build com dependências gerenciadas
- **Java 17**: Compatibilidade com versões modernas do Java
- **Jadex 4.0.241**: Framework de agentes multi-core
- **Spring Boot 3.1.5**: Framework web com Tomcat embarcado

---

## 📝 Legenda

- 🎉 **Adicionado**: Novas funcionalidades
- 🔧 **Corrigido**: Bugs e problemas resolvidos  
- ⚡ **Melhorado**: Funcionalidades existentes aprimoradas
- 🔄 **Alterado**: Modificações em funcionalidades existentes
- ❌ **Removido**: Funcionalidades descontinuadas
- 🔒 **Segurança**: Correções relacionadas à segurança