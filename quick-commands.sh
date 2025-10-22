#!/bin/bash

# 🎮 Script de Comandos Rápidos - Jogo da Cobrinha Jadex
# Uso: ./quick-commands.sh [comando]

PROJECT_DIR="meu-primeiro-jadex"
PORT=8080

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

show_help() {
    echo -e "${BLUE}🎮 COMANDOS DISPONÍVEIS:${NC}"
    echo ""
    echo -e "${GREEN}start${NC}     - Iniciar o jogo"
    echo -e "${GREEN}stop${NC}      - Parar o jogo"  
    echo -e "${GREEN}restart${NC}   - Reiniciar o jogo"
    echo -e "${GREEN}status${NC}    - Ver status da aplicação"
    echo -e "${GREEN}logs${NC}      - Ver logs em tempo real"
    echo -e "${GREEN}clean${NC}     - Limpar e compilar"
    echo -e "${GREEN}test${NC}      - Executar testes"
    echo -e "${GREEN}open${NC}      - Abrir jogo no browser"
    echo -e "${GREEN}help${NC}      - Mostrar esta ajuda"
    echo ""
    echo -e "${YELLOW}Exemplos:${NC}"
    echo "  ./quick-commands.sh start"
    echo "  ./quick-commands.sh status"
    echo "  ./quick-commands.sh logs"
}

start_game() {
    echo -e "${GREEN}🚀 Iniciando o jogo...${NC}"
    cd $PROJECT_DIR
    mvn spring-boot:run
}

stop_game() {
    echo -e "${RED}🛑 Parando o jogo...${NC}"
    pkill -f spring-boot:run
    echo -e "${GREEN}✅ Jogo parado!${NC}"
}

restart_game() {
    echo -e "${YELLOW}🔄 Reiniciando o jogo...${NC}"
    stop_game
    sleep 2
    start_game
}

show_status() {
    echo -e "${BLUE}📊 Status da Aplicação:${NC}"
    if curl -s http://localhost:$PORT/ > /dev/null; then
        echo -e "${GREEN}✅ Servidor rodando em http://localhost:$PORT${NC}"
        echo -e "${GREEN}🎮 Jogo disponível em http://localhost:$PORT/game${NC}"
    else
        echo -e "${RED}❌ Servidor não está rodando${NC}"
    fi
    
    echo ""
    echo -e "${BLUE}📈 Processos Java:${NC}"
    ps aux | grep java | grep -v grep || echo "Nenhum processo Java encontrado"
}

show_logs() {
    echo -e "${BLUE}📜 Logs da aplicação (Ctrl+C para sair):${NC}"
    cd $PROJECT_DIR
    mvn spring-boot:run | grep -E "(Started|GameManagerAgent|SnakeAgent|AppleAgent|ScoreAgent|ERROR|WARN)"
}

clean_build() {
    echo -e "${YELLOW}🧹 Limpando e compilando...${NC}"
    cd $PROJECT_DIR
    mvn clean compile
    echo -e "${GREEN}✅ Build concluído!${NC}"
}

run_tests() {
    echo -e "${BLUE}🧪 Executando testes...${NC}"
    cd $PROJECT_DIR
    mvn test
}

open_browser() {
    echo -e "${BLUE}🌐 Abrindo jogo no browser...${NC}"
    if command -v xdg-open > /dev/null; then
        xdg-open "http://localhost:$PORT/game"
    elif command -v open > /dev/null; then
        open "http://localhost:$PORT/game"  
    else
        echo -e "${YELLOW}⚠️ Abra manualmente: http://localhost:$PORT/game${NC}"
    fi
}

# Main script
case "$1" in
    start)
        start_game
        ;;
    stop)
        stop_game
        ;;
    restart)
        restart_game
        ;;
    status)
        show_status
        ;;
    logs)
        show_logs
        ;;
    clean)
        clean_build
        ;;
    test)
        run_tests
        ;;
    open)
        open_browser
        ;;
    help|--help|-h)
        show_help
        ;;
    *)
        echo -e "${RED}❌ Comando inválido: $1${NC}"
        echo ""
        show_help
        exit 1
        ;;
esac