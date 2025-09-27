import java.util.Arrays;

public class PriorityQueue<T extends Comparable<T>> {
	private T[] heap;
	private int size;
	private boolean isMinHeap;

	/**
	 * Constructor that builds a heap from an existing array. Validates the input
	 * and sets up the heap structure.
	 */
	public PriorityQueue(T[] arr, boolean isMinHeap) {
		if (arr == null || arr.length <= 1 || arr[0] != null) {
			throw new IllegalArgumentException("Invalid input array.");
		}
		this.heap = arr;
		this.isMinHeap = isMinHeap;
		this.size = arr.length - 1; // assume the rest of the array is filled
		heapify(); // build a valid heap (no logging here)
	}

	/**
	 * Returns the element at the root of the heap (highest priority). Throws an
	 * exception if the heap is empty.
	 */
	public T peek() {
		if (size == 0) {
			throw new IllegalStateException("PriorityQueue is empty.");
		}
		return heap[1]; // root of the heap
	}

	/**
	 * Removes and returns the root element (highest priority). Logs swaps and final
	 * heap state after the operation.
	 */
	public T remove(StringBuffer log) {
		if (size == 0) {
			throw new IllegalStateException("PriorityQueue is empty.");
		}
		T removedValue = heap[1];

		// Swap the root with the last element (we're about to remove the root)
		swap(1, size, log, true);
		heap[size] = null;
		size--;
		heapSwapDown(1, size, log); // fix the heap from the root down

		// Log the final state of the heap after removal
		if (log != null) {
			log.append("Array after removal: ").append(Arrays.toString(heap)).append("\n");
		}

		return removedValue;
	}

	/**
	 * Inserts a new element into the heap. Logs swaps and final heap state after
	 * the operation.
	 */
	public void insert(T item, StringBuffer log) {
		if (size == heap.length - 1) {
			throw new IllegalStateException("PriorityQueue is full. Cannot insert.");
		}

		size++;
		heap[size] = item;
		heapSwapUp(size, log);

		// Log the final state of the heap after insertion
		if (log != null) {
			log.append("Array after insertion: ").append(Arrays.toString(heap)).append("\n");
		}
	}

	/**
	 * Turns the array into a proper heap (starting from the bottom up). Called once
	 * during construction, no logging is done here.
	 */
	private void heapify() {
		// Start from the last non-leaf node and fix downwards
		for (int i = size / 2; i >= 1; i--) {
			heapSwapDown(i, size, null); // no logging
		}
	}

	/**
	 * Moves a node up the heap until the heap property is restored. Used after
	 * inserting a new element.
	 */
	private void heapSwapUp(int startIndex, StringBuffer log) {
		while (startIndex > 1) {
			int current = startIndex / 2; // parent index

			// If the child should be above the parent, swap
			if (compare(heap[startIndex], heap[current])) {
				swap(startIndex, current, log, true);
				startIndex = current; // continue up the tree
			} else {
				break;
			}
		}
	}

	/**
	 * Moves a node down the heap until the heap property is restored. Used after
	 * removing the root.
	 */
	private void heapSwapDown(int startIndex, int boundIndex, StringBuffer log) {
		while (2 * startIndex <= boundIndex) {
			int next = 2 * startIndex; // left child

			// If there's a right child and it's a better candidate, choose it
			if (next + 1 <= boundIndex && compare(heap[next + 1], heap[next])) {
				next++;
			}

			// If child has higher priority than parent, swap
			if (compare(heap[next], heap[startIndex])) {
				swap(startIndex, next, log, true);
				startIndex = next;
			} else {
				break;
			}
		}
	}

	/**
	 * Swaps two elements in the heap and optionally logs the swap.
	 */
	private void swap(int currIndex, int nextIndex, StringBuffer log, boolean logSwap) {
		T tempValue = heap[currIndex];
		heap[currIndex] = heap[nextIndex];
		heap[nextIndex] = tempValue;

		// Log the swap if logging is enabled
		if (log != null && logSwap) {
			log.append("Swapped index ").append(currIndex).append(" (").append(heap[nextIndex]).append(") with index ")
					.append(nextIndex).append(" (").append(heap[currIndex]).append(")\n");
		}
	}

	/**
	 * Compares two elements based on whether this is a min-heap or max-heap.
	 * Returns true if val1 has higher priority than val2.
	 */
	private boolean compare(T val1, T val2) {
		if (isMinHeap) {
			return val1.compareTo(val2) < 0;
		} else {
			return val1.compareTo(val2) > 0;
		}
	}
}
