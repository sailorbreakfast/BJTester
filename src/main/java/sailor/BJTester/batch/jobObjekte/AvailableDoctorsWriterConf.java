package sailor.BJTester.batch.jobObjekte;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import sailor.BJTester.fachobjekte.Doctor;

@Configuration
public class AvailableDoctorsWriterConf {
    @Bean
    public FlatFileItemWriter<Doctor> availableDoctorsWriter(){
        FlatFileItemWriter<Doctor> fileItemWriter = new FlatFileItemWriter<Doctor>();
        fileItemWriter.setResource(new FileSystemResource("src/main/resources/availableDoctors.csv"));
        fileItemWriter.setAppendAllowed(true);
        fileItemWriter.setLineAggregator(doctorLineAggregator());
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
        beanWrapperFieldExtractor.setNames(new String[]{"firstName", "lastName", "age", "hasCapacityForPatient", "Expertise", "monthsWorkedHours", "onPremise"});
        return beanWrapperFieldExtractor;

    }
}

