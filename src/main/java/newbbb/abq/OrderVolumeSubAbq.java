package newbbb.abq;

import newbbb.abq.model.OrderVolumeSubInfo;
import newbbb.service.IBuyOrderService;
import newbbb.service.ISellOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

@Component
public class OrderVolumeSubAbq implements Runnable {

    private final int SLEEP_TIME = 500;
    private final int ABQ_SIZE = 100;

    @Autowired
    private IBuyOrderService boService;

    @Autowired
    private ISellOrderService soService;

    private final ArrayBlockingQueue<OrderVolumeSubInfo> abq = new ArrayBlockingQueue<>(ABQ_SIZE);

    public void put(OrderVolumeSubInfo ovsi) {
        try {
            abq.put(ovsi);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (abq.size() == 0) {
                    Thread.sleep(SLEEP_TIME);
                }else {
                    OrderVolumeSubInfo ovsi = abq.remove();
                    switch (ovsi.getOrderTypeEnum()) {
                        case BUY:
                            boService.subVolume(ovsi.getUid(), ovsi.getVolume());
                            break;
                        case SELL:
                            soService.subVolume(ovsi.getUid(), ovsi.getVolume());
                            break;
                        default:
                            break;
                    }
                    System.out.println("订单更新：\t" + ovsi.toString() + "\t" + abq.size());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
