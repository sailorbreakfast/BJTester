package sailor.BJTester.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import sailor.BJTester.batch.jobObjekte.*;
import sailor.BJTester.fachobjekte.Doctor;


@Configuration
public class JobConfiguration {

    @Bean
    public Job princetonPlainsboroAvailabilityJob(JobRepository jobRepository, Step doctorAvailabilityStep){

        return new JobBuilder("princetonPlainsboroAvailabilityJob", jobRepository)
                .start(doctorAvailabilityStep)
                .build();
    }



    @Bean
    public Step doctorAvailabilityStep(JobRepository jobRepository, DoctorItemReaderConf doctorItemReaderConf, AvailableDoctorsWriterConf availableDoctorsWriterConf,
                                       DoctorItemWriter doctorItemWriter, DoctorItemProcessor doctorItemProcessor,
                                       DoctorItemWriteListener doctorItemWriteListener, TransactionManager transactionManager) {
        return new StepBuilder("doctorAvailabilityStep", jobRepository)
                .<Doctor, Doctor>chunk(4, (PlatformTransactionManager) transactionManager)
                .allowStartIfComplete(true)
                .reader(doctorItemReaderConf.doctorItemReader())
                .processor(doctorItemProcessor)
                .writer(availableDoctorsWriterConf.availableDoctorsWriter())
                .listener(doctorItemWriteListener)
                .build();
    }


//
//    @Bean
//    public Job helloJob(JobRepository jobRepository,
//                        Step helloStep) {
//
//        return new JobBuilder("helloJob", jobRepository)
//                .start(helloStep)
//                .build();
//    }
//
//    @Bean
//    public Step helloWorldStep (JobRepository jobRepository,
//                                 PlatformTransactionManager transactionManager,
//                                HelloWorldTasklet helloWorldTasklet) {
//        return new StepBuilder("helloWorldStep", jobRepository)
//                .tasklet(helloWorldTasklet, transactionManager)
//                .allowStartIfComplete(true)
//                .build();
//    }

}


