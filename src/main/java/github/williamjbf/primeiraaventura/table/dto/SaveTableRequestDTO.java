package github.williamjbf.primeiraaventura.table.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SaveTableRequestDTO {
    private Long id;
    private String titulo;
    private String resumo;
    private String sistema;
    private Long narradorId;
    private String tags; // vai vir como JSON string, depois parseamos
    private String local;
    private String horario; // idem JSON string

    private MultipartFile imagem;
}
