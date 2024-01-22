package giovannilongo.U5S3L1220124.repositories;

import giovannilongo.U5S3L1220124.entities.Device;
import giovannilongo.U5S3L1220124.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DevicesRepository extends JpaRepository<Device, Long> {
    List<Device> findByUser(User user);
}
