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
    .containsEntry(JsonEntry.entry("id", 1))
    .contains("otherValue")
    .isStrictlyEqualTo(myFile)
    .isEqualToIgnoringFields(myFile, "id");
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

    .isStatusEqualTo(int expected)
    .isStatusNotEqualTo(int expected)
    .isStatusBetween(int start, int end)
    .isStatusNotBetween(int start, int end)

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

    .isMimeTypeTypeEqualTo(String expected)
    .isMimeTypeIn(String... expecteds)
    .hasCharset()
    .isCharsetEqualTo(String expected)

    // Pre-Built assertions

    .isUtf8()
    .isOctetStream()   // Check mime type is 'application/octet-stream' (RFC 2046)
    .isJson()          // Check mime type is 'application/json' (RFC 4627)
    .isXml()           // Check mime type is 'application/xml' (RFC 3023)
    .isCss()           // Check mime type is 'text/css' (RFC 2318)
    .isJavascript()    // Check mime type is 'application/javascript' or
                       // 'text/javascript' (RFC 4329)
    .isTextPlain()     // Check mime type is 'text/plain' (RFC 2049, RFC 3676)
    .isHtml()          // Check mime type is 'text/html' (RFC 2854)
    .isXhtml()         // Check mime type is 'application/xhtml+xml' (RFC 3023)
    .isHtmlOrXhtml()   // Check mime type is html or xhtml
    .isPdf()           // Check mime type is 'application/pdf' (RFC 3778)
    .isCsv()           // Check mime type is 'application/csv' (RFC 4180)
    .isZip()           // Check mime type is 'application/zip'
    .isFlashContent()  // Check mime type is 'application/x-shockwave-flash'
                       // or 'video/x-flv'

    .isJsonUtf8()      // Check response is json with utf-8 charset
    .isXmlUtf8()       // Check response is xml with utf-8 charset
    .isHtmlUtf8()      // Check response is html with utf-8 charset
```
___
#### Headers/Cookies Assertions :

```java

REST.assertThat(response)

    .hasHeader(String name)
    .hasHeaderEqualTo(String name, String value)
    .hasETagHeader()
    .hasETagEqualTo(String value)
    .hasLocationHeader()
    .hasLocationEqualTo(String value)
    .hasCookie(String name)
    .hasCookieEqualTo(String name, String value)
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

    .isArray()
    .isObject()
    .isArrayWithSize()
    .isEmptyArray()

    .contain(String key)
    .notContain(String key)
    .notContainOrIsNull(String key)
    .contains(String... keys)
    .contains(String... keys)
    .contains(List<String> keys)
    .contain(String key, T value)
    .containEntry(JsonEntry entry)
    .containsEntries(JsonEntry... entries)

    // Pre-Built assertions:

    .isNull(String key)
    .isNotNull(String key)
    .isNumber(String key)
    .isString(String key)
    .isBoolean(String key)
    .isArray(String key)
    .isArrayWithSize(String key, int size)
    .isEmptyArray(String key)
    .isEmptyString(String key)
    .isStringNotEmpty(String key)
    .isTrue(String key)
    .isFalse(String key)
    .isZero(String key)
    .isGreaterThan(String key, int value)
    .isGreaterThanOrEqualTo(String key, int value)
    .isLessThan(String key, int value)
    .isLessThanOrEqualTo(String key, int value)
    .isPositive(String key)
    .isNegative(String key)

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

    .isNameEqualTo(String name)
    .isValueEqualTo(String value)
    .isDomainEqualTo(String domain)
    .isPathEqualTo(String path)
    .isSecure()
    .isNotSecure()
    .isHttpOnly()
    .isNotHttpOnly()
```