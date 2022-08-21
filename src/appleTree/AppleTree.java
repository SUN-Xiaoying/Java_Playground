package appleTree;
import java.util.concurrent.TimeUnit;

public class AppleTree {

    public static AppleTree[] newTreeGarden(int size){
        AppleTree[] appleTrees = new AppleTree[size];

        for(int i =0; i<size; i++){
            appleTrees[i] = new AppleTree("T"+i);
        }

        return appleTrees;
    }

    private final String treeLabel;
    private final int numberOfApples;

    public AppleTree(String treeLabel){
        this.treeLabel=treeLabel;
        numberOfApples=3;
    }

    public int pickApples(){
        try {
            System.out.println("Picking  " + treeLabel + " at " + System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return numberOfApples;
    }
}
