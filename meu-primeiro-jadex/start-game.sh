#!/bin/bash

# 🐍 Script de Inicialização do Jogo da Cobrinha com Jadex
# Autor: UniEuro - Primeiro Projeto Jadex
# Uso: ./start-game.sh

echo "🐍 ========================================="
echo "   JOGO DA COBRINHA COM JADEX MULTI-AGENTE"
echo "🐍 ========================================="
echo ""

# Verificar se está no diretório correto
if [ ! -f "pom.xml" ]; then
    echo "❌ Erro: Execute este script no diretório meu-primeiro-jadex/"
    echo "💡 Comando: cd meu-primeiro-jadex && ./start-game.sh"
    exit 1
fi

# Verificar Java
echo "🔍 Verificando Java..."
if ! command -v java &> /dev/null; then
    echo "❌ Java não encontrado. Instale Java 17+"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | grep -oP 'version "([0-9]+)' | grep -oP '[0-9]+')
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Java 17+ necessário. Versão encontrada: $JAVA_VERSION"
    exit 1
fi
echo "✅ Java $JAVA_VERSION encontrado"

# Verificar Maven
echo "🔍 Verificando Maven..."
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não encontrado. Instale Apache Maven"
    exit 1
fi
echo "✅ Maven encontrado"

# Limpar processos anteriores
echo "🧹 Limpando processos anteriores..."
pkill -f "meu-primeiro-jadex" 2>/dev/null || true
pkill -f "spring-boot:run" 2>/dev/null || true

# Compilar projeto
echo "🔨 Compilando projeto..."
mvn clean compile -q
if [ $? -ne 0 ]; then
    echo "❌ Erro na compilação. Verifique o código."
    exit 1
fi
echo "✅ Compilação bem-sucedida"

# Iniciar aplicação
echo ""
echo "🚀 Iniciando aplicação..."
echo "⏳ Aguarde ~10 segundos para inicialização completa..."
echo ""
echo "🌐 URLs disponíveis após inicialização:"
echo "   📱 Homepage: http://localhost:8080"
echo "   🎮 Jogo:     http://localhost:8080/game"
echo "   🔍 Debug:    http://localhost:8080/diagnostic"
echo ""
echo "⚠️  Para parar: Ctrl+C"
echo "=====================================..."
echo ""

# Executar Spring Boot (método mais confiável)
mvn spring-boot:run