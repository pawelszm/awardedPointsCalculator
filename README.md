## Getting Started

Prerequisites:
- JDK 11

### Running the app

1. Clone the repository
2. Import the project as a maven project into your favourite IDE (or run maven on the terminal)
3. Build and run project by run AwardedPointsCalculatorApplication


## Use the url: (http://localhost:8081/swagger-ui/)
to comfortably familiarize yourself with the functionality

Or:


### Use POST method with url: `http://localhost:8081/calculate`

Request must consist of transactions in that form:

```json
{
  "customerCode": "John1312",
  "transactionAmount": 180,
  "transactionDate": "2021-07-08"
}
```

Send request with example body:

```json
[
  {
    "customerCode": "John1312",
    "transactionAmount": 180,
    "transactionDate": "2021-07-08"
  },
  {
    "customerCode": "John1312",
    "transactionAmount": 40,
    "transactionDate": "2021-06-08"
  },
  {
    "customerCode": "Tom3444",
    "transactionAmount": 221.3,
    "transactionDate": "2021-04-19"
  },
 {
    "customerCode": "Tom3444",
    "transactionAmount": 99,
    "transactionDate": "2021-07-11"
  },
 {
    "customerCode": "Jeff8712",
    "transactionAmount": 220,
    "transactionDate": "2021-07-13"
  },
  {
    "customerCode": "Jeff8712",
    "transactionAmount": 30,
    "transactionDate": "2021-07-03"
  },
  {
    "customerCode": "Kate7712",
    "transactionAmount": 120,
    "transactionDate": "2021-04-22"
  },
  {
    "customerCode": "Kate7712",
    "transactionAmount": 310,
    "transactionDate": "2021-05-08"
  },
  {
    "customerCode": "Kate7712",
    "transactionAmount": 120,
    "transactionDate": "2021-06-03"
  }
]
```

to receive in response information about customers and their points:

```json
[
  {
    "customerCode": "Tom3444",
    "pointsPerEachMonth": {
      "APRIL-2021": 292,
      "JULY-2021": 49
    },
    "totalPoints": 341
  },
  {
    "customerCode": "Kate7712",
    "pointsPerEachMonth": {
      "APRIL-2021": 90,
      "MAY-2021": 470,
      "JUNE-2021": 90
    },
    "totalPoints": 650
  },
  {
    "customerCode": "John1312",
    "pointsPerEachMonth": {
      "JUNE-2021": 0,
      "JULY-2021": 210
    },
    "totalPoints": 210
  },
  {
    "customerCode": "Jeff8712",
    "pointsPerEachMonth": {
      "JULY-2021": 290
    },
    "totalPoints": 290
  }
]
```

Be aware of maximum dates interval (3 months) because after send request:

```json
[
  {
    "customerCode": "John1312",
    "transactionAmount": 180,
    "transactionDate": "2021-03-08"
  },
  {
    "customerCode": "John1312",
    "transactionAmount": 40,
    "transactionDate": "2021-06-18"
  }
]
```

error is returned in response:

```json
{
  "timeStamp": "07-21-2021 08:59:03",
  "httpStatusCode": 400,
  "httpStatus": "BAD_REQUEST",
  "reason": "BAD REQUEST",
  "message": "MAXIMUM INTERVAL BETWEEN DATES IS 3 MONTHS"
}
```