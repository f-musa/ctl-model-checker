# ctl-model-checker

## Contributors
- [Moussa FALL](https://github.com/f-musa)
- [Aboudourazakou TETEREOU](https://github.com/TchaloSon)

## Abstract
This project's objective is to program a model checking software that verifies the validity of a CTL formula on the initial state of a finite automaton.

## Compile
On Linux ```javac -d classes ./src/checker/*.java ./src/ctl/*.java ./src/ctl/ctlformula/*.java ./src/dot/*.java``` 

On Windows ```javac -d classes .\src\checker\*.java .\src\ctl\*.java .\src\ctl\ctlformula\*.java .\src\dot\*.java``` 

## Run
``` java -cp classes checker.CTLChecker example.dot ctlexample.txt``` 

## Important: Use of Parentheses After Operators E, A,U, F, G
When using existential (E) and universal (A) quantifiers, as well as the 
"Until" (U) operator in CTL, it is important to enclose the immediate
expressions following these operators in parentheses.

 ### Example : 
For a formula like E true U false, it should be written as E(true) U (false).
This adjustment makes the formula clearer and ensures that each part of the expression is 
correctly interpreted, especially by parsers that require explicit operator.



