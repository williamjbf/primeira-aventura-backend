package github.williamjbf.primeiraaventura.table.subscription.dto;

import github.williamjbf.primeiraaventura.table.subscription.model.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SubscriptionResponseDTO {
    private Long id;
    private Long userId;
    private Long tableId;
    private SubscriptionStatus status;
}
