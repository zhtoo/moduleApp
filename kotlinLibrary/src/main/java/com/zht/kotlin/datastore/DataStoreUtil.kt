package com.zht.kotlin.datastore

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.zht.kotlin.BuildConfig

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Date   2023/1/3 15:51
 * @Author zhanghaitao
 * @Description Jetpack ---> DataStore  Preferences
 */
object DataStoreUtil {

    private const val TAG = "DataStoreUtil"

    private const val LOG_FILE_NAME = "logDataStore"
    private const val CATCH_FILE_NAME = "catchDataStore"
    private const val APP_FILE_NAME = "appDataStore"

    // 创建 Preferences DataStore
    val Context.logDataStore: DataStore<Preferences> by preferencesDataStore(name = LOG_FILE_NAME)// 用于记录用户操作行为
    val Context.catchDataStore: DataStore<Preferences> by preferencesDataStore(name = CATCH_FILE_NAME)// 用于记录app崩溃日志
    val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = APP_FILE_NAME)// 用于存储用户数据

    // 缓存DataStore
    private lateinit var logDataStore: DataStore<Preferences>
    private lateinit var catchDataStore: DataStore<Preferences>
    private lateinit var appDataStore: DataStore<Preferences>
    private lateinit var logPath: String
    private lateinit var catchPath: String

    fun init(context: Context) {
        logDataStore = context.logDataStore
        catchDataStore = context.catchDataStore
        appDataStore = context.appDataStore
        logPath = context.preferencesDataStoreFile(LOG_FILE_NAME).absolutePath
        catchPath = context.preferencesDataStoreFile(CATCH_FILE_NAME).absolutePath
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "DataStoreUtil init. log file path is $logPath")
            Log.e(TAG, "DataStoreUtil init. catch file path is $catchPath")
        }
        log("开始记录日志")
        catch("开始记录崩溃日志")
    }

    private suspend fun saveBoolean(key: String, value: Boolean) {
        appDataStore.edit { mutablePreferences ->
            mutablePreferences[booleanPreferencesKey(key)] = value
        }
    }

    private suspend fun saveInt(key: String, value: Int) {
        appDataStore.edit { mutablePreferences ->
            mutablePreferences[intPreferencesKey(key)] = value
        }
    }

    private suspend fun saveString(key: String, value: String) {
        appDataStore.edit { mutablePreferences ->
            mutablePreferences[stringPreferencesKey(key)] = value
        }
    }

    private suspend fun saveFloat(key: String, value: Float) {
        appDataStore.edit { mutablePreferences ->
            mutablePreferences[floatPreferencesKey(key)] = value
        }
    }

    suspend fun saveLong(key: String, value: Long) {
        appDataStore.edit { mutablePreferences ->
            mutablePreferences[longPreferencesKey(key)] = value
        }
    }

    private fun readBoolean(key: String, default: Boolean = false): Flow<Boolean> =
        appDataStore.data
            .catch {
                //当读取数据遇到错误时，如果是 `IOException` 异常，发送一个 emptyPreferences 来重新使用
                //但是如果是其他的异常，最好将它抛出去，不要隐藏问题
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[booleanPreferencesKey(key)] ?: default
            }

    private fun readInt(key: String, default: Int = 0): Flow<Int> =
        appDataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[intPreferencesKey(key)] ?: default
            }

    private fun readString(key: String, default: String = ""): Flow<String> =
        appDataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[stringPreferencesKey(key)] ?: default
            }

    private fun readFloat(key: String, default: Float = 0f): Flow<Float> =
        appDataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[floatPreferencesKey(key)] ?: default
            }

    private fun readLong(key: String, default: Long = 0L): Flow<Long> =
        appDataStore.data
            .catch {
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map {
                it[longPreferencesKey(key)] ?: default
            }

    fun <U> getData(key: String, default: U): Flow<U> {
        if (appDataStore == null) {
            return flow { default }
        }
        val data = when (default) {
            is Long -> readLong(key, default)
            is String -> readString(key, default)
            is Int -> readInt(key, default)
            is Boolean -> readBoolean(key, default)
            is Float -> readFloat(key, default)
            else -> throw IllegalArgumentException("This type can not be read from DataStore")
        }
        return data as Flow<U>
    }

    fun setData(key: String, value: Any) {
        if (appDataStore == null) {
            return
        }
        GlobalScope.launch {
            when (value) {
                is Long -> saveLong(key, value)
                is String -> saveString(key, value)
                is Int -> saveInt(key, value)
                is Boolean -> saveBoolean(key, value)
                is Float -> saveFloat(key, value)
                else -> throw IllegalArgumentException("This type can be saved into DataStore")
            }
        }
    }

    /**
     * 用于记录log行为，不提供数据读取行为
     */
    fun log(value: Any) {
        if (logDataStore == null) {
            return
        }
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "$value")
        }
        GlobalScope.launch {
            val key =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(Date())
            logDataStore.edit { preferences ->
                when (value) {
                    is Long -> preferences[longPreferencesKey(key)] = value
                    is String -> preferences[stringPreferencesKey(key)] = value
                    is Int -> preferences[intPreferencesKey(key)] = value
                    is Boolean -> preferences[booleanPreferencesKey(key)] = value
                    is Float -> preferences[floatPreferencesKey(key)] = value
                    else -> {
                        if (BuildConfig.DEBUG) {
                            Log.e(TAG,
                                "DataStore can only save data of Int/Long/String/Boolean/Float！！！")
                        }
                    }
                }
            }
        }
    }

    /**
     * 用于记录app奔溃日志，不提供数据读取行为
     */
    fun catch(value: String) {
        if (catchDataStore == null) {
            return
        }
        GlobalScope.launch {
            val key =
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(Date())
            catchDataStore.edit { preferences ->
                preferences[stringPreferencesKey(key)] = value
            }
        }
    }

    fun exportLogPath(): Array<String> {
        return arrayOf(logPath, catchPath)
    }

}


