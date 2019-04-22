package newbbb.abq;

import newbbb.abq.model.AssetUpdateInfo;
import newbbb.service.IAccountAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.ArrayBlockingQueue;

@Component
public class AssetUpdateAbq implements Runnable {

    private final int SLEEP_TIME = 500;

    @Autowired
    private IAccountAssetService aaService;

    private final ArrayBlockingQueue<AssetUpdateInfo> abq = new ArrayBlockingQueue<>(100);

    public void put(AssetUpdateInfo aui) {
        try {
            abq.put(aui);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int i = 1;

    @Override
    public void run() {
        while (true) {
            if (abq.size() == 0) {
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                AssetUpdateInfo aui = abq.remove();
                switch (aui.getAssetTypeEnum()) {
                    case FORZEN:
                        aaService.updateForzenAmt(aui.getAccountUid(), aui.getCoinId(), aui.getVolume(), aui.getAssetUpdateEnum());
                        break;
                    case NORMAL:
                        aaService.updateAmt(aui.getAccountUid(), aui.getCoinId(), aui.getVolume(), aui.getAssetUpdateEnum());
                        break;
                    default:
                        break;
                }
                System.out.println("资产更新：\t" + (i++) + ":处理完成\t" + abq.size());
            }
        }
    }
}
