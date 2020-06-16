package utils;

import java.text.DecimalFormat;

public class PriceUtils {

    private static  DecimalFormat qty = new DecimalFormat("#.##");
    private static  DecimalFormat prc = new DecimalFormat("#.##");


    public static long getPriceAsLong(double input) {
        return (long)(input * 100000000 + 0.5);
    }

    public static long getQuantityAsLong(double input) {
        return (long)(input * 100000000 + 0.5);
    }


    public static long multiplyLong(long l1, long l2) {
        double d1 = getPriceAsDouble(l1);
        double d2 = getPriceAsDouble(l2);

        return getPriceAsLong(d1 * d2);
    }

    public static double getQuantityAsDouble(long quantity) {
        return (double) quantity / 1e8;
    }

    public static double getPriceAsDouble(long price) {
        return (double)price / 1e8;
    }

    public  static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }


}
