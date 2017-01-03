
public class userActions {
    public static void main(String[] args) {
        int viewC = main.LiveViewer.size();
        int randomNum = (int)(Math.random() * viewC);

        String winner = main.LiveViewer.get(randomNum);
        System.out.println("Winner is " + winner);
    }
}
