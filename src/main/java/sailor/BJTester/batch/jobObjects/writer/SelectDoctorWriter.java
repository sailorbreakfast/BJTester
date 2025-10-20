package sailor.BJTester.batch.jobObjects.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import sailor.BJTester.model.Doctor;

@Component
public class SelectDoctorWriter implements ItemWriter<Doctor> {
    @Override
    public void write( Chunk<? extends Doctor> chunk) throws Exception{

    }
}
