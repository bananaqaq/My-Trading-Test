package newbbb.abq;

import newbbb.model.TxRecord;
import newbbb.service.ITxRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

@Component
public class TxAddAbq implements Runnable {

    private final int ADD_SIZE = 20;

    @Autowired
    private ITxRecordService trService;

    private long lastCallTime = new Date().getTime();

    public final static ArrayBlockingQueue<TxRecord> abq = new ArrayBlockingQueue<>(100);

    public void put(TxRecord tr) {
        try {
            abq.put(tr);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (abq.size() == 0) {
                    Thread.sleep(500);
                } else if (abq.size() >= ADD_SIZE) {
                    handleTxAdd();
                } else {
                    long timeNow = new Date().getTime();
                    if (timeNow - lastCallTime >= 1500) {
                        handleTxAdd();
                    }
                    lastCallTime = timeNow;
                    Thread.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleTxAdd() {
        ArrayList<TxRecord> taiList = new ArrayList<>();
        for (int i = 0; i < ADD_SIZE; i++) {
            if(abq.size() == 0){
                break;
            }
            taiList.add(abq.remove());
        }
        if(taiList.size() != 0){
            trService.addList(taiList);
        }
    }

}
