# RSO: Exhibition metadata microservice

## Prerequisites

```bash
docker run -d --name pg-exhibition-metadata -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=exhibition-metadata -p 5432:5432 postgres:13
```