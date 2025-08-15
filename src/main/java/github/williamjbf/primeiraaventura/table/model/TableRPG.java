package github.williamjbf.primeiraaventura.table.model;

import github.williamjbf.primeiraaventura.tag.model.Tag;
import github.williamjbf.primeiraaventura.user.model.User;
import github.williamjbf.primeiraaventura.utils.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private String name;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(nullable = false)
    private String system;

    @Column
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_game_master", nullable = false)
    private User gameMaster;

    @ManyToMany
    @JoinTable(
            name = "table_rpg_tags",
            joinColumns = @JoinColumn(name = "table_rpg_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

}
