package appleTree;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class HandleExceptions{
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

    public static class SomethingWentWrong extends Exception{

    }

    public static class PickAppleTask extends RecursiveTask<Integer>{
        private final AppleTree[] appleTrees;
        private final int startInclusive;
        private final int endInclusive;
    
        private final int taskThreadshold = 4;
    
        public PickAppleTask(AppleTree[] array, int startInclusive, int endInclusive){
            this.appleTrees = array;
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
        protected Integer compute() {
            // throw an exception for every task from the right side
            if(startInclusive >= SIZE/2){
                /*
                 * throw new SomethingWentWrong();
                 * Cannot compile exception from subclass
                 */
                completeExceptionally( new SomethingWentWrong());
            } 


            if(endInclusive - startInclusive < taskThreadshold){
                return doCompute();
            }
            int midpoint = startInclusive + (endInclusive - startInclusive)/2;

            PickAppleTask leftSum = new PickAppleTask(appleTrees, startInclusive, midpoint);
            PickAppleTask rightSum = new PickAppleTask(appleTrees, midpoint+1, endInclusive);

            rightSum.fork();

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            rightSum.cancel(true);
            return leftSum.compute() + rightSum.join();
        }
    }
}