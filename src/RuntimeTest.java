import java.util.Random;
/** Code that allows us to test how the probability of promoting a newly added element to the next skiplist
 * height level affects the runtime of the 'find()' method. For our purposes, we only count 1) moving
 * over an element within the same list level and 2) moving down to a lower list level as operations*/
public class RuntimeTest {
    private Random rand;

    //method that calls the runTest method five times.
    public void go(double p) {
        rand = new Random(42);
        int n = 2097152; // 2^21
        for(int j = 0; j < 5; j++) {
            runTest(n, p);
        }
    }
    // Method to create new skiplist with specified size and probability of promoting added elements to
    // next unoccupied list height.
    public void runTest(int n, double p) {
        SkipList<Integer> list = new SkipList<Integer>(p);
        for(int i = 0; i <= n; i++) {
            list.add(rand.nextInt(n*10));
            if((i & (i - 1)) == 0){
                list.find(rand.nextInt(n*10));
                System.out.print(list.getOps() + " ");
            }
        }
        System.out.println();
    }

    // main method to test runtime of 'find()'. Takes in a float between 0 and 1 from the command line
    // to determine probability of promoting newly added element. If no argument is provided, defaults
    // to 0.5
    public static void main(String[] args) {
        double p = 0.5;
        if(args.length > 0) {
            try{
                p = Double.parseDouble(args[0]);
            }
            catch(NumberFormatException e) {
                System.out.println("No argument given, using p = 0.5.");
            }
        }

        RuntimeTest r = new RuntimeTest();
        r.go(p);
    }
}
