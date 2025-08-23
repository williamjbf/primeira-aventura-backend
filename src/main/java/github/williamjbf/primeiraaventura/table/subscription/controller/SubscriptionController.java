package github.williamjbf.primeiraaventura.table.subscription.controller;

import github.williamjbf.primeiraaventura.table.subscription.dto.SubscriptionRequestDTO;
import github.williamjbf.primeiraaventura.table.subscription.dto.SubscriptionResponseDTO;
import github.williamjbf.primeiraaventura.table.subscription.dto.SubscriptionUpdateRequestDTO;
import github.williamjbf.primeiraaventura.table.subscription.model.SubscriptionStatus;
import github.williamjbf.primeiraaventura.table.subscription.service.TableSubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/aceitar")
    public ResponseEntity<SubscriptionResponseDTO> aceitar(@RequestBody SubscriptionUpdateRequestDTO dto) {
        SubscriptionResponseDTO response = tableSubscriptionService.atualizar(dto.getId(), SubscriptionStatus.ACEITO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/negar")
    public ResponseEntity<SubscriptionResponseDTO> recusar(@RequestBody SubscriptionUpdateRequestDTO dto) {
        SubscriptionResponseDTO response = tableSubscriptionService.atualizar(dto.getId(), SubscriptionStatus.RECUSADO);
        return ResponseEntity.ok(response);
    }
}
