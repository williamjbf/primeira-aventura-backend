package github.williamjbf.primeiraaventura.table.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SubscriptionUserSummaryDTO {
    private Long id;       // id da inscrição (requisição)
    private Long userId;   // id do usuário
    private String username; // username do usuário
}

