package com.codingle.newsoncompose.api_settings.data.preference

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.codingle.newsoncompose.api_settings.datastore.UserSetting
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SettingSerializer : Serializer<UserSetting> {
    override suspend fun readFrom(input: InputStream): UserSetting {
        try {
            return UserSetting.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Error deserializing proto", exception)
        }
    }

    override suspend fun writeTo(t: UserSetting, output: OutputStream) = t.writeTo(output)

    override val defaultValue: UserSetting
        get() = UserSetting.getDefaultInstance()
}