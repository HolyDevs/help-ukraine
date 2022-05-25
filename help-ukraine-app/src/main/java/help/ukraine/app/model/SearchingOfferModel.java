package help.ukraine.app.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SearchingOfferModel {
    private Long id;
    @NotNull
    private Long refugeeId;
    @NotBlank
    private String additionalInfo;
    @NotNull
    private List<SearchingPersonModel> searchingPeople = new ArrayList<>();
    private String preferredLocation;
    private Double rangeFromPreferredLocationInKm;
    @NotNull
    private Boolean animalsInvolved;
    @NotNull
    private Boolean userMovingIssues;
}
