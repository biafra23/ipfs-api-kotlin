package io.ipfs.kotlin.model

import com.squareup.moshi.Json

//@JsonClass(generateAdapter = true)
data class PinLsList(
    @Json(name = "Keys")
    val keys: Map<String, PinDetails>? = null
)

//@JsonClass(generateAdapter = true)
data class PinDetails(
    @Json(name = "Name")
    val name: String? = null,
    @Json(name = "Type")
    val type: String? = null
)