package github.williamjbf.primeiraaventura.table.subscription.controller;

import github.williamjbf.primeiraaventura.table.subscription.dto.SubscriptionBulkUpdateRequestDTO;
import github.williamjbf.primeiraaventura.table.subscription.dto.SubscriptionRequestDTO;
import github.williamjbf.primeiraaventura.table.subscription.dto.SubscriptionResponseDTO;
import github.williamjbf.primeiraaventura.table.subscription.dto.SubscriptionUpdateRequestDTO;
import github.williamjbf.primeiraaventura.table.subscription.model.SubscriptionStatus;
import github.williamjbf.primeiraaventura.table.subscription.service.TableSubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables/subscribe")
public class SubscriptionController {

    private final TableSubscriptionService tableSubscriptionService;

    public SubscriptionController(TableSubscriptionService tableSubscriptionService) {
        this.tableSubscriptionService = tableSubscriptionService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> inscrever(@RequestBody SubscriptionRequestDTO dto) {
        SubscriptionResponseDTO response = tableSubscriptionService.inscrever(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<SubscriptionResponseDTO> aceitar(@PathVariable Long id) {
        SubscriptionResponseDTO response = tableSubscriptionService.atualizar(id, SubscriptionStatus.ACEITO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/deny")
    public ResponseEntity<SubscriptionResponseDTO> recusar(@PathVariable Long id) {
        SubscriptionResponseDTO response = tableSubscriptionService.atualizar(id, SubscriptionStatus.RECUSADO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/status/batch")
    public ResponseEntity<List<SubscriptionResponseDTO>> atualizarStatusEmLote(@RequestBody SubscriptionBulkUpdateRequestDTO dto) {
        List<SubscriptionResponseDTO> resposta = tableSubscriptionService.atualizarEmLote(dto.getIds(), dto.getStatus());
        return ResponseEntity.ok(resposta);
    }
}
