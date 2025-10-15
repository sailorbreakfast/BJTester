package sailor.BJTester.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import sailor.BJTester.batch.jobObjects.processor.FilterAvailableDoctorsProcessor;
import sailor.BJTester.batch.jobObjects.processor.MatchDoctorProcessor;
import sailor.BJTester.batch.jobObjects.reader.AvailableDoctorItemReaderConf;
import sailor.BJTester.batch.jobObjects.reader.DepartmentDoctorReaderConf;
import sailor.BJTester.batch.jobObjects.writer.AvailableDoctorsWriterConf;
import sailor.BJTester.batch.jobObjects.writer.MatchDoctorWriterConf;
import sailor.BJTester.model.Doctor;


@Configuration
public class JobConfiguration {

    @Bean
    public Job princetonPlainsboroDoctorPagerJob(JobRepository jobRepository, Step doctorAvailabilityStep, Step matchDoctorStep){

        return new JobBuilder("princetonPlainsboroAvailabilityJob", jobRepository)
                .start(doctorAvailabilityStep)
                .next(matchDoctorStep)
                .build();
    }



    @Bean
    public Step doctorAvailabilityStep(JobRepository jobRepository,
                                       DepartmentDoctorReaderConf departmentDoctorReaderConf,
                                       AvailableDoctorsWriterConf availableDoctorsWriterConf,
                                       FilterAvailableDoctorsProcessor filterAvailableDoctorsProcessor,
                                       TransactionManager transactionManager) {

        return new StepBuilder("doctorAvailabilityStep", jobRepository)
                .<Doctor, Doctor>chunk(10, (PlatformTransactionManager) transactionManager)
                .allowStartIfComplete(true)
                .reader(departmentDoctorReaderConf.departmentDoctorItemReader())
                .processor(filterAvailableDoctorsProcessor)
                .writer(availableDoctorsWriterConf.availableDoctorsWriter())
                .build();
    }

    @Bean
    public Step matchDoctorStep(JobRepository jobRepository,
                                AvailableDoctorItemReaderConf availableDoctorItemReaderConf,
                                MatchDoctorProcessor matchDoctorProcessor,
                                MatchDoctorWriterConf matchDoctorWriterConf,
                                TransactionManager transactionManager) {

        return new StepBuilder("matchDoctorStep",jobRepository)
                .<Doctor, Doctor>chunk(10, (PlatformTransactionManager) transactionManager)
                .allowStartIfComplete(true)
                .reader(availableDoctorItemReaderConf.availableDoctorItemReader())
                .processor(matchDoctorProcessor)
                .writer(matchDoctorWriterConf.scoredDoctorsWriter())
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
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


