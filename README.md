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
			<version>1.4.4</version>
		</dependency>
    ...
</dependencies>
```

Get the source
--------------

Fork the project source code on [github](https://github.com/arcao/geocaching-api):

	git clone git://github.com/arcao/geocaching-api.git


Required libraries (maven dependencies)
---------------------------------------

- log4j 1.2.16
- gson 2.1 (bundled and realocated classpath in release / snapshot jar archive)
- junit 4.10 (only for run tests)
- commons-lang3 3.1

License
-------

geocaching-api is distributed under [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0).

Contact
-------

- author: Martin Sloup aka arcao
- questions: arcao@arcao.com
