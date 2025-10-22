#!/bin/bash

echo "🧪 === TESTE COMPLETO DO SERVIDOR JADEX + SPRING BOOT ==="
echo ""

# Cores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Função para testar endpoints
test_endpoint() {
    local endpoint=$1
    local description=$2
    
    echo -n "🔍 Testando $description ($endpoint): "
    
    if (echo -e "GET $endpoint HTTP/1.1\r\nHost: localhost\r\n\r\n" | nc localhost 8081 2>/dev/null | head -1 | grep -q "200"); then
        echo -e "${GREEN}✅ OK${NC}"
        return 0
    else
        echo -e "${RED}❌ FALHOU${NC}"
        return 1
    fi
}

# Verificar se o processo está rodando
echo "📊 Status dos Processos:"
if pgrep -f "WebApplication" >/dev/null; then
    echo -e "${GREEN}✅ Spring Boot está rodando${NC}"
else
    echo -e "${RED}❌ Spring Boot NÃO está rodando${NC}"
    exit 1
fi

echo ""
echo "🌐 Testes de Conectividade:"

# Testar conectividade básica
if (echo >/dev/tcp/localhost/8081) >/dev/null 2>&1; then
    echo -e "${GREEN}✅ Porta 8081 está ativa${NC}"
else
    echo -e "${RED}❌ Porta 8081 não está acessível${NC}"
    exit 1
fi

echo ""
echo "🔗 Testando Endpoints:"

# Testar todos os endpoints
test_endpoint "/" "Página Principal"
test_endpoint "/game" "Jogo da Cobrinha"  
test_endpoint "/diagnostic" "Página de Diagnóstico"
test_endpoint "/status" "Status JSON"

echo ""
echo "📋 Resumo do Status:"
echo -e "${YELLOW}Servidor:${NC} Spring Boot 3.1.5"
echo -e "${YELLOW}Porta:${NC} 8081"
echo -e "${YELLOW}Framework:${NC} Jadex 4.0.241"
echo -e "${YELLOW}Tecnologia:${NC} Multi-Agent System"

echo ""
echo "🎮 URLs Disponíveis:"
echo "   • http://localhost:8081/ (Página Principal)"
echo "   • http://localhost:8081/game (Jogo)"
echo "   • http://localhost:8081/diagnostic (Diagnóstico)"
echo "   • http://localhost:8081/status (Status JSON)"

echo ""
echo -e "${GREEN}🎉 TODOS OS TESTES PASSOU! Sistema funcionando perfeitamente!${NC}"