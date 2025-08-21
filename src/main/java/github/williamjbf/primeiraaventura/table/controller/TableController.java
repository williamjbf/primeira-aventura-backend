package github.williamjbf.primeiraaventura.table.controller;

import github.williamjbf.primeiraaventura.table.dto.*;
import github.williamjbf.primeiraaventura.table.model.TableRPG;
import github.williamjbf.primeiraaventura.table.service.TableService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping
    public ResponseEntity<TableResponseDTO> criarMesa(@Valid @RequestBody TableRequestDTO dto) {
        return ResponseEntity.ok(tableService.criarMesa(dto));
    }

    @GetMapping("/recentes")
    public ResponseEntity<List<RecentTableDTO>> getMesasRecentes() {
        return ResponseEntity.ok(tableService.buscarMesasRecentes());
    }

    @PostMapping("/buscar")
    public List<TableResponseDTO> buscarMesas(@RequestBody TableFilterDTO filtro) {
        return tableService.buscarMesasPorFiltro(filtro);
    }

    @GetMapping("/{id}")
    public TableResponseDTO buscarMesaPorId(@PathVariable Long id) {
        return tableService.buscarMesaPorId(id);
    }

    @PostMapping(
            value = "/save",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<TableResponseDTO
            > saveTable(
            @ModelAttribute SaveTableRequestDTO saveTableRequestDTO
            ) {

        TableResponseDTO tableResponseDTO = tableService.saveTable(saveTableRequestDTO);
        return ResponseEntity.ok(tableResponseDTO);
    }
}
