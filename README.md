# ctl-model-checker

## Contributors
- [Moussa FALL](https://github.com/f-musa)
- [Aboudourazakou TETEREOU](https://github.com/TchaloSon)

## Abstract
This project's objective is to program a model checking software that verifies the validity of a CTL formula on the initial state of a finite automaton.
## Compile
```javac -d classes .\src\checker\*.java .\src\ctl\*.java .\src\ctl\ctlformula\*.java .\src\dot\*.java``` 

## Run
``` java -cp classes checker.CTLChecker example.dot ctlexample.txt``` 
