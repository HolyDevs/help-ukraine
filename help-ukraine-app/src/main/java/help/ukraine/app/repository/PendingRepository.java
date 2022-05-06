package help.ukraine.app.repository;

import help.ukraine.app.data.PendingEntity;
import help.ukraine.app.data.SearchingPremiseOfferId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PendingRepository extends JpaRepository<PendingEntity, SearchingPremiseOfferId> {

    void deleteBySearchingPremiseOfferId(SearchingPremiseOfferId searchingPremiseOfferId);

    @Query("SELECT p FROM PendingEntity  p WHERE p.searchingPremiseOfferId.searchingOffer.id = :searchingOfferId")
    List<PendingEntity> getAllBySearchingOfferId(@Param("searchingOfferId") Long searchingOfferId);

    @Query("SELECT p FROM PendingEntity  p WHERE p.searchingPremiseOfferId.premiseOffer.id = :premiseOfferId")
    List<PendingEntity> getAllByPremiseOfferId(@Param("premiseOfferId") Long premiseOfferId);
}
