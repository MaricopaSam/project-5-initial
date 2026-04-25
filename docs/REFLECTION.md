For all three algorithms, Claude produced working implementations on the first prompt. Each version had their variable names
match up exactly. While all ran on the first attempt, that does not mean correct on the first attempt. It was only after review and with the
tests that issues were found.

The most significant issue discovered was an issue in the Cocktail Shaker Sort. The original pseudocode showed the loop bound as length(a)-1, but accessing a[i+1] while i== length -1 would be out of bounds. Claude did identify this issue and fixed it but it was only found after looking over the lines of code myself.
Another issue was with the corrected bound when it was changed to i < a.length - 2. This caused 2 unit tests to fail. This was because the last pair of elements
never compared in the forward pass. The code had to be revises to i < a.length - 1.

I had heard of algorithms before this project but had never done anything with them before. Infact, I found the names of the different
sorting algorithms strange. Why was it called Gnome? Claude was helpful in helping me understand what I was working with. But I also had to verify with what I knew.
Having to check each result that the variables used in the pseudocode were kept the same in the actual implementation.

I do think that relying on AI did affect my understanding of these algorithms and how they work. I could tell you how they work, in the broad strokes.
But in the actual implementation I am more shaky. Since I felt that this faster, I had to make good prompts and check the work done. However, I don't feel like it is fully my work.
Since it was Claude making the code. I asked for it sure and I checked it.

Thanks to the comments provided by Claude, I find the code readable and well-structured.

Claude handled integer and string test cases when I asked for testing with comparable types.

I did not have any issues with efficiency or unnecessary complexity.

When making my prompts, I mainly focused on what was provided in the README. Taking the objectives there and what was being asked
and translating it into an understandable prompt with the correct guidelines. I ensured that Claude used the exact variable names by stating it several time and having it be in all uppercase.
Next Time, I think I would want to compress my prompts down. later prompts ended up being larger and I feel a smaller prompt that is pointed enough might be better.

My prompt for the test cases covered the required categories for the project and then added algorithm specific test on top of them.

The starting tests for Cocktail Shaker did not have tests for large elements on the left and small on the far right at the same time.
Claude added rabbitAndTurtle to verify the both passes do their job.

Tests were validating correct behavior. This can be proven in our test failures. Two Cocktail Shaker tests failed and caught an error in the implementation. The forward pass was stopping one position too early.
If the tests were unreliable, they would have passed regardless.

There was some overlap between "minimum element at end" test and the "random order" test for Gnome Sort. But the Gnome one is for making sure that the backtracking is going all the way back to 0 from a certain input.

Claude introduced a rabbit/turtle terminology for the Cocktail shaker sort. Which was used to explain the bidirectional sweep.


