package appleTree;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

/*
 * RecursiveAction extends ForkJoinTask<Void>
 * Use RecursiveAction when a task doesn't return any result
 */
public class AppleRecursiveAction {
    private static final int SIZE = 12;

    public static void main(String[] args) {
        AppleTree[] appleTrees = AppleTree.newTreeGarden(SIZE);
        ForkJoinPool pool = ForkJoinPool.commonPool();

        System.out.println("==============================");

        PickAppleAction action = new PickAppleAction(appleTrees, 0, SIZE-1);

        long startTime = System.currentTimeMillis();

        pool.invoke(action);

        /*
        * Done in 2
        * EXECUTE: only arrange tasks, it doesn't wait for them to finish.
        */
        // pool.execute(action);
        // action.join();

        long endTime = System.currentTimeMillis();
        System.out.println("==============================");
        System.out.println("Done in " + (endTime - startTime));
    }

    public static class PickAppleAction extends RecursiveAction{

        private final AppleTree[] appleTrees;
        private final int startInclusive;
        private final int endInclusive;
    
        private final int taskThreadshold = 4;
    
        public PickAppleAction(AppleTree[] appleTrees, int startInclusive, int endInclusive) {
            this.appleTrees = appleTrees;
            this.startInclusive = startInclusive;
            this.endInclusive = endInclusive;
        }

        protected Integer doCompute(){
            System.out.println("Pick apples from " + startInclusive +" to "+ endInclusive);
            return IntStream.rangeClosed(startInclusive, endInclusive)
                    .map(i->appleTrees[i].pickApples())
                    .sum();
        }
    
        @Override
        protected void compute() {
            if(endInclusive - startInclusive < taskThreadshold){
                doCompute();
                return;
            }
            int midpoint = startInclusive + (endInclusive - startInclusive)/2;

            PickAppleAction leftSum = new PickAppleAction(appleTrees, startInclusive, midpoint);
            PickAppleAction rightSum = new PickAppleAction(appleTrees, midpoint+1, endInclusive);

            rightSum.fork();
            
            // COMPUTE is synchronous, 4 times slower
            leftSum.compute();

            // Not all apple trees are included
            rightSum.join();
        }
    
    } 
}


