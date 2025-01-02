# Changelog

## 1.9.0 (unreleased)

### Notes
- Add the `maven-pmd-plugin` plugin with `pmd-core` and `pmd-java` dependencies
- Update the `junit-jupiter` dependency to `5.11.4`
- Update the `assertj-core` dependency to `3.27.1`
- Update the `license-maven-plugin` plugin to `4.6`
- Update the `maven-compiler-plugin` plugin to `3.13.0`
- Update the `maven-surefire-plugin` plugin to `3.5.2`
- Update the `jacoco-maven-plugin` plugin to `0.8.12`
- Update the `maven-source-plugin` plugin to `3.3.1`
- Update the `maven-javadoc-plugin` plugin to `3.11.2`
- Update the `maven-gpg-plugin` plugin to `3.2.7`

## [1.8.0](https://github.com/AlexisJehan/Javanilla/releases/tag/v1.8.0) (2022-12-23)

### API changes
<details>

#### `crypto`
- Add `StandardKeyAgreements`, `StandardKeyGenerators` and `StandardSecretKeyFactories` classes

#### `io`
- Add `CountInputStream`, `CountOutputStream`, `InputStreams`, `OutputStreams`, `RangeInputStream` and
  `RangeOutputStream` classes
- Add `CountReader`, `CountWriter`, `RangeReader`, `RangeWriter`, `Readers` and `Writers` classes

#### `io.bytes`
- Deprecate `CountInputStream`, `CountOutputStream`, `InputStreams`, `OutputStreams`, `RangeInputStream` and
  `RangeOutputStream` classes

#### `io.chars`
- Deprecate `CountReader`, `CountWriter`, `RangeReader`, `RangeWriter`, `Readers` and `Writers` classes

#### `io.line`
- Add `CountLineReader`, `CountLineWriter`, `FilterLineReader`, `FilterLineWriter`, `LineReader`, `LineSeparator`,
  `LineWriter`, `RangeLineReader` and `RangeLineWriter` classes

#### `io.lines`
- Deprecate `CountLineReader`, `CountLineWriter`, `FilterLineReader`, `FilterLineWriter`, `LineReader`, `LineSeparator`,
  `LineWriter`, `RangeLineReader` and `RangeLineWriter` classes

#### `lang.Strings`
- Add `substringBefore(CharSequence, char)`, `substringBefore(CharSequence, CharSequence)`,
  `substringAfter(CharSequence, char)` and `substringAfter(CharSequence, CharSequence)` methods

#### `lang.array.IntArrays`
- Add the `add(int[], int, int)` method
- Deprecate the `addTemporary(int[], int, int)` method

#### `misc.distance`
- Add `Distance`, `Distances`, `EditDistance`, `EditDistances`, `LevenshteinDistance` and `MinkowskiDistance` classes

#### `misc.distances`
- Deprecate `Distance`, `Distances`, `EditDistance`, `EditDistances`, `LevenshteinDistance` and `MinkowskiDistance`
  classes

#### `misc.quality.Ensure`
- Add `notNullAndNotNullKeys(String, Map)`, `notNullAndNotNullValues(String, Map)` and
  `notNullAndNotNullKeysAndValues(String, Map)` methods

#### `misc.tree`
- Add `LinkedTreeNode` and `TreeNode` classes

#### `misc.trees`
- Deprecate `LinkedTreeNode` and `TreeNode` classes

#### `misc.tuple`
- Add `Pair`, `SerializablePair`, `SerializableSingle`, `SerializableTriple`, `Single` and `Triple` classes

#### `misc.tuples`
- Deprecate `Pair`, `SerializablePair`, `SerializableSingle`, `SerializableTriple`, `Single` and `Triple` classes

#### `net.ssl`
- Add `StandardSslContexts` and `StandardTrustManagerFactories` classes

#### `security`
- Add `StandardAlgorithmParameterGenerators`, `StandardAlgorithmParameters`, `StandardKeyFactories`,
  `StandardKeyPairGenerators`, `StandardKeyStores`, `StandardMessageDigests` and `StandardSignatures` classes

#### `security.cert`
- Add `StandardCertificateFactories`, `StandardCertPathBuilders`, `StandardCertPathValidators` and `StandardCertStores`
  classes

#### `util.bag`
- Add `BatchIterator`, `CountIterator`, `FilterIterator`, `IndexedElement`, `Iterables`, `Iterators`,
  `PreparedIterator`, `PrimitiveIterable` and `RangeIterator` classes
