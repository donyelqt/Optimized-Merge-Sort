import java.util.Arrays;

public class OptimizedMergeSort {

    private static int[] left;
    private static int[] right;
    private static final int INSERTION_SORT_THRESHOLD = 10;  // Experiment with different threshold values

    public static void main(String[] args) {
        int[] array1To1000 = generateAscendingArray(1000);
        int[] array1000To1 = generateDescendingArray(1000);
        int[] arrayRandom = generateRandomArray(1000);

        System.out.println("Trial 1: Input 1 up to 1000");
        runAndMeasureSorting(array1To1000);

        System.out.println("\nTrial 2: Input 1000 down to 1");
        runAndMeasureSorting(array1000To1);

        System.out.println("\nTrial 3: Input 1 to 1000 random");
        runAndMeasureSorting(arrayRandom);

        
    }

    public static void mergeSort(int[] arr) {
        left = new int[arr.length / 2];
        right = new int[arr.length - left.length];

        mergeSortHelper(arr, 0, arr.length - 1);
    }

    private static void mergeSortHelper(int[] arr, int low, int high) {
        if (low < high) {
            if (high - low <= INSERTION_SORT_THRESHOLD) {
                insertionSort(arr, low, high);
            } else {
                int mid = low + (high - low) / 2;

                mergeSortHelper(arr, low, mid);
                mergeSortHelper(arr, mid + 1, high);

                merge(arr, low, mid, high);
            }
        }
    }

    private static void merge(int[] arr, int low, int mid, int high) {
        int n1 = mid - low + 1;
        int n2 = high - mid;

        for (int i = 0; i < n1; i++) {
            left[i] = arr[low + i];
        }
        for (int j = 0; j < n2; j++) {
            right[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = low;

        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = left[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = right[j];
            j++;
            k++;
        }
    }

    private static void insertionSort(int[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= low && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
    }

    public static void runAndMeasureSorting(int[] arr) {
        long startTime = System.currentTimeMillis();
        mergeSort(arr);
        long endTime = System.currentTimeMillis();

        System.out.println("Sorted Array: " + Arrays.toString(arr));
        System.out.println("Execution time: " + (endTime - startTime) + " milliseconds");
    }

    public static int[] generateAscendingArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i + 1;
        }
        return array;
    }

    public static int[] generateDescendingArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i;
        }
        return array;
    }

    public static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * size) + 1;
        }
        return array;
    }
}
