# Credorax
Credorax Assignment

Installation instructions:

1. Run maven build using JDK 11
   Command example: mvn clean install

2. Run Sping Booy Application
   command example: mvn spring-boot:run
   
3. Use Postman to run the REST API commands:

   Get method -> localhost:8080/transactions
   
   Example result:
   
   [
    {
        "id": "e4f8f790-263b-498d-b56a-57a9a525fd9d",
        "invoice": "1234567",
        "amount": "1299",
        "cardHolder": {
            "id": 2,
            "name": "Sm9obiBEb2U=",
            "email": "john@doe.com"
        },
        "card": {
            "id": 1,
            "pan": "NDIwMDAwMDAwMDAwMDAwMQ==",
            "expiry": "MDYyNA==",
            "cvv": ""
        }
    },
    {
        "id": "8478fbd3-a993-49f2-8fa3-88b131108cc1",
        "invoice": "1234568",
        "amount": "1599",
        "cardHolder": {
            "id": 4,
            "name": "Dirk Smith",
            "email": "dirk@smith.com"
        },
        "card": {
            "id": 3,
            "pan": "4200000000000002",
            "expiry": "0829",
            "cvv": "789"
        }
    },
    {
        "id": "66b4d0c9-a4ff-44dd-9d4f-7db415a1423k",
        "invoice": "1234567",
        "amount": "1299",
        "cardHolder": {
            "id": 2,
            "name": "Sm9obiBEb2U=",
            "email": "john@doe.com"
        },
        "card": {
            "id": 1,
            "pan": "NDIwMDAwMDAwMDAwMDAwMQ==",
            "expiry": "MDYyNA==",
            "cvv": ""
        }
    }
]

POST method: localhost:8080/transactions

Body:

{
    "id": "66b4d0c9-a4ff-44dd-9d4f-7db415a1423k",
    "invoice": "1234567",
    "amount": "1299",
    "cardHolder": {
        "id": 2,
        "name": "John Doe",
        "email": "john@doe.com"
    },
    "card": {
        "id": 1,
        "pan": "4200000000000001",
        "expiry": "0624",
        "cvv": "789"
    }
}

Example result:

{
    "approved": true
}

POST method: localhost:8080/transactions

Body:

{
    "id": "66b4d0c9-a4ff-44dd-9d4f-7db415a1423k",
    "invoice": "",
    "amount": "-1299",
    "cardHolder": {
        "id": 2,
        "name": "John Doe",
        "email": "john@doe.com"
    },
    "card": {
        "id": 1,
        "pan": "4200000000000001",
        "expiry": "0624",
        "cvv": "789"
    }
}

Example result:

{
    "amount": "must be greater than 0",
    "approved:": "false",
    "errors:": "true",
    "invoice": "invoice is mandatory"
}
