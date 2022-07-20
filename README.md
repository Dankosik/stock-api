# stock-api
run with default profile
```
docker run -p 8081:8080 dankosik/stock-api
```
run with kuber profile
```
docker run -p 8081:8080 -e "SPRING_PROFILES_ACTIVE=kuber" dankosik/stock-api
