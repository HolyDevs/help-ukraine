package help.ukraine.app.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchingAndPremiseOfferId implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    private SearchingOfferEntity searchingOffer;
    @ManyToOne(fetch = FetchType.LAZY)
    private PremiseOfferEntity premiseOffer;
}
