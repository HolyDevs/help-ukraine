package help.ukraine.app.unit.service;

import help.ukraine.app.repository.PendingRepository;
import help.ukraine.app.repository.PremiseOfferRepository;
import help.ukraine.app.repository.SearchingOfferRepository;
import help.ukraine.app.service.impl.PendingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "unittest")
public class PendingServiceTest {

    @MockBean
    private PremiseOfferRepository premiseOfferRepository;
    @MockBean
    private SearchingOfferRepository searchingOfferRepository;
    @MockBean
    private PendingRepository pendingRepository;

    @Autowired
    private PendingService pendingService;

    private void setUpMocks() {

    }

}
