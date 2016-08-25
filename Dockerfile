FROM openjdk:8
MAINTAINER Gustav Karlsson <gustav.karlsson@gmail.com>
COPY . /src
WORKDIR /src
RUN mkdir /app && \
    ./gradlew installDist && \
    cp -R build/install/rocketchat-jira-trigger/* /app && \
    cd / && \
    rm -rf /root/.gradle && \
    rm -rf /src
WORKDIR /app
VOLUME /app/config.toml
EXPOSE 4567
CMD ["bin/rocketchat-jira-trigger", "config.toml"]
