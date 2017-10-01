FROM java:8
VOLUME /tmp
ADD ./target/restnosql-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar","--spring.data.mongodb.uri=mongodb://dbkanghuawu368:27017/cmpe282kanghuawu368"]