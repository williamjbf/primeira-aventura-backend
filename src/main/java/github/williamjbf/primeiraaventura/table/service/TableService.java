package github.williamjbf.primeiraaventura.table.service;

import github.williamjbf.primeiraaventura.table.dto.RecentTableDTO;
import github.williamjbf.primeiraaventura.table.dto.TableFilterDTO;
import github.williamjbf.primeiraaventura.table.dto.TableRequestDTO;
import github.williamjbf.primeiraaventura.table.dto.TableResponseDTO;
import github.williamjbf.primeiraaventura.table.model.TableRPG;
import github.williamjbf.primeiraaventura.table.repository.TableRepository;
import github.williamjbf.primeiraaventura.table.specification.TableSpecification;
import github.williamjbf.primeiraaventura.tag.model.Tag;
import github.williamjbf.primeiraaventura.tag.service.TagService;
import github.williamjbf.primeiraaventura.user.model.User;
import github.williamjbf.primeiraaventura.user.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    private final TableRepository tableRepository;
    private final UserService userService;
    private final TagService tagService;

    public TableService(TableRepository tableRepository, UserService userService, TagService tagService) {
        this.tableRepository = tableRepository;
        this.userService = userService;
        this.tagService = tagService;
    }

    public TableResponseDTO criarMesa(TableRequestDTO dto) {

        User user = userService.findById(dto.getIdNarrador());

        List<Tag> tags = tagService.getTagsByIds(dto.getIdTags().stream().toList());

        TableRPG mesa = TableRPG.builder()
                .name(dto.getNome())
                .summary(dto.getResumo())
                .system(dto.getSistema())
                .image(dto.getImagem())
                .gameMaster(user)
                .tags(tags)
                .build();

        TableRPG salva = tableRepository.save(mesa);

        return TableResponseDTO.builder()
                .id(salva.getId())
                .titulo(salva.getName())
                .resumo(salva.getSummary())
                .sistema(salva.getSystem())
                .imagem(salva.getImage())
                .narrador(new TableResponseDTO.Narrador(
                        salva.getGameMaster().getId(),
                        salva.getGameMaster().getUsername()
                ))
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

    public List<TableResponseDTO> buscarMesasPorFiltro(TableFilterDTO filtro) {
        Specification<TableRPG> spec = Specification
                .where(TableSpecification.tituloContem(filtro.getTitulo()))
                .and(TableSpecification.sistemaIgual(filtro.getSistema()))
                .and(TableSpecification.tagsContem(filtro.getTags()))
                .and(TableSpecification.usuarioIgual(filtro.getUsuario()));

        List<TableResponseDTO> list = tableRepository.findAll(spec).stream().map(
                t -> new TableResponseDTO(
                        t.getId(),
                        t.getName(),
                        t.getSummary(),
                        t.getSystem(),
                        t.getImage(),
                        new TableResponseDTO.Narrador(
                                t.getGameMaster().getId(),
                                t.getGameMaster().getUsername()
                        ),
                        t.getTags()
                )
        ).toList();

        return list;
    }

    public TableResponseDTO buscarMesaPorId(Long id) {

        Optional<TableRPG> optinalTable = tableRepository.findById(id);

        if (optinalTable.isEmpty()) {
            throw new RuntimeException("Mesa não encontrada");
        }

        TableRPG table = optinalTable.get();

        return new TableResponseDTO(
                table.getId(),
                table.getName(),
                table.getSummary(),
                table.getSystem(),
                table.getImage(),
                new TableResponseDTO.Narrador(
                        table.getGameMaster().getId(),
                        table.getGameMaster().getUsername()
                ),
                table.getTags()
        );
    }
}
