# EnumStore – Clean Preferences with Kotlin Enums

⚠️ Early stage — not production-ready

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

How to use library with [Jetpack Compose](https://github.com/Mohekkus/EnumStore/blob/master/app/src/main/java/id/mohekkus/example/MainActivity.kt)

---

## 🛠 Core API

```kotlin
    inline fun <reified T, reified R: Any> T.get(
        defaultValue: R,
    ): R where T : Enum<T>, T : EnumStoreMarker {
        with(defaultValue.getTypeFromValue()) {
            return instance.from(this@get).block(
                getKey(name)
            ) ?: defaultValue
        }
    }

    inline fun <reified T, reified R : Any> T.get(
        typeOf: EnumStoreType<R>,
        crossinline callback: (R) -> Unit
    ) where T : Enum<T>, T : EnumStoreMarker {
        callback(
            instance.from(this).block(
                typeOf.getKey(name)
            ) ?: typeOf.defaultValue
        )
    }

    inline fun <reified T, reified R : Any> T.asFlow(
        typeOf: EnumStoreType<R>
    ): Flow<R> where T : Enum<T>, T : EnumStoreMarker {
        return instance.from(this).async(
            typeOf.getKey(name), typeOf.defaultValue
        )
    }

    inline fun <reified T, reified R : Any> T.asStateFlow(
        typeOf: EnumStoreType<R>
    ): StateFlow<R> where T : Enum<T>, T : EnumStoreMarker {
        return instance.from(this).state(
            typeOf.getKey(name), typeOf.defaultValue
        )
    }

    inline fun <reified T, reified R: Any> T.set(
        value: R
    ) where T : Enum<T>, T : EnumStoreMarker {
        with(value.getTypeFromValue()) {
            instance.from(this@set)
                .edit(getKey(name), value)
        }
    }

    inline fun <reified T, reified R : Any> T.erase(
        typeOf: EnumStoreType<R>,
    ) where T : Enum<T>, T : EnumStoreMarker =
        instance.from(this)
            .erase(typeOf.getKey(name))

```

---

## ⚠️ Limitations
- Kotlin cannot infer the generic type `R` from enum metadata — it must be provided by passing a default value or using `EnumStoreType`.
- Enums cannot carry generic type info for the compiler to use in type inference.
- You must call `EnumStore.create()` before accessing any stored values.

---

## 📄 License
MIT
