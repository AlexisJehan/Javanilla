![Javanilla](logo.png)
# Javanilla
[![Maven Central](https://img.shields.io/maven-central/v/com.github.alexisjehan/javanilla.svg)](https://mvnrepository.com/artifact/com.github.alexisjehan/javanilla/latest)
[![Javadoc](http://www.javadoc.io/badge/com.github.alexisjehan/javanilla.svg)](http://www.javadoc.io/doc/com.github.alexisjehan/javanilla)
[![Travis](https://img.shields.io/travis/alexisjehan/javanilla.svg)](https://travis-ci.org/alexisjehan/javanilla)
[![Codecov](https://img.shields.io/codecov/c/github/alexisjehan/javanilla.svg)](https://codecov.io/gh/alexisjehan/javanilla)
[![License](https://img.shields.io/github/license/alexisjehan/javanilla.svg)](https://github.com/alexisjehan/javanilla/blob/master/LICENSE.txt)

A _Java 10_ lightweight utility library to enhance the Java standard API.

## Introduction
**Javanilla** has been made to improve the developer experience with Java, in fact it provides lot of easy-to-use
components for commons usages. Its goal is not to replace popular utility libraries such as _Apache Commons_ Lang or
_Google Guava_ because it is not as much complete or reliable, but Javanilla is composed of some original tools that
could complete them.

## Getting started
To include and use Javanilla, you need to add the following dependency into your _Maven_ _pom.xml_ file:
```xml
<dependency>
	<groupId>com.github.alexisjehan</groupId>
	<artifactId>javanilla</artifactId>
	<version>1.0.0</version>
</dependency>
```

Also the Javadoc can be accessed [here](http://www.javadoc.io/doc/com.github.alexisjehan/javanilla).

## Examples
Here are non-exhaustives examples of how you can use Javanilla.

### I/O examples
Some _InputStream_ and _OutputStream_ utility tools and decorators:
```java
// Read from an optional InputStream and then from another one which gives 0x00 and 0xff bytes
final var concatInputStream = InputStreams.concat(
		InputStreams.nullToEmpty(optionalInputStream),
		InputStreams.of((byte) 0x00, (byte) 0xff)
);
// Write to both a buffered file OutputStream and a sampling one
final var teeOutputStream = OutputStreams.tee(
		OutputStreams.buffered(fileOutputStream),
		new RangeOutputStream(sampleOutputStream, 100L) // Write only the 100 firsts bytes
);
// Wrap the InputStream to be used in a foreach-style loop for a better readability
for (final var b : Iterables.wrap(concatInputStream)) {
	teeOutputStream.write(b);
}
teeOutputStream.flush();
```

Convert line separators from an _Unix_ file to a _Windows_ one using _LineReader_ and _LineWriter_:
```java
final var ignoreTerminatingNewLine = true;
try (final var lineReader = new LineReader(unixFilePath, LineSeparator.LF, ignoreTerminatingNewLine)) {
	final var appendTerminatingNewLine = false;
	try (final var lineWriter = new LineWriter(windowsFilePath, LineSeparator.CR_LF, appendTerminatingNewLine)) {
		// Transfers all lines from the LineReader to the LineWriter
		final var transferred = lineReader.transferTo(lineWriter);
		System.out.println(transferred + " lines transferred");
	}
}
```

### Lang examples
_String_ and _CharSequence_ utility tools:
```java
System.out.println(Strings.blankToEmpty("   ")); // Prints an empty String
final var times = 5;
System.out.println(Strings.repeat("xX", times)); // Prints "xXxXxXxXxX"
final var size = 5;
System.out.println(Strings.padLeft("foo", size)); // Prints "  foo"
System.out.println(Strings.removeEnd("foo", 'o')); // Prints "fo"
System.out.println(Strings.replaceLast("foo", 'o', 'r')); // Prints "for"
System.out.println(Strings.concatMerge("Once upon a time ...", "... the end")); // Prints "Once upon a time ... the end"
System.out.println(Strings.isHex(ByteArrays.toHexString("foo".getBytes())) ? "yes" : "no"); // Prints "yes"
System.out.println(Strings.isBase64(Base64.getEncoder().encodeToString("foo".getBytes())) ? "yes" : "no"); // Prints "yes"
```

_Throwable_ utility tools:
```java
// Sleep 5 seconds and throw an unchecked Exception if the thread is interrupted, no try/catch required
Throwables.uncheck(() -> Thread.sleep(5_000L));

// Handle checked Exceptions in lambda converting them automatically to unchecked ones
try {
	Throwables.uncheck(() -> {
		throw new IOException("A checked Exception inside a lambda");
	});
} catch (final UncheckedIOException e) {
	System.out.println(Throwables.getRootCause(e).getMessage()); // Prints "A checked Exception inside a lambda"
}
```

Primitive and generic arrays utility tools:
```java
IntArrays.of(1, 2, 3); // Similar to new int[] {1, 2, 3};
ObjectArrays.of("foo", "bar"); // Similar to new String[] {"foo", "bar"};
LongArrays.concat(LongArrays.of(1L, 2L, 3L), LongArrays.of(4L, 5L, 6L)); // 1, 2, 3, 4, 5, 6
final var separator = CharArrays.of(' ');
CharArrays.join(separator, CharArrays.of('f', 'o', 'o'), CharArrays.of('b', 'a', 'r')); // 'f', 'o', 'o', ' ', 'b', 'a', 'r'
FloatArrays.containsAll(FloatArrays.of(0.0f, 1.0f, 2.0f), 2.0f, 3.0f); // False
FloatArrays.containsAll(FloatArrays.of(1.0f, 2.0f, 3.0f), 2.0f, 3.0f); // True
DoubleArrays.indexOf(DoubleArrays.of(1.0d, 2.0d, 3.0d), 2.0d); // 1
ByteArrays.toHexString(
		ByteArrays.concat(
				ByteArrays.of((byte) 0x00, (byte) 0xff),
				ByteArrays.ofDouble(3.14d, ByteOrder.BIG_ENDIAN),
				ByteArrays.ofHexString("0xff"),
				"foo".getBytes()
		)
); // 00ff40091eb851eb851fff666f6f
```

### Misc examples
Print values as pretty strings using _StringFormatter_:
```java
final var floatPrecision = 3; // Up to 3 digits after the floating point
final var stringFormatter = new StringFormatter(Locale.US, floatPrecision);
System.out.println(stringFormatter.format(1_234_567L)); // Prints 1,234,567
System.out.println(stringFormatter.formatPercent(1.0d, 3.0d)); // Prints 33.333%
System.out.println(stringFormatter.formatCurrency(123.456789d)); // Prints $123.457
System.out.println(stringFormatter.formatBytes(1_300_000L)); // Prints 1.24MiB
System.out.println(stringFormatter.formatBytes(1_300_000L, StringFormatter.BytePrefix.SI)); // Prints 1.3MB
```

Some _Distance_ and _EditDistance_ calculations:
```java
System.out.println(Distances.MANHATTAN.calculate(0.0d, 0.0d, 1.0d, 1.0d)); // Prints 2
final var order = 1;
System.out.println(new MinkowskiDistance(order).calculate(0.0d, 1.0d, 2.0d, 3.0d)); // Prints 4
System.out.println(EditDistances.HAMMING.calculate("foo", "for")); // Prints 1
System.out.println(new LevenshteinDistance().calculate("append", "apple")); // Prints 3
```

### Util examples
New _Comparator_ implementations:
```java
System.out.println("foo10".compareTo("foo2")); // Prints -1
System.out.println(Comparators.NUMBER_AWARE.compare("foo10", "foo2")); // Prints 1
System.out.println(Comparators.DOUBLE_ARRAYS.compare(
		DoubleArrays.of(0.0d, 1.0d, 2.0d),
		DoubleArrays.of(0.0d, 1.0d)
)); // Prints 1
System.out.println(Comparators.<String>array().compare(
		ObjectArrays.of("foo", "bar2"),
		ObjectArrays.of("foo", "bar1")
)); // Prints 1
```

New _Bag_ collection type:
```java
final var bag = new MapBag<String>();
bag.add("foo");
bag.add("bar", 5L);
System.out.println(bag.count("foo")); // Prints 1
System.out.println(bag.distinct()); // Prints 2
System.out.println(bag.size()); // Prints 6
```

_Iterator_ utility tools:
```java
// Iterator to iterate over groups of integers
final var batchIterator = new BatchIterator<>(
		Iterators.ofInt(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
		3 // Batch size
);
// Iterator that counts iterated groups
final var countIterator = new CountIterator<>(batchIterator);
// Wrap the Iterator to be used in a foreach-style loop for a better readability
for (final var list : Iterables.wrap(countIterator)) {
	System.out.println(list); // Prints [1, 2, 3], [4, 5, 6], [7, 8, 9] and [10]
}
System.out.println(countIterator.getCount()); // Prints 4
```

## Recurrent functions availability
|                | InputStreams | OutputStreams | Readers  | Writers       | Strings  | XxxArrays  |
| :------------: | :----------: | :-----------: | :------: | :-----------: | :------: | :--------: |
| EMPTY          | &#x2713;     | _BLANK_       | &#x2713; | _BLANK_       | &#x2713; | &#x2713;   |
| ENDLESS        | &#x2713;     |               | &#x2713; |               |          |            |
| nullToEmpty    | &#x2713;     | _nullToBlank_ | &#x2713; | _nullToBlank_ | &#x2713; | &#x2713;   |
| nullToDefault  | &#x2713;     | &#x2713;      | &#x2713; | &#x2713;      | &#x2713; | &#x2713;   |
| emptyToNull    |              |               |          |               | &#x2713; | &#x2713;   |
| emptyToDefault |              |               |          |               | &#x2713; | &#x2713;   |
| buffered       | &#x2713;     | &#x2713;      | &#x2713; | &#x2713;      |          |            |
| uncloseable    | &#x2713;     | &#x2713;      | &#x2713; | &#x2713;      |          |            |
| length         | &#x2713;     |               | &#x2713; |               |          |            |
| concat         | &#x2713;     |               | &#x2713; |               |          | &#x2713;   |
| join           | &#x2713;     |               | &#x2713; |               |          | &#x2713;   |
| tee            |              | &#x2713;      |          | &#x2713;      |          |            |
| singleton      | &#x2713;     |               | &#x2713; |               |          | &#x2713;   |
| of             | &#x2713;     |               | &#x2713; |               | &#x2713; | &#x2713;   |

|                | Lists    | Sets        | Maps               | Bags     | Iterables | Iterators |
| :------------: | :------: | :---------: | :----------------: | :------: | :-------: | :-------: |
| empty          |          |             |                    | &#x2713; | &#x2713;  | &#x2713;  |
| nullToEmpty    | &#x2713; | &#x2713;    | &#x2713;           | &#x2713; | &#x2713;  | &#x2713;  |
| nullToDefault  | &#x2713; | &#x2713;    | &#x2713;           | &#x2713; | &#x2713;  | &#x2713;  |
| emptyToNull    | &#x2713; | &#x2713;    | &#x2713;           | &#x2713; | &#x2713;  | &#x2713;  |
| emptyToDefault | &#x2713; | &#x2713;    | &#x2713;           | &#x2713; | &#x2713;  | &#x2713;  |
| unmodifiable   |          |             |                    | &#x2713; | &#x2713;  | &#x2713;  |
| concat         |          |             |                    |          | &#x2713;  | &#x2713;  |
| join           |          |             |                    |          | &#x2713;  | &#x2713;  |
| of             |          | _ofOrdered_ | _ofEntriesOrdered_ | &#x2713; | &#x2713;  | &#x2713;  |

## Maven phases and goals
Compile, test and install the JAR in the local Maven repository:
```
mvn install
```

Run JUnit 5 tests:
```
mvn test
```

Generate the Javadoc API documentation:
```
mvn javadoc:javadoc
```

Generate the Jacoco test coverage report:
```
mvn jacoco:report
```

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE.txt) file for details