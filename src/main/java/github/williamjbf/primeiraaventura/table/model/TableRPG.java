package github.williamjbf.primeiraaventura.table.model;

import github.williamjbf.primeiraaventura.user.model.User;
import github.williamjbf.primeiraaventura.utils.AuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

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

    @Column
    private List<String> tags;
}
