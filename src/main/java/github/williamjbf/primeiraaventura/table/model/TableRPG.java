package github.williamjbf.primeiraaventura.table.model;

import github.williamjbf.primeiraaventura.tag.model.Tag;
import github.williamjbf.primeiraaventura.user.model.User;
import github.williamjbf.primeiraaventura.utils.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tables")
@Where(clause = "deleted_at IS NULL")
public class TableRPG extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String resumo;

    @Column(nullable = false)
    private String sistema;

    @Column
    private String imagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_narrador", nullable = false)
    private User narrador;

    @ManyToMany
    @JoinTable(
            name = "table_rpg_tags",
            joinColumns = @JoinColumn(name = "table_rpg_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    @Column(nullable = false)
    private String local;

    @Column(columnDefinition = "TEXT")
    private String horario;
}
