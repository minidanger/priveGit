# Challenge

Implement a simple ORDER SYSTEM API, which accepts orders from a USER to buy assets,  
pass them to a BROKER service responsible for orders execution, and process results.

**We LOVE questions, please ask if anything is not clear.**

## NOTES

- You need a Docker to run development environment and the final solution
- We expect production ready solution according to the TASK below
- We do not specify how errors should be handled/returned, this is up to you
- For DB and BROKER server you can hardcode connection strings and credentials in your code
- We expect automated tests

This directory includes:
- **./app** directory, contains a simple Spring Boot Java app, modify or replace it with your solution
- **Dockerfile** defines how to build and run code from ./app dir in Docker.
  - You may need to modify it depending on how your app must be built, tested and launched
- **docker-compose.yml**: compiles and runs your solution and development environment:
  - **MySQL**. Accessible via `localhost:3306` or `db:3306`, user: `root`, pass: `root`, database: `challenge`
  - **Dummy BROKER** server. Listens on `http://localhost:9080`, always returns HTTP 200
  - **Adminer**. Database admin tool, accessible via [this link](http://localhost:8081/?server=db&username=root&db=challenge) (pass: `root`)

**Step 1: start development environment, write some code**

Run the command, may need `sudo` in Linux.
```
$ docker-compose up
```

All services would start. Java app will serve `GET http://localhost:8080/ping` endpoint. You can now  
replace Java app with your solution, build, run and debug it separately, ie outside Docker, use  
random port.

For MySQL connection in your app use the following `HOST:PORT`:
- App running outside Docker container: `localhost:3306` 
- App running inside Docker container: `db:3306`

Make sure that your **final** solution runs on port `:8080`.

**Step 2: build and run your solution in Docker**

- Check Dockerfile and make changes if required, ie install Gradle, change build commands.
- Clean ./app folder from any build artifacts, a clean docker build must work for the final solution
- Stop containers (Ctrl+c) and remove containers `docker-compose down`
- We recommend removing `prive-ordering-challenge_app` Docker image manually to make sure that an  
  up-to-date code would be copied to the new Java app Docker container.
  - `docker images` to list images
  - `docker rmi prive-ordering-challenge_app` to remove `prive-ordering-challenge_app` image
- Re-run `docker-compose up`, make sure tests are executed on the start, and the solution works  
  properly on `http://localhost:8080`

**Step 3: add README**

Include README file
- Describe how to compile/run your tests. The reviewer may do it manually inside the Docker  
  container for your solution.
- Document anything that you would like to add to your solution but haven't or couldn't do
- Add any other points relevant to the reviewer.

**Step 4: Ship it!**

ZIP this folder and send it back to us. We will provide feedback about your solution soon.

## TASK

### Tech Stack
- Java with OpenJDK 11
- Spring Framework
- JUnit
- Database, pick one
  - MySQL, included in docker-compose.yml
  - other SQL DB like PostgreSQL, must be included in docker-compose.yml
  - MongoDB, must be included in docker-compose.yml

### Rules

- All correct orders are saved and have an active status:
  - An order sent to the ORDER SYSTEM API, but not yet accepted by the BROKER is in `PLACED` state
  - An order sent to the ORDER SYSTEM API, and accepted by the BROKER is in `QUEUED` state  
    (see `POST /broker-callback` below)
  - An order processed by the BROKER, would be reported by the BROKER as `FULFILLED` or `REJECTED`  
    (see `POST /broker-callback` below)
- BROKER communicates with the ORDER SYSTEM API asynchronously via `POST /broker-callback`
- Do not assume that BROKER's requests are always syntactically or logically correct,  
  ie BROKER may report that the same order is `FULFILLED` and `REJECTED`.
- Order active status got modified in the order of calls to `POST /broker-callback`

### Endpoints

All endpoints are JSON-based.

**1. Create order**  
`POST /orders`

Create a new order.

Request HTTP body:
- `type`:  order type. Allowed values: `MARKET` or `LIMIT`
- `qty`:   quantity of assets. A positive integer number
- `code`:  asset code. 3 to 4 capitalised symbols of an alpha-numeric string
- `price`: limit price. A positive number string with up-to 4 decimal digits.  
           Required if the order type is `LIMIT`

Example: `{ "type": "LIMIT", "qty": 100, "code": "AAPL", "price": "123.4567" }`

Response HTTP body:
- `id`: a unique order ID string

Example: `{ "id": "ORDER ID" }`

Logic:
- The endpoint accepts user input.
- On correct input generates a unique order ID and request ID (as strings)
- Adds order ID to users payload as `"id": "..."` and request ID as `"req_id": "..."`
- Calls broker's `POST /create-order` with a new payload
- Broker returns HTTP 200, on successful data transfer
- Order is now created and its status is `PLACED`
- Returns order ID back to the user
- Order status would be `QUEUED` or `REJECTED` later based on a call to `POST /broker-callback`

**2. Broker callback**  
`POST /broker-callback`

A callback used by the broker to report requests status or finalize orders.

Request HTTP body (for requests):
- `req_id`: request ID generated by the API on `POST /orders`
- `status`: request status: `ACCEPTED` or `REJECTED`

Example: `{ "req_id": "REQUEST ID", "status": "ACCEPTED" }`

Request HTTP body (for orders):
- `id`:     order ID generated by the API on `POST /orders`
- `status`: order final status: `FULFILLED` or `REJECTED`

Example: `{ "id": "ORDER ID", "status": "FULFILLED" }`

**3. List orders**  
`GET /orders`

Returns the list of orders with their active state

Response HTTP body, a JSON array of objects with the following format:
- `id`:     order ID string
- `type`:   order type string
- `qty`:    quantity of assets. Integer number
- `code`:   asset code string
- `price`:  limit price number string with up-to 4 decimal digits. Required for `LIMIT` order type 
- `status`: a string representing the order status: `PLACED`, `QUEUED`, `FULFILLED`, or `REJECTED`

Example:
```
[
	{ "id": "ORDER ID", "type": "LIMIT", "qty": 100, "code": "AAPL", "price": "123.4567", "status": "PLACED" },
	...
]
```

**We LOVE questions, please ask if anything is not clear.**