package io.ipfs.kotlin.model

import com.squareup.moshi.Json

data class AddrsMap(
    @Json(name = "Addrs")
    val addrs: Map<String, List<String>>
)