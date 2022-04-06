package help.ukraine.app.data;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "hosts")
public class HostEntity implements Serializable {

    private static final String USER_ID_COLUMN_NAME = "user_id";

    @Id
    @Column(name = USER_ID_COLUMN_NAME)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = USER_ID_COLUMN_NAME, referencedColumnName = UserEntity.ID_COLUMN_NAME,
            insertable = false, updatable = false)
    private UserEntity user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = PremiseOfferEntity.HOST_ID_FIELD_NAME)
    private List<PremiseOfferEntity> premiseOffers;
}
