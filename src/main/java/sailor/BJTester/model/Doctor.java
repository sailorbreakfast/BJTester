package sailor.BJTester.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    String firstName;
    String lastName;
    Integer age;
    Boolean hasCapacityForPatient;
    String expertise;
    Integer monthsWorkedHours;
    Boolean onPremise;
    Integer matchTreatmentScore;
        }