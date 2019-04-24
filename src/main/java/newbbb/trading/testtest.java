package newbbb.trading;

import java.util.Date;
import java.util.Random;

public class testtest {

    public static void main(String[] args) {
        long l = Long.MAX_VALUE;
        Long t1 = 1555980438463L;
        Long t2 = 0L;
        Random random = new Random();
        double TEST_TIMES = 1000.0;

        int err = 0;
        for (int i = 0; i < TEST_TIMES; i++) {
            t2 = new Date().getTime() - random.nextInt(2000000000);
            long r = t2 - t1;
            long rr = xx(t2) - xx(t1);
            boolean b = r > 0 ? true : false;
            boolean bb = rr > 0 ? true : false;
            boolean br = (b == bb) ? true : false;
            if (!br) {
                err++;
            }
            System.out.println(i + "\t" + r + " : " + rr);
        }

        System.out.println("err: " + err + " " + (err / TEST_TIMES * 100.0) + "%");


    }

    public static long xx(Long t) {
        StringBuilder sb = new StringBuilder();
        char[] tArr = t.toString().toCharArray();
        for (int i = 0; i < 6; i++) {
            sb.append(tArr[i * 2 + 1]);
        }
        return Long.parseLong(sb.toString());
    }

}
