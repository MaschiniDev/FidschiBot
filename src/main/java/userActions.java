
public class userActions {
    public static void main(String[] args) {
        int viewC = main.viewerLive.size();
        int randomNum = (int)(Math.random() * viewC);

        String winner = main.viewerLive.get(randomNum);
        System.out.println("Winner is " + winner);
    }
}
