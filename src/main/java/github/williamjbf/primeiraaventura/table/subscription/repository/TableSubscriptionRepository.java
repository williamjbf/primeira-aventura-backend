package github.williamjbf.primeiraaventura.table.subscription.repository;

import github.williamjbf.primeiraaventura.table.subscription.model.SubscriptionStatus;
import github.williamjbf.primeiraaventura.table.subscription.model.TableSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableSubscriptionRepository extends JpaRepository<TableSubscription, Long> {

    boolean existsByUserIdAndTableRPGId(Long userId, Long tableId);

    List<TableSubscription> findByUserIdAndStatus(Long userId, SubscriptionStatus status);

}
