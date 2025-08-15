package github.williamjbf.primeiraaventura.table.service;

import github.williamjbf.primeiraaventura.table.dto.RecentTableDTO;
import github.williamjbf.primeiraaventura.table.dto.TableRequestDTO;
import github.williamjbf.primeiraaventura.table.dto.TableResponseDTO;
import github.williamjbf.primeiraaventura.table.model.TableRPG;
import github.williamjbf.primeiraaventura.table.repository.TableRepository;
import github.williamjbf.primeiraaventura.user.model.User;
import github.williamjbf.primeiraaventura.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    private final TableRepository tableRepository;
    private final UserService userService;

    public TableService(TableRepository tableRepository, UserService userService) {
        this.tableRepository = tableRepository;
        this.userService = userService;
    }

    public TableResponseDTO criarMesa(TableRequestDTO dto) {

        User user = userService.findById(dto.getNarrador().getId());

        TableRPG mesa = TableRPG.builder()
                .name(dto.getNome())
                .summary(dto.getResumo())
                .system(dto.getSistema())
                .image(dto.getImagem())
                .gameMaster(user)
                .tags(dto.getTags())
                .build();

        TableRPG salva = tableRepository.save(mesa);

        return TableResponseDTO.builder()
                .id(salva.getId())
                .nome(salva.getName())
                .resumo(salva.getSummary())
                .sistema(salva.getSystem())
                .imagem(salva.getImage())
                .narrador(salva.getGameMaster().getUsername())
                .tags(salva.getTags())
                .build();
    }

    public List<RecentTableDTO> buscarMesasRecentes() {
        return tableRepository.findTop16byOrderByCreatedAtDesc()
                .stream()
                .map(m -> new RecentTableDTO(
                        m.getId(),
                        m.getImage(),
                        m.getName(),
                        m.getSystem(),
                        m.getGameMaster().getUsername(),
                        m.getCreatedAt()
                ))
                .toList();
    }

}
