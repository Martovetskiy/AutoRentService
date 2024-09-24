@file:Suppress("EXTERNAL_SERIALIZER_USELESS")
@file:OptIn(ExperimentalSerializationApi::class)

package api.serial

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Serializer(forClass = OffsetDateTime::class)
object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeString(formatter.format(value))
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        return OffsetDateTime.parse(decoder.decodeString(), formatter)
    }
}