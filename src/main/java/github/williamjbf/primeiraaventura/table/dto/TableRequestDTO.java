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
    private Long id;
    private String titulo;
    private String resumo;
    private String sistema;
    private String imagem;
    private Long narradorId;
    private Set<Long> tags;
    private String local;
    private Horario horario;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Horario {
        private String dia;
        private String hora;
    }
}