package help.ukraine.app.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SearchingOfferModel {
    private Long id;
    private Long refugeeId;
    private String additionalInfo;
    private List<SearchingPersonModel> searchingPeople = new ArrayList<>();
    private String preferredLocation;
    private Double rangeFromPreferredLocationInKm;
    private Boolean animalsInvolved;
}
