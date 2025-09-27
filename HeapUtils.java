public class HeapUtils {

	public HeapUtils() {
	}

	public static <T extends Comparable<T>> void heapify(T[] arr, boolean isMinHeap, StringBuffer log) {
		int currIndex = arr.length - 1; // last valid index (1-based array)
		// Start from the last parent node and sift down to fix the heap property
		for (int i = currIndex / 2; i >= 1; i--) {
			heapSwap(arr, isMinHeap, log, i, currIndex, "Heapify");
		}
	}

	/**
	 * Sorts the array using heap sort. First builds a max-heap (no logging), then
	 * repeatedly extracts the max element. Logs all swaps made during sorting.
	 */
	public static <T extends Comparable<T>> void heapSort(T[] arr, StringBuffer log) {
		int currIndex = arr.length - 1;
		heapify(arr, false, null);
		for (int i = currIndex; i >= 2; i--) {
			// Move current max (root) to end
			swapper(arr, log, 1, i, "HeapSort");

			// Restore max-heap property in the reduced heap
			heapSwap(arr, false, log, 1, i - 1, "HeapSort");
		}
	}

	/**
	 * Helper method to sift down a node to its correct position in the heap. Works
	 * for both min-heaps and max-heaps.
	 */
	private static <T extends Comparable<T>> void heapSwap(T[] arr, boolean isMinHeap, StringBuffer log, int startIndex,
			int boundIndex, String label) {
		int currNode = startIndex;
		while (2 * currNode <= boundIndex) {
			int nextNode = 2 * currNode; // assume left child
			if (nextNode + 1 <= boundIndex) {
				if (isMinHeap) {
					if (arr[nextNode + 1].compareTo(arr[nextNode]) < 0) {
						nextNode = nextNode + 1;
					}
				} else {
					if (arr[nextNode + 1].compareTo(arr[nextNode]) > 0) {
						nextNode = nextNode + 1;
					}
				}
			}

			boolean swappable;

			// Decide whether to swap based on heap type
			if (isMinHeap) {
				swappable = arr[nextNode].compareTo(arr[currNode]) < 0;
			} else {
				swappable = arr[nextNode].compareTo(arr[currNode]) > 0;
			}

			if (swappable) {
				swapper(arr, log, currNode, nextNode, label);
				currNode = nextNode;
			} else {
				break; // Heap property is satisfied
			}
		}
	}

	/**
	 * Swaps two elements in the array and logs the action if logging is enabled.
	 */
	private static <T> void swapper(T[] arr, StringBuffer log, int startIndex, int nextIndex, String label) {
		T startValue = arr[startIndex];
		T nextValue = arr[nextIndex];
		arr[startIndex] = nextValue;
		arr[nextIndex] = startValue;

		// If logging is enabled, record the swap with a descriptive label
		if (log != null) {
			log.append(label).append(" swap: Swapped index ").append(startIndex).append(" (").append(startValue)
					.append(") with index ").append(nextIndex).append(" (").append(nextValue).append(")\n");
		}
	}
}
