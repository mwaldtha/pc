interview
=========

A coding exercise for an interview.

This repository includes the Java source code (including unit tests) and configuration for a maven project. If you do not have maven installed you can compile the Java sources, run the tests, and run the default evaluation using 

    compileAndRun.bat

from the command line (or similar commands for your environment).

If you do have maven, it can be built and tested using maven from the command line and basic maven commands. For example:

To delete any previous builds, rebuild, and test:
    
    mvn clean package

To just run the tests you can execute:
    
    mvn test