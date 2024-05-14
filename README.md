# Drools demo

The goal of this project is to explore the different drools functionalities.

## Endpoints

### Global instance of a kContainer

The endpoint demonstrate how we can create a session from a container.

```bash
curl --location 'http://localhost:8080/' \
--header 'Content-Type: application/json' \
--data '{
    "phmAge": 67,
    "shmAge": 27,
    "phmAssets": 500,
    "shmAssets": 70000,
    "maritalStatus": "MARRIED"
}'
```

### Dynamically update the rules

The goal of this endpoint is to demonstrate how we can dynamically update rules.

```bash
curl --location 'http://localhost:8080/update' \
--header 'Content-Type: application/json' \
--data '{
    "rules": [
        {
            "code": "PHM_AND_SHM_IS_ADULT",
            "spel": "phmAge >= 18 and shmAge >= 18"
        }
    ],
    "facts": {
        "phmAge": 18,
        "shmAge": 27,
        "phmAssets": 500,
        "shmAssets": 70000
    }
}'
```

### Validation

The goal of this endpoint is to demonstrate how a way to validate the drl syntax.

#### Valid rule
```bash
curl --location 'http://localhost:8080/validate' \
--header 'Content-Type: application/json' \
--data '{
    "rule": "import ca.nathan.demo.model.Facts;\r\nimport ca.nathan.demo.model.Results;\r\nimport java.util.*;\r\n\r\nglobal ca.nathan.demo.model.Results results;\r\ndialect  \"mvel\"\r\n\r\nrule \"PHM_IS_ADULT\"\r\n    when\r\n        Facts(phmAge >= 18)\r\n    then\r\n        results.ruleCode.add(\"PHM_IS_ADULT\")\r\nend"
}'
```

### Invalid rule
```bash
curl --location 'http://localhost:8080/validate' \
--header 'Content-Type: application/json' \
--data '{
    "rule": "import ca.nathan.demo.model.Facts;\r\nimport ca.nathan.demo.model.Results;\r\nimport java.util.*;\r\n\r\nglobal ca.nathan.demo.model.Results results;\r\ndialect  \"mvel\"\r\n\r\nrule \"PHM_IS_ADULT\"\r\n    when\r\n        Facts(phmAge >= 18)\r\n    then\r\n        results.ruleCodea.add(\"PHM_IS_ADULT\")\r\nend"
}'
```

## Load and execute
Putting everything together 
- Creates the DRL string with all the rules.
- Creates the KieBase (implicit validation otherwise it will throw).
- Creates a KieSession and executes all rules.

```bash
curl --location 'http://localhost:8080/execute' \
--header 'Content-Type: application/json' \
--data '{
    "rules": [
        {
            "code": "OLD",
            "spel": "phmAge >= 50 and (maritalStatus == '\''SINGLE'\'' or maritalStatus == '\''MARRIED'\'')"
        },
        {
            "code": "PHM_IS_ADULT",
            "spel": "phmAge >= 18"
        },
        {
            "code": "PHM_AND_SHM_IS_ADULT",
            "spel": "phmAge >= 18 and shmAge >= 18"
        }
    ],
    "facts": {
        "phmAge": 18,
        "shmAge": 27,
        "phmAssets": 500,
        "shmAssets": 70000
    }
}'
```
