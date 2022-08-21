package animator;

public class Animator extends Thread {
    boolean animating = true;

    public void run(){
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Animator bouncy = new Animator();
        bouncy.start();
    }
}
