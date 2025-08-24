package github.williamjbf.primeiraaventura.user.dto;

import github.williamjbf.primeiraaventura.table.dto.TableListItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private AllSubscriptionsUser subscriptions;
    private List<TableListItemDTO> ownedTables;
}
