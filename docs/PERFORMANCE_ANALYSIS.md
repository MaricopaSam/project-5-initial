Ran the Sorting Driver with the default large dataset. All three algorithms correctly sorted the array, the first 10 elements of every sorted result were identical.

Gnome Sort: 176.226 ms and 39% of total
Cocktail Shaker Sort: 275.1792 ms and 60% of total
Shell Sort: 4.0747 ms and 01% of sort

Gnome sort performed better than Cocktail Shaker Sort despite both being o(n^2).

Cocktail Shaker Sort improves on bubble sort by going both left to right and right to left in each iteration. This bidirectional approach is good on arrays with a skew on the right. 
Despite this, Cocktail Shaker Sort was the slowest algorithm out of three with the dataset given at 275.1792 ms. This is most likely because of how large the
random dataset was. Every element was out of place so both passes had to do a great amount of work on every single iteration. On a dataset that is partly sorted, Cocktail Shaker would have performed better and outdone Gnome Sort.

The Shell sort did much better than the other two algorithms with a 4.0747 ms.

The results would change depending on how sorted the data is and how large it is. In a large set like this, Shell sort does the best while Cocktail does the worst.
However, in a nearly sorted set, Cocktail would do the best with Gnome doing the worst. A reverse sorted would have a Shell Sort doing the best.