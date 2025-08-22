package id.mohekkus.enumstore

import java.io.File

internal interface EnumStorePreferenceInterface {
    fun onGeneratingFile(key: String): File
}