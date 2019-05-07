package newbbb.model.me;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Settings {

    private Integer assetNum;
    private Asset assets;
    private Integer marketNum;
    private Market markets;
    private String borkers;
    private Integer sliceInterval;
    private Integer sliceKeepTime;
    private Integer historyThread;
    private Long cacheTimeout;

}
