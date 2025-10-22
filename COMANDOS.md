# 🎮 GUIA DE COMANDOS - JOGO DA COBRINHA JADEX

## 🚀 COMANDOS PARA INICIAR O JOGO

### **Método 1: Maven (Recomendado)**
```bash
# Navegar para o diretório do projeto
cd meu-primeiro-jadex

# Iniciar o jogo
mvn spring-boot:run
```

### **Método 2: Script Automatizado**
```bash
# Tornar o script executável (apenas primeira vez)
chmod +x meu-primeiro-jadex/start-game.sh

# Executar o script
./meu-primeiro-jadex/start-game.sh
```

### **Método 3: Compilar e Executar**
```bash
# Compilar o projeto
mvn clean compile

# Executar a aplicação
mvn spring-boot:run
```

---

## 🌐 ACESSAR O JOGO

Após iniciar a aplicação, acesse:
- **Jogo Principal**: http://localhost:8080/game
- **Página Inicial**: http://localhost:8080/
- **Diagnóstico**: http://localhost:8080/diagnostic
- **Teste**: http://localhost:8080/test

---

## 🎯 CONTROLES DO JOGO

### **Teclado**
- **W** ou **↑** = Mover para cima
- **S** ou **↓** = Mover para baixo  
- **A** ou **←** = Mover para esquerda
- **D** ou **→** = Mover para direita

---

## 🔧 COMANDOS DE DESENVOLVIMENTO

### **Build e Compilação**
```bash
# Limpar e compilar
mvn clean compile

# Limpar, compilar e empacotar
mvn clean package

# Pular testes durante o build
mvn clean package -DskipTests
```

### **Testes**
```bash
# Executar todos os testes
mvn test

# Executar teste específico
mvn test -Dtest=NomeDoTeste
```

### **Gerenciamento de Dependências**
```bash
# Verificar dependências
mvn dependency:tree

# Baixar dependências
mvn dependency:resolve

# Atualizar dependências
mvn versions:use-latest-versions
```

---

## 🐛 COMANDOS DE DEBUG

### **Logs e Monitoramento**
```bash
# Executar com logs detalhados
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dlogging.level.com.unieuro=DEBUG"

# Verificar processos Java rodando
jps -l

# Matar processo específico
kill -9 <PID>
```

### **Verificar Porta**
```bash
# Ver o que está rodando na porta 8080
lsof -i :8080

# Ou no Linux
netstat -tulpn | grep 8080

# Matar processo na porta 8080
sudo fuser -k 8080/tcp
```

---

## 📦 COMANDOS GIT

### **Controle de Versão**
```bash
# Ver status
git status

# Adicionar arquivos
git add .

# Fazer commit
git commit -m "Sua mensagem"

# Enviar para GitHub
git push origin main

# Ver histórico
git log --oneline

# Ver diferenças
git diff
```

### **Branches**
```bash
# Criar nova branch
git checkout -b nova-feature

# Trocar de branch
git checkout main

# Listar branches
git branch -a

# Merge branch
git merge nova-feature
```

---

## 🔄 COMANDOS DE SISTEMA

### **Gerenciamento de Processos**
```bash
# Ver processos Java
ps aux | grep java

# Matar todos os processos Java
pkill -f java

# Matar processo Spring Boot específico
pkill -f spring-boot:run
```

### **Limpeza do Sistema**
```bash
# Limpar cache Maven
mvn clean

# Remover diretório target
rm -rf target/

# Limpar logs
> logs/application.log
```

---

## 🚨 RESOLUÇÃO DE PROBLEMAS

### **Problema: Porta 8080 ocupada**
```bash
# Encontrar e matar processo na porta
sudo lsof -t -i:8080 | xargs kill -9

# Ou usar porta diferente
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

### **Problema: Dependências não encontradas**
```bash
# Limpar e baixar dependências
mvn clean dependency:resolve

# Forçar atualização
mvn clean install -U
```

### **Problema: Java não encontrado**
```bash
# Verificar versão Java
java -version

# Verificar JAVA_HOME
echo $JAVA_HOME

# Listar versões Java instaladas (Linux)
update-alternatives --list java
```

---

## 📊 COMANDOS DE MONITORAMENTO

### **Performance**
```bash
# Monitorar CPU e memória
top

# Ver uso de memória JVM
jstat -gc <PID>

# Thread dump
jstack <PID>
```

### **Logs da Aplicação**
```bash
# Ver logs em tempo real
tail -f logs/application.log

# Procurar erros nos logs  
grep -i error logs/application.log

# Filtrar logs por agente
grep "SnakeAgent" logs/application.log
```

---

## 🎯 SEQUÊNCIA RECOMENDADA PARA INICIAR

```bash
# 1. Navegar para o projeto
cd /caminho/para/unieuro-meu-primeiro-jadex

# 2. Entrar no diretório Maven
cd meu-primeiro-jadex

# 3. Limpar e compilar (opcional)
mvn clean compile

# 4. Iniciar o jogo
mvn spring-boot:run

# 5. Abrir no browser
# http://localhost:8080/game
```

---

## 🛑 PARAR A APLICAÇÃO

### **No Terminal**
- **Ctrl + C** (interrompe o processo)

### **Por Comando**
```bash
# Matar processo Spring Boot
pkill -f spring-boot:run

# Ou por PID específico
kill <PID>
```

---

## 📝 COMANDOS ÚTEIS ADICIONAIS

### **Verificar Status da Aplicação**
```bash
# Verificar se está rodando
curl http://localhost:8080/

# Verificar WebSocket
curl -H "Connection: Upgrade" -H "Upgrade: websocket" http://localhost:8080/game-websocket
```

### **Backup do Projeto**
```bash
# Criar backup
tar -czf backup-jadex-$(date +%Y%m%d).tar.gz meu-primeiro-jadex/

# Restaurar backup
tar -xzf backup-jadex-20251022.tar.gz
```

---

## 🚀 SCRIPTS PERSONALIZADOS

O projeto inclui scripts prontos:

### **start-game.sh**
```bash
./meu-primeiro-jadex/start-game.sh
```
- Compila automaticamente
- Inicia a aplicação
- Mostra instruções

### **test-server.sh**
```bash
./meu-primeiro-jadex/test-server.sh
```
- Testa se o servidor está respondendo
- Verifica WebSocket
- Mostra status dos agentes

---

**💡 Dica**: Salve este arquivo como referência rápida para comandos essenciais do projeto!