package com.compose.starter.networking.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull

object RatedDynamicSerializer : KSerializer<Any?> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("rated")

    override fun serialize(encoder: Encoder, value: Any?) {
        val jsonEncoder = encoder as JsonEncoder
        when (value) {
            is Boolean -> jsonEncoder.encodeBoolean(value)
            is Rated -> jsonEncoder.encodeSerializableValue(Rated.serializer(), value)
            else -> throw SerializationException("Unsupported type: $value")
        }
    }

    override fun deserialize(decoder: Decoder): Any? {
        val jsonDecoder = decoder as JsonDecoder
        val element = jsonDecoder.decodeJsonElement()

        return when {
            element is JsonPrimitive && element.booleanOrNull != null -> {
                element.booleanOrNull
            }

            element is JsonObject -> {
                jsonDecoder.json.decodeFromJsonElement(
                    Rated.serializer(),
                    element
                )
            }

            else -> throw SerializationException("Unexpected JSON element: $element")
        }
    }
}