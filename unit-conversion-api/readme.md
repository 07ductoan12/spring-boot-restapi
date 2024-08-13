# Summary

**@PostMapping** :

- Specifies the handler method will process only HTTP POST requests
- You should use HTTP POST if the request body must contain data in from of JSON

**@RequestBody** :

- Binds method parameter to the body of the request
- Spring automatically serializes JSON into Java object which is then passed to handler method

You can specify HTTP status code explicitly for the response via <span style="color:red">ResponseEntiry</span>

- `ResponseEntiry.badRequest().build();`
- `new ResponseEntiry<>(details, HttpStatus.OK);`
