FROM openjdk:8
EXPOSE 8080
ADD target/parking-lot.jar parking-lot.jar
ENTRYPOINT ["java","-jar","parking-lot.jar"]