# Summary

**@RestController** :

- Expose a class as a REST Webservices controller
- Method return value will be bound to the web response directly, instead of going through a view resolver

**@GetMaping** : specifies the handler method will process only HTTP GET requests

The object returned by the handler method is automatically serialized into JSON and passed back into the HttpResponse object

**Testing with curl** :

- **curl localhost:8080/api/hello**
- use -v flag to see verbose output, e.g see details of request and response
- **curl localhost:8080/api/hello | <span style="color:red">json_pp</span>**

**Example:**

```text
curl -v -s localhost:8080/api/hello
*   Trying 127.0.0.1:8080...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /api/hello HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.81.0
> Accept: */*
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 200
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Tue, 13 Aug 2024 04:37:55 GMT
<
* Connection #0 to host localhost left intact
{"message":"Hello World, is the first REST API. The current time is:","time":"Tue Aug 13 11:37:55 ICT 2024"}%

```

with **| json_pp**

```text
➜  ~ curl -v -s localhost:8080/api/hello | json_pp
*   Trying 127.0.0.1:8080...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /api/hello HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.81.0
> Accept: */*
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 200
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Tue, 13 Aug 2024 04:38:27 GMT
<
{ [114 bytes data]
* Connection #0 to host localhost left intact
{
   "message" : "Hello World, is the first REST API. The current time is:",
   "time" : "Tue Aug 13 11:38:27 ICT 2024"
}
```
