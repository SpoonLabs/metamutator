metamutator: a muta-mutation tool for Java
===========

[![Build Status](https://travis-ci.org/SpoonLabs/metamutator.svg?branch=master)](https://travis-ci.org/SpoonLabs/metamutator)

```
mvn test
```

Mutation operators
------------------

Logical comparison (switch between ">", ">=", "!=", etc).

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