package github.williamjbf.primeiraaventura.table.subscription.dto;

import github.williamjbf.primeiraaventura.table.subscription.model.SubscriptionStatus;
import lombok.Data;

import java.util.List;

@Data
public class SubscriptionBulkUpdateRequestDTO {
    private List<Long> ids;
    private SubscriptionStatus status;
}
