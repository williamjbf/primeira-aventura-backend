package github.williamjbf.primeiraaventura.table.subscription.repository;

import github.williamjbf.primeiraaventura.table.subscription.model.SubscriptionStatus;
import github.williamjbf.primeiraaventura.table.subscription.model.TableSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableSubscriptionRepository extends JpaRepository<TableSubscription, Long> {

    boolean existsByUserIdAndTableRPGId(Long userId, Long tableId);

    List<TableSubscription> findByUserIdAndStatus(Long userId, SubscriptionStatus status);

    @Query("select s from TableSubscription s join fetch s.user u where s.tableRPG.id = :tableId")
    List<TableSubscription> findAllByTableIdFetchUser(@Param("tableId") Long tableId);
}
