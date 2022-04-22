package help.ukraine.app.model;


import help.ukraine.app.enumerator.Sex;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SearchingPersonModel {
    private Long id;
    private Sex sex;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private boolean movingIssues;
}
