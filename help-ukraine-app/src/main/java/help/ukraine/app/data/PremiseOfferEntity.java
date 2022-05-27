package help.ukraine.app.data;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "premise_offers")
public class PremiseOfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private HostEntity host;
    public static final String HOST_ID_COLUMN_NAME = "host_user_id";
    public static final String HOST_ID_FIELD_NAME = "host";

    @Column(name = HOST_ID_COLUMN_NAME, updatable = false, insertable = false)
    private Long hostId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = OfferImageEntity.PREMISE_OFFER_FIELD_NAME)
    @Fetch(FetchMode.SUBSELECT)
    private List<OfferImageEntity> offerImages;

    private String city;
    private String street;
    private String houseNumber;
    private String postalCode;
    private int peopleToTake;
    public static final String PEOPLE_TO_TAKE_FIELD_NAME = "peopleToTake";
    private int bathrooms;
    private int kitchens;
    private int bedrooms;
    private boolean animalsAllowed;
    public static final String ANIMALS_ALLOWED_FIELD_NAME = "animalsAllowed";
    private boolean wheelchairFriendly;
    public static final String WHEELCHAIR_FRIENDLY_FIELD_NAME = "wheelchairFriendly";
    private boolean smokingAllowed;
    private LocalDate fromDate;
    public static final String TO_DATE_FIELD_NAME = "toDate";
    private LocalDate toDate;
    public static final String ACTIVE_FIELD_NAME = "active";
    private boolean active;
    private boolean verified;
    private String description;
}
