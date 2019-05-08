package newbbb.model.me;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Settings {

    private Integer assetNum;
    private List<Asset> assets;
    private Integer marketNum;
    private List<Market> markets;
    private String borkers;
    private Integer sliceInterval;
    private Integer sliceKeepTime;
    private Integer historyThread;
    private Long cacheTimeout;

}
