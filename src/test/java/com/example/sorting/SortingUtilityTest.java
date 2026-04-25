package com.example.sorting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Unit tests for SortingUtility.
 *
 * Each algorithm gets its own @Nested class, keeping shared category names
 * (empty, single, alreadySorted, …) while allowing algorithm-specific tests
 * that probe behaviour unique to that sort (e.g. gnome backtracking,
 * cocktail bidirectional sweeps).
 *
 * All helpers return a FRESH array so one test never pollutes another.
 */
public class SortingUtilityTest {

    // ══════════════════════════════════════════════════════════════════════════
    //  GNOME SORT
    // ══════════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("Gnome Sort")
    class GnomeSortTests {

        // ── Standard categories ───────────────────────────────────────────────

        @Test
        @DisplayName("Empty array — pos starts at 0, while-condition is immediately false, nothing happens")
        void emptyArray() {
            Integer[] a = {};
            SortingUtility.gnomeSort(a);
            assertArrayEquals(new Integer[]{}, a);
        }

        @Test
        @DisplayName("Single element — pos advances to 1, loop exits; no comparisons or swaps needed")
        void singleElement() {
            Integer[] a = {5};
            SortingUtility.gnomeSort(a);
            assertArrayEquals(new Integer[]{5}, a);
        }

        @Test
        @DisplayName("Already sorted — pos never backtracks; each step satisfies a[pos] >= a[pos-1]")
        void alreadySorted() {
            Integer[] a = {1, 2, 3, 4, 5};
            SortingUtility.gnomeSort(a);
            assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, a);
        }

