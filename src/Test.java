public class Test {
    public static void main(String[] args) {
        Protocol.start("test.txt", true, true);
        Protocol.write("TEST 1243");
        Protocol.write("First test");
    }
}
