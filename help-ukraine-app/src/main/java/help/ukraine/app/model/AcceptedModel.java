package help.ukraine.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptedModel {
    @NotNull
    private Long searchingOfferId;
    @NotNull
    private Long premiseOfferId;
}
