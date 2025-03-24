# Project 1

## Course Information

- **Course Name:** CSC 203
- **Instructor:** Professor Vanessa Rivera
- **Term:** 2023-24 Spring Quarter

## Overview

Welcome to the first project of CSC 203! 

In this project, you apply your Java syntax skills by designing and implementing a class to manipulate very large numbers that standard data types like `int` cannot handle.
This class, named `BigNumber`, will represent these large numbers as a custom linked list.
This exercise will assess your ability to abstract and implement intricate logic (in this case, elementary, but not necessarily simple, arithmetic operations) into, well-tested, class methods.

Once complete, you will integrate your class into an existing file-processing program for verification.
This experience is universally useful, as designing new classes to incorporate into existing systems is commonplace in most object-oriented application development scenarios.

> [!Tip]
> 
> Students find projects to be the most time-intensive assessments in the course.
> 
> Plan accordingly and do not underestimate the amount of work involved, especially if you are uncomfortable with prerequisite knowledge.
> 
> Start early to find and resolve unexpected issues before the due dates approach.

## Learning Objectives

In completing this assessment, you will be able to:

- Write a custom linked list Java class. (☕)
- Write non-trivial arithmetic logic utilizing linked lists. (☕)
- Integrate a new class into an existing program. (🛠️)

## Background

### Large Numbers

Java stores `int` type values using 32 bits.
Because Java integers are signed (they can be either positive or negative), one bit is used to determine an integer's sign.
This leaves 31 bits available to represent a single number.
As such, the maximum value that can be represented by a Java `int` is `2 ^ 31 = 2147483648`.
That is, you can't use a *single* `int` to store a value larger than `2147483648` or less than `-2147483648`.

In this project, you will overcome this limitation by storing integers not as type `int` but as a *custom* linked list type you define.
For example, your class might be able to represent the number `1234` as a linked list like so:

![An example linked list diagram for the integer `1234`](linked_list_diagram.png)

Where each numerical value represents a linked list "node" object containing a specific integer value as an instance variable.
Remember, the Java `null` value represents the lack of a memory address for a specific instance.
In this case, it represents the end of the linked list

### Linked Lists

In this project, you will write a custom linked list class named `BigNumber`.
For example, an instance of this class might represent the number `123412341234`.
To achieve this, you will need to construct a `BigNumber` object, consisting of at least a "head" instance variable of a "node" class (partially provided as `BigNumberNode`).
Nodes will represent individual digits of the number and have an `int` instance variable with a value 0 through 9, inclusive.

You are expected to be familiar with the following linked list concepts to complete this project:

- Construction of a linked list
- Insertion of elements to the front (and possibly back) of a linked list
- Iteration over a linked list
- Fold operations on a linked list (to create a String representation of one, for example)

It is your choice to perform these operations iteratively or recursively.

You may choose to use whichever type of linked list you desire: a singly linked list, a doubly linked list, one that keeps track of a "tail", etc.
It is strongly recommended to store nodes in order of lowest to highest order digits as this will make arithmetic calculations easier.
For example, the number `1234` would be represented in the following linked list format as in the previous section:

![An example linked list diagram for the integer `1234`](linked_list_diagram.png)

The "Resources" section below includes a review of linked lists from a Python perspective.

> [!Warning]
>
> You are not allowed to use the built-in `LinkedList`, any other `List`, or `BigInteger` classes, arrays, or strings (outside unrestricted methods) to represent or hold your numbers in this project.

### Arithmetic

The following sections provide tips on how you can carry out the required arithmetic operations with linked lists.

#### Addition

Addition is easily performed by starting at the lowest order digit, and then adding pairs of digits until you reach the end of both numbers.
You will need to take into account carry values.
For example, if you add the digits `6 + 7` you obtain a value `13` which must be separated into the digit `3` and a carry value.

A possible solution may contain simultaneous iteration over two linked lists in this case.

#### Multiplication

Multiplication can be performed the same way as you would do it on paper.
Try a 2- or 3-digit multiplication problem on paper to remind yourself of how this process works:
- For each Nth digit, make a copy of the right operand.
- Multiply the Nth digit of the left operand by each digit in the copy while remembering to carry, then put N zeros on the end of the copy.
- Add all of these copies together to get the result.

A possible solution may contain nested iteration over two linked lists. 

#### Exponentiation

One trivial implementation would be to use repeated multiplication.
For example, `12 ^ 4 = 12 * 12 * 12 * 12`.
However, with large numbers, this becomes impractically slow for even moderately large exponents.

Instead, you must implement the exponentiation by **squaring algorithm**, which takes advantage of the observation that for a positive integer `n`:

|                Formula                |      Case      |
|:-------------------------------------:|:--------------:|
| `x ^ n = x * (x ^ 2) ^ ((n - 1) / 2)` | if `n` is odd  |
|     `x ^ n = (x ^ 2) ^ (n / 2) `      | if `n` is even |

