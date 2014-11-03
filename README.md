interview
=========

A coding exercise for an interview.

This repository includes the Java source code (including unit tests) and configuration for a maven project. If you do not have maven installed you can compile the Java sources, run the tests, and run the requested evaluations using 

    compileAndRun.bat

from the command line (or similar commands for your environment).

If you do have maven, it can be built and tested using maven from the command line and basic maven commands. For example:

To delete any previous builds, rebuild, and test:
    
    mvn clean package

To just run the tests you can execute:
    
    mvn test
    
To execute the requested evaluations after creating the packaging from maven, you can run the following:
 
    java -cp ./target/portfolio-0.0.1-SNAPSHOT.jar personal.capital.interview.portfolio.PortfolioEvaluation Aggressive 9.4324 15.675
	java -cp ./target/portfolio-0.0.1-SNAPSHOT.jar personal.capital.interview.portfolio.PortfolioEvaluation Conservative 6.189 6.3438
