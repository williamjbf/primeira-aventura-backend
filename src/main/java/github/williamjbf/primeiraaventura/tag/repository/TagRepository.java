package github.williamjbf.primeiraaventura.tag.repository;

import github.williamjbf.primeiraaventura.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByIdIn(List<Long> ids);

    boolean existsByNomeIgnoreCase(String nome);
}
