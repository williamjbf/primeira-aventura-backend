package github.williamjbf.primeiraaventura.table.subscription.service;

import github.williamjbf.primeiraaventura.table.dto.TableListItemDTO;
import github.williamjbf.primeiraaventura.table.dto.TableResponseDTO;
import github.williamjbf.primeiraaventura.table.model.TableRPG;
import github.williamjbf.primeiraaventura.table.repository.TableRepository;
import github.williamjbf.primeiraaventura.table.service.TableService;
import github.williamjbf.primeiraaventura.table.subscription.dto.SubscriptionRequestDTO;
import github.williamjbf.primeiraaventura.table.subscription.dto.SubscriptionResponseDTO;
import github.williamjbf.primeiraaventura.table.subscription.model.SubscriptionStatus;
import github.williamjbf.primeiraaventura.table.subscription.model.TableSubscription;
import github.williamjbf.primeiraaventura.table.subscription.repository.TableSubscriptionRepository;
import github.williamjbf.primeiraaventura.user.model.User;
import github.williamjbf.primeiraaventura.user.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TableSubscriptionService {

    private final TableSubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final TableService tableService;
    private final TableRepository tableRepository;

    public TableSubscriptionService(TableSubscriptionRepository subscriptionRepository, UserService userService, TableRepository tableRepository, TableService tableService, TableRepository tableRepository1) {
        this.subscriptionRepository = subscriptionRepository;
        this.userService = userService;
        this.tableService = tableService;
        this.tableRepository = tableRepository1;
    }

    public SubscriptionResponseDTO inscrever(SubscriptionRequestDTO dto) {
        User user = userService.findById(dto.getUserId());

        TableRPG table = tableRepository.findById(dto.getTableId())
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));

        boolean jaExiste = subscriptionRepository.existsByUserIdAndTableRPGId(user.getId(), table.getId());

        if (jaExiste) {
            throw new RuntimeException("Inscrição já existente para este usuário nesta mesa");
        }

        TableSubscription subscription = TableSubscription.builder()
                .user(user)
                .tableRPG(table)
                .status(SubscriptionStatus.PENDENTE)
                .build();

        TableSubscription saved = subscriptionRepository.save(subscription);

        return SubscriptionResponseDTO.builder()
                .id(saved.getId())
                .userId(saved.getUser().getId())
                .tableId(saved.getTableRPG().getId())
                .status(saved.getStatus())
                .build();
    }

    public List<TableListItemDTO> listarMesasInscritasPorStatus(Long userId, SubscriptionStatus status) {
        List<TableSubscription> subs = subscriptionRepository.findByUserIdAndStatus(userId, status);
        return subs.stream()
                .map(TableSubscription::getTableRPG)
                .map(t -> TableListItemDTO.builder()
                        .id(t.getId())
                        .titulo(t.getTitulo())
                        .build())
                .toList();
    }

    public SubscriptionResponseDTO atualizar(Long id, SubscriptionStatus status) {

        Optional<TableSubscription> optional = subscriptionRepository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("Inscrição não encontrada");
        }
        TableSubscription subscription = optional.get();
        subscription.setStatus(status);
        subscription.setUpdatedAt(LocalDateTime.now());

        TableSubscription saved = subscriptionRepository.save(subscription);

        return SubscriptionResponseDTO.builder()
                .id(saved.getId())
                .userId(saved.getUser().getId())
                .tableId(saved.getTableRPG().getId())
                .status(saved.getStatus())
                .build();
    }

}
