# Changelog

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