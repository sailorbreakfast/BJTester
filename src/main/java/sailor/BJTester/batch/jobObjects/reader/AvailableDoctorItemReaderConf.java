package sailor.BJTester.batch.jobObjects.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import sailor.BJTester.model.Doctor;

@Configuration
public class AvailableDoctorItemReaderConf {

    @Bean
        public FlatFileItemReader<Doctor> availableDoctorItemReader(){
            return new FlatFileItemReaderBuilder<Doctor>()
                    .name("doctorItemReader")
                    .delimited()
                    .names("firstName", "lastName", "age", "hasCapacityForPatient", "Expertise", "monthsWorkedHours", "onPremise", "matchTreatmentScore")
                    .targetType(Doctor.class)
                    .lineMapper(doctorLineMapper())
                    .resource(new FileSystemResource("C:\\Users\\ratze\\Desktop\\BJTester\\BJTester\\springOutputData\\availableDoctors.csv"))
                    .build();
        }

        public LineMapper<Doctor> doctorLineMapper(){
            DefaultLineMapper<Doctor> mapper = new DefaultLineMapper<Doctor>();
            mapper.setLineTokenizer(doctorLineTokenizer());
            mapper.setFieldSetMapper(new BeanWrapperFieldSetMapper<>(){{setTargetType(Doctor.class);}});
            return mapper;
        }

        public LineTokenizer doctorLineTokenizer(){
            DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
            tokenizer.setDelimiter(",");
            tokenizer.setNames(new String[]{"firstName", "lastName", "age", "hasCapacityForPatient", "Expertise", "monthsWorkedHours", "onPremise", "matchTreatmentScore"});
            return tokenizer;
        }
    }