- Add `Lists`, `Maps` and `Sets` classes

#### `util.bag`
- Add `Bag`, `Bags`, `FilterBag`, `LimitedBag` and `MapBag` classes

#### `util.collection`
- Deprecate `Lists`, `Maps` and `Sets` classes

#### `util.collection.bags`
- Deprecate `Bag`, `Bags`, `FilterBag`, `LimitedBag` and `MapBag` classes

#### `util.function`
- Add `SerializableBiConsumer`, `SerializableBiFunction`, `SerializableBiPredicate`, `SerializableConsumer`,
  `SerializableFunction`, `SerializablePredicate`, `SerializableProcedure` and `SerializableSupplier` classes
- Add `ThrowableBiConsumer`, `ThrowableBiFunction`, `ThrowableBiPredicate`, `ThrowableConsumer`, `ThrowableFunction`,
  `ThrowablePredicate`, `ThrowableProcedure` and `ThrowableSupplier` classes

#### `util.function.serializable`
- Deprecate `SerializableBiConsumer`, `SerializableBiFunction`, `SerializableBiPredicate`, `SerializableConsumer`,
  `SerializableFunction`, `SerializablePredicate`, `SerializableProcedure` and `SerializableSupplier` classes

#### `util.function.throwable`
- Deprecate `ThrowableBiConsumer`, `ThrowableBiFunction`, `ThrowableBiPredicate`, `ThrowableConsumer`,
  `ThrowableFunction`, `ThrowablePredicate`, `ThrowableProcedure` and `ThrowableSupplier` classes

#### `util.iteration`
- Deprecate `BatchIterator`, `CountIterator`, `FilterIterator`, `IndexedElement`, `Iterables`, `Iterators`,
  `PreparedIterator`, `PrimitiveIterable` and `RangeIterator` classes
</details>

### Notes
- Update the `junit-jupiter` dependency to `5.9.1`
- Update the `assertj-core` dependency to `3.23.1`
- Update the `maven-javadoc-plugin` plugin to `3.4.1`

## [1.7.0](https://github.com/AlexisJehan/Javanilla/releases/tag/v1.7.0) (2022-05-17)

### API changes
<details>

#### `lang.Strings`
- Add `removeStartIgnoreCase(CharSequence, char)`, `removeEndIgnoreCase(CharSequence, char)`,
  `contains(CharSequence, char)`, `containsIgnoreCase(CharSequence, char)`, `startsWith(CharSequence, char)`,
  `startsWithIgnoreCase(CharSequence, char)`, `endsWith(CharSequence, char)`, `endsWithIgnoreCase(CharSequence, char)`,
  `isBinary(CharSequence, boolean)`, `isOctal(CharSequence, boolean)`, `isDecimal(CharSequence, boolean)`,
  `isHexadecimal(CharSequence, boolean)`, `isBase64(CharSequence)` and `isBase64Url(CharSequence)` methods

#### `lang.Throwables`
- Add the `sneakyThrow(Throwable)` method
- Deprecate `uncheck(ThrowableRunnable)` and `uncheck(ThrowableSupplier)` methods

#### `lang.array.ByteArrays`
- Add `ofBinaryString(CharSequence, boolean)`, `ofOctalString(CharSequence)`, `ofOctalString(CharSequence, boolean)`,
  `ofDecimalString(CharSequence)`, `ofDecimalString(CharSequence, boolean)`,
  `ofHexadecimalString(CharSequence, boolean)`, `toBinaryString(CharSequence, boolean)`, `toOctalString(CharSequence)`,
  `toOctalString(CharSequence, boolean)`, `toDecimalString(CharSequence)`, `toDecimalString(CharSequence, boolean)` and
  `toHexadecimalString(CharSequence, boolean)` methods

#### `lang.array.IntArrays`
- Remove the `add(int[], int, int)` method

#### `util.function`
- Add the `Procedure` class

#### `util.function.serializable`
- Add the `SerializableProcedure` class
- Deprecate the `SerializableRunnable` class

#### `util.function.throwable`
- Add the `ThrowableProcedure` class
- Deprecate the `ThrowableRunnable` class

#### `util.function.throwable.ThrowableBiConsumer`
- Add the `sneaky(ThrowableBiConsumer)` method

