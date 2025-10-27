package sailor.BJTester.batch.jobObjects.tasklets;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class RenameMatchDoctorCSVTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        Path oldFilePath = Paths.get("springOutputData/scoredDoctors.csv");
        Path newFilePath = Paths.get("springOutputData/shortendScoredDoctors.csv");

        try {
            Files.move(newFilePath, oldFilePath, StandardCopyOption.REPLACE_EXISTING);

        }catch (Exception e){
            System.err.println("Error while renaming " + oldFilePath + " to " + newFilePath);
        }

        return RepeatStatus.FINISHED;
    }

}
