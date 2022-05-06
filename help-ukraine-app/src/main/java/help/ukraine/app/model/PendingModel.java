package help.ukraine.app.model;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class PendingModel {
    @NotNull
    private Long searchingOfferId;
    @NotNull
    private Long premiseOfferId;
}
