package com.example.common_feature.data.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object CurrentUserSerializer: Serializer<UserSerial> {
    override val defaultValue: UserSerial
        get() = UserSerial("", "")

    override suspend fun readFrom(input: InputStream): UserSerial {
        return try {
            Json.decodeFromString<UserSerial>(
                input.readBytes().decodeToString()
            )
        }catch (e: SerializationException){
            throw CorruptionException("Unable to read current user", e)
        }
    }

    override suspend fun writeTo(
        t: UserSerial,
        output: OutputStream
    ) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(t).encodeToByteArray()
            )
        }
    }
}