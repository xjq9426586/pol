package wiki;

public class Knapsack {
    public static void main(String[] args) throws Exception {
        int val[] = {10, 40, 30, 50};
        int wt[] = {5, 4, 6, 3};
        int W = 10;

        System.out.println(knapsack(val, wt, W));
    }

    public static int knapsack(int val[], int wt[], int W) {
        //Get the total number of items. 
        //Could be wt.length or val.length. Doesn't matter
        int N = wt.length;

        //Create a matrix. 
        //Items are in rows and weight at in columns +1 on each side
        int[][] V = new int[N + 1][W + 1];

        //What if the knapsack's capacity is 0 - Set
        //all columns at row 0 to be 0
        for (int col = 0; col <= W; col++) {
            V[0][col] = 0;
        }

        //What if there are no items at home.  
        //Fill the first row with 0
        for (int row = 0; row <= N; row++) {
            V[row][0] = 0;
        }

        for (int item = 1; item <= N; item++) {
            for (int weight = 1; weight <= W; weight++) {
                if (wt[item - 1] <= weight) {

                    V[item][weight] = Math.max(val[item - 1] + V[item - 1][weight - wt[item - 1]], V[item - 1][weight]);
                } else {
                    V[item][weight] = V[item - 1][weight];
                }
            }

        }

        //Printing the matrix
        for (int[] rows : V) {
            for (int col : rows) {
                System.out.format("%5d", col);
            }
            System.out.println();
        }

        return V[N][W];
    }
}
