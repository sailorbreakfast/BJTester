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
import sailor.BJTester.batch.jobObjects.listener.SelectDoctorStepListener;
import sailor.BJTester.batch.jobObjects.processor.FilterAvailableDoctorsProcessor;
import sailor.BJTester.batch.jobObjects.processor.MatchDoctorProcessor;
import sailor.BJTester.batch.jobObjects.processor.SelectedDoctorProcessor;
import sailor.BJTester.batch.jobObjects.reader.AvailableDoctorItemReaderConf;
import sailor.BJTester.batch.jobObjects.reader.DepartmentDoctorReaderConf;
import sailor.BJTester.batch.jobObjects.reader.MatchingDoctorsReaderConf;
import sailor.BJTester.batch.jobObjects.writer.AvailableDoctorsWriterConf;
import sailor.BJTester.batch.jobObjects.writer.MatchDoctorWriterConf;
import sailor.BJTester.batch.jobObjects.writer.SelectDoctorWriter;
import sailor.BJTester.model.Doctor;


@Configuration
public class JobConfiguration {

    @Bean
    public Job princetonPlainsboroDoctorPagerJob(JobRepository jobRepository,
                                                 Step doctorAvailabilityStep,
                                                 Step matchDoctorStep,
                                                 Step selectDoctorStep){

        return new JobBuilder("princetonPlainsboroAvailabilityJob", jobRepository)
                .start(doctorAvailabilityStep)
                .next(matchDoctorStep)
                .next(selectDoctorStep)
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
    public Step selectDoctorStep(JobRepository jobRepository,
                                 MatchingDoctorsReaderConf matchingDoctorsReaderConf,
                                 SelectedDoctorProcessor selectedDoctorProcessor,
                                 SelectDoctorWriter selectDoctorWriter,
                                 SelectDoctorStepListener selectDoctorStepListener,
                                 TransactionManager transactionManager) {

        return new StepBuilder("selectDoctorStep", jobRepository)
                .<Doctor, Doctor>chunk(10, (PlatformTransactionManager) transactionManager)
                .allowStartIfComplete(true)
                .reader(matchingDoctorsReaderConf.matchingDoctorItemReader())
                .processor(selectedDoctorProcessor)
                .writer(selectDoctorWriter)
                .listener(selectedDoctorProcessor)
                .listener(selectDoctorStepListener)
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


