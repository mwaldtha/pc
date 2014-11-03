javac -d bin -classpath bin src/main/java/personal/capital/interview/portfolio/*.java
javac -d bin -classpath bin;./lib/testng-6.8.jar;./lib/powermock-core-1.5.6.jar;./lib/powermock-module-testng-1.5.6.jar;./lib/powermock-api-mockito-1.5.6.jar;./lib/powermock-module-testng-common-1.5.6.jar;./lib/powermock-api-support-1.5.6.jar;./lib/mockito-all-1.9.5.jar;./lib/powermock-reflect-1.5.6.jar;./lib/jcommander-1.27.jar;./lib/bsh-2.0b4.jar;./lib/javassist-3.18.2-GA.jar;./lib/objenesis-2.1.jar; src/test/java/personal/capital/interview/portfolio/*.java

java -noverify -cp ./lib/testng-6.8.jar;./lib/powermock-core-1.5.6.jar;./lib/powermock-module-testng-1.5.6.jar;./lib/powermock-api-mockito-1.5.6.jar;./lib/powermock-module-testng-common-1.5.6.jar;./lib/powermock-api-support-1.5.6.jar;./lib/mockito-all-1.9.5.jar;./lib/powermock-reflect-1.5.6.jar;./lib/jcommander-1.27.jar;./lib/bsh-2.0b4.jar;./lib/javassist-3.18.2-GA.jar;./lib/objenesis-2.1.jar;./bin; org.testng.TestNG testng.xml

java -cp ./bin; personal.capital.interview.portfolio.PortfolioEvaluation Aggressive 9.4324 15.675
java -cp ./bin; personal.capital.interview.portfolio.PortfolioEvaluation Conservative 6.189 6.3438