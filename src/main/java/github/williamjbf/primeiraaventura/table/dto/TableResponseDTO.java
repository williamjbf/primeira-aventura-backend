package github.williamjbf.primeiraaventura.table.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableResponseDTO {
    private Long id;
    private String nome;
    private String resumo;
    private String sistema;
    private String imagem;
    private String narrador;
    private List<String> tags;
}