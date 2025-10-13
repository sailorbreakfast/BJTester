package sailor.BJTester.fachobjekte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    String firstName;
    String lastName;
    Integer age;
    Boolean hasCapacityForPatient;
    String expertise;
    Integer monthsWorkedHours;
    Boolean onPremise;
        }