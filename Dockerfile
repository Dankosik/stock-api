FROM openjdk:17
ADD build/libs/stock-api.jar stock-api.jar
ENTRYPOINT ["java","-jar","/stock-api.jar"]