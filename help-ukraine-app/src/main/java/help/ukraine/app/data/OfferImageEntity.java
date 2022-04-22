package help.ukraine.app.data;


import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "offer_images")
public class OfferImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imageLocation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PremiseOfferEntity premiseOffer;
    public static final String PREMISE_OFFER_FIELD_NAME = "premiseOffer";
}
