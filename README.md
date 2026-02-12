# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)
[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBNA7CMjEIpwBG0hwAoMAADIQFkhRYcwTrUP6zRtF0vQGOo+RoFmipzGsvz-BwVygYKQH+uB5afJCIJqTsXzQo8wHiVQSIwAgQnihignCQSRJgKSb6GLuNL7gyTJTspXI3l5d5LsKYoSm6MpymW7xKpgKrBhqACSaAgNAKLgDATT6H8OyBbywWWdZEVbu5IHVP6yWpcWKDgFlWw7FGMZxoUWlJpUolpk4ACMBE5qoebzNBRYlvUPjTJe0BIAAXigux0U2w4Om1HZWS6MA9n224eR+5n+o54oZKoAGYDp8KWbphH6fMJGod8FFUfWN20ehZ3tcgqYwLh+GjJdMXEaRd2Xg9yFPWh9GMd4fj+F4KDoDEcSJDDcOOb4WCiYKoH1A00gRvxEbtBG3Q9HJqgKcM92IegL3abtpYU0hGLxMGHAYWgEDMDoEDmqSp0Y86XY2UJqMOULp7OWorlbYKi30jAjJgBQ8kFOeQOU2gc5BUtlTLtIKDcOiiuk-GMsCst7ndr2-alctp31CjYv-gggG02VElgZp5WvfA73YZ9eEwCM80MZ4kMBCi67+Ng4oavxaIwAA4kqGjo+dpYNPH+NE-YSrk6rSHUztsL+vT6CM8zrPs7KXOWDzLuFWtyA5A5aLiySUuefl-I+Qr-IKSr8FqxrneLtrwrx0yCdKrHOR5Qupv1wLidzNPYCvvz7a2wJLeO87Rde5jAdjNnOYFg04zHygiXSAWXXhMEgQgps8S6igbqcl8KzDGMySgGqb+QYZZYF8ABySpFhQk6B7N2mEfZgCxl9ToBEL6qFPufJUV8b53wfssJ+L9-4GRgl-H+IA-5EUGjBIBSpQFzEhBcSBjZg5MShhwAA7G4JwKAnAxAjMEOAXEABs8AJyGCXoYIosC+Zuyxq0DoWcc7jQHkhLMIClRQMTJUTeaC5jUPIWhXmC96iHjkCgDEojW6S2tlSW8XcYBGPRKIjEKi5huXXh3OeY5hGiKvo4qhSoh5z3vMKR8k85gehNq7VaAsNpW1cYXL89Q4DCMOsdfRqcXiULmBg+ot974wDURhZaHU-bfRGBky+19slYLyQwiGzF-CWD1rZTY8MkAJDAA0vsEBmkACkIDihCYYfwxC1TiJKGJX0ntpHMhkj0C+udFHoCzNgBAwAGlQDgBAWyUA1gXyvvk+EGjabpJ+CstZGytl7AAOosESgTHoAAhfiCg4AAGlvi7IqTAHJgRqn6ImZ2eoAArPpaAMS9IOigQkEsXGdmltY2W8sHEfP8fuQJopxTrlEbKeUV84qqg1Fi1KWyYC1ipuEs2-N6iiLXrCilUiAxVlDOGNAjUUCxgUgXZMsCsbpmCIg0Yyw+oDQLGMYa0B6gmjNDWZlSwg6z1RXSyJFtNrWziXCeo4K0DJKdidOuaSA77JgWMnC-tA41JDnUrwqyWltKtfKRAwZYDAGwMswgeQCgwFGeYSRNQsY4zxgTImxgC6HL3vUCgEojixhrBAGA8RaYYjZlXbmuq94VHNiAbgeAMQwsiRUE2tR5Y8hzfKkcFQdZ6yZIYLQ8gtoAB4kwFuiamr8DbSjmwdXgbVWBQJtttma0SbbMZfQNTUoAA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
