# Fest Rest Assert

This library is an extension of FEST which aims to make it even easier to test Rest Services (status code, response, json matchers).
Do request using your favorite library (I love Async-Http-Client) and check response use Fest-Rest-Assert.

## Examples

```java
AsyncHttpClient client = new AsyncHttpClient();
Response response = client.prepareGet("http://domain.com/api/get")
    .execute()
    .get();

// Check response status
REST.assertThat(response)
    .isOk()
    .hasETagHeader()
    .isJsonUtf8();

// Check response body
REST.assertJsonThat(response)
    .isObject()
    .containsEntry(JsonEntry.entry("id", 1)) // Check a key is found with an
                                             // expected value
    .contains("otherValue")                  // Check a key is found
    .isStrictlyEqualTo(myFile)               // Compare json in response with json in a given file
    .isEqualToIgnoringFields(myFile, "id");  // Compare json ignoring field 'id'
```

# Api

### Response Assertions:

```java
// Build an assertion object:

// - Use async-http-client
REST.assertThat(com.ning.http.client.Response response);

// - Use apache http components:
REST.assertThat(org.apache.http.HttpResponse response);

// In case you use another client api, you can wrap your
// response in org.fest.assertions.util.Response:
REST.assertThat(org.fest.assertions.util.Response response);

```
___
#### Status Assertions :

```java

REST.assertThat(response)

    .isStatusEqualTo(int expected)          // Check statusCode == expected
    .isStatusNotEqualTo(int expected)       // Check statusCode != expected

    .isStatusBetween(int start, int end)    // Check start <= statusCode <= end
    .isStatusNotBetween(int start, int end) // Check statusCode > start
                                            //       || statusCode < end

    // Pre-Built assertions :

    .isSuccess()                            // Check: 200 <= statusCode <= 299
    .isRedirection()                        // Check: 300 <= statusCode <= 399
    .isClientError()                        // Check: 400 <= statusCode <= 499
    .isServerError()                        // Check: 500 <= statusCode <= 599

    .isOk()                                 // Check statusCode == 200
    .isCreated()                            // Check statusCode == 201
    .isAccepted()                           // Check statusCode == 202
    .isNonAuthoritativeInformation()        // Check statusCode == 203
    .isNoContent()                          // Check statusCode == 204
    .isResetContent()                       // Check statusCode == 205
    .isPartialContent()                     // Check statusCode == 206

    .isMultipleChoice()                     // Check statusCode == 300
    .isMovedPermanently()                   // Check statusCode == 301
    .isMovedTemporarily()                   // Check statusCode == 302
    .isSeeOther()                           // Check statusCode == 303
    .isNotModified()                        // Check statusCode == 304

    .isBadRequest()                         // Check statusCode == 400
    .isUnauthorized()                       // Check statusCode == 401
    .isForbidden()                          // Check statusCode == 403
    .isNotFound()                           // Check statusCode == 404
    .isMethodNotAllowed()                   // Check statusCode == 405

    .isInternalServerError()                // Check statusCode == 500
    .isNotImplemented();                    // Check statusCode == 501
```

___
#### Content-Type Assertions :

```java

REST.assertThat(response)

    .isMimeTypeTypeEqualTo(String expected)  // Check mime type == expected
    .isMimeTypeIn(String... expecteds)       // Check mime type is in expecteds

    .hasCharset()                            // Check charset is defined
    .isCharsetEqualTo(String expected)       // Check charset == expected

    // Pre-Built assertions

    .isUtf8()                                // Check charset is UTF-8

    .isOctetStream()                         // Check mime type is
                                             //   'application/octet-stream' (RFC 2046)
    .isJson()                                // Check mime type is
                                             //   'application/json' (RFC 4627)
    .isXml()                                 // Check mime type is
                                             //   'application/xml' (RFC 3023)
    .isCss()                                 // Check mime type is
                                             //   'text/css' (RFC 2318)
    .isJavascript()                          // Check mime type is
                                             //   'application/javascript' or
                                             //   'text/javascript' (RFC 4329)
    .isTextPlain()                           // Check mime type is
                                             //   'text/plain' (RFC 2049, RFC 3676)
    .isHtml()                                // Check mime type is
                                             //   'text/html' (RFC 2854)
    .isXhtml()                               // Check mime type is
                                             //   'application/xhtml+xml' (RFC 3023)
    .isHtmlOrXhtml()                         // Check mime type is html or xhtml
    .isPdf()                                 // Check mime type is
                                             //   'application/pdf' (RFC 3778)
    .isCsv()                                 // Check mime type is
                                             //   'application/csv' (RFC 4180)
    .isZip()                                 // Check mime type is 'application/zip'
    .isFlashContent()                        // Check mime type is
                                             //   'application/x-shockwave-flash'
                                             //   or 'video/x-flv'

    .isJsonUtf8()                            // Check response is json
                                             // with utf-8 charset
    .isXmlUtf8()                             // Check response is xml
                                             // with utf-8 charset
    .isHtmlUtf8()                            // Check response is html
                                             // with utf-8 charset
```
___
#### Headers/Cookies Assertions :

