[![Build Status](https://travis-ci.org/gustavkarlsson/rocketchat-jira-trigger.svg?branch=master)](https://travis-ci.org/gustavkarlsson/rocketchat-jira-trigger)
[![codecov](https://codecov.io/gh/gustavkarlsson/rocketchat-jira-trigger/branch/master/graph/badge.svg)](https://codecov.io/gh/gustavkarlsson/rocketchat-jira-trigger)

# rocketchat-jira-trigger
Outgoing webhook integration for Rocket.Chat that summarizes any JIRA issues mentioned

## Prerequisites
JDK 8 or Docker

## Building

### Using Gradle
Clone the repository and run:
```
./gradlew installDist
```
The binaries will be located in `build/install/rocketchat-jira-trigger`

*Note: On windows, the gradle command is `gradlew.bat`.* 

### Using Docker
As an alternative to building the app using Gradle, you can use Docker build an image directly from Github:
```
docker build -t <image_name> https://github.com/gustavkarlsson/rocketchat-jira-trigger.git
```

## Configuring
To get started you only need to configure the URI of your JIRA server and some user credentials (unless anonymous access is allowed).
Create a configuration file with the `.toml` extension and set it up like this:
```
[jira]
uri = "https://jira.mycompany.com"
username = "someuser"
password = "somepassword"
```
If you don't want to save your password in plain text, then leave it out and you will be prompted to enter it when running the app.
For further configuration settings, check out the [defaults](https://github.com/gustavkarlsson/rocketchat-jira-trigger/blob/master/src/main/resources/defaults.toml).

## Running

### With a start script
Run the start scripts with a configuration file as the only argument:
```
bin/rocketchat-jira-trigger config.toml
```
*Note: On windows, the start script is `rocketchat-jira-trigger.bat`.*

### With docker-compose
Make sure a configuration file named `config.toml` exists in your working directory and run:
```
docker-compose up
```
The app will listen on port `4567`.

### With Docker
Use the `docker` cli and mount a config file as volume `/app/config.toml` and set up port mapping for port `4567`:
```
docker run --rm -v $(pwd)/config.toml:/app/config.toml -p 4567:4567 <image_name>
```

## Trying it out
In Rocket.Chat, set up an outgoing webhook pointing at the server on port 4567. Example: `http://server.mycompany.com:4567/`
and write a message containing a known JIRA issue to try it out. Example: `Let's check out SUP-1234`

Rocket.Chat should reply with details about the JIRA issue.

## Troubleshooting
If your messages aren't getting any replies, first check the log.

### HTTP 403 errors
If you're getting HTTP 403 errors, it might be because CAPTCHA is enabled on your JIRA server and it wants you to manually re-authenticate. In that case, log out of JIRA in your browser and then log in again.

If you're still having trouble, feel free to [create an issue](https://github.com/gustavkarlsson/rocketchat-jira-trigger/issues/new) explaining your problem.
