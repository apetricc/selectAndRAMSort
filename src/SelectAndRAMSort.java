import java.util.Arrays;
import java.util.Random;

/**
 * Created by aj on 9/28/15.
 */
public class SelectAndRAMSort {

/**
    12 points: In the main method, invoke all the methods you've implemented (described below) at least five times each
 on different inputs, for testing purposes.  Write all the inputs and the results into the console. This is the
 "testing" portion of your code.


    10 points extra credit: Define a method named radixSort, implementing a base-10 Radix Sort as in the
 slides/textbook. This will only be a few lines of code to define the radixSort method, but you will also need to
 create some private helper methods:
    A private helper method named getDigit to obtain and return the value of a single given base-10 digit's value
 from a given int value. For example, getDigit(1, 493) would return the value of the "1's place" in 493, which is
 the number 3. You can make that helper method a purely math function, or instead make it with String methods.
    A private helper method named countingSortOnDigit, which is a modified version of countingSort with an additional
 parameter indicating which base-10 digit is to be used for comparisons. (1 for rightmost digit, 2 for second digit
 from the right, etc.) Make sure your countingSort works properly before you make this new version.  The only
 difference will be the line(s) of code that directly compare two elements, which will invoke digitOf and use that
 single digit value, instead of comparing one entire input value with another.
    Do not use the Java Standard Library or other libraries, except you may use:

    console I/O
    the Java String class
            java.util.Arrays.toString
    java.util.Arrays.copyOf
    java.util.Arrays.copyOfRange
    java.util.Random class
            You must implement your methods described above from scratch. Do not copy a solution from any person or
 third party source, but you may use the pseudocode in the textbook and the course webpage. Design and write the code
 yourself. No partial credit on the extra credit; each extra credit problem must be fully correct to receive any extra
 credit.
    **/

    /**
     * 24 points: Define a method named randomizedQuickselect. Implement Randomized Quickselect as in the
     * slides/textbook. One parameter is the input array.  Two more parameters are the inclusive bounds of the subarray
     * to consider. One more parameter is i, the order statistic number to find the value of in the input array.
     * This method must be nondestructive, meaning it does not reorder or modify the original input array!
     * So, you will want to make a local copy of the input array using java.util.Arrays.copyOf, then use the
     * local copy for everything. You will also need to reuse the same partition method from homework 4,
     * which you used for quicksort.  If your partition method is not working properly, I strongly recommend you debug
     * it and get it working correctly before moving on to the rest of this assignment.
     *
     * Pseudocode:
     *
     * RANDOMIZED-QUICKSELECT(A, p, r, i) // one-indexed array A
         if p == r // base case is length-1 subarray
            return A[p]
         z = RANDOM-INT(p, r) //random pivot choice like randomized quicksort
         swap A[z] with A[r]
         q = PARTITION(A, p, r) // the same PARTITION used in quicksort
         k = q - p + 1 // calculate the order statistic k of the pivot
         if i == k // check if pivot is the ith order statistic, our answer!
            return A[q]
         elseif i < k // we’re looking for an earlier order statistic
            return RANDOMIZED-QUICKSELECT(A, p, q-1, i)
         else return RANDOMIZED-QUICKSELECT(A, q+1, r, i-k)

     *
     */

