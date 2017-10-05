FROM anapsix/alpine-java:latest

RUN apk add --update curl && \
    rm -rf /var/cache/apk/*

COPY target/app.jar /app/app.jar
CMD ["java","-jar","/app/app.jar"]