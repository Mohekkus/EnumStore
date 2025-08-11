# EnumStore – Clean Preferences with Kotlin Enums

**EnumStore** is a Kotlin utility for managing stored preferences or settings using `enum class` entries as keys — no raw strings, no context passing, and full type safety.  
It uses Kotlin `inline` functions with reified generics for minimal overhead and avoids sealed class complexity when enums are enough.

---

## ✨ Features
- **Type-safe**: Each preference key is tied to a type at the call site.
- **No boilerplate**: Use enums directly as preference keys.
- **Default values**: Supply inline or via `EnumStoreType`.
- **Minimal overhead**: Uses enums for lightweight, efficient preference keys.

---

## 📦 How to Use

### 1. Define your preference keys
Each enum must implement `EnumStoreOption`:

```kotlin
interface EnumStoreOption

enum class Settings : EnumStoreOption {
    USERNAME,        // String
    LOGIN_COUNT,     // Int
    REMEMBER_ME      // Boolean
}
```

---

### 2. Initialize `EnumStore`
Before you can use any `.get()`, `.set()`, or `.erase()` calls, you **must** create the `EnumStore` instance.  
This is typically done in your `Application` class:

```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        EnumStore.create(this) // Uses default key name "Settings"
    }
}
```

---

### 3. Read & write preferences

```kotlin
// Get with default value
val username = Settings.USERNAME.get("Guest")

// Save a value
Settings.LOGIN_COUNT.set(42)

// Use EnumStoreType for defaults
Settings.REMEMBER_ME.get(EnumStoreType.TypeBoolean) { remember ->
    println("Remember Me: $remember")
}

// Erase a value
Settings.REMEMBER_ME.erase(EnumStoreType.TypeBoolean)
```

---

## 🛠 Core API

```kotlin
@Suppress("UNCHECKED_CAST")
inline fun <reified R> getPreferenceKey(name: String): Preferences.Key<R> {
    return when (R::class) {
        String::class -> stringPreferencesKey(name)
        Boolean::class -> booleanPreferencesKey(name)
        Set::class -> stringSetPreferencesKey(name)
        Int::class -> intPreferencesKey(name)
        Double::class -> doublePreferencesKey(name)
        Long::class -> longPreferencesKey(name)
        ByteArray::class -> byteArrayPreferencesKey(name)
        else -> error("Type ${R::class} is not supported")
    } as Preferences.Key<R>
}

inline fun <reified T, reified R> T.get(defaultValue: R): R
    where T : Enum<T>, T : EnumStoreOption =
    instance?.block(getPreferenceKey<R>(name)) ?: defaultValue

@Suppress("UNCHECKED_CAST")
inline fun <reified T, reified R : Any> T.get(
    typeOf: EnumStoreType<R>,
    crossinline callback: (R) -> Unit
) where T : Enum<T>, T : EnumStoreOption {
    val value = instance?.block(typeOf.getKey(name)) ?: typeOf.defaultValue
    callback(value)
}

inline fun <reified T, reified R> T.set(value: R)
    where T : Enum<T>, T : EnumStoreOption =
    instance?.edit(getPreferenceKey(name), value)

inline fun <reified T, reified R : Any> T.erase(typeOf: EnumStoreType<R>)
    where T : Enum<T>, T : EnumStoreOption =
    instance?.erase(typeOf.getKey(name))
```

---

## ⚠️ Limitations
- Kotlin cannot infer the generic type `R` from enum metadata — it must be provided by passing a default value or using `EnumStoreType`.
- Enums cannot carry generic type info for the compiler to use in type inference.
- You must call `EnumStore.create()` before accessing any stored values.

---

## 📄 License
MIT
