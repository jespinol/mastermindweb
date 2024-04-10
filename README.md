# Getting started
## Prerequisites
JDK 21.0.2 or later[*](https://jdk.java.net/21/)

Maven 3.9.6 or later[*](https://maven.apache.org/download.cgi)

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
curl 'localhost:8080/guess?id=bac7e344-1afd-45fa-ab06-6112e45fa2ff' --json '[1,2,3,0]'
```
assuming the game identifier is valid and the game is in progress, will return feedback for the guess.

### Getting information about a game
To get information about the game, send a `GET` request to the endpoint `/gameInfo` with the game identifier. For example:
```shell
curl "localhost:8080/gameInfo?id=<UUID>"
```
Will return a string with information about the game's configuration and state.
