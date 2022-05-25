package help.ukraine.app.repository;

import help.ukraine.app.data.PremiseOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PremiseOfferRepository extends JpaRepository<PremiseOfferEntity, Long> {
    List<PremiseOfferEntity> findByHostId(Long hostId);
    List<PremiseOfferEntity> findByHostUserEmail(String email);
}
