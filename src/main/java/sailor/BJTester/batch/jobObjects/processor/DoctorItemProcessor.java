package sailor.BJTester.batch.jobObjects.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import sailor.BJTester.model.Doctor;

@Component
public class DoctorItemProcessor implements ItemProcessor<Doctor, Doctor> {
    @Override
    public Doctor process(Doctor item) {
        if (!item.getHasCapacityForPatient()) {
            return null;
        }
        else return item;
    }
}
