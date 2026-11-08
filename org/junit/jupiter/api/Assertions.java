package org.junit.jupiter.api;

/** Very small subset of JUnit assertion methods for compile‑time only. */
public class Assertions {
    public static void assertTrue(boolean condition) {
        if (!condition) throw new AssertionError("assertTrue failed");
    }

    public static void assertFalse(boolean condition) {
        if (condition) throw new AssertionError("assertFalse failed");
    }

    public static void assertEquals(Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError("assertEquals failed: expected=" + expected + ", actual=" + actual);
        }
    }

    public static void assertNotEquals(Object unexpected, Object actual) {
        if (unexpected == null ? actual == null : unexpected.equals(actual)) {
            throw new AssertionError("assertNotEquals failed: unexpected=" + unexpected + ", actual=" + actual);
        }
    }

    public static void fail(String msg) {
        throw new AssertionError(msg);
    }
}