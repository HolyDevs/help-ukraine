package help.ukraine.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class PremiseOfferModel {
    @JsonIgnore
    private final static String WARSAW_TIMEZONE = "Europe/Warsaw";
    @JsonIgnore
    private final static String DATE_PATTERN = "yyyy-MM-dd";

    private Long id;
    private Long hostId;
    private List<String> offerImagesLocations;
    private String city;
    private String street;
    private String houseNumber;
    private String postalCode;
    private int peopleToTake;
    private int bathrooms;
    private int kitchens;
    private int bedrooms;
    private boolean animalsAllowed;
    private boolean wheelchairFriendly;
    private boolean smokingAllowed;
    private boolean active;
    private boolean verified;
    private String description;
    @JsonFormat(pattern = DATE_PATTERN, timezone = WARSAW_TIMEZONE)
    private LocalDate fromDate;
    @JsonFormat(pattern = DATE_PATTERN, timezone = WARSAW_TIMEZONE)
    private LocalDate toDate;
}