#### `util.function.throwable.ThrowableBiFunction`
- Add the `sneaky(ThrowableBiFunction)` method

#### `util.function.throwable.ThrowableBiPredicate`
- Add the `sneaky(ThrowableBiPredicate)` method

#### `util.function.throwable.ThrowableConsumer`
- Add the `sneaky(ThrowableConsumer)` method

#### `util.function.throwable.ThrowableFunction`
- Add the `sneaky(ThrowableFunction)` method

#### `util.function.throwable.ThrowablePredicate`
- Add the `sneaky(ThrowablePredicate)` method

#### `util.function.throwable.ThrowableSupplier`
- Add the `sneaky(ThrowableSupplier)` method
</details>

### Notes
- Update the `maven-javadoc-plugin` plugin to `3.4.0`
- Update the `jacoco-maven-plugin` plugin to `0.8.8`
- Migrate the continuous integration from _Travis CI_ to _GitHub Actions_

## [1.6.0](https://github.com/AlexisJehan/Javanilla/releases/tag/v1.6.0) (2022-03-24)

### API changes
<details>

#### `crypto`
- Deprecate `StandardCiphers`, `StandardKeyFactories`, `StandardKeyPairGenerators`, `StandardMacs`,
  `StandardMessageDigests` and `StandardSignatures` classes

#### `io.Serializables`
- Add the `serialize(Serializable, OutputStream)` method
- Deprecate the `serialize(OutputStream, Serializable)` method

#### `lang`
- Deprecate the `UncheckedInterruptedException` class

#### `lang.Strings`
- Add `of(byte...)` and `of(Charset, byte...)` methods

#### `lang.Throwables`
- Add `isCheckedException(Throwable)` and `isUncheckedException(Throwable)` methods
- Change the `unchecked(Throwable)` method
- Deprecate `isChecked(Throwable)` and `isUnchecked(Throwable)` methods

#### `lang.array.BooleanArrays`
- Add `add(boolean[], boolean, int)` and `shuffle(boolean[], Random)` methods
- Deprecate `add(boolean[], int, boolean)` and `shuffle(boolean[])` methods

#### `lang.array.ByteArrays`
- Add `add(byte[], byte, int)` and `shuffle(byte[], Random)` methods
- Add `of(boolean)`, `of(short)`, `of(short, ByteOrder)`, `of(char)`, `of(char, ByteOrder)`, `of(int)`,
  `of(int, ByteOrder)`, `of(long)`, `of(long, ByteOrder)`, `of(float)`, `of(float, ByteOrder)`, `of(double)` and
  `of(double, ByteOrder)` methods
- Deprecate `add(byte[], int, byte)` and `shuffle(byte[])` methods
- Deprecate `ofBoolean(boolean)`, `ofShort(short)`, `ofShort(short, ByteOrder)`, `ofChar(char)`,
  `ofChar(char, ByteOrder)`, `ofInt(int)`, `ofInt(int, ByteOrder)`, `ofLong(long)`, `ofLong(long, ByteOrder)`,
  `ofFloat(float)`, `ofFloat(float, ByteOrder)`, `ofDouble(double)` and `ofDouble(double, ByteOrder)` methods

#### `lang.array.CharArrays`
- Add `add(char[], char, int)` and `shuffle(char[], Random)` methods
- Deprecate `add(char[], int, char)` and `shuffle(char[])` methods

#### `lang.array.DoubleArrays`
- Add `add(double[], double, int)` and `shuffle(double[], Random)` methods
- Deprecate `add(double[], int, double)` and `shuffle(double[])` methods

#### `lang.array.FloatArrays`
- Add `add(float[], float, int)` and `shuffle(float[], Random)` methods
- Deprecate `add(float[], int, float)` and `shuffle(float[])` methods

#### `lang.array.IntArrays`
- Add `addTemporary(int[], int, int)` and `shuffle(int[], Random)` methods
- Deprecate `add(int[], int, int)` and `shuffle(int[])` methods

#### `lang.array.LongArrays`
- Add `add(long[], long, int)` and `shuffle(long[], Random)` methods
- Deprecate `add(long[], int, long)` and `shuffle(long[])` methods

#### `lang.array.ObjectArrays`
- Add `add(Object[], Object, int)` and `shuffle(Object[], Random)` methods
- Deprecate `add(Object[], int, Object)` and `shuffle(Object[])` methods

