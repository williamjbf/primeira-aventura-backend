package github.williamjbf.primeiraaventura.table.subscription.repository;

import github.williamjbf.primeiraaventura.table.subscription.model.TableSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableSubscriptionRepository extends JpaRepository<TableSubscription, Long> {

    boolean existsByUser_IdAndTableRPG_Id(Long userId, Long tableId);
}
