# Geocaching API

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
			<version>1.3</version>
		</dependency>
    ...
</dependencies>
```

## Required libraries (maven dependencies):
* log4j 1.2.16
* gson 2.1 (bundled and realocated classpath in release / snapshot jar archive)
* junit 4.10 (only for run tests)