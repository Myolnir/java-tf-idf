# TD-IDF Algorithm

Given a directory, a "word" and a word it implements the TD-IDF algorithm for this files and word.

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
</table>

As prerequisite the service only checks files with ".txt" extension.

The service is always running unless that there are an error and every time a new txt file is created in the given
directory it will execute again the algorithm and recalculate the results.

We also sort the results by td-idf value from higher to lower and only shows the "n" results that we input on the count
parameter.

The complexity of the solution is O(n^2) because to calculate idf part we have to chained loops.

## Installation

The class is situated on the package com.myolnir so to compile you execute

`javac -d <where do you want to put the class files>/TdIdf.java`


After a couple of seconds you will have a TdIdf.class in the route you put on the above command.

To execute this class you should run:

`java com.myolnir.TdIdf`

The program should run and ask you for a string, after you enter the script the program returns if
the given string is a palindrome or not.

