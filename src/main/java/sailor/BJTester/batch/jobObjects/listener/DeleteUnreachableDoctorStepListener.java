package sailor.BJTester.batch.jobObjects.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.StepListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class DeleteUnreachableDoctorStepListener implements StepExecutionListener {
    @Override
    public ExitStatus afterStep(StepExecution stepExecution){
        Path oldFilePath = Paths.get("springOutputData/scoredDoctors.csv");
        File oldFile = new File(oldFilePath.toString());

        Path newFilePath = Paths.get("springOutputData/shortendScoredDoctors.csv");
        File newFile = newFilePath.toFile();

        try {
            Files.deleteIfExists(oldFilePath);
            Files.move(newFilePath, oldFilePath, StandardCopyOption.REPLACE_EXISTING);

            return ExitStatus.COMPLETED;

        }catch (Exception e){
            return ExitStatus.FAILED;
        }

    }
}
