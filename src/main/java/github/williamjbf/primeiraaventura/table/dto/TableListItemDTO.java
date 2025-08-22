package github.williamjbf.primeiraaventura.table.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TableListItemDTO {

    private Long id;
    private String titulo;
}
