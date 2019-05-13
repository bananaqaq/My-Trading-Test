package newbbb.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalUtil {

    public static BigDecimal SetScale(BigDecimal amt, int scale){
        return amt.setScale(scale, RoundingMode.DOWN);
    }

}
