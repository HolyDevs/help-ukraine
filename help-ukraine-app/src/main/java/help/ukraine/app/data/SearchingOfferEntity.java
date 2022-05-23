package help.ukraine.app.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "searching_offers")
public class SearchingOfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private RefugeeEntity refugee;
    public static final String REFUGEE_ID_COLUMN_NAME = "refugee_user_id";
    public static final String REFUGEE_ID_FIELD_NAME = "refugee";

    @Column(name = REFUGEE_ID_COLUMN_NAME, updatable = false, insertable = false)
    private Long refugeeId;

    @Column(columnDefinition = "TEXT")
    private String additionalInfo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = SearchingPersonEntity.SEARCHING_OFFER_FIELD_NAME)
    private List<SearchingPersonEntity> searchingPeople;

    private String preferredLocation;

    @Column(columnDefinition = "DOUBLE PRECISION")
    private Double rangeFromPreferredLocationInKm;

    private Boolean animalsInvolved;

    private Boolean userMovingIssues;
}
