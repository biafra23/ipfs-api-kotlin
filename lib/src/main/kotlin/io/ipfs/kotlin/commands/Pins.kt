package io.ipfs.kotlin.commands

import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import io.ipfs.kotlin.IPFSConnection

class Pins(val ipfs: IPFSConnection) {

    private val pinsAdapter: JsonAdapter<PinLsList> by lazy {
        ipfs.config.moshi.adapter(PinLsList::class.java)
    }

    fun add(hash: String): Boolean {
        val resultString = ipfs.callCmd("pin/add/$hash").use { it.string() }
        val resultBoolean = resultString.contains(hash)
        if (!resultBoolean) {
            ipfs.setErrorByJSON(resultString)
        }
        return resultBoolean
    }

    fun ls(): PinLsListResult {
        val httpResponse = ipfs.executeCmd("pin/ls")

        if (httpResponse.isSuccessful) {
            val result = pinsAdapter.fromJson(httpResponse.body()!!.use {
                it.string()
            })
            return PinLsListResult.Success(result!!)
        } else {
            ipfs.setErrorByJSON(httpResponse.body()!!.use { it.string() })
            return PinLsListResult.Failure(ipfs.lastError?.Message ?: "Unknown error")
        }
    }
}

sealed class PinLsListResult {
    data class Success(val pinLsList: PinLsList) : PinLsListResult()
    data class Failure(val errorMessage: String) : PinLsListResult()
}

@JsonClass(generateAdapter = true)
data class PinLsList(
    @Json(name = "Keys")
    val keys: Map<String, PinDetails>? = null
)

@JsonClass(generateAdapter = true)
data class PinDetails(
    @Json(name = "Name")
    val name: String? = null,
    @Json(name = "Type")
    val type: String? = null
)