package appleTree;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class AppleRecursiveTask {

    private static final int SIZE = 12;

    public static void main(String[] args) {
        AppleTree[] appleTrees = AppleTree.newTreeGarden(SIZE);
        ForkJoinPool pool = ForkJoinPool.commonPool();

        System.out.println("==============================");

        PickAppleTask task = new PickAppleTask(appleTrees, 0, SIZE-1);

        int result = pool.invoke(task);

        System.out.println("==============================");
        System.out.println("Total Apples: " + result);

    }

    public static class PickAppleTask extends RecursiveTask<Integer>{
        private final AppleTree[] appleTrees;
        private final int startIndex;
        private final int endIndex;
    
        private final int threadsHold = 4;
    
        public PickAppleTask(AppleTree[] array, int startIndex, int endIndex){
            this.appleTrees = array;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        protected Integer doCompute(){
            System.out.println("Pick apples from " + startIndex +" to "+ endIndex);
            return IntStream.rangeClosed(startIndex, endIndex)
                    .map(i->appleTrees[i].pickApples())
                    .sum();
        }

        @Override
        protected Integer compute() {
            if(endIndex - startIndex < threadsHold){
                return doCompute();
            }
            int midpoint = startIndex + (endIndex - startIndex)/2;

            PickAppleTask leftSum = new PickAppleTask(appleTrees, startIndex, midpoint);
            PickAppleTask rightSum = new PickAppleTask(appleTrees, midpoint+1, endIndex);

            rightSum.fork();
            return leftSum.compute() + rightSum.join();
        }
    }
}


