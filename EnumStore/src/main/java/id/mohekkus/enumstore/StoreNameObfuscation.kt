package id.mohekkus.enumstore

fun interface StoreNameObfuscation {
    fun onPlainText(plainText: String): String
}