# Github-api

Is a tool that enables interaction with the GitHub service through
its public API. The application allows users to search for repositories.

## Requirements
- Java 21
- Maven

### Run in Dev Mode
```shell
mvn quarkus:dev
```

### Run with Docker
Before building docker image run:
```shell
mvn package
```

```shell
docker build -t quarkus/githubapi-jvm -f src/main/docker/Dockerfile.jvm . 
docker run -p 8080:8080 quarkus/githubapi-jvm
```

## **Endpoint: Get User's Non-Forked Repositories**
Retrieves a list of repositories owned by the specified user that are **not forks**.
Gets user not forked repositories.

### **Request**
```http
GET /api/github/repos/{username}
```

### Responses

#### 200 OK - Success
Returns a list of repositories that are not forks.
Example response:
```json
[
  {
    "name": "cloud",
    "ownerLogin": "npawlenko",
    "branches": [
      {
        "name": "master",
        "lastCommitSha": "25df6ab1d0974b839800014ee61976894b5d762d"
      },
      {
        "name": "develop",
        "lastCommitSha": "25df6ab1d0974b839800014ee61976894b5d762d"
      }
    ]
  }
]
```

#### 404 Not Found
User was not found.
Example response:
```json
{
  "status": 404,
  "message": "Resource was not found"
}
```
#### 500 Internal server error
Returned when an unexpected error occurs on the server.
For example when GitHub API rate limit was exceeded.


### Usage example
```shell
curl -X GET "http://localhost:8080/api/github/repos/npawlenko" -H "Accept: application/json"
```