```java

REST.assertThat(response)

    .hasHeader(String name)                        // Check header 'name' is defined
    .hasHeaderEqualTo(String name, String value)   // Check header 'name' is defined
                                                   // with an expected value
    .hasETagHeader()                               // Check header 'ETag' is defined
    .hasETagEqualTo(String value)                  // Check header 'ETag' is defined
                                                   // with and expected value
    .hasLocationHeader()                           // Check header 'Location' is
                                                   // defined
    .hasLocationEqualTo(String value)              // Check header 'Location'
                                                   // defined with and expected value
    .hasCookie(String name)                        // Check cookie 'name' is defined
    .hasCookieEqualTo(String name, String value);  // Check cookie 'name' is defined
                                                   // with an expected value
```

### JSON Assertions:

```java
// Build an assertion object:

// - Use async-http-client
REST.assertJsonThat(com.ning.http.client.Response response);

// - Use apache http components:
String json = "{}";
REST.assertJsonThat(json);
```


```java

REST.assertJsonThat(json)

    .isArray()                                // Check json is an array
    .isObject()                               // Check json is an object
    .isArrayWithSize()                        // Check json is an array
                                              // with an expected size
    .isEmptyArray()                           // Check json is an empty array

    .contain(String key)                      // Check json contains a key
    .notContain(String key)                   // Check json does not contain a key
    .notContainOrIsNull(String key)           // Check json does not contain a key
                                              // or value for the key is null
    .contains(String... keys)                 // Check json contains each keys
    .contains(String... keys)                 // Check json contains each keys
    .contains(List<String> keys)              // Check json contains each keys
    .contain(String key, T value)             // Check json contain key with an
                                              // expected value
    .containEntry(JsonEntry entry)            // Check json contain an expected entry
    .containsEntries(JsonEntry... entries)    // Check json contains expected entries

    .isNull(String key)                       // Check key is found and value for
                                              // given key is null
    .isNotNull(String key)                    // Check key is found and value for
                                              // given key is not null
    .isNumber(String key)                     // Check key is found and value for
                                              // given key is a number
    .isString(String key)                     // Check key is found and value for
                                              // given key is a string
    .isBoolean(String key)                    // Check key is found and value for
                                              // given key is a string
    .isArray(String key)                      // Check key in json is an array
    .isArrayWithSize(String key, int size)    // Check key in json is an array
                                              // with an expected size
    .isEmptyArray(String key)                 // Check key in json is an empty array

    .isEmptyString(String key)                // Check json contain key and value is
                                              // an empty string
    .isStringNotEmpty(String key)             // Check json contain key and value is
                                              // not an empty string
    .isTrue(String key)                       // Check json contain key
                                              // and value is true
    .isFalse(String key)                      // Check json contain key
                                              // and value is false
    .isZero(String key)                       // Check json contain key and value is 0

    .isGreaterThan(String key, int value)     // Check json contain key and
                                              // value is > an expected value
    .isGreaterThanOrEqualTo(String key,
                            int value)        // Check found value is >= value
    .isLessThan(String key, int value)        // Check found value is < value
    .isLessThanOrEqualTo(String key,
                         int value)           // Check found value is <= value
    .isPositive(String key)                   // Check value is >= 0
    .isNegative(String key)                   // Check value is <= 0

     // Compare json representation
    .isStrictlyEqualsTo(String json)
    .isStrictlyEqualsTo(File file)
    .isStrictlyEqualsTo(URI uri)
    .isStrictlyEqualsTo(URL url)
    .isStrictlyEqualsTo(Object object)
    .isStrictlyEqualsTo(Object object, ObjectMapper mapper)

     // Compare json representation, ignoring given fields
    .isEqualsToIgnoringFields(String json, List<String> fields)
    .isEqualsToIgnoringFields(File file, List<String> fields)
    .isEqualsToIgnoringFields(URI uri, List<String> fields)
    .isEqualsToIgnoringFields(URL url, List<String> fields)
    .isEqualsToIgnoringFields(Object object, List<String> fields)
    .isEqualsToIgnoringFields(Object object, ObjectMapper mapper, List<String> fields)

     // Compare json representation, ignoring given fields
    .isEqualsToIgnoringFields(String json, List... fields)
    .isEqualsToIgnoringFields(File file, List... fields)
    .isEqualsToIgnoringFields(URI uri, List... fields)
    .isEqualsToIgnoringFields(URL url, List... fields)
    .isEqualsToIgnoringFields(Object object, List... fields)
    .isEqualsToIgnoringFields(Object object, ObjectMapper mapper, List... fields)
```
### Cookies Assertions:

```java
// Build an assertion object:

// - Use async-http-client
REST.assertCookieThat(String cookieName, com.ning.http.client.Response response);

// - Use javax.servlet.http.Cookie
REST.assertThat(javax.servlet.http.Cookie cookie);
```

```java
REST.assertThat(cookie)

    .isNameEqualTo(String name)     // Check that name of cookie is equal
                                    // to an expected value
    .isValueEqualTo(String value)   // Check that value of cookie is equal
                                    // to an expected value
    .isDomainEqualTo(String domain) // Check that domain of cookie is equal
                                    // to an expected value
    .isPathEqualTo(String path)     // Check that path of cookie is equal
                                    // to an expected value
    .isSecure()                     // Check that cookie is secure
    .isNotSecure()                  // Check that cookie is not secure
    .isHttpOnly()                   // Check that cookie is http only
    .isNotHttpOnly()                // Check that cookie is not http only

```