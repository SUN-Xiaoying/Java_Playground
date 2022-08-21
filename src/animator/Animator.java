package animator;

public class Animator extends Thread {
    boolean animating = true;

    public void run(){
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            //TODO: handle exception
            
        }
    }

    public static void main(String[] args) throws Exception {
        Animator bouncy = new Animator();
        bouncy.start();
    }
}
