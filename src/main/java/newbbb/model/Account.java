package newbbb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Account {

    private Long id;
    private String uid;
    private Long createTime;
    private Long updateTime;

}