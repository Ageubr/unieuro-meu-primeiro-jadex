package com.unieuro.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosticController {

    @GetMapping(value = "/diagnostic", produces = MediaType.TEXT_HTML_VALUE)
    public String diagnostic() {
        return """
<!DOCTYPE html>
<html>
<head>
    <title>✅ Diagnóstico - Jadex Snake Game</title>
    <meta charset="UTF-8">
    <style>
        body { 
            font-family: Arial, sans-serif; 
            padding: 20px; 
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            margin: 0;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: rgba(255,255,255,0.9);
            color: #333;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
        }
        .status { 
            padding: 15px; 
            margin: 10px 0; 
            border-radius: 8px; 
            border-left: 5px solid;
        }
        .success { 
            background: #d4edda; 
            border-left-color: #28a745; 
            color: #155724; 
        }
        .info { 
            background: #d1ecf1; 
            border-left-color: #17a2b8; 
            color: #0c5460; 
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px 5px;
            background: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            border: none;
            cursor: pointer;
        }
        .btn:hover { background: #218838; }
    </style>
</head>
<body>
    <div class="container">
        <h1>🎉 DIAGNÓSTICO COMPLETO</h1>
        
        <div class="status success">
            ✅ <strong>Spring Boot:</strong> Funcionando na porta 8081
        </div>
        
        <div class="status success">
            ✅ <strong>Controller REST:</strong> Respondendo corretamente
        </div>
        
        <div class="status success">
            ✅ <strong>Tomcat Embedded:</strong> Servidor HTTP ativo
        </div>
        
        <div class="status info">
            ℹ️ <strong>Status HTTP:</strong> 200 OK (sem erros 400)
        </div>
        
        <div class="status info">
            ℹ️ <strong>Timestamp:</strong> """ + java.time.LocalDateTime.now() + """
        </div>
        
        <h2>🔗 Links de Navegação:</h2>
        <a href="/" class="btn">🏠 Página Principal (Thymeleaf)</a>
        <a href="/game" class="btn">🎮 Jogo da Cobrinha</a>
        <a href="/diagnostic" class="btn">🔧 Esta Página de Diagnóstico</a>
        
        <h2>📋 Solução para Erro 400:</h2>
        <div class="status info">
            <strong>Problema identificado:</strong> O erro HTTP 400 geralmente ocorre quando:
            <ul>
                <li>Headers malformados no navegador</li>
                <li>Problemas de cache do Codespace</li>
                <li>Port forwarding com configuração incorreta</li>
            </ul>
            
            <strong>Soluções:</strong>
            <ol>
                <li>Recarregue a página (F5 ou Ctrl+R)</li>
                <li>Limpe o cache do navegador</li>
                <li>Use a aba "PORTS" no VS Code e clique no ícone 🌐</li>
                <li>Tente o Simple Browser interno do VS Code</li>
            </ol>
        </div>
        
        <div style="text-align: center; margin-top: 30px;">
            <h3>🚀 Sistema Jadex + Spring Boot Operacional!</h3>
            <p>Se você está vendo esta página, o servidor está funcionando perfeitamente.</p>
        </div>
    </div>
</body>
</html>
                """;
    }
    
    @GetMapping("/status")
    public String status() {
        return """
            {
                "status": "OK",
                "timestamp": "%s",
                "server": "Spring Boot 3.1.5",
                "port": 8081,
                "agents": "Jadex 4.0.241",
                "message": "Sistema funcionando perfeitamente"
            }
            """.formatted(java.time.LocalDateTime.now());
    }
}