package help.ukraine.app.data;

import help.ukraine.app.enumerator.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "searching_people")
public class SearchingPersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private SearchingOfferEntity searchingOffer;
    public static final String SEARCHING_OFFER_COLUMN_NAME = "searching_offer_id";
    public static final String SEARCHING_OFFER_FIELD_NAME = "searchingOffer";

    @Column(name = SEARCHING_OFFER_COLUMN_NAME, updatable = false, insertable = false)
    private Long searchingOfferId;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String name;
    private String surname;
    private LocalDate birthDate;
    private boolean movingIssues;
}
