package help.ukraine.app.repository;

import help.ukraine.app.data.AcceptedEntity;
import help.ukraine.app.data.SearchingPremiseOfferId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AcceptedRepository extends JpaRepository<AcceptedEntity, SearchingPremiseOfferId> {

    void deleteBySearchingPremiseOfferId(SearchingPremiseOfferId searchingPremiseOfferId);

    @Query("SELECT a FROM AcceptedEntity a WHERE a.searchingPremiseOfferId.searchingOffer.id = :searchingOfferId")
    List<AcceptedEntity> getAllBySearchingOfferId(@Param("searchingOfferId") Long searchingOfferId);

    @Query("SELECT a FROM AcceptedEntity a WHERE a.searchingPremiseOfferId.premiseOffer.id = :premiseOfferId")
    List<AcceptedEntity> getAllByPremiseOfferId(@Param("premiseOfferId") Long premiseOfferId);
}
