[![Build Status](https://travis-ci.org/gustavkarlsson/rocketchat-jira-trigger.svg?branch=master)](https://travis-ci.org/gustavkarlsson/rocketchat-jira-trigger)
[![codecov](https://codecov.io/gh/gustavkarlsson/rocketchat-jira-trigger/branch/master/graph/badge.svg)](https://codecov.io/gh/gustavkarlsson/rocketchat-jira-trigger)

# rocketchat-jira-trigger
Outgoing webhook integration for Rocket.Chat that summarizes any JIRA issues mentioned

## Prerequisites
- Java 8

Yeah, that's it.

## Building
Run `./gradlew installDist` (`gradlew.bat installDist` on Windows). The binaries will be located in `build/install/rocketchat-jira-trigger`.

## Configurating
To get started you only need to configure the URI of your JIRA server and some user credentials (unless anonymous access is allowed).
Create a configuration file with the `.toml` extension and set it up like this:
```
[jira]
uri = "https://jira.mycompany.com"
username = "someuser"
password = "somepassword"
```
If you don't want to save your password in plain text, then leave it out, and you will be prompted to enter it when running the app.
For further configuration settings, check out the [defaults](https://github.com/gustavkarlsson/rocketchat-jira-trigger/blob/master/src/main/resources/defaults.toml).

## Running
Run one of the start scripts with the configuration file as the only argument: `bin/rocketchat-jira-trigger myconf.toml`.

In Rocket.Chat, set up an outgoing webhook pointing at the server on port 4567. Example: `http://server.mycompany.com:4567/` and write a message containing a know JIRA issue to try it out. Example: `Let's check out SUP-1234`

## Troubleshooting
If your messages aren't getting any replies, first check the logs for reasons.

### HTTP 403 errors
If you're getting HTTP 403 errors, it might be because CAPTCHA is enabled on your JIRA server and it wants you to manually re-authenticate. In that case, log out of JIRA in your browser and then log in again.

If you're still having trouble, feel free to [create an issue](https://github.com/gustavkarlsson/rocketchat-jira-trigger/issues/new) explaining your problem.
