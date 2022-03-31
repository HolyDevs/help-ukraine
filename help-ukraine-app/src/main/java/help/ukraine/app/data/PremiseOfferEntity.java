package help.ukraine.app.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    private String premiseAddress;
    private int peopleToTake;
    private int bathrooms;
    private int kitchens;
    private int bedrooms;
    private boolean animalsAllowed;
    private boolean wheelchairFriendly;
    private Date fromDate;
    private Date toDate;
    private boolean active;
    private boolean verified;
    private String description;
}
