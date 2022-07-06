FROM openjdk:17
EXPOSE 8080
ADD build/libs/stock-api.jar stock-api.jar
ENTRYPOINT ["java","-jar","/stock-api.jar"]