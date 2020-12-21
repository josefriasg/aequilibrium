# AEQUILIBRIUM - TRANSFORMERS PROJECT


## 1. HOW TO BUILD THE PROJECT

This project was created using Maven.
To build the project you can use the following maven command:

mvn package

In case the build is successful, it will create a new artifact named Transformers-0.0.1-SNAPSHOT.jar in "target" directory.


## 2. HOW TO RUN THE TESTS

To run the tests, you can use the maven command:

mvn test


## 3. HOW TO RUN THE PROJECT

Once you built the project and created the JAR file with the name Transformers-0.0.1-SNAPSHOT.jar, you can run the project using the following command:

java -jar Transformers-0.0.1-SNAPSHOT.jar


## 4. API ENDPOINTS

### 4.1 List transformers

This endpoint will retrieve all the Transformers stored in the database.

#### Endpoint:

/api/transformers/list


#### Sample request:

(None)


#### Sample response:

[
    {
        "idTransformer": 1,
        "name": "Optimus Prime",
        "type": "A",
        "strength": 10,
        "intelligence": 10,
        "speed": 10,
        "endurance": 10,
        "rank": 1,
        "courage": 10,
        "firepower": 10,
        "skill": 10
    },
    {
        "idTransformer": 2,
        "name": "Bumblebee",
        "type": "A",
        "strength": 9,
        "intelligence": 6,
        "speed": 8,
        "endurance": 8,
        "rank": 2,
        "courage": 9,
        "firepower": 7,
        "skill": 7
    }
]

### 4.2 Create transformer

This endpoint will create a new transformer. If transformer Id is provided, it will validate that doesn't exist a transformer with the same ID. If transformer Id is not present, the program will assign one automatically.

The endpoint validates that the "type" is A or D (for Autobot or Decepticon), and that the strength, intelligence, speed, endurance, rank, courage, firepower and skill are between 1 and 10.

#### Endpoint:

/api/transformers/create


#### Sample request:

{
    "name": "Jazz",
    "type": "A",
    "strength": 3,
    "intelligence": 4,
    "speed": 2,
    "endurance": 6,
    "rank": 4,
    "courage": 9,
    "firepower": 3,
    "skill": 4
}

#### Sample response:
{
  "body": {
    "idTransformer": 7,
    "name": "Jazz",
    "type": "A",
    "strength": 3,
    "intelligence": 4,
    "speed": 2,
    "endurance": 6,
    "rank": 4,
    "courage": 9,
    "firepower": 3,
    "skill": 4
	},
	  "statusCode": "ACCEPTED",
	  "statusCodeValue": 0
	}



### 4.3 Update transformer

This endpoint will update an existing transformer. It will validate that the transformer Id provided in the parameters exists in the database.

The endpoint validates that the "type" is A or D (for Autobot or Decepticon), and that the strength, intelligence, speed, endurance, rank, courage, firepower and skill are between 1 and 10.

#### Endpoint:
/api/transformers/update/{id}

#### Sample request:
Path variable:
id(integer)

Payload:

{
    "idTransformer": 7,
    "name": "Jazz",
    "type": "A",
    "strength": 4,
    "intelligence": 4,
    "speed": 7,
    "endurance": 6,
    "rank": 4,
    "courage": 9,
    "firepower": 3,
    "skill": 4
}

#### Sample response:
{
  "body": {
    "idTransformer": 7,
    "name": "Jazz",
    "type": "A",
    "strength": 4,
    "intelligence": 4,
    "speed": 7,
    "endurance": 6,
    "rank": 4,
    "courage": 9,
    "firepower": 3,
    "skill": 4
	},
	  "statusCode": "ACCEPTED",
	  "statusCodeValue": 0
	}

### 4.4 Delete transformer

This endpoint will delete an existing transformer. It will validate that the transformer Id provided in the parameters exists in the database.

#### Endpoint:
/api/transformers/delete/{id}

#### Sample request:
Path variable:
id(integer)

#### Sample response:
{
  "body": {},
  "statusCode": "ACCEPTED",
  "statusCodeValue": 0
}

### 4.5 Battle

This endpoint will receive a list of transformer IDs and will determine a winner, if any, based on the requirements provided.

#### Endpoint:
/api/transformers/battle

#### Sample request:
[
  1,2,3
]

#### Sample response:
{
  "body": {
    "numBattles": 1,
    "winningTeam": "Autobots",
    "survivors": [
        {
            "idTransformer": 5,
            "name": "Megatron",
            "type": "D",
            "strength": 6,
            "intelligence": 6,
            "speed": 7,
            "endurance": 4,
            "rank": 2,
            "courage": 7,
            "firepower": 5,
            "skill": 5,
            "overallRating": 28
        }
    ]
},
  "statusCode": "ACCEPTED",
  "statusCodeValue": 0
}


**For more information, once project is started you can go to http://localhost:8080/swagger-ui/index.html

## ASSUMPTIONS AND NOTES

* Transformer type: The only available options for transformer types are A and D, for Autobots and Decepticons respectively.

* Rank sort order: Transformers from a team will be sorted in descending order depending on their rank. This decision was made because it was the way that the provided example would generate the expected result.

* Response battle: In the following cases, the program will return "None" as the winner of a battle and 0 survivors:

	-In case there is a tie in the number of battles won
	
	-In case Optimus Prime and Predaking are part of a battle. Also, it will return 0 number of battles.
	
	-In case that all transformer IDs provided are from the same team. It will return 0 number of battles.

* "Create transformer" endpoint: When creating a transformer, the program will validate that there is no other transformer with the same ID. If it does, the consumer will get a Bad Request response.

* "Update transformer" endpoint: When updating a transformer, the program will validate that a transformer with the given Id exists in the Database. If it doesn't, the consumer will get a Bad Request response.

* Database: This program uses an in-memory H2 database. To access it, go to http://localhost:8080/h2-console/ and access with:

	-URL:jdbc:h2:mem:transformers

	-user:sa

	-password:(empty)

## AUTHOR
Jose Frías - joseduardofrias@gmail.com