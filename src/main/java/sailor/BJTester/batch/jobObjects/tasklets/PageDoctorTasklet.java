package sailor.BJTester.batch.jobObjects.tasklets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import sailor.BJTester.model.Doctor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class PageDoctorTasklet implements Tasklet {

    private static final Logger log = LoggerFactory.getLogger(HelloWorldTasklet.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        Path path = Paths.get("springOutputData/selectedDoctor.csv");
        if (!Files.exists(path)) {
            contribution.setExitStatus(ExitStatus.FAILED);
            return RepeatStatus.FINISHED;
        }
        List<String> lines = Files.readAllLines(path);
        if (lines.isEmpty()) {
            contribution.setExitStatus(ExitStatus.FAILED);
            return RepeatStatus.FINISHED;
        }
        String line = lines.get(0);
        String[] parts = line.split(",");
        Doctor doctor = new Doctor(parts[0],
                parts[1],
                Integer.parseInt(parts[2]),
                Boolean.parseBoolean(parts[3]),
                parts[4],
                Integer.parseInt(parts[5]),
                Boolean.parseBoolean(parts[6]),
                Integer.parseInt(parts[7]));
        log.info("\n \nPAGING DR. " + doctor.getLastName().toUpperCase() + "\n" +
                "Sailor-Auromatic-Pager-Messages, Re New Patient 42F waiting in Room 420, urgency high, " +
                "x6927442 pag 7035 c245-543-8772 \n");

        if (Math.random() < 0.1) {
            contribution.setExitStatus(ExitStatus.FAILED);
        } else {
            contribution.setExitStatus(ExitStatus.COMPLETED);
        }

        return RepeatStatus.FINISHED;
    }


}
