FROM openjdk:14-alpine
COPY build/libs/tinaptic-backend-*-all.jar tinaptic-backend.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "tinaptic-backend.jar"]