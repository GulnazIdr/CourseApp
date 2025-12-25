package com.example.common_feature.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer: Serializer<List<UserSerial>> {
    override val defaultValue: List<UserSerial>
        get() = emptyList<UserSerial>()

    override suspend fun readFrom(input: InputStream): List<UserSerial> {
        return try {
            Json.decodeFromString<List<UserSerial>>(
                input.readBytes().decodeToString()
            )
        }catch (e: SerializationException){
            throw CorruptionException("Unable to read UserSerial", e)
        }
    }

    override suspend fun writeTo(
        t: List<UserSerial>,
        output: OutputStream
    ) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(t).encodeToByteArray()
            )
        }
    }
}