package codewings.emily.hw4;

public class BigInteger implements Comparable<BigInteger> {
    private static final int ARRAY_SIZE = 1024;

    public static BigInteger parse(String s)
            throws NumberFormatException {
        BigInteger bigint = new BigInteger();
        int beginIndex = 0;
        if (s.charAt(0) == '-') {
            bigint.sign = -1;
            beginIndex = 1;
        }
        for (int i = beginIndex; i < s.length(); i++) {
            char digit = s.charAt(i);
            if ('0' <= digit && digit <= '9') {
                bigint.array[i - beginIndex] = digit - '0';
            } else {
                throw new NumberFormatException(
                        String.format("Invalid character at %d (%s)", i, digit));
            }
        }
        return bigint;
    }

    public static BigInteger of(int value) {
        return BigInteger.of((long) value);
    }

    public static BigInteger of(long value) {
        BigInteger bigint = new BigInteger();
        if (value < 0) {
            value = -value;
            bigint.sign = -1;
        }
        for (int i = 0; value > 0; i++) {
            bigint.array[i] = (int) (value % 10L);
            value /= 10;
        }
        return bigint;
    }

    private int sign = 1;
    private int[] array = new int[ARRAY_SIZE];

    /**
     * No one can create BigInteger directly (i.e. new BigInteger() is prohibited)
     * Use {@link BigInteger#parse(String)} or {@link BigInteger#of(int)} instead.
     */
    private BigInteger() {}

    private BigInteger(int sign, int[] array) {
        this.sign = sign;
        this.array = array;
    }

    public BigInteger add(BigInteger other) {
        if (this.sign != other.sign) {
            return subtract(other.negate());
        }
        BigInteger result = this.addUnsigned(other);
        result.sign = this.sign;
        return result;
    }

    private BigInteger addUnsigned(BigInteger other) {
        BigInteger result = new BigInteger();
        int carry = 0;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            result.array[i] = this.array[i] + other.array[i] + carry;
            carry = result.array[i] / 10;
            result.array[i] %= 10;
        }
        return result;
    }

    public BigInteger subtract(BigInteger other) {
        if (this.sign != other.sign) {
            return add(other.negate());
        }
        BigInteger result = this.abs().compareTo(other.abs()) > 0
                ? this.subtractUnsigned(other)
                : other.subtractUnsigned(this).negate();
        result.sign *= this.sign;
        return result;
    }

    private BigInteger subtractUnsigned(BigInteger other) {
        BigInteger result = new BigInteger();
        int borrow = 0;
        for (int i = 0; i < ARRAY_SIZE; i++) {
            result.array[i] = this.array[i] - other.array[i] + borrow;
            borrow = 0;
            while (result.array[i] < 0) {
                result.array[i] += 10;
                borrow--;
            }
        }
        return result;
    }

    private BigInteger negate() {
        return new BigInteger(-this.sign, this.array);
    }

    private BigInteger abs() {
        return sign > 0 ? this : this.negate();
    }

    public int compareTo(BigInteger other) {
        if (this.sign != other.sign) {
            return this.sign - other.sign;
        }
        for (int i = ARRAY_SIZE - 1; i >= 0; i--) {
            if (this.array[i] != other.array[i])
                return (this.array[i] - other.array[i]) * sign;
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (sign < 0)
            sb.append('-');
        int firstNonZeroIndex = ARRAY_SIZE - 1;
        while (array[firstNonZeroIndex] == 0 && firstNonZeroIndex > 0)
            firstNonZeroIndex--;
        for (int i = firstNonZeroIndex; i >= 0; i--)
            sb.append(array[i]);
        return sb.toString();
    }
}
