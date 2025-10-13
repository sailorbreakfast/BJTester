package sailor.BJTester.batch.jobObjekte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import sailor.BJTester.fachobjekte.Doctor;

@Component
public class DoctorItemWriter  implements ItemWriter<Doctor> {
    private static final Logger log = LoggerFactory.getLogger(DoctorItemWriter.class);
    @Override
    public void write(Chunk<? extends Doctor> doctors) throws Exception {
        for (Doctor doctor : doctors) {
            log.info("Dr.{} {}", doctor.getLastName(), doctor.getExpertise());
        }
    }
}
