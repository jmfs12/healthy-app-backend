package com.backend.java.saude_e_bem_estar.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

@Schema(description = "Resposta padronizada de erro da API")
public class CustomErrorResponse {

    @Schema(description = "Timestamp do momento do erro", example = "2026-06-15T12:00:00Z")
    private Instant timestamp;
    @Schema(description = "Código de status HTTP correspondente", example = "404")
    private Integer status;
    @Schema(description = "Descrição curta do erro", example = "Recurso não encontrado")
    private String error;
    @Schema(description = "Mensagem detalhada contendo a causa do erro", example = "Usuário não encontrado.")
    private String message;
    @Schema(description = "Caminho da URI da requisição que causou o erro", example = "/usuarios/999")
    private String path;

    public CustomErrorResponse() {
    }

    public CustomErrorResponse(Instant timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    // Getters e Setters
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}