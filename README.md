metamutator: a meta-mutation tool for Java
===========

[![Build Status](https://travis-ci.org/SpoonLabs/metamutator.svg?branch=master)](https://travis-ci.org/SpoonLabs/metamutator)

metamutator takes as input a program and produces a metaprogram which contains all possible mutations inserted at compile-time according to a mutant schemata. It is a Java implementation of ["Mutation analysis using mutant schemata"](http://cs.gmu.edu/~offutt/rsrch/papers/schema.pdf). 
However, the mutations are all deactivated by default, meaning that the metaprogram behaves as the original one.
Metamutants can be activated and deactivated one by one at runtime.


Mutation operators
------------------

* Logical comparison (switch between ">", ">=", "!=", etc) (`LogicalExpressionMetaMutator`).
* Breaks the loops (no iteration, at 3 iterations, at 100 iterations) (`LoopExpressionMetaMutator`)
* Adds unary operators to numeric values (absolute value, minus, increment (++), decrement (--)) (`NumericVariableMetaMutator`)
* Sets variables to null at their declaration (`VariabletoNullMetaMutator`)
* Replaces integer constants by 0, INT_MAX (`IntegerConstantReplacementMetaMutator`)
* Metamutates return statements that returns Boolean (`ReturnReplacementOperatorMetaMutator`)
* Skips statements (statement deletion operator) (`StatementDeletionMetaMutator`)

Usage
------

Let's assume your project is called `foo` and contains two folders `src/main/java` and `src/test/java` with a test class `PgmTest`.

Install metamutator

    git clone https://github.com/SpoonLabs/metamutator.git
    cd metamutator
    mvn package
    # now we have target/metamutator-*-jar-with-dependencies.jar
    
    
Create the metaprogram

    java -jar target/metamutator-*-jar-with-dependencies.jar -i src/main/java -p metamutator.LogicalExpressionMetaMutator -o spooned
    
Compile the metaprogram

    javac `find spooned -name "*.java"`
    
Run the test class (with the metaprogram in the classpath and not the original program)

    MutantSearchSpaceExplorator.runMetaProgramWith(PgmTest.class)
    

Credits
-------

Based on awesome code by Carlos Fau and Alejandro Russel (<https://github.com/totemcaf/code-fixer/>)

Great contributions from University of Lille's students enrolled in IAGL 2015-2016.

