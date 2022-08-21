package appleTree;

import java.util.concurrent.Callable;

public class ApplePicker{

    public static Callable<Void> createApplePicker(AppleTree[] appleTrees, int fromIndex , int toIndex, String workerName) {
        return ()->{
            for(int i=fromIndex; i<toIndex;i++){
                appleTrees[i].pickApples();
            }
            return null;
        };
    }
}

// // TODO Xiao 2022-08-21 Must have a easy way to implement
// Callable<Void> applePicker1 = ApplePicker.createApplePicker(appleTrees, 0, 2, "AAA");
// Callable<Void> applePicker2 = ApplePicker.createApplePicker(appleTrees, 2, 4, "BBB");
// Callable<Void> applePicker3 = ApplePicker.createApplePicker(appleTrees, 4, 6, "CCC");

// ForkJoinPool.commonPool().invokeAll(Arrays.asList(applePicker1, applePicker2, applePicker3));
