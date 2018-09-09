# Changelog

## 1.2.0 _(2018-09-10)_

**crypto**
- Renamed the _security_ package to _crypto_

**io.bytes**
- Added _UncheckedInputStream_ and _UncheckedOutputStream_ classes

**io.bytes.InputStreams**
- Added the _of(Path)_ method
- Removed the _ENDLESS_ attribute

**io.bytes.OutputStreams**
- Added the _of(Path)_ method
- Renamed the _BLANK_ attribute to _EMPTY_
- Renamed the _nullToBlank()_ method to _nullToEmpty()_

**io.chars**
- Added _UncheckedReader_ and _UncheckedWriter_ classes

**io.chars.Readers**
- Added _of(Path)_ and _of(Path, Charset)_ methods
- Removed the _ENDLESS_ attribute

**io.chars.Writers**
- Added _of(Path)_ and _of(Path, Charset)_ methods
- Renamed the _BLANK_ attribute to _EMPTY_
- Renamed the _nullToBlank()_ method to _nullToEmpty()_

**io.lines**
- Added _UncheckedLineReader_ and _UncheckedLineWriter_ classes

**lang.Strings**
- Added _nullToEmpty(String)_, _emptyToNull(String)_, _blankToNull(String)_ and _blankToEmpty(String)_ methods
- Added the _isEmpty()_ method
- Added _isBoolean()_, _isShort()_, _isInt()_, _isLong()_, _isFloat()_, _isDouble()_, _isBinary()_, _isOctal()_ and _isDecimal()_ methods
- Changed _isBase64(CharSequence, boolean)_ and _isBase64Url(CharSequence, boolean)_ methods
- Added _quote(char)_, _quote(char, char, char)_, _unquoteChar(CharSequence)_ and _unquoteChar(CharSequence, char, char)_  methods
- Removed _quote(Object)_ and _quote(Object, char, char)_ methods

**lang.array.BooleanArrays**
- Added the _isEmpty()_ method
- Added _shuffle()_, _reverse()_, _reorder()_ and _swap()_ methods
- Added _of(Boolean[])_ and _toBoxed()_ methods
- Changed _containsAny()_, _containsAll()_, _containsOnce()_ and _containsOnly()_ methods

**lang.array.ByteArrays**
- Added the _isEmpty()_ method
- Added _shuffle()_, _reverse()_, _reorder()_ and _swap()_ methods
- Added _of(Byte[])_ and _toBoxed()_ methods
- Added _ofBinaryString()_ and _toBinaryString()_ methods
- Changed _containsAny()_, _containsAll()_, _containsOnce()_ and _containsOnly()_ methods

**lang.array.CharArrays**
- Added the _isEmpty()_ method
- Added _shuffle()_, _reverse()_, _reorder()_ and _swap()_ methods
- Added _of(Char[])_ and _toBoxed()_ methods
- Changed _containsAny()_, _containsAll()_, _containsOnce()_ and _containsOnly()_ methods

**lang.array.DoubleArrays**
- Added the _isEmpty()_ method
- Added _shuffle()_, _reverse()_, _reorder()_ and _swap()_ methods
- Added _of(Double[])_ and _toBoxed()_ methods
- Changed _containsAny()_, _containsAll()_, _containsOnce()_ and _containsOnly()_ methods

**lang.array.FloatArrays**
- Added the _isEmpty()_ method
- Added _shuffle()_, _reverse()_, _reorder()_ and _swap()_ methods
- Added _of(Float[])_ and _toBoxed()_ methods
- Changed _containsAny()_, _containsAll()_, _containsOnce()_ and _containsOnly()_ methods

**lang.array.IntArrays**
- Added the _isEmpty()_ method
- Added _shuffle()_, _reverse()_, _reorder()_ and _swap()_ methods
- Added _of(Integer[])_ and _toBoxed()_ methods
- Changed _containsAny()_, _containsAll()_, _containsOnce()_ and _containsOnly()_ methods

**lang.array.LongArrays**
- Added the _isEmpty()_ method
- Added _shuffle()_, _reverse()_, _reorder()_ and _swap()_ methods
- Added _of(Long[])_ and _toBoxed()_ methods
- Changed _containsAny()_, _containsAll()_, _containsOnce()_ and _containsOnly()_ methods

**lang.array.ObjectArrays**
- Added the _isEmpty()_ method
- Added _shuffle()_, _reverse()_, _reorder()_ and _swap()_ methods
- Added _concat()_ and _join()_ methods
- Added the _singleton(Class, Object)_ method
- Changed _containsAny()_, _containsAll()_, _containsOnce()_ and _containsOnly()_ methods

**lang.array.ShortArrays**
- Added the _isEmpty()_ method
- Added _shuffle()_, _reverse()_, _reorder()_ and _swap()_ methods
- Added the _singleton(Class, Object)_ method
- Added _of(Short[])_ and _toBoxed()_ methods
- Changed _containsAny()_, _containsAll()_, _containsOnce()_ and _containsOnly()_ methods

**misc**
- Added the _BloomFilter_ class

**misc.trees**
- Added _TreeNode_ and _LinkedTreeNode_ classes

**util.function.Consumers**
- Added the _distinct()_ method

**util.function.Suppliers**
- Renamed _cached()_ methods to _cache()_

