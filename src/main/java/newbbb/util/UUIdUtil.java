package newbbb.util;

import java.util.UUID;

public class UUIdUtil {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
