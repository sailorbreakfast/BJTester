package sailor.BJTester.batch.jobObjects.processor;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import sailor.BJTester.model.Doctor;

@Component
public class DeleteUnreachableDoctorProcessor implements ItemProcessor<Doctor, Doctor>, StepExecutionListener {

    private JobExecution jobExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution){
        jobExecution = stepExecution.getJobExecution();
    }

    @Override
    public Doctor process(Doctor item) throws Exception {
        ExecutionContext ctx = jobExecution.getExecutionContext();
        Doctor bestMatchDoctor = (Doctor) ctx.get("bestMatchDoctor");
        if(item.equals(bestMatchDoctor)){
            return null;
        }else {
            return item;
        }
    }
}