        @Test
        @DisplayName("Reverse sorted — worst case; pos backtracks all the way to 0 repeatedly")
        void reverseSorted() {
            Integer[] a = {5, 4, 3, 2, 1};
            SortingUtility.gnomeSort(a);
            assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, a);
        }

        @Test
        @DisplayName("Duplicates — equal elements satisfy a[pos] >= a[pos-1], so no unnecessary swaps")
        void duplicates() {
            Integer[] a = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
            SortingUtility.gnomeSort(a);
            assertArrayEquals(new Integer[]{1, 1, 2, 3, 3, 4, 5, 5, 6, 9}, a);
        }

        @Test
        @DisplayName("All same elements — every comparison is >= so pos only ever advances forward")
        void allSameElements() {
            Integer[] a = {5, 5, 5, 5, 5};
            SortingUtility.gnomeSort(a);
            assertArrayEquals(new Integer[]{5, 5, 5, 5, 5}, a);
        }

        @Test
        @DisplayName("Two elements swapped — pos=1 backtracks to 0, swaps, then advances past end")
        void twoElementsSwapped() {
            Integer[] a = {2, 1};
            SortingUtility.gnomeSort(a);
            assertArrayEquals(new Integer[]{1, 2}, a);
        }

        @Test
        @DisplayName("Two elements already sorted — pos advances straight through without backtracking")
        void twoElementsSorted() {
            Integer[] a = {1, 2};
            SortingUtility.gnomeSort(a);
            assertArrayEquals(new Integer[]{1, 2}, a);
        }

        @Test
        @DisplayName("Random order — general correctness across a typical unsorted dataset")
        void randomOrder() {
            Integer[] a = {64, 34, 25, 12, 22, 11, 90};
            SortingUtility.gnomeSort(a);
            assertArrayEquals(new Integer[]{11, 12, 22, 25, 34, 64, 90}, a);
        }

        @Test
        @DisplayName("String array — validates generic Comparable behaviour (lexicographic order)")
        void stringArray() {
            String[] a = {"zebra", "apple", "mango", "banana"};
            SortingUtility.gnomeSort(a);
            assertArrayEquals(new String[]{"apple", "banana", "mango", "zebra"}, a);
        }

        // ── Gnome-specific tests ──────────────────────────────────────────────

        @Test
        @DisplayName("Gnome-specific: minimum element at end forces maximum backtracking to pos=0")
        void minElementAtEnd_maximumBacktrack() {
            // 1 is the smallest but sits at the far right.
            // gnome must drag it all the way back to index 0,
            // hitting the pos==0 guard condition on the way.
            Integer[] a = {2, 3, 4, 5, 1};
            SortingUtility.gnomeSort(a);
            assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, a);
        }

        @Test
        @DisplayName("Gnome-specific: String array with mixed-case — compareTo is case-sensitive (uppercase < lowercase in ASCII)")
        void stringsMixedCase() {
            // In Java, 'A'(65) < 'a'(97), so "Apple" < "banana" lexicographically.
            String[] a = {"mango", "Apple", "banana", "Zebra"};
            SortingUtility.gnomeSort(a);
            // Expected order follows Character.compareTo natural ordering
            assertArrayEquals(new String[]{"Apple", "Zebra", "banana", "mango"}, a);
        }

        @Test
        @DisplayName("Gnome-specific: large sorted block followed by one small element — tests repeated backtracking")
        void sortedBlockThenSmall() {
            // pos advances through 10, 20, 30, 40 with no backtracking,
            // then hits 5 and must backtrack all the way to pos=0.
            Integer[] a = {10, 20, 30, 40, 5};
            SortingUtility.gnomeSort(a);
            assertArrayEquals(new Integer[]{5, 10, 20, 30, 40}, a);
        }
    }


    // ══════════════════════════════════════════════════════════════════════════
    //  COCKTAIL SHAKER SORT
    // ══════════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("Cocktail Shaker Sort")
    class CocktailShakerSortTests {

        // ── Standard categories ───────────────────────────────────────────────

        @Test
        @DisplayName("Empty array — do-while body runs once; forward loop condition is false immediately")
        void emptyArray() {
            Integer[] a = {};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new Integer[]{}, a);
        }

        @Test
        @DisplayName("Single element — forward loop body never executes; swapped stays false, loop exits")
        void singleElement() {
            Integer[] a = {5};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new Integer[]{5}, a);
        }

        @Test
        @DisplayName("Already sorted — forward pass sets swapped=false, early-exit break fires immediately")
        void alreadySorted() {
            Integer[] a = {1, 2, 3, 4, 5};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, a);
        }

        @Test
        @DisplayName("Reverse sorted — worst case; both forward and backward passes swap on every iteration")
        void reverseSorted() {
            Integer[] a = {5, 4, 3, 2, 1};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, a);
        }

        @Test
        @DisplayName("Duplicates — equal elements are never swapped; correct order is still produced")
        void duplicates() {
            Integer[] a = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new Integer[]{1, 1, 2, 3, 3, 4, 5, 5, 6, 9}, a);
        }

        @Test
        @DisplayName("All same elements — no swaps on forward pass; early-exit triggers after first pass")
        void allSameElements() {
            Integer[] a = {5, 5, 5, 5, 5};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new Integer[]{5, 5, 5, 5, 5}, a);
        }

        @Test
        @DisplayName("Two elements swapped — forward pass swaps them; backward pass finds nothing; loop ends")
        void twoElementsSwapped() {
            Integer[] a = {2, 1};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new Integer[]{1, 2}, a);
        }

        @Test
        @DisplayName("Two elements already sorted — forward pass makes no swap; early-exit fires")
        void twoElementsSorted() {
            Integer[] a = {1, 2};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new Integer[]{1, 2}, a);
        }

        @Test
        @DisplayName("Random order — general correctness across a typical unsorted dataset")
        void randomOrder() {
            Integer[] a = {64, 34, 25, 12, 22, 11, 90};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new Integer[]{11, 12, 22, 25, 34, 64, 90}, a);
        }

        @Test
        @DisplayName("String array — validates generic Comparable behaviour (lexicographic order)")
        void stringArray() {
            String[] a = {"zebra", "apple", "mango", "banana"};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new String[]{"apple", "banana", "mango", "zebra"}, a);
        }

        // ── Cocktail-specific tests ───────────────────────────────────────────

        @Test
        @DisplayName("Cocktail-specific: turtle element (small value at far right) — backward pass pulls it left efficiently")
        void turtleElementAtEnd() {
            // Standard bubble sort is slow on 'turtles' (small values stuck at the right).
            // The cocktail backward pass fixes this in far fewer iterations than
            // plain bubble sort would require.
            Integer[] a = {2, 3, 4, 5, 1};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, a);
        }

        @Test
        @DisplayName("Cocktail-specific: rabbit element (large value at far left) — forward pass pushes it right efficiently")
        void rabbitElementAtStart() {
            // A large element at position 0 is a 'rabbit' — the forward pass
            // bubbles it all the way to the right end in a single sweep.
            Integer[] a = {99, 1, 2, 3, 4};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new Integer[]{1, 2, 3, 4, 99}, a);
        }

        @Test
        @DisplayName("Cocktail-specific: both a rabbit AND a turtle — tests that each directional pass does its job independently")
        void rabbitAndTurtle() {
            // 99 (rabbit) at start must go right; 1 (turtle) at end must go left.
            // Forward pass handles 99; backward pass handles 1 — both in one do-while cycle.
            Integer[] a = {99, 3, 4, 5, 1};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new Integer[]{1, 3, 4, 5, 99}, a);
        }

        @Test
        @DisplayName("Cocktail-specific: String array — bidirectional sweep works correctly on Comparable Strings")
        void stringsWithTurtleAndRabbit() {
            // "zebra" is the rabbit (large, at left); "apple" is the turtle (small, at right).
            // Verifies the bidirectional logic is not integer-specific.
            String[] a = {"zebra", "mango", "cherry", "apple"};
            SortingUtility.cocktailShakerSort(a);
            assertArrayEquals(new String[]{"apple", "cherry", "mango", "zebra"}, a);
        }
    }


    // ══════════════════════════════════════════════════════════════════════════
    //  SHELL SORT
    // ══════════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("Shell Sort")
    class ShellSortTests {

        // ── Standard categories ───────────────────────────────────────────────

        @Test
        @DisplayName("Empty array — all gap-pass loops have n=0, nothing executes")
        void emptyArray() {
            Integer[] a = {};
            SortingUtility.shellSort(a);
            assertArrayEquals(new Integer[]{}, a);
        }

        @Test
        @DisplayName("Single element — every gap >= n, so the middle for-loop body never runs")
        void singleElement() {
            Integer[] a = {5};
            SortingUtility.shellSort(a);
            assertArrayEquals(new Integer[]{5}, a);
        }

        @Test
        @DisplayName("Already sorted — inner loop condition (a[j-gap] > temp) is never true; no shifts occur")
        void alreadySorted() {
            Integer[] a = {1, 2, 3, 4, 5};
            SortingUtility.shellSort(a);
            assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, a);
        }

        @Test
        @DisplayName("Reverse sorted — worst case; every gap pass must shift elements significantly")
        void reverseSorted() {
            Integer[] a = {5, 4, 3, 2, 1};
            SortingUtility.shellSort(a);
            assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, a);
        }

        @Test
        @DisplayName("Duplicates — equal elements satisfy !(a[j-gap] > temp) so they are never shifted past each other")
        void duplicates() {
            Integer[] a = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
            SortingUtility.shellSort(a);
            assertArrayEquals(new Integer[]{1, 1, 2, 3, 3, 4, 5, 5, 6, 9}, a);
        }

        @Test
        @DisplayName("All same elements — inner shift condition is never triggered; array unchanged")
        void allSameElements() {
            Integer[] a = {5, 5, 5, 5, 5};
            SortingUtility.shellSort(a);
            assertArrayEquals(new Integer[]{5, 5, 5, 5, 5}, a);
        }

        @Test
        @DisplayName("Two elements swapped — gap=1 pass acts as plain insertion sort and corrects them")
        void twoElementsSwapped() {
            Integer[] a = {2, 1};
            SortingUtility.shellSort(a);
            assertArrayEquals(new Integer[]{1, 2}, a);
        }

        @Test
        @DisplayName("Two elements already sorted — inner condition false; temp placed back in same position")
        void twoElementsSorted() {
            Integer[] a = {1, 2};
            SortingUtility.shellSort(a);
            assertArrayEquals(new Integer[]{1, 2}, a);
        }

        @Test
        @DisplayName("Random order — general correctness across a typical unsorted dataset")
        void randomOrder() {
            Integer[] a = {64, 34, 25, 12, 22, 11, 90};
            SortingUtility.shellSort(a);
            assertArrayEquals(new Integer[]{11, 12, 22, 25, 34, 64, 90}, a);
        }

        @Test
        @DisplayName("String array — validates generic Comparable behaviour (lexicographic order)")
        void stringArray() {
            String[] a = {"zebra", "apple", "mango", "banana"};
            SortingUtility.shellSort(a);
            assertArrayEquals(new String[]{"apple", "banana", "mango", "zebra"}, a);
        }

        // ── Shell-specific tests ──────────────────────────────────────────────

        @Test
        @DisplayName("Shell-specific: large array (>701 elements) — exercises the largest Ciura gap (701) meaningfully")
        void largeArray() {
            // Build a reverse-sorted array of 800 elements so every Ciura gap is active.
            // This is the only test among the three sorts that actually triggers gap=701.
            Integer[] a = new Integer[800];
            for (int i = 0; i < 800; i++) a[i] = 800 - i;

            Integer[] expected = new Integer[800];
            for (int i = 0; i < 800; i++) expected[i] = i + 1;

            SortingUtility.shellSort(a);
            assertArrayEquals(expected, a);
        }

        @Test
        @DisplayName("Shell-specific: j scoping — element that must shift across multiple gap steps lands in correct slot")
        void multiStepShift() {
            // 1 is far out of place; with gap=4 then gap=1 it must be shifted
            // across several sub-list positions. Validates that j is correctly
            // declared outside the inner loop so a[j]=temp places it accurately.
            Integer[] a = {9, 8, 7, 6, 1};
            SortingUtility.shellSort(a);
            assertArrayEquals(new Integer[]{1, 6, 7, 8, 9}, a);
        }

        @Test
        @DisplayName("Shell-specific: all same Strings — confirms compareTo==0 never triggers a shift")
        void allSameStrings() {
            String[] a = {"mango", "mango", "mango"};
            SortingUtility.shellSort(a);
            assertArrayEquals(new String[]{"mango", "mango", "mango"}, a);
        }

        @Test
        @DisplayName("Shell-specific: interleaved duplicates — gap passes correctly sort across non-adjacent duplicate values")
        void interleavedDuplicates() {
            // Alternating 5s and 1s mean every gap pass has work to do across sub-lists.
            Integer[] a = {5, 1, 5, 1, 5, 1};
            SortingUtility.shellSort(a);
            assertArrayEquals(new Integer[]{1, 1, 1, 5, 5, 5}, a);
        }
    }
}
