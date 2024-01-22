package giovannilongo.U5S3L1220124.repositories;

import giovannilongo.U5S3L1220124.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
