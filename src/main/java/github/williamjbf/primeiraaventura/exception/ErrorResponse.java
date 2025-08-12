package github.williamjbf.primeiraaventura.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {

    private int status; // Código HTTP
    private String error; // Texto curto do erro (ex: "Bad Request")
    private String path; // Endpoint que gerou o erro

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime timestamp; // Quando ocorreu
    private Map<String, String> mensagem; // Mapa de erros por campo

    public ErrorResponse(int status, String error, String path, Map<String, String> mensagem) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = LocalDateTime.now();
        this.mensagem = mensagem;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getMensagem() {
        return mensagem;
    }
}