#### `lang.array.ShortArrays`
- Add `add(short[], short, int)` and `shuffle(short[], Random)` methods
- Deprecate `add(short[], int, short)` and `shuffle(short[])` methods

#### `sql`
- Deprecate the `UncheckedSQLException` class

#### `standard.crypto`
- Add `StandardCiphers`, `StandardKeyAgreements`, `StandardKeyGenerators`, `StandardMacs` and
  `StandardSecretKeyFactories` classes

#### `standard.net.ssl`
- Add `StandardSslContexts` and `StandardTrustManagerFactories` classes

#### `standard.security`
- Add `StandardAlgorithmParameterGenerators`, `StandardAlgorithmParameters`, `StandardKeyFactories`,
  `StandardKeyPairGenerators`, `StandardKeyStores`, `StandardMessageDigests` and `StandardSignatures` classes

#### `standard.security.cert`
- Add `StandardCertificateFactories`, `StandardCertPathBuilders`, `StandardCertPathValidators` and `StandardCertStores`
  classes

#### `util.collection.Sets`
- Add `unify(Set[])` and `unify(Collection)` methods
- Deprecate `union(Set[])` and `union(Collection)` methods

#### `util.collection.bags.LimitedBag`
- Add the `getLimit()` method

#### `util.iteration.Iterables`
- Add `singleton(int)`, `singleton(long)`, `singleton(double)`, `ofInts(int...)`, `ofLongs(long...)` and
  `ofDoubles(double...)` methods
- Deprecate `singletonInt(int)`, `singletonLong(long)`, `singletonDouble(double)`, `ofInt(int...)`, `ofLong(long...)`
  and `ofDouble(double...)` methods

#### `util.iteration.Iterators`
- Add `singleton(int)`, `singleton(long)`, `singleton(double)`, `ofInts(int...)`, `ofLongs(long...)` and
  `ofDoubles(double...)` methods
- Deprecate `singletonInt(int)`, `singletonLong(long)`, `singletonDouble(double)`, `ofInt(int...)`, `ofLong(long...)`
  and `ofDouble(double...)` methods
</details>

### Notes
- Update the `junit-jupiter` dependency to `5.8.2`
- Update the `assertj-core` dependency to `3.22.0`
- Update the `maven-compiler-plugin` plugin to `3.10.1`
- Update the `maven-javadoc-plugin` plugin to `3.3.2`
- Update the `maven-gpg-plugin` plugin to `3.0.1`
- Update the `jacoco-maven-plugin` plugin to `0.8.7`
- Update the `license-maven-plugin` plugin to `4.1`

## [1.5.0](https://github.com/AlexisJehan/Javanilla/releases/tag/v1.5.0) (2020-01-23)

### API changes
<details>

#### `lang.Strings`
- Add the `capitalize()` method

#### `misc`
- Add the `CaseStyle` class

#### `misc.quality.Ensure`
- Add multiple `multipleOf()` methods
</details>

### Bug fixes
- Fix the `misc.StringFormatter.formatPercent()` method

### Notes
- Update the `junit-jupiter` dependency to `5.6.0`
- Update the `assertj-core` dependency to `3.14.0`
- Update the `maven-compiler-plugin` plugin to `3.8.1`
- Update the `maven-source-plugin` plugin to `3.2.1`
- Update the `maven-javadoc-plugin` plugin to `3.1.1`
- Update the `maven-surefire-plugin` plugin to `2.22.2`
- Update the `jacoco-maven-plugin` plugin to `0.8.5`

## [1.4.2](https://github.com/AlexisJehan/Javanilla/releases/tag/v1.4.2) (2019-12-06)

