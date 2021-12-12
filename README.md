# Security course CSRF security lab (#9)

The main goal of this lab consists of implementing an application allowing user to perform some confidential operation,
and preventing this operation from CSRF attacks.

## Running locally

## Prerequisites

To run this application locally use need to have the following tools installed on your workstation:
* JDK 11+ (OpenJDK used during development)

## Configuration

CSRF protection can be configured inside the `src/main/resources/application.yml` file.

### Running

Run the following command inside the root of this repository:

```bash
# *nix
> ./gradlew bootRun

# Windows
> .\gradlew.bat bootRun
```

The application will start in a few seconds and will be accessible under `http://localhost:8080/` web page.

## Demo

[App demo video](https://vimeo.com/655777602/ad6876737b)

## Implementation notes

I've used Spring Boot framework that uses a `JSESSIONID` HTTP Header marked as `HttpOnly`
to track user sessions.

To demonstrate the issue without CSRF protection we need an application that
is accessible from other domains and cookies should be passed together with that request.
Nowadays this requires an additional setup to bypass modern browser security settings. At first to
access a resource from a different domain, server should implement CORS policy that allows accessing its
resources from other sites (domains). I've set up the most permissive policy just for the demonstration
purposes. After that, in order to pass HTTP Cookies with cross-domain requests those Cookies
must be marked with a `SameSite=None` policy to allow passing them in background (hidden from user)
requests. In order to set this policy another policy called `Secure` should be applied for that particular
Cookie. Unfortunately but browser disallows setting `Secure` Cookies over HTTP so application
should also serve traffic other HTTPS.

Such a setup is required **only** for demonstration of the problems with an application without CSRF protection
and would not be required otherwise. However, setting those policies with more restrictive values
is a good practice that can help prevent other security problems as well.

In order to prevent CSRF attacks I used _Synchronizer Token Pattern_. When applying this pattern
user will be required to have a CSRF token when user wants to produce a state modifying request. This
token is bound to user's session and therefore can't be obtained by an attacker as
user's session details are contained inside the Cookie that cannot be accessed from attacker's
scripts.
