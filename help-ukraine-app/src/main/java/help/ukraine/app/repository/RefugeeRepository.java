package help.ukraine.app.repository;

import help.ukraine.app.data.RefugeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefugeeRepository extends JpaRepository<RefugeeEntity, Long> {
}
