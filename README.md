# EnumStore 🛡️ — Enums as Preference Keys

### 🛑 STATUS: EXPERIMENTAL & NO LONGER MAINTAINED
*This project was an exploration into making Android's DataStore feel more "Kotlin-y." It is not production-ready, and I am no longer actively maintaining it. If you find it useful, feel free to fork it and make it your own!*

---

## The "Why"
We've all been there: wading through a sea of `sharedPrefs.getString("PREF_USER_KEY_V2_FINAL", "")` and hoping we didn't typo the key name somewhere. 

**EnumStore** was born from a simple desire: What if we used `enum class` entries as keys? 
- No raw strings.
- No passing `Context` around every time you want to read a value.
- Real type safety (mostly).
- One-liner access from anywhere.

---

## 🚀 How it works (The "Human" Version)

### 1. Tell your Enums they belong to a Store
To make an Enum work with this library, it needs to implement a marker interface.

#### The "I want my own file" way:
If you want an Enum to have its own dedicated storage file, use `EnumStoreCollection`.
```kotlin
enum class UserSettings : EnumStoreCollection {
    USERNAME,
    THEME_DARK_MODE
}
```

#### The "I want to share a file" way:
Sometimes you have tiny Enums that don't need their own file. You can bundle them together using `@PartOf`.
```kotlin
@PartOf(UserSettings::class) // This will save into UserSettings.preferences_pb
enum class ProfileSettings : EnumStoreShared {
    AVATAR_URL,
    BIO
}
```

### 2. Wake it up in your Application
You only need to do this once. It sets up the singleton and the coroutine scopes.
```kotlin
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        EnumStore.create(this) 
    }
}
```

### 3. Use it anywhere
Because of Kotlin extension functions, your Enums now have superpowers.

**The Simple Get/Set:**
```kotlin
// Save it
UserSettings.USERNAME.set("KotlinLover99")

// Grab it (returns "Guest" if empty)
val name = UserSettings.USERNAME.get("Guest")
```

**The Reactive Way (Perfect for Compose):**
```kotlin
// Get a StateFlow that updates automatically
val themeFlow = UserSettings.THEME_DARK_MODE.asStateFlow(EnumStoreType.TypeBoolean)

// Or a standard Flow
val bioFlow = ProfileSettings.BIO.asFlow(EnumStoreType.TypeString)
```

---

## 🛠 The Type System
Since Kotlin can't "peek" inside an Enum to know what type it's supposed to hold, you help it out by either providing a default value or an `EnumStoreType`.

Supported types: `String`, `Int`, `Long`, `Boolean`, `Double`, `Float`, `ByteArray`, and `Set<String>`.

---

## ⚠️ The "Catch" (Limitations)
- **Type Inference**: You'll notice you often have to pass the type or a default value. That's because Kotlin enums don't carry generic info to the compiler. It's a trade-off for the clean syntax.
- **Initialization**: If you forget `EnumStore.create(this)`, the app will crash when you first try to access a preference. Don't forget it.
- **Experimental**: The multi-file registry is cool, but it hasn't been stress-tested for 10,000 keys. 

## 📄 License
MIT. Go wild.
