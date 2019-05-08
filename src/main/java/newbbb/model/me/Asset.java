package newbbb.model.me;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Asset {

    private String name;
    private Integer precSave;
    private Integer precShow;

}
