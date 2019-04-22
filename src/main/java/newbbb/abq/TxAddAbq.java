package newbbb.abq;

import newbbb.abq.model.TxAddInfo;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;

@Component
public class TxAddAbq implements Runnable {

    public final static ArrayBlockingQueue<TxAddInfo> abq = new ArrayBlockingQueue<>(100);

    public void put(TxAddInfo txi) {
        try {
            abq.put(txi);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

    }
}
