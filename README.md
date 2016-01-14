metamutator: a muta-mutation tool for Java
===========

[![Build Status](https://travis-ci.org/SpoonLabs/metamutator.svg?branch=master)](https://travis-ci.org/SpoonLabs/metamutator)

```
mvn test
```

In the context of this project, a metaprogram is a program with all mutations inserted at compile-time.
However, the mutations are all desactivated by default, meaning that the metaprogram behaves as the original one.
Mutations can be activated and desactivated one by one at runtime.


Mutation operators
------------------

* Logical comparison (switch between ">", ">=", "!=", etc).
* LoopExpression breaks the loops (no iteration, at 3 iterations, at 100 iterations)
* NumericVariableMetaMutator adds unary operators to numeric values (absolute value, minus, increment (++), decrement (--))
* VariabletoNullMetaMutator sets variables to null at their declaration

Usage
------

Let's assume your project is called `foo` and contains two folders `src/main/java` and `src/test/java` with a test class `PgmTest`.

1) Create the metaprogram

    java -jar spoon.jar -i src/main/java -p metamutator.BinaryOperatorMetaMutator -o spooned
    
2) Compile the metaprogram

    javac `find spooned -name "*.java"`
    
3) Run the test class (with the metaprogram in the classpath and not the original program)

    MutantSearchSpaceExplorator.runMetaProgramWith(PgmTest.class)
    

Credits
-------

Based on awesome code by Carlos Fau and Alejandro Russel (<https://github.com/totemcaf/code-fixer/>)
