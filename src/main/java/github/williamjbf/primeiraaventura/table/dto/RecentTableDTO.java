package github.williamjbf.primeiraaventura.table.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecentTableDTO {
    private Long id;
    private String imagem;
    private String titulo;
    private String sistema;
    private String mestreNome;
    private LocalDateTime createdAt;
}
