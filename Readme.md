**Portfolio Real-time Value Application**


This application calculates and displays the real-time value of a portfolio consisting of common stocks and european option contracts. It utilizes Java Spring Boot and an embedded H2 database for security definitions.

Prerequisites
1. JDK 1.8 or later installed on your system.
2. Gradle installed on your system.

**Installation**

Clone/Copy the repository to your local machine:
1. Then navigate to the project directory.

**Build the project using Gradle:**
1. gradle build 

**Run the Application:**

Run the Spring Boot application using the following Gradle command:
1. gradle bootRun 

**Verify Application Startup:**

Once the application has started, you should see log messages indicating that the application has successfully started and is listening for incoming requests.

**Generate Mock Market Data Updates:**

The application will automatically generate mock market data updates at regular intervals. You can monitor these updates in the console output.

**View Portfolio Details:**

As mock market data updates are received, the application will calculate and print the real-time value of the portfolio to the console.

**Configuration**
1. Embedded Database: 
   i. By default, the application uses an embedded H2 database for storing security definitions. You can configure the database connection properties in the application.properties file.
2. You should have a csv file named as "portfolio-position.csv", and you have to give it's path in the main class. Replace the existing path with your file path.

**Assumptions**
1. mu is provided statically as 10-year annualized return of the given stock. 
2. sigma is provided statically as annualized standard deviation of the given stock.
3. For simplicity, option expires at the end of each month 