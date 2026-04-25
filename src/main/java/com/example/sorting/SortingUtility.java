package com.example.sorting;

public class SortingUtility {


    public static <T extends Comparable<T>> void gnomeSort(T[] a) {
        // Start at the beginning of the array
        int pos = 0;

        while (pos < a.length) {
            // If we're at the start OR current element is in order, advance forward
            if (pos == 0 || a[pos].compareTo(a[pos - 1]) >= 0) {
                pos = pos + 1;
            } else {
                // Current element is out of order — swap it backward and step back
                swap(a, pos, pos - 1);
                pos = pos - 1;
            }
        }
    }


    public static <T extends Comparable<T>> void cocktailShakerSort(T[] a) {
        boolean swapped;

        do {
            // ── Forward pass (left → right) ──────────────────────────────────────
            // Bubbles the largest unsorted element to the right end,
            // exactly like a single pass of standard bubble sort.
            swapped = false;
            for (int i = 0; i < a.length - 1; i++) {
                // If the left element is greater than its right neighbour, they are
                // out of order — swap them and flag that a change was made.
                if (a[i].compareTo(a[i + 1]) > 0) {
                    swap(a, i, i + 1);
                    swapped = true;
                }
            }

            // ── Early exit ───────────────────────────────────────────────────────
            // If the forward pass made no swaps the array is already fully sorted;
            // skip the backward pass and stop immediately.
            if (!swapped) {
                break;
            }

            // ── Backward pass (right → left) ─────────────────────────────────────
            // Bubbles the smallest unsorted element to the left end.
            // Reset swapped first so the while condition reflects only this pass.
            swapped = false;
            for (int i = a.length - 2; i >= 0; i--) {
                // If the left element is greater than its right neighbour, swap them.
                if (a[i].compareTo(a[i + 1]) > 0) {
                    swap(a, i, i + 1);
                    swapped = true;
                }
            }

            // ── Loop condition ───────────────────────────────────────────────────────
            // If the backward pass also made no swaps, the array is sorted — stop.
            // Otherwise, start the next forward pass.
        } while (swapped);
    }


    public static <T extends Comparable<T>> void shellSort(T[] a) {
        // Ciura gap sequence — empirically derived intervals that minimize
        // comparisons. Largest gap is used first, and we work down to 1,
        // which guarantees a fully sorted array on the final pass.
        int[] gaps = {701, 301, 132, 57, 23, 10, 4, 1};

        int n = a.length;

        // ── Outer loop: iterate through each gap value, largest → smallest ───────
        for (int gap : gaps) {

            // ── Middle loop: gapped insertion sort ───────────────────────────────
            // Treats elements that are `gap` positions apart as a sub-list and
            // insertion-sorts each sub-list. Starting at index `gap` means the
            // first element of every sub-list is trivially "sorted", so we begin
            // by considering the second element of each sub-list (index = gap).
            for (int i = gap; i < n; i += 1) {

                // Save a[i] into temp and leave a logical "hole" at position i.
                // This element will be placed in its correct position below.
                T temp = a[i];

                // ── Inner loop: shift elements right to open the correct slot ─────
                // Walk j backwards in steps of `gap`, shifting each element that
                // is larger than temp one gap-position to the right, until we
                // either reach the start of the sub-list (j < gap) or find an
                // element that is ≤ temp (its correct sorted position).
                int j;
                for (j = i; (j >= gap) && (a[j - gap].compareTo(temp) > 0); j -= gap) {
                    a[j] = a[j - gap];
                }

                // Place temp into the hole left by the shifting above —
                // this is its correctly sorted position within the current sub-list.
                a[j] = temp;
            }
        }
    }

    private static <T extends Comparable<T>> void swap(T[] data, int index1, int index2) {

        T temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;

    }
}





