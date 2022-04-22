package help.ukraine.app.repository;

import help.ukraine.app.data.SearchingPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchingPersonRepository extends JpaRepository<SearchingPersonEntity, Long> {
}
