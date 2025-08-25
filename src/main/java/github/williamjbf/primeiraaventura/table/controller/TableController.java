package github.williamjbf.primeiraaventura.table.controller;

import github.williamjbf.primeiraaventura.table.dto.*;
import github.williamjbf.primeiraaventura.table.service.TableService;
import github.williamjbf.primeiraaventura.table.subscription.dto.TableSubscriptionsGroupedResponseDTO;
import github.williamjbf.primeiraaventura.table.subscription.model.SubscriptionStatus;
import github.williamjbf.primeiraaventura.table.subscription.service.TableSubscriptionService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    private final TableService tableService;
    private final TableSubscriptionService tableSubscriptionService;

    public TableController(TableService tableService, TableSubscriptionService tableSubscriptionService) {
        this.tableService = tableService;
        this.tableSubscriptionService = tableSubscriptionService;
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
        return tableService.buscarMesasPorFiltroAnd(filtro);
    }

    @PostMapping("/buscar-avancado")
    public List<TableResponseDTO> buscarMesasAvancado(@RequestBody TableFilterDTO filtro) {
        return tableService.buscarMesasPorFiltroOr(filtro);
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

    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<TableSubscriptionsGroupedResponseDTO> listarInscricoesAgrupadas(@PathVariable("id") Long tableId) {
        TableSubscriptionsGroupedResponseDTO response = tableSubscriptionService.listarInscricoesDaMesaAgrupadas(tableId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/participate/{userId}")
    public List<TableListItemDTO> listarMesasQueUsuarioParticipa(@PathVariable Long userId) {
        return tableSubscriptionService.listarMesasInscritasPorStatus(userId, SubscriptionStatus.ACEITO);
    }

    @GetMapping("/owner/{userId}")
    public List<TableListItemDTO> listarMesasPorDono(@PathVariable Long userId) {
        return tableService.listarMesasPorNarrador(userId);
    }

    @GetMapping("/pending/{userId}")
    public List<TableListItemDTO> listarMesasPendentes(@PathVariable Long userId){
        return tableSubscriptionService.listarMesasInscritasPorStatus(userId, SubscriptionStatus.PENDENTE);
    }

    @GetMapping("/denied/{userId}")
    public List<TableListItemDTO> listarMesasNegas(@PathVariable Long userId){
        return tableSubscriptionService.listarMesasInscritasPorStatus(userId, SubscriptionStatus.RECUSADO);
    }

}
