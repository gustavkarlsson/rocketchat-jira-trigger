[![Build Status](https://travis-ci.org/gustavkarlsson/rocketchat-jira-trigger.svg?branch=master)](https://travis-ci.org/gustavkarlsson/rocketchat-jira-trigger)
[![codecov](https://codecov.io/gh/gustavkarlsson/rocketchat-jira-trigger/branch/master/graph/badge.svg)](https://codecov.io/gh/gustavkarlsson/rocketchat-jira-trigger)
[![Docker Automated build](https://img.shields.io/docker/automated/gustavkarlsson/rocketchat-jira-trigger.svg)](https://hub.docker.com/r/gustavkarlsson/rocketchat-jira-trigger)
[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/gustavkarlsson/rocketchat-jira-trigger/blob/master/LICENSE)
[![Image Layers](https://images.microbadger.com/badges/image/gustavkarlsson/rocketchat-jira-trigger.svg)](https://microbadger.com/images/gustavkarlsson/rocketchat-jira-trigger)

# rocketchat-jira-trigger
Outgoing webhook integration for Rocket.Chat that summarizes any JIRA issues mentioned

## Building
Clone the repository and run:

**Linux/OS X**
```
./gradlew installDist
```
**Windows**
```
gradlew.bat installDist
```
The binaries will be located in `build/install`

## Configuration
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

**Linux/OS X**
```
bin/rocketchat-jira-trigger config.toml
```
**Windows**
```
bin\rocketchat-jira-trigger.bat config.toml
```

### With Docker
Use the `docker` cli and mount a config file as volume `/app/config.toml` and set up port mapping for port `4567`:
```
docker run -v $(pwd)/config.toml:/app/config.toml -p 4567:4567 --rm -it gustavkarlsson/rocketchat-jira-trigger:latest
```

## Trying it out
In Rocket.Chat, set up an outgoing webhook pointing at the server on port 4567. Example: `http://server.mycompany.com:4567/`
and write a message containing a known JIRA issue to try it out. Example: `Let's check out SUP-1234`

Rocket.Chat should reply with details about the JIRA issue.

*Tip: If you want more details, append a + like this: `Let's check out SUP-1234+`*

## Troubleshooting
If your messages aren't getting any replies, first check the log.

### HTTP 403 errors
If you're getting HTTP 403 errors, it might be because CAPTCHA is enabled on your JIRA server and it wants you to manually re-authenticate. In that case, log out of JIRA in your browser and then log in again.

If you're still having trouble, feel free to [create an issue](https://github.com/gustavkarlsson/rocketchat-jira-trigger/issues/new) explaining your problem.

### Connection refused when using docker
When using docker, you must NOT override the app port in the configuration file. The docker image is configured to
only export export port 4567. You can change what port the container should listen to with the `-p` option.