### Bug fixes
- Fix the `misc.StringFormatter.formatBytes()` method ([The most copied StackOverflow snippet of all time is flawed!](https://programming.guide/worlds-most-copied-so-snippet.html))

## [1.4.1](https://github.com/AlexisJehan/Javanilla/releases/tag/v1.4.1) (2019-04-19)

### Bug fixes
- Fix `util.collection.Sets.union()` and `util.collection.Sets.intersect()` methods

### Notes
- Update the `junit-jupiter` dependency to `5.4.2`
- Update the `assertj-core` dependency to `3.12.2`
- Update the `maven-javadoc-plugin` plugin to `3.1.0`

## [1.4.0](https://github.com/AlexisJehan/Javanilla/releases/tag/v1.4.0) (2019-02-12)

### API changes
<details>

#### `lang.Strings`
- Add multiple `split()` methods

#### `lang.array.BooleanArrays`
- Add multiple `add()` methods
- Add the `remove()` method

#### `lang.array.ByteArrays`
- Add multiple `add()` methods
- Add the `remove()` method

#### `lang.array.CharArrays`
- Add multiple `add()` methods
- Add the `remove()` method

#### `lang.array.DoubleArrays`
- Add multiple `add()` methods
- Add the `remove()` method

#### `lang.array.FloatArrays`
- Add multiple `add()` methods
- Add the `remove()` method

#### `lang.array.IntArrays`
- Add multiple `add()` methods
- Add the `remove()` method

#### `lang.array.LongArrays`
- Add multiple `add()` methods
- Add the `remove()` method

#### `lang.array.ObjectArrays`
- Add multiple `add()` methods
- Add the `remove()` method
- Change multiple `join()` methods

#### `lang.array.ShortArrays`
- Add multiple `add()` methods
- Add the `remove()` method

#### `misc.quality.Ensure`
- Add the `notNullAndMatches()` method

#### `util.function.serializable`
- Add `SerializableBiConsumer`, `SerializableBiFunction`, `SerializableBiPredicate`, `SerializableConsumer`,
  `SerializableFunction`, `SerializablePredicate`, `SerializableRunnable` and `SerializableSupplier` classes

#### `util.function.throwable.ThrowableFunction`
- Add the `identity()` method

#### `util.function.throwable.ThrowablePredicate`
- Add the `isEqual()` method
</details>

### Notes
- Add the `license-maven-plugin` plugin
- Migrate `junit-jupiter-api`, `junit-jupiter-engine` and `junit-pioneer` dependencies to `junit-jupiter`
- Update the `jacoco-maven-plugin` plugin to `0.8.3`

## [1.3.1](https://github.com/AlexisJehan/Javanilla/releases/tag/v1.3.1) (2018-12-22)

### API changes
<details>

#### `io.crypto`
- Add `StandardKeyFactories`, `StandardKeyPairGenerators` and `StandardSignatures` classes

#### `io.crypto.StandardCiphers`
- Add the `getAesGcmInstance()` method

#### `lang.array.ByteArrays`
- Rename the `ofHexString()` method to `ofHexadecimalString()`
- Rename the `toHexString()` method to `toHexadecimalString()`

#### `lang`
- Add the `Comparables` class

#### `lang.Strings`
- Add multiple `split()` methods

#### `misc.quality.Ensure`
- Add `notNullAndEqualTo()`, `notEqualTo()`, `notNullAndNotEqualTo()`, `notNullAndLowerThan()`,
  `notNullAndLowerThanOrEqualTo()`, `notNullAndGreaterThan()`, `notNullAndGreaterThanOrEqualTo()` and
  `notNullAndBetween()` methods

#### `util.NullableOptional`
- Add the `orElseThrow()` method
</details>

### Notes
- Update `junit-jupiter-api` and `junit-jupiter-engine` dependencies to `5.3.2`

## [1.3.0](https://github.com/AlexisJehan/Javanilla/releases/tag/v1.3.0) (2018-11-18)

### API changes
<details>

#### `io.bytes`
- Remove `UncheckedInputStream` and `UncheckedOutputStream` classes

#### `io.chars`
- Remove `UncheckedReader` and `UncheckedWriter` classes

#### `io.lines`
- Remove `UncheckedLineReader` and `UncheckedLineWriter` classes

#### `lang.Strings`
- Add `containsIgnoreCase()`, `startsWithIgnoreCase()`, `endsWithIgnoreCase()` and `frequency()` methods
- Rename the `isHex()` method to `isHexadecimal()`

#### `lang.array.BooleanArrays`
- Add the `frequency()` method

#### `lang.array.ByteArrays`
- Add the `frequency()` method

#### `lang.array.CharArrays`
- Add the `frequency()` method

#### `lang.array.DoubleArrays`
- Add the `frequency()` method

#### `lang.array.FloatArrays`
- Add the `frequency()` method

#### `lang.array.IntArrays`
- Add the `frequency()` method

#### `lang.array.LongArrays`
- Add the `frequency()` method

#### `lang.array.ObjectArrays`
- Add the `frequency()` method

#### `lang.array.ShortArrays`
- Add the `frequency()` method

#### `misc.BloomFilter`
- Rename the `calculateOptionalNumberOfHashFunctions()` method to `calculateOptimalNumberOfHashFunctions()`

#### `misc.StringFormatter`
- Add the `DEFAULT` constant
- Add the `toString()` method

#### `misc.distances.LevenshteinDistance`
- Add the `DEFAULT` constant

#### `misc.quality`
- Add `Ensure`, `Equals`, `HashCode` and `ToString` classes

#### `misc.trees.TreeNode`
- Rename the `parent()` method to `optionalParent()`

#### `misc.tuples.Pair`
- Remove `toMutableEntry()` and `toImmutableEntry()` methods

#### `util.Comparators`
- Add the `normalize()` method
- Remove `BOOLEAN_ARRAYS`, `SIGNED_BYTE_ARRAYS`, `UNSIGNED_BYTE_ARRAYS`, `SHORT_ARRAYS`, `INT_ARRAYS`, `LONG_ARRAYS`,
  `FLOAT_ARRAYS` and `DOUBLE_ARRAYS` constants
- Remove multiple `array()` methods

#### `util.collection.Lists`
- Add `concat()` and `join()` methods
- Rename the `getFirst()` method to `getOptionalFirst()`
- Rename the `getLast()` method to `getOptionalLast()`

#### `util.collection.Maps`
- Rename the `ofEntriesOrdered()` method to `ofOrdered()`

#### `util.collection.Sets`
- Add `union()` and `intersect()` methods

#### `util.function`
- Add the `Functions` class

#### `util.iteration.Iterables`
- Rename the `getFirst()` method to `getOptionalFirst()`
- Rename the `getLast()` method to `getOptionalLast()`

#### `util.iteration.Iterables`
- Rename the `getFirst()` method to `getOptionalFirst()`
- Rename the `getLast()` method to `getOptionalLast()`
</details>

### Notes
- Add the `junit-pioneer` dependency
- Update `junit-jupiter-api` and `junit-jupiter-engine` dependencies to `5.3.1`
- Update the `maven-surefire-plugin` plugin to `2.22.1`

## [1.2.0](https://github.com/AlexisJehan/Javanilla/releases/tag/v1.2.0) (2018-09-09)

### API changes
<details>

#### `crypto`
- Rename the `security` package to `crypto`

#### `io.bytes`
- Add `UncheckedInputStream` and `UncheckedOutputStream` classes

#### `io.bytes.InputStreams`
- Add the `of(Path)` method
- Remove the `ENDLESS` constant

#### `io.bytes.OutputStreams`
- Add the `of(Path)` method
- Rename the `BLANK` constant to `EMPTY`
- Rename the `nullToBlank()` method to `nullToEmpty()`

#### `io.chars`
- Add `UncheckedReader` and `UncheckedWriter` classes

#### `io.chars.Readers`
- Add `of(Path)` and `of(Path, Charset)` methods
- Remove the `ENDLESS` constant

#### `io.chars.Writers`
- Add `of(Path)` and `of(Path, Charset)` methods
- Rename the `BLANK` constant to `EMPTY`
- Rename the `nullToBlank()` method to `nullToEmpty()`

#### `io.lines`
- Add `UncheckedLineReader` and `UncheckedLineWriter` classes

#### `lang.Strings`
- Add `nullToEmpty(String)`, `emptyToNull(String)`, `blankToNull(String)`, `blankToEmpty(String)`, `quote(char)`,
  `quote(char, char, char)`, `unquoteChar(CharSequence)`, `unquoteChar(CharSequence, char, char)`, `isEmpty()`,
  `isBoolean()`, `isShort()`, `isInt()`, `isLong()`, `isFloat()`, `isDouble()`, `isBinary()`, `isOctal()` and
  `isDecimal()` methods
- Change `isBase64(CharSequence, boolean)` and `isBase64Url(CharSequence, boolean)` methods
- Remove `quote(Object)` and `quote(Object, char, char)` methods

#### `lang.array.BooleanArrays`
- Add `shuffle()`, `reverse()`, `reorder()`, `swap()` and `isEmpty()` methods
- Add `of(Boolean[])` and `toBoxed()` methods
- Change `containsAny()`, `containsAll()`, `containsOnce()` and `containsOnly()` methods

#### `lang.array.ByteArrays`
- Add `shuffle()`, `reverse()`, `reorder()`, `swap()` and `isEmpty()` methods
- Add `of(Byte[])` and `toBoxed()` methods
- Add `ofBinaryString()` and `toBinaryString()` methods
- Change `containsAny()`, `containsAll()`, `containsOnce()` and `containsOnly()` methods

#### `lang.array.CharArrays`
- Add `shuffle()`, `reverse()`, `reorder()`, `swap()` and `isEmpty()` methods
- Add `of(Char[])` and `toBoxed()` methods
- Change `containsAny()`, `containsAll()`, `containsOnce()` and `containsOnly()` methods

#### `lang.array.DoubleArrays`
- Add `shuffle()`, `reverse()`, `reorder()`, `swap()` and `isEmpty()` methods
- Add `of(Double[])` and `toBoxed()` methods
- Change `containsAny()`, `containsAll()`, `containsOnce()` and `containsOnly()` methods

#### `lang.array.FloatArrays`
- Add `shuffle()`, `reverse()`, `reorder()`, `swap()` and `isEmpty()` methods
- Add `of(Float[])` and `toBoxed()` methods
- Change `containsAny()`, `containsAll()`, `containsOnce()` and `containsOnly()` methods

#### `lang.array.IntArrays`
- Add `shuffle()`, `reverse()`, `reorder()`, `swap()` and `isEmpty()` methods
- Add `of(Integer[])` and `toBoxed()` methods
- Change `containsAny()`, `containsAll()`, `containsOnce()` and `containsOnly()` methods

#### `lang.array.LongArrays`
- Add `shuffle()`, `reverse()`, `reorder()`, `swap()` and `isEmpty()` methods
- Add `of(Long[])` and `toBoxed()` methods
- Change `containsAny()`, `containsAll()`, `containsOnce()` and `containsOnly()` methods

#### `lang.array.ObjectArrays`
- Add `shuffle()`, `reverse()`, `reorder()`, `swap()` and `isEmpty()` methods
- Add `concat()`, `join()` and `singleton(Class, Object)` methods
- Change `containsAny()`, `containsAll()`, `containsOnce()` and `containsOnly()` methods

#### `lang.array.ShortArrays`
- Add `shuffle()`, `reverse()`, `reorder()`, `swap()` and `isEmpty()` methods
- Add `of(Short[])` and `toBoxed()` methods
- Change `containsAny()`, `containsAll()`, `containsOnce()` and `containsOnly()` methods

#### `misc`
- Add the `BloomFilter` class

#### `misc.trees`
- Add `TreeNode` and `LinkedTreeNode` classes

#### `util.function.Consumers`
- Add the `distinct()` method

#### `util.function.Suppliers`
- Rename multiple `cached()` methods to `cache()`

#### `util.iteration`
- Add `FilterIterator` and `IndexedElement` classes

#### `util.iteration.Iterables`
- Add multiple `nullToEmpty(PrimitiveIterable)` methods
- Add `index()`, `getFirst()` and `getLast()` methods
- Add the `wrap(Stream)` method

#### `util.iteration.Iterators`
- Add `nullToEmpty(PrimitiveIterator)` and `emptyToNull(PrimitiveIterator)` methods
- Add `index()`, `getFirst()` and `getLast()` methods
- Add `removeAll()`, `removeIf()` and `isEmpty()` methods
- Remove the `toEnumeration()` method
</details>

### Notes
- Update `junit-jupiter-api` and `junit-jupiter-engine` dependencies to `5.3.0`
- Update the `assertj-core` dependency to `3.11.1`
- Update the `maven-compiler-plugin` plugin to `3.8.0`
- Update the `jacoco-maven-plugin` plugin to `0.8.2`
- Remove the `annotations` dependency
- Remove the `junit-platform-surefire-provider` dependency

## [1.1.0](https://github.com/AlexisJehan/Javanilla/releases/tag/v1.1.0) (2018-06-20)

### API changes
<details>

#### `io.bytes.InputStreams`
- Add `nullToDefault()` and `singleton()` methods

#### `io.bytes.OutputStreams`
- Add the `nullToDefault()` method

#### `io.chars.Readers`
- Add `nullToDefault()` and `singleton()` methods

#### `io.chars.Writers`
- Add the `nullToDefault()` method

#### `lang.Strings`
- Add `blankToEmpty()`, `blankToDefault()` and `of()` methods
- Add multiple `quote()` and `unquote()` methods
- Change the `isBlank()` method so that an empty `String` is not blank anymore
- Change the `isHex()` method so that it does not handle the `0x` prefix anymore
- Change the `isBase64Url()` method so that it does not require a padding anymore

#### `lang.Throwables`
- Add `isChecked()` and `isUnchecked()` methods
- Change the `getRootCause()` method return type to an `Optional`

#### `lang.array.BooleanArrays`
- Add `nullToDefault()`, `emptyToDefault()`, `containsOnce()` and `singleton()` methods

#### `lang.array.ByteArrays`
- Add `nullToDefault()`, `emptyToDefault()`, `containsOnce()` and `singleton()` methods
- Change the `ofHexString()` method so that it does not handle the `0x` prefix anymore

#### `lang.array.CharArrays`
- Add `nullToDefault()`, `emptyToDefault()`, `containsOnce()` and `singleton()` methods

#### `lang.array.DoubleArrays`
- Add `nullToDefault()`, `emptyToDefault()`, `containsOnce()` and `singleton()` methods

#### `lang.array.FloatArrays`
- Add `nullToDefault()`, `emptyToDefault()`, `containsOnce()` and `singleton()` methods

#### `lang.array.IntArrays`
- Add `nullToDefault()`, `emptyToDefault()`, `containsOnce()` and `singleton()` methods

#### `lang.array.LongArrays`
- Add `nullToDefault()`, `emptyToDefault()`, `containsOnce()` and `singleton()` methods

#### `lang.array.ObjectArrays`
- Add `nullToDefault()`, `emptyToDefault()`, `containsOnce()` and `singleton()` methods

#### `lang.array.ShortArrays`
- Add `nullToDefault()`, `emptyToDefault()`, `containsOnce()` and `singleton()` methods

#### `misc.tuples`
- Add `Single` and `SerializableSingle` classes

#### `security`
- Add the `StandardMacs` class

#### `util`
- Add the `NullableOptional` class

#### `util.collection.Lists`
- Add `nullToDefault()`, `emptyToDefault()`, `getFirst()` and `getLast()` methods

#### `util.collection.Maps`
- Add multiple `nullToDefault()` and `emptyToDefault()` methods

#### `util.collection.Sets`
- Add multiple `nullToDefault()` and `emptyToDefault()` methods

#### `util.collection.bags.Bag`
- Change `min()` and `max()` methods return type to `NullableOptional`

#### `util.collection.bags.Bags`
- Add `nullToDefault()` and `emptyToDefault()`, `equals()`, `hashCode()` and `toString()` methods
- Change `min()` and `max()` methods return type to `NullableOptional`

#### `util.collection.bags.FilterBag`
- Change `min()` and `max()` methods return type to `NullableOptional`

#### `util.collection.bags.MapBag`
- Change `min()` and `max()` methods return type to `NullableOptional`

#### `util.function`
- Add the `Consumers` class
- Remove the `Predicates` class

#### `util.function.Suppliers`
- Add the `once()` method
- Add multiple `cached()` methods

#### `util.iteration.Iterables`
- Add the `nullToDefault()` method
- Add `filter()` and `length()` methods
- Add multiple `singleton()` methods

#### `util.iteration.Iterators`
- Add multiple `nullToDefault()`, `emptyToNull()` and `emptyToDefault()` methods
- Add `filter()` and `length()` methods
- Add multiple `singleton()` methods
</details>

### Bug fixes
- Fix `util.collection.bags.FilterBag.equals()` and `util.collection.bags.FilterBag.hashCode()` methods
- Fix `util.collection.bags.LimitedBag.equals()` and `util.collection.bags.LimitedBag.hashCode()` methods
- Fix `util.collection.bags.MapBag.equals()` and `util.collection.bags.MapBag.hashCode()` methods

### Notes
- Update the `annotations` dependency to `16.0.2`
- Update the `junit-jupiter-api` dependency to `5.2.0`
- Update the `assertj-core` dependency to `3.10.0`
- Update the `maven-surefire-plugin` plugin to `2.22.0`
- Update the `junit-platform-surefire-provider` dependency to `1.2.0`
- Update the `junit-jupiter-engine` dependency to `5.2.0`
- Update the `maven-javadoc-plugin` plugin to `3.0.1`

## [1.0.0](https://github.com/AlexisJehan/Javanilla/releases/tag/v1.0.0) (2018-04-24)
Initial release