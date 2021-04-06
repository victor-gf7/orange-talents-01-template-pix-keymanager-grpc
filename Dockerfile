FROM adoptopenjdk/openjdk11:alpine
ARG JAR_FILE=build/libs/*all.jar
COPY ${JAR_FILE} keymanager-grpc.jar
ENTRYPOINT ["java", "-Xmx512m","-jar","/keymanager-grpc.jar"]