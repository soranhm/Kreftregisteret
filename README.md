# Kreftregisteret

# Programmvare

Denne oppgaven er løst med:

- Spring boot: version 2.3.4
- Java: version 11
- Maven
- Dependencies:

  - spring-boot-starter-actuator
  - jackson-dataformat-xml
  - spring-boot-starter-data-jpa
  - spring-boot-starter-web
  - spring-boot-devtools
  - h2

# Kjøring

REST apien kan kjøres ved å klone over repositoriet på egen pc og kjøre filen: Kreftregisteret/src/main/java/com/test/api/ApiApplication.java. Da vil det være mulig visualisere på nettleseren [local](http://localhost:8080). Selve innholde er plassert bak api/v1/, men det er også mulig å visualisere og kjøre SQL kommandoer via h2, dette gjøres på [h2-console](http://localhost:8080/h2-console/) (JDBC URL: jdbc:h2:mem:testdb).

Det er flere måter å kjøre CRUD kommandoene:

- [Postman](https://www.postman.com)
  - Forenkler visualisering og testing av api
- [Curl](https://curl.haxx.se/docs/manpage.html)
  - Direkte kjøring av kommandoer i termianlen som kan visualiseres på http.

# Tankegang

Denne oppgaven var ganske spennende, den fikk meg til å vise mine ferdigheter i Java, Spring Boot, SQL og mange andre steder. Målet med oppgvaen var å sørge for at alle "Requests" ble oppfylt.

## Diagram

![](https://github.com/soranhm/Kreftregisteret/blob/master/Diagram.png?raw=true)

## Requests

Listene under viser oppgavene som skulle kjøres og testes. HUSK å fyll unn verdiene i {...}. De er testet med Postman og Curl:

- List all users

Postmann/HTTP

```
GET ttp://localhost:8080/api/v1/users
```

Curl

```Bash
> curl -X GET http://localhost:8080/api/v1/users
> [
{"id":1,"name":"Alice","version":1},
{"id":2,"name":"Bob","version":2},
{"id":3,"name":"Eve","version":1}
]
```

- List all users with at least one valid user role at a given unit at a given time

Postmann/HTTP ( dateformat = YYYY-MM-DDTHH:MM:SS )

```
GET http://localhost:8080/api/v1/users/?unitid={unitid}&date={date}
```

Curl

```Bash
> curl -X GET "http://localhost:8080/api/v1/users/?                       date=2019-03-04T00:00:00&unitid=12"
> [
{"id":2,"name":"Bob","version":2},
{"id":2,"name":"Bob","version":2}
]
```

- List all units

Postmann/HTTP

```
GET http://localhost:8080/api/v1/units
```

Curl

```Bash
> curl -X GET http://localhost:8080/api/v1/units
> [
{"id":11,"name":"Kreftregisteret","version":2},
{"id":12,"name":"Akershus universitetssykehus HF","version":1},{"id":13,"name":"Sørlandet sykehus HF","version":2},
{"id":14,"name":"Vestre Viken HF","version":2}
]
```

- List all roles

Postmann/HTTP

```
GET http://localhost:8080/api/v1/roles
```

Curl

```Bash
> curl -X GET http://localhost:8080/api/v1/roles
> [
{"id":101,"name":"User administration","version":1},
{"id":102,"name":"Endoscopist administration","version":2},
{"id":103,"name":"Report colonoscopy capacity","version":1},
{"id":104,"name":"Send invitations","version":2},
{"id":105,"name":"View statistics","version":2}
]
```

- List all user roles (both currently valid and invalid) for a given user id and unit id

Postmann/HTTP

```
GET http://localhost:8080/api/v1/userroles/?userid={userid}&unitid={unitid}
```

Curl

```Bash
> curl -X GET "http://localhost:8080/api/v1/userroles/?userid=1&unitid=11"
>[
{"id":1001,"version":1,"userId":1,"unitId":11,"roleId":101,"validFrom":"2019-01-02 00:00:00","validTo":"2019-12-13 23:59:59"},{"id":1002,"version":2,"userId":1,"unitId":11,"roleId":104,"validFrom":"2019-01-02 00:00:00","validTo":"2019-12-13 23:59:59"},{"id":1003,"version":1,"userId":1,"unitId":11,"roleId":105,"validFrom":"2019-06-11 00:00:00","validTo":null},
{"id":1008,"version":1,"userId":1,"unitId":11,"roleId":101,"validFrom":"2019-02-01 07:00:00","validTo":null},
{"id":1009,"version":1,"userId":1,"unitId":11,"roleId":104,"validFrom":"2019-02-01 07:00:00","validTo":null}
]
```

- List only valid user roles for a given user id and unit id at a given timestamp

Postmann/HTTP ( dateformat = YYYY-MM-DDTHH:MM:SS )

```
GET  http://localhost:8080/api/v1/userroles/?userid=2&unitid={unitid}&date={date}
```

Curl

```Bash
> curl -X GET "http://localhost:8080/api/v1/userroles/?userid=2&unitid=12&date=2019-03-04T00:00:00"
> [
{"id":1004,"version":2,"userId":2,"unitId":12,"roleId":101,"validFrom":"2019-01-28 00:00:00","validTo":null},
{"id":1005,"version":1,"userId":2,"unitId":12,"roleId":105,"validFrom":"2019-01-28 00:00:00","validTo":null}
]
```

- Create a new user

Postmann ( Use Post and fill json body )

```
POST http://localhost:8080/api/v1/users
json-body: {"name": "Soran","version": 1}
```

Curl

```Bash
> curl -d '{"name":"Soran", "version":1}' -H "Content-Type: application/json" -X POST http://localhost:8080/api/v1/users
> {"id":4,"name":"Soran","version":1}
```

- Update an existing user

Postmann ( Use Put and fill json body )

```
PUT http://localhost:8080/api/v1/users/{id}
json-body: {"name": "Soran","version": 2}
```

Curl

```Bash
> curl -d '{"name":"Soran2", "version":2}' -H "Content-Type: application/json" -X PUT http://localhost:8080/api/v1/users/4
> {"id":4,"name":"Soran2","version":2}
```

- Delete an existing user. A user can only be deleted if there are no user roles for that user.

Postmann ( Use DELETE )

```
DELETE http://localhost:8080/api/v1/users/{id}
```

Curl ( en kjøring der id=3 ikke blir fjernet og en der id = blir fjernet )

```Bash
> curl -X DELETE http://localhost:8080/api/v1/users/3
> curl -X DELETE http://localhost:8080/api/v1/users/2
```

- Create a new user role for a given user id, unit id, role id, an optional valid from timestamp (if not specified, default to the current date and time) and an optional valid to timestamp (if not specified, default to no timestamp). If a valid to timestamp is specified, it must be after the valid from timestamp (or the current date and time if valid from timestamp is not specified in the request). At most one user role for a given combination of user id, unit id and role id can be valid at any point in time.

Postmann ( Use POST and fill json-body )

```
POST http://localhost:8080/api/v1/userroles
json-body :{
  "version": 1,
  "userId":1,
  "unitId":11,
  "roleId":101
  ("validFrom": "2020-09-21 23:01:18",
  "validTo": null)
  }
```

Curl ( Alle tillfelene er tested og skal funke, kunn en er vist her )

```Bash
> curl -X POST http://localhost:8080/api/v1/userroles -d '{"version": 1,"userId":2,"unitId":13,"roleID":110}' -H "Content-Type: application/json"
>{"id":1010,"version":1,"userId":2,"unitId":13,"roleId":0,"validFrom":"2020-09-22 11:32:19","validTo":null}
```

- Update an existing user role. Only the valid from and valid to timestamps can be changed. The valid from timestamp, if specified, must be a timestamp (a user role must always have a valid from timestamp). The requirement that the valid to timestamp, if specified, must come after the valid from timestamp must be enforced, and an update that would cause two user roles for the same user id, unit id and role id to be valid at the same time must be rejected.

Postmann ( Use PUT and fill json-body )

```
PUT http://localhost:8080/api/v1/userroles/{userRoleId}
json-body: {
"validFrom": "2020-01-28 00:00:00",
"validTo": "2029-01-28 00:00:00"
}
```

Curl ( Alle tillfelene er tested og skal funke, kunn en er vist her )

```Bash
> curl -d '{"validFrom":"2020-01-28 00:00:00", "validTo":"2023-01-28 00:00:00"}' -H "Content-Type: application/json" -X PUT http://localhost:8080/api/v1/userroles/1005
> {"id":1005,"version":1,"userId":2,"unitId":12,"roleId":105,"validFrom":"2020-01-28 00:00:00","validTo":"2023-01-28 00:00:00"}
```

- Delete an existing user role.

Postmann ( Use DELETE )

```
DELETE http://localhost:8080/api/v1/userroles/{userRoleid}
```

Curl

```Bash
> curl -X DELETE http://localhost:8080/api/v1/userroles/1005
```

- For a given unit id, list all users with at least one user role at that unit (whether the user role is currently valid or not), and for each user, list all ofthe user's user roles at the given unit id. If application/xml is specified in the Accept header of the request, the message body of the response should be valid XML. If application/json is specified in the Accept header of the request, the message body of the response should be as JSON.

Postmann ( Use GET )

- Sett Headers: key = Accept, value = application/json or application/xml

```
GET http://localhost:8080/api/v1/userroles/{userRoleid}
```

```Bash
> curl -H "Accept: application/xml" http://localhost:8080/api/v1/userroles
> ....
> curl -H "Accept: application/json" http://localhost:8080/api/v1/userroles
> ...
```
