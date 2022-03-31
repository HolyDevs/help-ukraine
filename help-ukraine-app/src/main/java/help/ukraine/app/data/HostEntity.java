package help.ukraine.app.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "hosts")
public class HostEntity implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private UserEntity user;
    private static final String USER_ID_COLUMN_NAME = "user_id";

    @Column(name = USER_ID_COLUMN_NAME, updatable = false, insertable = false)
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = PremiseOfferEntity.HOST_ID_FIELD_NAME)
    private List<PremiseOfferEntity> premiseOffers;
}
