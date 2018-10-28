FROM openjdk:8 AS builder
COPY . /src
WORKDIR /src
RUN ./gradlew installDist

FROM openjdk:8
MAINTAINER Gustav Karlsson <gustav.karlsson@gmail.com>
WORKDIR /app
EXPOSE 4567
RUN ln -s etc/config.toml
CMD ["bin/rocketchat-jira-trigger", "config.toml"]

COPY src/main/resources/defaults.toml /app/etc/config.toml
COPY --from=builder /src/build/install/rocketchat-jira-trigger/ /app
