package sailor.BJTester.batch.jobObjekte;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import sailor.BJTester.fachobjekte.Doctor;

@Component
public class DoctorItemProcessor implements ItemProcessor<Doctor, Doctor> {
    @Override
    public Doctor process(Doctor item) throws Exception {
        if (!item.getHasCapacityForPatient()) {
            return null;
        }
        else return item;
    }
}
