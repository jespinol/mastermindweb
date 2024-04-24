This repository contains a simple web implementation of the Mastermind game library found at https://github.com/jespinol/mastermind

# Getting started
## Prerequisites
- JDK 21.0.2 or later[*](https://jdk.java.net/21/)

- Maven 3.9.6 or later[*](https://maven.apache.org/download.cgi)

- mastermindcore 1.1 library installed in your local Maven repository. Detailed instructions [can be found here](https://github.com/jespinol/mastermind?tab=readme-ov-file#installation).
- MySQL 8.0.27 or later

   This application requires a mySQL database. Additional information to install and run mySQL server can be found [here](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/) and [here](https://www.mysql.com/products/workbench/)
   
   Once mySQL server is installed and running, you must create the required database.
   First, log into mySQL:
   ```shell
   sudo /path/to/mysql --password
   ```
   You may need to enter your OS admin password, followed by the mySQL root password.
   
   Then, to create the database run the following commands:
   ```sql
   CREATE DATABASE IF NOT EXISTS mastermind;
   CREATE USER IF NOT EXISTS 'mastermind'@'%' IDENTIFIED BY 'password';
   GRANT ALL ON mastermind.* TO 'mastermind'@'%';
   ```

## Installation and starting the application

These instructions were tested on a Mac M1 laptop running macOS Sonoma 14.4.1

1. Clone the repository
    ```shell
    git clone https://github.com/jespinol/mastermindweb.git && cd mastermindweb
    ```
2. From the repository root, run
    ```shell
    mvn spring-boot:run
    ```
    This will start a server listening on port 8080.

## To play the game
The game can be played via HTTP requests.
### Starting the game
The game can be started by sending a `POST` request to the endpoint `/new`with a JSON body containing one or more of game settings. For example:
```shell
curl localhost:8080/new --json ''
```
will initialize a game with default settings. The response will contain a unique identifier for the game needed to continue playing the same game.
The game can also be customized by POSTing a JSON body with a subset of the following settings, e.g.:
```shell
curl localhost:8080/new --json '{
   "codeLength": 4,
   "numColors": 8,
   "maxAttempts": 10,
   "secretCode": [0, 1, 2, 3],
   "codeSupplierPreference": "USER_DEFINED",
   "feedbackStrategy": "DEFAULT"
}'
```

The allowed choices for `codeSupplierPreference` are: `[LOCAL_RANDOM, RANDOM_ORG_API, USER_DEFINED]`. If `USER_DEFINED` is selected, `secretCode` must also be provided.

The allowed choices for `feedbackStrategy` are: `[DEFAULT, ORIGINAL_MASTERMIND, HIGHER_LOWER, ENCOURAGING, PERFECT]`.

### Playing the game
To send a guess, send a `POST` request to the endpoint `/guess` with the game identifier and a JSON body containing a list of integers. For example:
```shell
curl 'localhost:8080/guess?id=<UUID>' --json '[1,2,3,0]'
```
assuming the game identifier is valid and the game is in progress, will return feedback for a valid guess.

### Getting information about a game
To get information about the game, send a `GET` request to the endpoint `/gameInfo` with the game identifier. For example:
```shell
curl "localhost:8080/gameInfo?id=<UUID>"
```
will return a string with information about the game's configuration and state.
