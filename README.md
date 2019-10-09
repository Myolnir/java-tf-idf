# TD-IDF Algorithm

Given a directory, a "word" and a word it implements the TD-IDF algorithm for this files and word.

## Input params

The input for this service will be:

<table>
  <thead>
    <tr>
      <th>Param</th><th>Type</th><th>Description</th>
    </tr>
  </thead>
  <tbody>
<tr>
    <td>directory</td><td><code>String</code></td><td>Directory where we want to check the TD-IDF</td>
    </tr><tr>
    <td>term to analyze</td><td><code>String</code></td><td><p>Term we have to analyze inside of the files</td>
    </tr><tr>
    <td>count</td><td><code>String</code></td><td><p>Number of results we want to show</td>
    </tr><tr>
    <td>period</td><td><code>String</code></td><td><p>Period of time (in seconds) to check the directory and run the algorithm</td>
    </tr><tr>
</table>

As prerequisite the service only checks files with ".txt" extension and I only check the first level of the given directory.

The service is always running unless there are an error and every number of seconds indicated on the *period* parameter
the directory will be checked again and the algorithm re-executed and recalculate the results.

We also sort the results by td-idf value from higher to lower and only shows the number of results that we input on the *count*
parameter.

The complexity of the solution is O(n^2) because to calculate idf part we have two chained loops.

## Installation

1. Download/Clone this repository.
2. Import the downloaded Java Project into some IDE (e.g., IntelliJ, Eclipse, etc.)
3. Run the [TfIdf.java](src/com/myolnir/TfIdf.java) file for Palindrome Logic.
4. Run the [TfIdfCalculatorTest.java](test/com/myolnir/TfIdfCalculatorTest.java) file for JUnit Test Cases.

When you run the program the command line should ask you for several strings (check input params section), 
after you enter all params the program returns the TF_IDF algorithm result for this input and after that it recalculates
after the number of seconds you entered on *period* parameter.

