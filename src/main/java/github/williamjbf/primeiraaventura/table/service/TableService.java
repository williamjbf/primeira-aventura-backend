package github.williamjbf.primeiraaventura.table.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.williamjbf.primeiraaventura.table.dto.*;
import github.williamjbf.primeiraaventura.table.model.TableRPG;
import github.williamjbf.primeiraaventura.table.repository.TableRepository;
import github.williamjbf.primeiraaventura.table.specification.TableSpecification;
import github.williamjbf.primeiraaventura.tag.model.Tag;
import github.williamjbf.primeiraaventura.tag.service.TagService;
import github.williamjbf.primeiraaventura.user.model.User;
import github.williamjbf.primeiraaventura.user.service.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TableService {

    private final TableRepository tableRepository;
    private final UserService userService;
    private final TagService tagService;
    private final ObjectMapper objectMapper;

    public TableService(TableRepository tableRepository, UserService userService, TagService tagService, ObjectMapper objectMapper) {
        this.tableRepository = tableRepository;
        this.userService = userService;
        this.tagService = tagService;
        this.objectMapper = objectMapper;
    }

    public TableResponseDTO criarMesa(TableRequestDTO dto) {

        User user = userService.findById(dto.getNarradorId());

        List<Tag> tags = tagService.getTagsByIds(dto.getTags().stream().toList());

        TableRPG mesa = TableRPG.builder()
                .titulo(dto.getTitulo())
                .resumo(dto.getResumo())
                .sistema(dto.getSistema())
                .imagem(dto.getImagem())
                .narrador(user)
                .tags(tags)
                .build();

        TableRPG salva = tableRepository.save(mesa);

        return this.toResponseDTO(salva);
    }

    public List<RecentTableDTO> buscarMesasRecentes() {
        return tableRepository.findTop16byOrderByCreatedAtDesc()
                .stream()
                .map(m -> new RecentTableDTO(
                        m.getId(),
                        m.getImagem(),
                        m.getTitulo(),
                        m.getSistema(),
                        m.getNarrador().getUsername(),
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
                this::toResponseDTO).toList();

        return list;
    }

    public TableResponseDTO buscarMesaPorId(Long id) {

        Optional<TableRPG> optinalTable = tableRepository.findById(id);

        if (optinalTable.isEmpty()) {
            throw new RuntimeException("Mesa não encontrada");
        }

        TableRPG table = optinalTable.get();

        return this.toResponseDTO(table);
    }

    public TableResponseDTO saveTable(SaveTableRequestDTO request) {
        TableRPG table = Optional.ofNullable(request.getId())
                .flatMap(tableRepository::findById)
                .orElse(new TableRPG());

        table.setTitulo(request.getTitulo());
        table.setResumo(request.getResumo());
        table.setSistema(request.getSistema());
        table.setLocal(request.getLocal());
        table.setHorario(request.getHorario());

        // Narrador
        User narrador = userService.findById(request.getNarradorId());
        table.setNarrador(narrador);

        // Tags
        try {
            List<Long> tagIds = objectMapper.readValue(request.getTags(), new TypeReference<>() {});
            List<Tag> tags = tagService.getTagsByIds(tagIds);
            table.setTags(tags);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao parsear tags", e);
        }

        // Imagem
        if (request.getImagem() != null && !request.getImagem().isEmpty()) {
            String fileName = UUID.randomUUID() + ".jpg";
            String uploadDir = "uploads/";
            File outputFile = new File(uploadDir + fileName);
            outputFile.getParentFile().mkdirs();

            compressAndSaveImage(request.getImagem(), outputFile);
            table.setImagem("/uploads/" + fileName); // URL acessível
        }

        if(request.getId() == null && (request.getImagem() == null || request.getImagem().isEmpty())) {
            table.setImagem("/uploads/default.png");
        }

        TableRPG save = tableRepository.save(table);
        return this.toResponseDTO(save);
    }

    public TableResponseDTO toResponseDTO(TableRPG table){
        return TableResponseDTO.builder()
                .id(table.getId())
                .titulo(table.getTitulo())
                .resumo(table.getResumo())
                .sistema(table.getSistema())
                .imagem(table.getImagem())
                .local(table.getLocal())
                .horario(table.getHorario())
                .narrador(new TableResponseDTO.Narrador(
                        table.getNarrador().getId(),
                        table.getNarrador().getUsername() // ou getNome()
                ))
                .tags(table.getTags().stream()
                        .map(tag -> new TableResponseDTO.TagDTO(tag.getId(), tag.getNome()))
                        .toList()
                )
                .build();
    }

    private void compressAndSaveImage(MultipartFile file, File outputFile) {
        try {
            // Lê a imagem original
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            if (originalImage == null) {
                throw new RuntimeException("Formato de imagem não suportado");
            }

            // Converte sempre para RGB (remove transparência se existir)
            BufferedImage rgbImage = new BufferedImage(
                    originalImage.getWidth(),
                    originalImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );

            Graphics2D g = rgbImage.createGraphics();
            // Fundo branco caso a imagem original tenha transparência
            g.drawImage(originalImage, 0, 0, Color.WHITE, null);
            g.dispose();

            // Salva como JPG com compressão
            try (FileOutputStream fos = new FileOutputStream(outputFile);
                 ImageOutputStream ios = ImageIO.createImageOutputStream(fos)) {

                ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
                writer.setOutput(ios);

                ImageWriteParam param = writer.getDefaultWriteParam();
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.7f); // 70% qualidade

                writer.write(null, new IIOImage(rgbImage, null, null), param);
                writer.dispose();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar imagem", e);
        }
    }

}
