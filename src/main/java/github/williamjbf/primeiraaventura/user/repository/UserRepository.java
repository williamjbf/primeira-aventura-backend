package github.williamjbf.primeiraaventura.user.repository;

import github.williamjbf.primeiraaventura.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String username);

}
