FROM openjdk:21-ea-3-jdk

ENV APP_HOME /u01/app
WORKDIR $APP_HOME

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]
#ENTRYPOINT ["ls"]
#ENTRYPOINT ["java","-version"]