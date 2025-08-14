package github.williamjbf.primeiraaventura.table.repository;

import github.williamjbf.primeiraaventura.table.model.TableRPG;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<TableRPG, Long> {
}
