# API Subscription Fields

[![Build Status](https://travis-ci.org/hmrc/api-subscription-fields.svg)](https://travis-ci.org/hmrc/api-subscription-fields) [ ![Download](https://api.bintray.com/packages/hmrc/releases/api-subscription-fields/images/download.svg) ](https://bintray.com/hmrc/releases/api-subscription-fields/_latestVersion)

This microservice stores definitions and values for the HMRC Developer Hub. 

For a description of the road map for further development [see](docs/ROADMAP.md) 

## API Summary

| Path                                                                                                       |  Methods | Description                              |
|------------------------------------------------------------------------------------------------------------|----------|------------------------------------------|
| [`/definition/context/:apiContext/version/:apiVersion`](#user-content-put-field-definitions)               | `PUT`    | Creates or updates the definitions of the subscriptions fields for an API. |
| [`/definition/context/:apiContext/version/:apiVersion`](#user-content-get-field-definitions)               | `GET`    | Retrieves the definitions of subscription fields for an API |
| [`/definition`](#user-content-get-field-definitions-for-all-apis)                                          | `GET`    | Retrieves the definitions of subscription fields for all APIs |
| [`/field/application/:clientId/context/:apiContext/version/:apiVersion`](#user-content-put-field-values)   | `PUT`    | Creates or updates the field values of an API subscription |
| [`/field/application/:clientId/context/:apiContext/version/:apiVersion`](#user-content-get-field-values)   | `GET`    | Retrieves the field values of an API subscription by providing the application and API details |
| [`/field/:fieldsId`](#user-content-get-field-values-by-fieldsid)                                           | `GET`    | Retrieves the field values of an API subscription by providing the `fieldsId` |
| [`/field/application/:clientId`](#user-content-get-field-values-by-application)                            | `GET`    | Retrieves the field values of all API subscriptions related to a specific application |
| [`/field/application/:clientId/context/:apiContext/version/:apiVersion`](#user-content-delete-field-values)| `DELETE` | Deletes the field values of an API subscription |

## PUT Field Definitions 
### `PUT /definition/context/:apiContext/version/:apiVersion`
Creates or updates the definitions of the subscriptions fields for an API.

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 201    | Created                              |
| 200    | Updated                              |
| 400    | Bad request                          |
| 404    | Not found                            |

### PUT Field Definitions example

#### CURL command*
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
None

## GET Field Definitions 
### `GET /definition/context/:apiContext/version/:apiVersion`
Retrieves the definitions of subscription fields for an API

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 200    | OK                                   |
| 404    | Not found                            |

### GET Field Definitions example

#### CURL command
```
curl -v -X GET "http://localhost:9650/definition/context/hello/version/1.0" -H "Cache-Control: no-cache"
```
#### Response body
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

## GET Field Definitions For All APIs
### `/definition`
Retrieves the definitions of subscription fields for all APIs

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 200    | OK                                   |

### GET Field Definitions example

#### CURL command
```
curl -v -X GET "http://localhost:9650/definition"   -H "Cache-Control: no-cache"
```
#### Response body
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

## PUT Field Values 
### `PUT /field/application/:clientId/context/:apiContext/version/:apiVersion`
Creates or updates the field values of an API subscription   

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 201    | Created                              |
| 200    | Updated                              |
| 400    | Bad request                          |
| 404    | Not found                            |

### PUT Field Values

#### CURL command
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
  "clientId" : "xcsvvbe2882L",
  "apiContext" : "hello",
  "apiVersion" : "1.0",
  "fieldsId" : "55c2b945-1c82-4749-b4fc-42e5u32192ew", 
  "fields": {
    "callback-id": "http://localhost",
    "token": "abc123"
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

### GET Field Values example

#### CURL command
```
curl -v -X GET    "http://localhost:9650/field/application/hBnFo14C0y4SckYUbcoL2PbFA40a/context/hello/version/1.0" -H "Cache-Control: no-cache"
```
#### Response body
```json
{
  "clientId" : "xcsvvbe2882L",
  "apiContext" : "hello",
  "apiVersion" : "1.0",
  "fieldsId" : "55c2b945-1c82-4749-b4fc-42e5u32192ew", 
  "fields": {
    "callback-id": "http://localhost",
    "token": "abc123"
  }
}
```

## GET Field Values by `fieldsId` 
### `GET /field/application/:clientId`
Retrieves the field values of all API subscriptions related to a specific application

### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 200    | OK                                   |
| 404    | Not found                            |

### GET Field Values example

#### CURL command
```
curl -v -X GET    "http://localhost:9650/field/f121ffa3-df94-43a0-8235-ac4530f9700a" -H "Cache-Control: no-cache"
```
#### Response body
```json
{
  "clientId" : "xcsvvbe2882L",
  "apiContext" : "hello",
  "apiVersion" : "1.0",
  "fieldsId" : "55c2b945-1c82-4749-b4fc-42e5u32192ew", 
  "fields": {
    "callback-id": "http://localhost",
    "token": "abc123"
  }
}
```

## GET Field Values by application 
### `GET /field/application/:clientId`


### Response with

| Status | Description                          |
|--------|--------------------------------------|
| 200    | OK                                   |

### GET Field Values example

#### CURL command
```
curl -v -X GET "http://localhost:9650/field/application/xp5036mSZooNOlD0Nfjz7LKnCy0a" -H "Cache-Control: no-cache"
```
#### Response body
```json
{
  "fields":
  [
    {
      "clientId" : "xcsvvbe2882L",
      "apiContext" : "hello",
      "apiVersion" : "1.0",
      "fieldsId" : "55c2b945-1c82-4749-b4fc-42e5u32192ew", 
      "fields": {
        "callback-id": "http://localhost",
        "token": "abc123"
      }
    },
    {
      "clientId" : "xcsvvbe9999L",
      "apiContext" : "another-app",
      "apiVersion" : "1.0",
      "fieldsId" : "66c2b945-1c82-4749-b4fc-42e5u39999ew", 
      "fields": {
        "callback-id": "http://localhost",
        "token": "abc123"
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
| 204    | No content                           |
| 404    | Not found                            |

### DELETE Field Values example

#### CURL command
```
curl -v -X DELETE "http://localhost:9650/field/application/hBnFo14C0y4SckYUbcoL2PbFA40a/context/hello/version/1.0" -H "Cache-Control: no-cache"
```
#### Response body
None
