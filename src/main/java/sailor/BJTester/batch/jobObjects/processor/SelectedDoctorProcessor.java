package sailor.BJTester.batch.jobObjects.processor;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import sailor.BJTester.model.Doctor;

@Component
public class SelectedDoctorProcessor implements ItemProcessor<Doctor, Doctor> {

    @Override
    public Doctor process(Doctor doctor, JobExecution jobExecution) throws Exception {

        Doctor bestMatchDoctor = (Doctor) jobExecution.getExecutionContext().get("bestMatchDoctor");

        if( bestMatchDoctor != null ){
            if(doctor.getMatchTreatmentScore()>bestMatchDoctor.getMatchTreatmentScore()){
                jobExecution.setExecutionContext((doctor);
            }
        }else {
            jobExecution.getExecutionContext().put("bestMatchDoctor",doctor);
        }
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            return (Doctor) jobExecution.getExecutionContext().get("bestMatchDoctor");
        }else {
            return null;
        }
    }
}
