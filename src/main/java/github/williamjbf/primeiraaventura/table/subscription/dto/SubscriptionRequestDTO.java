package github.williamjbf.primeiraaventura.table.subscription.dto;

import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class SubscriptionRequestDTO {
    @NotNull(message = "O id do usuario não pode ser nulo")
    private Long userId;

    @NotNull(message = "O id da mesa não pode ser nulo")
    private Long tableId;
}
