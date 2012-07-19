Geocaching API for Java
=======================

Java implementation of Groundspeak's Geocaching Live API.

Released builds are available from a private maven repository. So add bellow to
your `pom.xml` file:

```xml
<repositories>
  <repository>
    <id>maven-arcao-com</id>
    <url>http://maven.arcao.com</url>
  </repository>
</repositories>
```

> Note: It's a Maven2 compatible repository.

And add dependency to geocaching-api artifact:

```xml
<dependencies>
		<dependency>
			<groupId>com.arcao</groupId>
			<artifactId>geocaching-api</artifactId>
			<version>1.5.5.1</version>
		</dependency>
    ...
</dependencies>
```

Get the source
--------------

Fork the project source code on [github][geocaching-api]:

	git clone git://github.com/arcao/geocaching-api.git

Get latest binaries
-------------------

Binaries you can found in [my maven repository][binaries].

Required libraries (maven dependencies)
---------------------------------------

- log4j 1.2.16
- gson 2.2.1 (bundled and realocated classpath in release / snapshot jar archive)
- junit 4.10 (only for run tests)
- commons-lang3 3.1

Examples
--------

Example codes how to use geocaching-api library you can found in [geocaching-api-examples] repository.

License
-------

geocaching-api is distributed under [Apache License, Version 2.0][license].

Contact
-------

- author: Martin Sloup aka arcao
- questions: arcao@arcao.com

[license]: http://www.apache.org/licenses/LICENSE-2.0
[geocaching-api]: https://github.com/arcao/geocaching-api
[geocaching-api-examples]: https://github.com/arcao/geocaching-api-examples
[binaries]: http://maven.arcao.com/com/arcao/geocaching-api/