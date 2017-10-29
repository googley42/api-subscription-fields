# API Subscription Fields Development Road Map

[![Build Status](https://travis-ci.org/hmrc/api-subscription-fields.svg)](https://travis-ci.org/hmrc/api-subscription-fields) [ ![Download](https://api.bintray.com/packages/hmrc/releases/api-subscription-fields/images/download.svg) ](https://bintray.com/hmrc/releases/api-subscription-fields/_latestVersion)

This microservice stores definitions and values for the HMRC Developer Hub.

## Endpoints Summary

| Path                                                                                                       |  Method  | Description                              |
|------------------------------------------------------------------------------------------------------------|----------|------------------------------------------|
| [`/definition/context/:apiContext/version/:apiVersion`](#user-content-put-field-definitions)               | `PUT`    | Creates or updates the definitions of the subscriptions fields for an API. |
| [`/definition/context/:apiContext/version/:apiVersion`](#user-content-get-field-definitions)               | `GET`    | Retrieves the definitions of subscription fields for an API |
| [`/definition`](#user-content-get-field-definitions-for-all-apis)                                          | `GET`    | Retrieves the definitions of subscription fields for all APIs |
| [`/definition/context/:apiContext/version/:apiVersion`](#user-content-delete-field-definitions)            | `DELETE` | Deletes the definitions of all subscriptions fields for an API |
| [`/field/application/:clientId/context/:apiContext/version/:apiVersion`](#user-content-put-field-values)   | `PUT`    | Creates or updates the field values of an API subscription |
| [`/field/application/:clientId/context/:apiContext/version/:apiVersion`](#user-content-get-field-values)   | `GET`    | Retrieves the field values of an API subscription by providing the application and API details |
| [`/field/:fieldsId`](#user-content-get-field-values-by-fieldsid)                                           | `GET`    | Retrieves the field values of an API subscription by providing the `fieldsId` |
| [`/field/application/:clientId`](#user-content-get-field-values-by-application)                            | `GET`    | Retrieves the field values of all API subscriptions related to a specific application |
| [`/field`](#user-content-get-all-field-values)                                                             | `GET`    | Retrieves the field values of all API subscriptions |
| [`/field/application/:clientId/context/:apiContext/version/:apiVersion`](#user-content-delete-field-values)| `DELETE` | Deletes the field values of an API subscription |

## PUT Field Definitions 
### `PUT /definition/context/:apiContext/version/:apiVersion`
Creates or updates the definitions of the subscriptions fields for an API.

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 201    | Created                              |
| 200    | Updated                              |

### example

#### curl command
```
curl -v -X PUT "http://localhost:9650/definition/context/hello/version/1.0" -H "Cache-Control: no-cache" -H "Content-Type: application/json" -d '{ "fieldDefinitions": [ { "name": "callback-url", "description": "Callback URL", "type": "URL" }, { "name": "token", "description": "Secure Token", "type": "SecureToken" } ] }' 
```
#### Request body
```json
{
  "fieldDefinitions": [
    {
      "name": "callback-url",
      "description": "Callback URL",
      "type": "URL"
    },
    {
      "name": "token",
      "description": "Secure Token",
      "type": "SecureToken"
    }
  ]
}
```
#### Response body
```json
{
  "apiContext": "hello",
  "apiVersion": "1.0",
  "fieldDefinitions": [
    {
      "name": "callback-url",
      "description": "Callback URL",
      "type": "URL"
    },
    {
      "name": "token",
      "description": "Secure Token",
      "type": "SecureToken"
    }
  ]
}
```

## GET Field Definitions 
### `GET /definition/context/:apiContext/version/:apiVersion`
Retrieves the definitions of subscription fields for an API

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 200    | Updated                              |
| 404    | Not found                            |

### example

#### curl command
```
curl -v -X GET "http://localhost:9650/definition/context/hello/version/1.0" -H "Cache-Control: no-cache"
```
#### Request body
None
#### Response body
```json
{
  "apiContext": "hello",
  "apiVersion": "1.0",
  "fieldDefinitions": [
    {
      "name": "callback-url",
      "description": "Callback URL",
      "type": "URL"
    },
    {
      "name": "token",
      "description": "Secure Token",
      "type": "SecureToken"
    }
  ]
}
```

## GET Field Definitions For All APIs
### `/definition`
Retrieves the definitions of subscription fields for all APIs

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 200    | OK                                   |

### example

#### curl command
```
curl -v -X GET "http://localhost:9650/definition" -H "Cache-Control: no-cache"
```
#### Request body
None
#### Response body
```json
{
  "apis": [
    {
      "apiContext": "hello",
      "apiVersion": "1.0",
      "fieldDefinitions": [
        {
          "name": "callback-url",
          "description": "Callback URL",
          "type": "URL"
        },
        {
          "name": "token",
          "description": "Secure Token",
          "type": "SecureToken"
        }
      ]
    },
    {
      "apiContext": "ciao",
      "apiVersion": "2.0",
      "fieldDefinitions": [
        {
          "name": "address",
          "description": "where you live",
          "type": "String"
        },
        {
          "name": "number",
          "description": "telephone number",
          "type": "String"
        }
      ]
    }
  ]
}
```

## DELETE Field Definitions 
### `DELETE /definition/context/:apiContext/version/:apiVersion`
Deletes the definitions of all subscriptions fields for an API

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 204    | Deleted                              |
| 404    | Not found                            |

### example

#### curl command
```
curl -v -X DELETE "http://localhost:9650/definition/context/hello/version/1.0" -H "Cache-Control: no-cache"
```
#### Request body
None
#### Response body
None

## PUT Field Values 
### `PUT /field/application/:clientId/context/:apiContext/version/:apiVersion`
Creates or updates the field values of an API subscription   

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 201    | Created                              |
| 200    | Updated                              |

### example

#### curl command
```
curl -v -X PUT "http://localhost:9650/field/application/hBnFo14C0y4SckYUbcoL2PbFA40a/context/hello/version/1.0" -H "Cache-Control: no-cache" -H "Content-Type: application/json" -d '{ "fields" : { "callback-url" : "http://localhost:8080/callback", "token" : "abc59609za2q" } }'
```
#### Request body
```json
{
  "fields": {
    "callback-id": "http://localhost",
    "token": "abc123"
  }
}
```
#### Response body
```json
{
  "clientId": "hBnFo14C0y4SckYUbcoL2PbFA40a",
  "apiContext": "hello",
  "apiVersion": "1.0",
  "fieldsId": "55c2b945-1c82-4749-b4fc-42e5u32192ew", 
  "fields": {
    "callback-id": "http://localhost:8080/callback",
    "token": "abc59609za2q"
  }
}
```

## GET Field Values 
### `GET /field/application/:clientId/context/:apiContext/version/:apiVersion`
Retrieves the field values of an API subscription by providing the application and API details

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 200    | OK                                   |
| 404    | Not found                            |

### example

#### curl command
```
curl -v -X GET "http://localhost:9650/field/application/hBnFo14C0y4SckYUbcoL2PbFA40a/context/hello/version/1.0" -H "Cache-Control: no-cache"
```
#### Request body
None
#### Response body
```json
{
  "clientId": "hBnFo14C0y4SckYUbcoL2PbFA40a",
  "apiContext": "hello",
  "apiVersion": "1.0",
  "fieldsId": "55c2b945-1c82-4749-b4fc-42e5u32192ew", 
  "fields": {
    "callback-id": "http://localhost",
    "token": "abc123"
  }
}
```

## GET Field Values by `fieldsId` 
### `GET /field/application/:fieldsId`
Retrieves the field values by providing the `fieldsId` 

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 200    | OK                                   |
| 404    | Not found                            |

### example

#### curl command
```
curl -v -X GET "http://localhost:9650/field/f121ffa3-df94-43a0-8235-ac4530f9700a" -H "Cache-Control: no-cache"
```
#### Request body
None
#### Response body
```json
{
  "clientId": "xp5036mSZooNOlD0Nfjz7LKnCy0a",
  "apiContext": "hello",
  "apiVersion": "1.0",
  "fieldsId": "55c2b945-1c82-4749-b4fc-42e5u32192ew", 
  "fields": {
    "callback-id": "http://localhost",
    "token": "abc123"
  }
}
```

## GET Field Values by application 
### `GET /field/application/:clientId`
Retrieves the field values of all API subscriptions related to a specific application

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 200    | OK                                   |
| 404    | Not found                            |

### example

#### curl command
```
curl -v -X GET "http://localhost:9650/field/application/xp5036mSZooNOlD0Nfjz7LKnCy0a" -H "Cache-Control: no-cache"
```
#### Request body
None
#### Response body
```json
{
  "subscriptions": [
    {
      "clientId": "xp5036mSZooNOlD0Nfjz7LKnCy0a",
      "apiContext": "hello",
      "apiVersion": "1.0",
      "fieldsId": "55c2b945-1c82-4749-b4fc-42e5u32192ew",
      "fields": {
        "callback-id": "http://localhost:8080",
        "token": "abc123"
      }
    },
    {
      "clientId": "xp5036mSZooNOlD0Nfjz7LKnCy0a",
      "apiContext": "callme",
      "apiVersion": "2.0",
      "fieldsId": "66c2b945-1c82-4749-b4fc-42e5u39999ew",
      "fields": {
        "callback-id": "http://localhost:8081",
        "token": "def456"
      }
    }
  ]
}
```

## GET All Field Values
### `GET /field`
Retrieves the field values of all API subscriptions

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 200    | OK                                   |

### example

#### curl command
```
curl -v -X GET "http://localhost:9650/field -H "Cache-Control: no-cache"
```
#### Request body
None
#### Response body
```json
{
  "subscriptions": [
    {
      "clientId": "xp5036mSZooNOlD0Nfjz7LKnCy0a",
      "apiContext": "hello",
      "apiVersion": "1.0",
      "fieldsId": "55c2b945-1c82-4749-b4fc-42e5u32192ew",
      "fields": {
        "callback-id": "http://localhost:8080",
        "token": "abc123"
      }
    },
    {
      "clientId": "ab4536mPZooNOlD0Nfjz7LKuJy0b",
      "apiContext": "callme",
      "apiVersion": "2.0",
      "fieldsId": "66c2b945-1c82-4749-b4fc-42e5u39999ew",
      "fields": {
        "callback-id": "http://localhost:8081",
        "token": "def456"
      }
    }
  ]
}
```

## DELETE Field Values 
### `DELETE /field/application/:clientId/context/:apiContext/version/:apiVersion`
Deletes the field values of an API subscription

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 204    | Deleted                              |
| 404    | Not found                            |

### example

#### curl command
```
curl -v -X DELETE "http://localhost:9650/field/application/hBnFo14C0y4SckYUbcoL2PbFA40a/context/hello/version/1.0" -H "Cache-Control: no-cache"
```
#### Request body
None
#### Response body
None


## Tests
Some tests require MongoDB to run. 
Thus, remember to start up MongoDB if you want to run the tests locally.
There are unit tests, integration tests and acceptance tests plus code coverage reports are generated too.
In order to run them, use this command line:
```
./precheck.sh
```


### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")
