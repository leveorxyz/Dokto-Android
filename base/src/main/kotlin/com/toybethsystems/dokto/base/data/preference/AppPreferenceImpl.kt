package com.toybethsystems.dokto.base.data.preference

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.toybethsystems.dokto.base.R
import com.toybethsystems.dokto.base.data.model.DoktoUser
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class AppPreferenceImpl @Inject constructor(@ApplicationContext context: Context) :
    AppPreference {

    companion object {
        const val USER = "Dokto User"
        const val FIRST_TIME_USER = "First time user"
    }

    private var preference =
        context.getSharedPreferences(context.getString(R.string.pref_name), MODE_PRIVATE)
    private var editor = preference.edit()

    override var user: DoktoUser?
        get() = getObject(USER, DoktoUser::class.java)
        set(value) {
            saveObject(USER, value)
        }

    override var isFirstTimeUser: Boolean
        get() = getBoolean(FIRST_TIME_USER, true)
        set(value) {
            saveBoolean(FIRST_TIME_USER, value)
        }

    private fun saveString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    private fun getString(key: String, defaultValue: String = ""): String {
        return preference.getString(key, defaultValue) ?: defaultValue
    }

    private fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preference.getBoolean(key, defaultValue)
    }

    private fun saveInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    private fun getInt(key: String, defaultValue: Int): Int {
        return preference.getInt(key, defaultValue)
    }

    private fun saveFloat(key: String, value: Float) {
        editor.putFloat(key, value).apply()
    }

    private fun getFloat(key: String, defaultValue: Float): Float {
        return preference.getFloat(key, defaultValue)
    }

    private fun saveLong(key: String, value: Long) {
        editor.putLong(key, value).apply()
    }

    private fun getLong(key: String, defaultValue: Long): Long {
        return preference.getLong(key, defaultValue)
    }

    private fun saveObject(key: String, value: Any?) {
        val valueString = Gson().toJson(value)
        saveString(key, valueString)
    }

    private fun <T> getObject(key: String, clazz: Class<T>): T? {
        return Gson().fromJson(preference.getString(key, null), clazz)
    }

}