This drastically reduces the number of multiplications that need to be performed and can be used to deal with larger values of `x` or `n`.
You may assume that the exponent `n` can be represented as a basic Java `int`.

### File Processing

Though you do not need to directly work with them, file-handling operations are utilized in this project to read numbers.
For example, the included `FileProcessor` program will open `input1.txt` to read the following text:
```
1 + 2
3 * 4
5 ^ 6
```

A typical loop for reading files in Java utilizes the `BufferedReader` and `FileReading` classes.
The `FileProcessor.main()` method iterates line-by-line while reading a file given as a command-line argument like so:

```java
class FileProcessor {
    // ...
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 'line' now contains the next line in the file
                // ...
            }
        }
    }
}
```

Regarding `FileProcessor`, ***you only need to modify the `parseLineUsingLittleNumber()` and `parseLineUsingBigNumber()` methods.***
These methods both accept a single `String` which represents an arbitrary line from any of the given "input" files.
The methods will *automatically* be called by the included `FileProcessorTests` class.

For example when `FileProcessorTests.testLittleNumber1()` uses `input1.txt`, three calls to `parseUsingLittleNumber()` will be made passing in the strings `"1 + 2"`, `"3 * 4"`, and `"5 ^ 6".

Finally, and rather importantly, ***your grade is entirely dependent on these two methods producing output***.
I cannot assess the functionality of your work otherwise.

> [!Warning]
> 
> You are only required to perform print output in this project and **do not** need your program to perform any writing to a file.

### Regular Expressions

The built-in `String.split()` method is extremely useful for parsing strings.
However, unlike its Python counterpart, it accepts a regular expression (as a string) rather than a standard string.
Also, it returns a Java *array* of strings, not a list.

Regular expressions (or regex) are a separate language whose details are outside the scope of this course.
Most important are the following special regex symbols:
 - `\s`: Represents a whitespace character.
 - `+`: Means to match one (or any number of repetitions) of the preceding character.
 
Here are some examples of the split function and the resultant array:
```java
"banana".split("a"); // Output: ["b", "n", "n"]

"book".split("o"); // includes an empty string in the output, Output: ["b", "", "k"]

"book".split("o+"); // Output: ["b", "k"]

