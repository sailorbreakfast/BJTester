package sailor.BJTester.batch.jobObjects.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import sailor.BJTester.model.Doctor;

@Configuration
public class ShortendMatchDoctorWriterConf {
    @Bean
    @StepScope
    public FlatFileItemWriter<Doctor> shortendMatchDoctorsWriter(){
        FlatFileItemWriter<Doctor> fileItemWriter = new FlatFileItemWriter<>();
        fileItemWriter.setResource(new FileSystemResource("springOutputData/shortendScoredDoctors.csv"));
        fileItemWriter.setAppendAllowed(true);
        fileItemWriter.setLineAggregator(doctorLineAggregator());
        fileItemWriter.close();
        return fileItemWriter;
    }

    public DelimitedLineAggregator<Doctor> doctorLineAggregator(){
        DelimitedLineAggregator<Doctor> doctorDelimitedLineAggregator = new DelimitedLineAggregator<>();
        doctorDelimitedLineAggregator.setDelimiter(",");
        doctorDelimitedLineAggregator.setFieldExtractor(doctorFieldExtractor());
        return doctorDelimitedLineAggregator;
    }

    public BeanWrapperFieldExtractor<Doctor> doctorFieldExtractor(){
        BeanWrapperFieldExtractor<Doctor> beanWrapperFieldExtractor = new BeanWrapperFieldExtractor<>();
        beanWrapperFieldExtractor.setNames(new String[]{"firstName", "lastName", "age", "hasCapacityForPatient", "Expertise", "monthsWorkedHours", "onPremise", "matchTreatmentScore"});
        return beanWrapperFieldExtractor;

    }
}

