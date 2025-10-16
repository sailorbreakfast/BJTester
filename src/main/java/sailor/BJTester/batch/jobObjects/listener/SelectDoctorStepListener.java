package sailor.BJTester.batch.jobObjects.listener;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepListener;
import org.springframework.stereotype.Component;

@Component
public class SelectDoctorStepListener implements StepListener {

    @Override
    public void beforeStep(StepExecution stepExecution){

    }
}