**util.iteration**
- Added the _FilterIterator_ class
- Added the _IndexedElement_ class

**util.iteration.Iterables**
- Added _nullToEmpty(PrimitiveIterable)_ methods
- Added the _index()_ method
- Added _getFirst()_ and _getLast()_ methods
- Added the _wrap(Stream)_ method

**util.iteration.Iterators**
- Added _nullToEmpty(PrimitiveIterator)_ and _emptyToNull(PrimitiveIterator)_ methods
- Added the _isEmpty()_ method
- Added the _index()_ method
- Added _getFirst()_ and _getLast()_ methods
- Added _removeAll()_ and _removeIf()_ methods
- Removed the _toEnumeration()_ method


## 1.1.0 _(2018-06-20)_

**io.bytes.InputStreams**
- Added the _nullToDefault()_ method
- Added the _singleton()_ method

**io.bytes.OutputStreams**
- Added the _nullToDefault()_ method

**io.chars.Readers**
- Added the _nullToDefault()_ method
- Added the _singleton()_ method

**io.chars.Writers**
- Added the _nullToDefault()_ method

**lang.Strings**
- Added _blankToEmpty()_ and  _blankToDefault()_ methods
- Added multiple _quote()_ and _unquote()_ methods
- Added the _of()_ method
- Changed the _isBlank()_ method so that an empty _String_ is not blank anymore
- Changed the _isHex()_ method so that it does not handle the "0x" prefix anymore
- Fixed the _isBase64Url()_ method so that it does not require a padding anymore

**lang.Throwables**
- Added _isChecked()_ and _isUnchecked()_ methods
- Changed the _getRootCause()_ method return type to an _Optional_

**lang.array.BooleanArrays**
- Added _nullToDefault()_ and _emptyToDefault()_ methods
- Added the _containsOnce()_ method
- Added the _singleton()_ method

**lang.array.ByteArrays**
- Added _nullToDefault()_ and _emptyToDefault()_ methods
- Added the _containsOnce()_ method
- Added the _singleton()_ method
- Changed the _ofHexString()_ method so that it does not handle the "0x" prefix anymore

**lang.array.CharArrays**
- Added _nullToDefault()_ and _emptyToDefault()_ methods
- Added the _containsOnce()_ method
- Added the _singleton()_ method

**lang.array.DoubleArrays**
- Added _nullToDefault()_ and _emptyToDefault()_ methods
- Added the _containsOnce()_ method
- Added the _singleton()_ method

**lang.array.FloatArrays**
- Added _nullToDefault()_ and _emptyToDefault()_ methods
- Added the _containsOnce()_ method
- Added the _singleton()_ method

**lang.array.IntArrays**
- Added _nullToDefault()_ and _emptyToDefault()_ methods
- Added the _containsOnce()_ method
- Added the _singleton()_ method

**lang.array.LongArrays**
- Added _nullToDefault()_ and _emptyToDefault()_ methods
- Added the _containsOnce()_ method
- Added the _singleton()_ method

**lang.array.ObjectArrays**
- Added _nullToDefault()_ and _emptyToDefault()_ methods
- Added the _containsOnce()_ method
- Added the _singleton()_ method

**lang.array.ShortArrays**
- Added _nullToDefault()_ and _emptyToDefault()_ methods
- Added the _containsOnce()_ method
- Added the _singleton()_ method

**misc.tuples**
- Added _Single_ and _SerializableSingle_ classes

**security**
- Added the _StandardMacs_ class

**util**
- Added the _NullableOptional_ class

**util.collection.Lists**
- Added _nullToDefault()_ and _emptyToDefault()_ methods
- Added _getFirst()_ and _getLast()_ methods

**util.collection.Maps**
- Added multiple _nullToDefault()_ and _emptyToDefault()_ methods

**util.collection.Sets**
- Added multiple _nullToDefault()_ and _emptyToDefault()_ methods

**util.collection.bags.Bag**
- Changed _min()_ and _max()_ methods return type to _NullableOptional_

**util.collection.bags.Bags**
- Added _nullToDefault()_ and _emptyToDefault()_ methods
- Added _equals()_, _hashCode()_ and _toString()_ implementations
- Changed _min()_ and _max()_ methods return type to _NullableOptional_

**util.collection.bags.FilterBag**
- Changed _min()_ and _max()_ methods return type to _NullableOptional_
- Fixed _equals()_ and _hashCode()_ implementations

**util.collection.bags.Limited**
- Fixed _equals()_ and _hashCode()_ implementations

**util.collection.bags.MapBag**
- Changed _min()_ and _max()_ methods return type to _NullableOptional_
- Fixed _equals()_ and _hashCode()_ implementations

**util.function**
- Added the _Consumers_ class
- Removed the _Predicates_ class

**util.function.Suppliers**
- Added the _once()_ method
- Added new _cached()_ methods

**util.iteration.Iterables**
- Added the _nullToDefault()_ method
- Added the _filter()_ method
- Added the _length()_ method
- Added multiple _singleton()_ method implementations

**util.iteration.Iterators**
- Added multiple _nullToDefault()_, _emptyToNull()_ and _emptyToDefault()_ methods
- Added the _filter()_ method
- Added the _length()_ method
- Added multiple _singleton()_ method implementations