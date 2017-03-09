import java.util.concurrent.TimeUnit;

public class Threads {

    static void viewerPoints(boolean start) {
        Thread pointGiver = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        TimeUnit.MINUTES.sleep(5);

                        //loop to get all live user
                        for (int i = 0; i< Lists.viewerLive.size(); i++ ) {
                            String user = Lists.viewerLive.get(i); //live user from Lists
                            int index = Lists.viewerALL.indexOf(user); //position of liveUser in Main Lists
                            int pointsU = Lists.viewerPoints.get(index); //get user points
                            pointsU++; //increase points
                            Lists.viewerPoints.set(index, pointsU); //set new points value

                            System.out.println(user + " + 1 Point");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        if (start)
            pointGiver.start();
    }

    static void watchTime(boolean start) {
        Thread timeGiver = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        TimeUnit.MINUTES.sleep(1);

                        //loop to get all live user
                        for (int i = 0; i< Lists.viewerLive.size(); i++ ) {
                            String user = Lists.viewerLive.get(i); //live user from Lists
                            int index = Lists.viewerALL.indexOf(user); //position of liveUser in Main Lists
                            int watch = Lists.watchtime.get(index); //get user watchtime
                            watch++; //increase points
                            Lists.watchtime.set(index, watch); //set new time value

                            System.out.println(user + " + 1 Minute");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ArrayIndexOutOfBoundsException be) {
                        System.out.println(be);
                    }
                }
            }
        });
        if (start)
            timeGiver.start();
    }

    static void autoSave(boolean start) {
        Thread save = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        TimeUnit.MINUTES.sleep(2);
                        Log.write("Start Autosave");
                        Json.Writer();
                        Json.Reader();
                        Log.write("Autosaved");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        if (start)
            save.start();
    }
}