    public int randomizedQuickSelect(int[] A, int p, int r, int i) {
        int[] aCopy = Arrays.copyOf(A, A.length);
        if (p == r) return aCopy[p];
        Random myRand = new Random();
        int z = myRand.nextInt(r - p + 1) + p;
        int swap = aCopy[z];
        aCopy[z] = aCopy[r];
        aCopy[r] = swap;
        int q = partition(aCopy, p, r);
        int k = q - p + 1;
        if (i == k) return aCopy[q];
        else if (i < k) return randomizedQuickSelect(aCopy, p, q - 1, i);
        else return randomizedQuickSelect(aCopy, q + 1, r, i - k);
    }
    /**
     * partition is a helper method for the sorting methods quicksort and randomizedQuicksort which functions
     * to partition an array into smaller sub-arrays that can be sorted recursively.
     * @param arr The array to partition.
     * @param p   The starting point (lower bounds) index of the sub-array to partition.
     * @param r   The ending point (upper bounds) index of the sub-array to partition.
     * @return the index to be passed to the calling method
     */
    private static int partition(int[] arr, int p, int r) {
        int x = arr[r];
        int i = p - 1;
        for (int j = p;j < r;j++) {
            if (arr[j] <= x) {
                i++;
                int swap = arr[i];
                arr[i] = arr[j];
                arr[j] = swap;
            }
        }
        int swap2 = arr[i + 1];
        arr[i + 1] = arr[r];
        arr[r] = swap2;
        return i + 1;
    }
    /**
     * arrayPrinter is a helper method to print the contents of an array of ints.
     * @param arr The array to be printed.
     * @return a String that can be printed with System.out.print
     */
    public static String arrayPrinter(int[] arr){
        String result = "";
        for (int i = 0; i < arr.length; i++) {
            result += "[" + arr[i] + "]";
        }
        return result;
    }









    /**
     *  24 points: Define a method named countingSort. Implement Counting Sort as in the slides/textbook. One parameter
     will be the input array to sort. Another parameter will be a memory allocated array of the same length, for storing
     the output. The third parameter will be the largest single input value. You may write your code one-indexed or
     zero-indexed, but be aware the pseudocode one-indexes the input and output while zero-indexing the auxiliary array C.


     COUNTING-SORT(A, B, k)
         declare new array C[0...k] // named C, indices 0 to k, length k+1
         for i = 0 to k // Step 0: initialize C to all 0’s
            C[i] = 0
         for i = 1 to A.length // Step 1: count occurrences of each input val
            C[A[i]]++          // So, each C[i] tallies # input values == i
         for i = 1 to k // Step 2: Each C[i] will tally # input values <= i
            C[i] = C[i] + C[i-1]
         for i = A.length downto 1 // Step 3: scan back through A, writing B
            B[C[A[i]]] = A[i]
            C[A[i]]--


     okay, so if for example A[] = {1,2,3,4}
                             B[] = {1,2,3,4}
                             C[1]++,C[2]++,C[3]++ up to the last index of A[]
     **/
public void countingSort(int[] A,int[] B, int largest) {
    int[] C = new int[B.length];
    for (int i = 0; i < C.length; i++) {
        C[i] = 0;
    }
    for (int i = 0; i < A.length; i++) {
        C[A[i]]++;
    }
    for (int i = 0; i < C.length; i++) {
        C[i] = C[i] + C[i - 1];
    }
    for (int i = A.length - 1; i > -1; i--) {
        B[C[A[i]]] = A[i];
        C[A[i]]--;
    }
}

    /**
     * 10 points extra credit: Define a method named radixSort, implementing a base-10 Radix Sort as in the
     slides/textbook. This will only be a few lines of code to define the radixSort method, but you will also need to
     create some private helper methods:
     A private helper method named getDigit to obtain and return the value of a single given base-10 digit's value
     from a given int value. For example, getDigit(1, 493) would return the value of the "1's place" in 493, which is
     the number 3. You can make that helper method a purely math function, or instead make it with String methods.
     A private helper method named countingSortOnDigit, which is a modified version of countingSort with an additional
     parameter indicating which base-10 digit is to be used for comparisons. (1 for rightmost digit, 2 for second digit
     from the right, etc.) Make sure your countingSort works properly before you make this new version.  The only
     difference will be the line(s) of code that directly compare two elements, which will invoke digitOf and use that
     single digit value, instead of comparing one entire input value with another.



     RADIX-SORT(A, d)
         for i = 1 to d
         invoke a stable sorting algorithm to sort array A on digit i

     */

    public void radixSort(int[] A, int d) {
        for (int i = 0; i <= d; i++) {
            //invode stable sorting algorithm to sort array on digit 1
        }
    }
    private int getDigit(int place, int target) {
        int digit = -1;
        if (place > target) return digit;
        return digit;
    }

    public static void main(String[] args) {
        int[] arr1 = {2,4,1,5,6,7,8};

        System.out.println("arr1 starts out like this: "+arrayPrinter(arr1));
        countingSort(arr1, arr2, 8);

    }

}
