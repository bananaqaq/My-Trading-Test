package newbbb.util;

import java.util.UUID;

public class UUIdUtil {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/*public static String getOrderUUID(long time, OrderTypeEnum orderTypeEnum){
		String prefix = "";
		switch (orderTypeEnum){
			case BUY:
				prefix = "b";
				break;
			case SELL:
				prefix = "s";
				break;
			default:
				return "";
		}
		return prefix + time + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 18);
	}*/

}
