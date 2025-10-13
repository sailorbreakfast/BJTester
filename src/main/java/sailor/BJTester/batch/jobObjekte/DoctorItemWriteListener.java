package sailor.BJTester.batch.jobObjekte;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;

@Component
public class DoctorItemWriteListener implements ItemWriteListener {

    Log log = LogFactory.getLog(getClass());

    @Override
    public void beforeWrite(Chunk items) {
        log.info("The processor found  "+ items.size() + " available doctors in the checked chunk ");
    }

//    @Override
//    public void afterWrite(Chunk items) {
//        log.info("After Write geht auch");
//    }
}
