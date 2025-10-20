package sailor.BJTester.batch.jobObjects.processor;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import sailor.BJTester.model.Doctor;

@Component
public class SelectedDoctorProcessor implements ItemProcessor<Doctor, Doctor> {

    private JobExecution jobExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution){
        jobExecution = stepExecution.getJobExecution();
    }

    @Override
    public Doctor process(Doctor doctor) throws Exception {
        if(jobExecution!=null) {
            ExecutionContext ctx = jobExecution.getExecutionContext();
            Doctor bestMatchDoctor = (Doctor) ctx.get("bestMatchDoctor");
            if (bestMatchDoctor != null) {
                if (doctor.getMatchTreatmentScore() > bestMatchDoctor.getMatchTreatmentScore()) {
                    ctx.put("bestMatchDoctor", doctor);
                }
            } else {
                ctx.put("bestMatchDoctor", doctor);
            }
        }
        else {
            throw new Exception("No Doctor found");
        }
        return null;
    }
    }