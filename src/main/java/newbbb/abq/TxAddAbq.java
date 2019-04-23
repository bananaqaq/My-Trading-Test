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

    private final int MAX_SIZE = 25;
    private final int SLEEP_TIME = 500;
    private final int MAX_WAIT_TIME = 6000;
    private final int ABQ_SIZE = 100;

    @Autowired
    private ITxRecordService trService;

    private long lastCallTime = new Date().getTime();

    public final ArrayBlockingQueue<TxRecord> abq = new ArrayBlockingQueue<>(ABQ_SIZE);

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
                long timeNow = new Date().getTime();
                if (abq.size() == 0) {
                    Thread.sleep(SLEEP_TIME);
                } else if (abq.size() >= MAX_SIZE) {
                    handleTxAdd();
                    lastCallTime = timeNow;
                } else {
                    if (timeNow - lastCallTime >= MAX_WAIT_TIME) {
                        handleTxAdd();
                        lastCallTime = timeNow;
                    }
                    Thread.sleep(SLEEP_TIME);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleTxAdd() {
        ArrayList<TxRecord> taiList = new ArrayList<>();
        for (int i = 0; i < MAX_SIZE; i++) {
            if (abq.size() == 0) {
                break;
            }
            taiList.add(abq.remove());
            System.out.println("交易记录添加：\t" + i + "\t" + taiList.get(i).toString());
        }
        if (taiList.size() != 0) {
            trService.addList(taiList);
        }
    }

}