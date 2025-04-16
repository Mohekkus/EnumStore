package id.mohekkus.enumstore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

open class BaseDatastore(context: Context, keyName: String) {

    private val Context._datastore: DataStore<Preferences> by preferencesDataStore(keyName)
    open val setting = context._datastore

}