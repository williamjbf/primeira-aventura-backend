package github.williamjbf.primeiraaventura.table.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class TableSubscriptionsGroupedResponseDTO {
    private List<SubscriptionUserSummaryDTO> accepted;
    private List<SubscriptionUserSummaryDTO> denied;
    private List<SubscriptionUserSummaryDTO> pending;
}

