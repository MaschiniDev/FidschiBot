import java.util.concurrent.TimeUnit;

public class threads {

    static void viewerPoints(boolean start) {
        Thread pointGiver = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        TimeUnit.MINUTES.sleep(5);

                        //loop to get all live user
                        for (int i = 0; i< list.viewerLive.size(); i++ ) {
                            String user = list.viewerLive.get(i); //live user from list
                            int index = list.viewerALL.indexOf(user); //position of liveUser in main list
                            int pointsU = list.viewerPoints.get(index); //get user points
                            pointsU++; //increase points
                            list.viewerPoints.set(index, pointsU); //set new points value

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
                        for (int i = 0; i< list.viewerLive.size(); i++ ) {
                            String user = list.viewerLive.get(i); //live user from list
                            int index = list.viewerALL.indexOf(user); //position of liveUser in main list
                            int watch = list.watchtime.get(index); //get user watchtime
                            watch++; //increase points
                            list.watchtime.set(index, watch); //set new time value

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
                        TimeUnit.MINUTES.sleep(30);
                        json.Writer();
                        json.Reader();
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
