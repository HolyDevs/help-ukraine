package help.ukraine.app.dto;

import help.ukraine.app.enumerator.Sex;
import help.ukraine.app.model.SearchingPersonModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CandidateDto {
    private Long searchingOfferId;
    private String name;
    private String surname;
    private Sex sex;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private boolean movingIssues;
    private boolean animalsInvolved;
    private String additionalInfo;
    private List<SearchingPersonModel> searchingPeople = new ArrayList<>();



}
