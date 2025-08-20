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
    private Narrador narrador;
    private List<Tag> tags;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Narrador {
        private Long id;
        private String nome;
    }
}