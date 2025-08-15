package github.williamjbf.primeiraaventura.table.dto;

import github.williamjbf.primeiraaventura.tag.model.Tag;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableRequestDTO {
    private String nome;
    private String resumo;
    private String sistema;
    private String imagem;
    private Long idNarrador;
    private Set<Long> idTags;
}