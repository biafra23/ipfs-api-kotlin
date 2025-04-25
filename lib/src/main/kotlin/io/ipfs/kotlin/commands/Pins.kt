package io.ipfs.kotlin.commands

import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import io.ipfs.kotlin.IPFSConnection

class Pins(val ipfs: IPFSConnection) {

    private val pinsAdapter: JsonAdapter<PinLsResult> by lazy {
        ipfs.config.moshi.adapter(PinLsResult::class.java)
    }

    fun add(hash: String): Boolean {
        val resultString = ipfs.callCmd("pin/add/$hash").use { it.string() }
        val resultBoolean = resultString.contains(hash)
        if (!resultBoolean) {
            ipfs.setErrorByJSON(resultString)
        }
        return resultBoolean
    }

    fun ls(): PinLsResultResult {
        val httpResponse = ipfs.executeCmd("pin/ls")

        if (httpResponse.isSuccessful) {
            val result = pinsAdapter.fromJson(httpResponse.body()!!.use {
                it.string()
            })
            return PinLsResultResult.Success(result!!)
        } else {
            ipfs.setErrorByJSON(httpResponse.body()!!.use { it.string() })
            return PinLsResultResult.Failure(ipfs.lastError?.Message ?: "Unknown error")
        }
    }
}

sealed class PinLsResultResult {
    data class Success(val pinLsResult: PinLsResult) : PinLsResultResult()
    data class Failure(val errorMessage: String) : PinLsResultResult()
}

@JsonClass(generateAdapter = true)
data class PinLsResult(
    @Json(name = "PinLsList")
    val pinLsList: PinLsList? = null,
    @Json(name = "PinLsObject")
    val pinLsObject: PinLsObject? = null
)

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

@JsonClass(generateAdapter = true)
data class PinLsObject(
    @Json(name = "Cid")
    val cid: String? = null,
    @Json(name = "Name")
    val name: String? = null,
    @Json(name = "Type")
    val type: String? = null
)