package help.ukraine.app.repository;

import help.ukraine.app.data.SearchingOfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchingOfferRepository extends JpaRepository<SearchingOfferEntity, Long> {
    List<SearchingOfferEntity> findByRefugeeId(Long refugeeId);
}
