javac -d bin -classpath bin src/main/java/personal/capital/interview/portfolio/*.java
javac -d bin -classpath bin;./lib/testng-6.8.jar; src/test/java/personal/capital/interview/portfolio/*.java

java -cp ./lib/testng-6.8.jar;./bin; org.testng.TestNG testng.xml

java -cp ./bin; personal.capital.interview.portfolio.PortfolioEvaluation