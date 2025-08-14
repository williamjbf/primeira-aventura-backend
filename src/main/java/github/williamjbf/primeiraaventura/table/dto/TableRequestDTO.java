package github.williamjbf.primeiraaventura.table.dto;

import lombok.*;

import java.util.List;

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
    private NarradorRequest narrador;
    private List<String> tags;

    @Setter
    @Getter
    public class NarradorRequest {
        private Long id;
    }
}