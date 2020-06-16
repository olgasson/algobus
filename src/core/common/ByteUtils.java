package core.common;

import java.nio.ByteBuffer;

public class ByteUtils {

    private static final byte[] EMPTY = new byte[Long.BYTES];

    private final static byte[] buf = new byte[Long.BYTES];

    private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);

    public static String longToString(long x) {
        return new String(longToBytes(x)).trim();
    }

    public static byte[] longToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        System.arraycopy(EMPTY, 0, buf, 0, Long.BYTES);
        System.arraycopy(bytes, 0, buf, 0, (bytes.length > Long.BYTES) ? Long.BYTES : bytes.length);
        buffer.clear();
        buffer.put(buf, 0, Long.BYTES);
        buffer.flip();//need flip
        return buffer.getLong(0);
    }
}
