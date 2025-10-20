package sailor.BJTester.batch.jobObjects.listener;

import lombok.SneakyThrows;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;
import sailor.BJTester.model.Doctor;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Component
public class SelectDoctorStepListener implements StepExecutionListener {


    @SneakyThrows
    @Override
    public ExitStatus afterStep(StepExecution stepExecution){
        JobExecution jobExecution = stepExecution.getJobExecution();
        ExecutionContext ctx = jobExecution.getExecutionContext();

        Doctor bestMatch = (Doctor) ctx.get("bestMatchDoctor");
        Path outputFile = Paths.get("springOutputData/selectedDoctor.csv");
        if (bestMatch != null) {
            try (BufferedWriter writer = Files.newBufferedWriter(outputFile, StandardOpenOption.CREATE)) {
                writer.write(String.format("%s,%s,%d,%b,%s,%d,%b,%d",
                        bestMatch.getFirstName(),
                        bestMatch.getLastName(),
                        bestMatch.getAge(),
                        bestMatch.getHasCapacityForPatient(),
                        bestMatch.getExpertise(),
                        bestMatch.getMonthsWorkedHours(),
                        bestMatch.getOnPremise(),
                        bestMatch.getMatchTreatmentScore()
                ));
            }
        } else {
            System.out.println("No Doctor found");
        }

        return ExitStatus.COMPLETED;
    }
    }

