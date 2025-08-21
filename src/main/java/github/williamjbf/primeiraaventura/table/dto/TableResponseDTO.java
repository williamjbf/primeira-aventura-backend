package github.williamjbf.primeiraaventura.table.dto;

import github.williamjbf.primeiraaventura.tag.model.Tag;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableResponseDTO {
    private Long id;
    private String titulo;
    private String resumo;
    private String sistema;
    private String imagem;
    private String local;
    private String horario;
    private Narrador narrador;
    private List<TagDTO> tags;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Narrador {
        private Long id;
        private String nome;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class TagDTO {
        private Long id;
        private String nome;
    }
}