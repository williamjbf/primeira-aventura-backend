package github.williamjbf.primeiraaventura.user.dto;

import github.williamjbf.primeiraaventura.table.dto.TableListItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AllSubscriptionsUser {

    private List<TableListItemDTO> acceptedList;
    private List<TableListItemDTO> pendingList;
    private List<TableListItemDTO> deniedList;

}
