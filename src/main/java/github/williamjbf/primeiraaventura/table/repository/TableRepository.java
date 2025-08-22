package github.williamjbf.primeiraaventura.table.repository;

import github.williamjbf.primeiraaventura.table.model.TableRPG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TableRepository extends JpaRepository<TableRPG, Long>, JpaSpecificationExecutor<TableRPG> {

    @Query("""
            SELECT t FROM TableRPG t
            WHERE t.deletedAt IS NULL 
            ORDER BY t.createdAt DESC
            LIMIT 16
            """)
    List<TableRPG> findTop16byOrderByCreatedAtDesc();

    List<TableRPG> findByNarradorId(Long userId);
}
