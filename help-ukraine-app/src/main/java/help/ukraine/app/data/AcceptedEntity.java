package help.ukraine.app.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "accepted")
public class AcceptedEntity {
    @EmbeddedId
    private SearchingPremiseOfferId searchingPremiseOfferId;
}
