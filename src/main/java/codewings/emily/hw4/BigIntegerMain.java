package codewings.emily.hw4;

public class BigIntegerMain {
    public static void main(String[] args) {
        System.out.println("123");
        System.out.println(BigInteger.of(123));

        System.out.println("123 - 321");
        System.out.println(BigInteger.of(123).subtract(BigInteger.of(321)));

        System.out.println("-123 + 321");
        System.out.println(BigInteger.of(-123).add(BigInteger.of(321)));
    }
}
