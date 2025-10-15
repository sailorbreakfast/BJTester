package sailor.BJTester.batch.jobObjects.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import sailor.BJTester.model.Doctor;

@Component
public class MatchDoctorProcessor implements ItemProcessor<Doctor, Doctor> {
    @Override
    public Doctor process(Doctor doctor) throws Exception {

        int onPremiseFactor;

        if (doctor.getOnPremise()) {
            onPremiseFactor =200;
        }  else {
            onPremiseFactor = 0;
        }

        int hoursWorkedScore = 1000 - doctor.getMonthsWorkedHours();

        doctor.setMatchTreatmentScore( hoursWorkedScore + onPremiseFactor );

        return doctor;
    }
}
