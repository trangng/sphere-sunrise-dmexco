FROM java:latest

ENV BUILD_NUMBER $BUILD_NUMBER
ENV BUILD_VCS_NUMBER $BUILD_VCS_NUMBER

ADD ./ /app
RUN cd /app && ./activator stage

EXPOSE 9000

WORKDIR /app
ENTRYPOINT ["/app/target/universal/stage/bin/sphere-sunrise"]
CMD ["-Dconfig.resource=prod.conf", "-Dlogger.resource=docker-logger.xml"]
