package github.williamjbf.primeiraaventura.table.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableFilterDTO {

    private String titulo;
    private String sistema;
    private List<String> tags;
    private String usuario;
}
