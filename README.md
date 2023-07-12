# 🔐 Spring Boot JWT Token Auth

## What is JWT?
JSON Web Token is an open standard used to share information between client and server.

## Structure Of JWT
JWT tokens contains three parts:

- Header:
    - The signing algorithm being used (HMAC, SHA256, RSA).
    - The type of token (JWT).

- Payload:
    - Claims which are statements about an entity and additional data (iss, exp, sub, aud).

- Signature:
    - A signature used to verify the integrity of the JSON payload.

A JWT token is represented as:
```
xxxxx.yyyyy.zzzzz
```

## How does work?
When a user successfully logs in using their credentials, a JWT token will be returned. Whenever the user wants to access a protected route or resource, the user agent should send the token with the Authorization header using the Bearer schema.
```
Authorization: Bearer <token>
```