"a   b".split("\\s"); // three spaces separating 'a' and 'b', Output: [ "a", "", "", "b"]
```
Note the usage of double backslashes to escape a single backslash character in `\s`.

More information in regular expressions is provided as a resource in the "Resources" section below.

## Instructions

### Checkpoint

#### Task 1: Complete the Project 1 Instructions Quiz

**🎯Task Goal:** Complete the "🔨 Project 1 (Checkpoint Quiz)" on Canvas.

1. **Complete the Canvas Quiz:** A quiz that asks questions about these instructions is listed under the Project 1 module. Complete this quiz to earn credit for this task.
  - **Note:** You may complete this quiz every 18 hours before its due date.

#### Task 2: Implement "LittleNumber" File Processing 

**🎯Task Goal:** Complete the `parseLineUsingLittleNumber()` method, ensuring `testLittleNumber1` and `testLittleNumber2` pass.

1. **Complete the Method:** Complete each "TODO" comment in `FileProcessor.parseLineUsingLittleNumber()`.
   - **Method Input:** You can expect this method to accept a two-value addition, multiplication, or exponentation operation, as a string, e.g., `"123    + 456"`.
   - **Method Output:** This method should not return any value, but should *print* the result.
     For example, if `"123    + 456"` is passed into this method, it should print the text `123 + 456 = 579`.
     If an empty string or blank line is provided to the function, it should not error or print anything.
2. **Guidelines:** The "TODO" comments included within the method provide guidelines to help you complete the method.
  Additional information is provided here:
   - **String Parsing:** The `String.split()` method makes parsing input strings relatively simple.
     Your goal is to get the values and operation represented in the input string isolated (e.g., `"123"`, `"+"`, and `"456"` in the above example).
     Please see the above "Regular Expression" section for details.
     Remember to ignore empty strings or blank lines.
   - **The "LittleNumber" Class:** You must create and use `LittleNumber` instances to carry out arithmetic for this part.
     Examine the class and determine which methods to use.
   - **Perform the Operation:** You will then need to determine which operation to perform (e.g., addition in the above example).
     You can do so with a simple string comparison.
     Once determined, you will then perform the appropriate operation using a `LittleNumber` class method.
   - **Print Output:** When you perform the operation, the result is a new `LittleNumber` object that represents the arithmetic result.
     Your final step will be to use the data at your disposal to print the required output (e.g., `123 + 456 = 579` in the above example).
3. **Verification:** Once complete, you can run `FileProcessorTests.java`.
   `testLittleNumber1` and `testLittleNumber2` should both pass if you are successful.
4. **Submission:** For completion, you must commit and push your changes to `FileProcessor` to GitHub.
   Additionally, you must submit a screenshot showing proof of your GitHub submission to Canvas.

### Final Submission

#### Task 3: Implement and Test Your "BigNumber" Class

**🎯Task Goal:** Complete your `BigNumber` and supporting classes while achieving 100% unit test coverage.

1. **Complete the "BigNumber" Class:** Implement the BigNumber and any supporting classes.
   This is when you will write your addition, multiplication, and exponentiation methods.
   In addition to your methods and any additional classes, you must complete the included `BigNumber.fromString()` and `BigNumber.toString()` methods, described below:
   - **"FromString":** This method should create and return a `BigNumber` object by passing in a string of a number (e.g. `"12341234"`).
     This is intended to be used when parsing input file lines.
   - **"ToString":** This overridden method should return a string representation of a `BigNumber` object.
     This is intended to be used when printing out results. 
2. **Testing:** You are required to write unit tests in `BigNumberTests` that cover 100% of all your lines of code in `BigNumber` **and** your supporting classes.
   You are **strongly** encouraged to write unit tests as you write your methods.
   You can check how many lines you have covered with your tests by pressing the "Run with Coverages" (or "Shield") button.
   More information is provided as a resource in the "Resources" section.

#### Task 4: Implement "BigNumber" File Processing

**🎯Task Goal:** Complete the `parseLineUsingBigNumber()` method, ensuring "`testBigNumber`" tests pass.

1. **Complete the Method:** Complete each "TODO" comment in `FileProcessor.parseLineUsingBigNumber()`.
   You will follow all the guidelines from "Task 2" except using your `BigNumber` class and methods in place of `LittleNumber`.
2. **Verification:** Once complete, you can run `FileProcessorTests.java`.
   `testBigNumber1`, `testBigNumber2`, `testBigNumber3`, `testBigNumber4`, and `testBigNumber5` should all pass if you are successful.
3. **Submission:** For completion, you must commit and push all your code changes to GitHub.
   Additionally, you must submit a screenshot showing proof of your GitHub submission to Canvas.

## Restrictions

To demonstrate an understanding of our learning objectives, please follow these restrictions or your grade may be adversely affected:

- You may not use nested classes, e.g. for a "Node" class.
  - Each class you define requires its own file.
- You may not use any `List` implementing class, any other built-in collection class, arrays, Streams, `String`, or `BigInteger` anywhere in `BigNumber`, `BigNumberNode`, or any additional classes you write. 
  - Of course, this does not apply to the `BigNumber.fromString()` and `BigNumber.toString()` methods where you may use the `String` type.
- Do not modify the following file(s):
  - `src/FileProcessorTests.java`
  - `src/LittleNumber.java`
  - `input1.txt`
  - `input2.txt`
  - `input3.txt`
  - `input4.txt`
  - `input5.txt`
- Do not modify the following method(s):
  - `FileProcessor.main()`

## Resources

- [Problem Solving with Algorithms and Data Structures in Python: Implementing an Unordered List: Linked Lists](https://runestone.academy/ns/books/published/pythonds/BasicDS/ImplementinganUnorderedListLinkedLists.html): A brief overview of linked lists using Python.
- [Microsoft: Regular Expression Language - Quick Reference (Character Classes)](https://learn.microsoft.com/en-us/dotnet/standard/base-types/regular-expression-language-quick-reference): A list of commonly used regular expression character classes.
- [IntelliJ IDEA: Code Coverage](https://www.jetbrains.com/help/idea/running-test-with-coverage.html#run-config-with-coverage): A tutorial for running programs with code coverage in IntelliJ IDEA.

## Submission

### Checkpoint

For completion of this checkpoint, please complete the following:

1. Complete the Canvas quiz.
2. Commit and push your updated code to your version of this assignment's GitHub repository.
3. Submit a screenshot of your repository on GitHub.com, including your repository name and number of commits, to this assignment's checkpoint submission Canvas page.
   - **Note:** No checkmark will be present.

### Final

For completion of this assignment, please complete the following:

1. Commit and push your updated code to your version of this assignment's GitHub repository.
2. In a submission to this assignment's final submission Canvas page, include the following:
    1. A screenshot of your repository on GitHub.com, including your repository name and number of commits.
       - **Note:** No checkmark will be present.
    2. A screenshot of your unit testing 100% line coverage in your completed classes.

> [!Warning]
>
> It is your responsibility to ensure proper submission of all assignment components according to the assignment instructions before the due date.
>
> Improperly submitted lab assignments will receive a grade of zero.
>
> You are encouraged to verify submission with your instructor if you are ever unsure.

## Academic Integrity

> [!Warning]
>
> Submitting this assignment confirms that you did not use solutions or code from external, AI-generated, or peer sources.
>
> You also agree to have your code checked by standard plagiarism detection software.
>
> Violation will result in a grade penalty, a report to the University, and further potential action.
>
> Please contact me or see our course syllabus for clarification or further details.

## Due Date

Please refer to Canvas for due-date information, if applicable.

## Grading

Please refer to Canvas for additional grading information, if applicable.
