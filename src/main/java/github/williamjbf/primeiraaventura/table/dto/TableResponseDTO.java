package github.williamjbf.primeiraaventura.table.dto;

import github.williamjbf.primeiraaventura.tag.model.Tag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableResponseDTO {
    private Long id;
    private String titulo;
    private String resumo;
    private String sistema;
    private String imagem;
    private String narrador;
    private List<Tag> tags;
}