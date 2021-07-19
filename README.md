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


### Use POST method with url: `http://localhost:8081/calulate`
with example body:

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


to receive in response information about customers and their points:

[
  {
    "customerCode": "John1312",
    "pointsPerEachMonth": {
      "JULY2021": 210,
      "JUNE2021": 0
    },
    "totalPoints": 210
  },
  {
    "customerCode": "Tom3444",
    "pointsPerEachMonth": {
      "JULY2021": 49,
      "APRIL2021": 292
    },
    "totalPoints": 341
  },
  {
    "customerCode": "Jeff8712",
    "pointsPerEachMonth": {
      "JULY2021": 290
    },
    "totalPoints": 290
  },
  {
    "customerCode": "Kate7712",
    "pointsPerEachMonth": {
      "APRIL2021": 90,
      "JUNE2021": 90,
      "MAY2021": 470
    },
    "totalPoints": 650
  }
]