package io.ipfs.kotlin.commands

import com.squareup.moshi.JsonAdapter
import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.Strings
import io.ipfs.kotlin.model.SwarmPeers

class Swarm(val ipfs: IPFSConnection) {

    private val stringsAdapter: JsonAdapter<Strings> by lazy {
        ipfs.config.moshi.adapter(Strings::class.java)
    }

    private val swarmPeersAdapter: JsonAdapter<SwarmPeers> by lazy {
        ipfs.config.moshi.adapter(SwarmPeers::class.java)
    }

    fun connect(address: String): StringsResult {
        val httpResponse = ipfs.executeCmd("swarm/connect?arg=$address")
        if (httpResponse.isSuccessful) {
            val result = stringsAdapter.fromJson(httpResponse.body()!!.use {
                it.string()
            })
            return StringsResult.Success(result)
        } else {
            ipfs.setErrorByJSON(httpResponse.body()!!.use { responseBody ->
                responseBody.string()
            })
            return StringsResult.Failure(ipfs.lastError?.Message ?: "Unknown error")
        }
    }


    fun peers(): SwarmPeersResult {
        val httpResponse = ipfs.executeCmd("swarm/peers")
        if (httpResponse.isSuccessful) {
            val result = swarmPeersAdapter.fromJson(httpResponse.body()!!.use {
                val result = it.string()
                println("swarm/peers: $result")
                result
            })
            return SwarmPeersResult.Success(result)
        } else {
            ipfs.setErrorByJSON(httpResponse.body()!!.use { responseBody ->
                responseBody.string()
            })
            return SwarmPeersResult.Failure(ipfs.lastError?.Message ?: "Unknown error")
        }
    }
}

sealed class StringsResult {
    data class Success(val result: Strings) : StringsResult()
    data class Failure(val errorMessage: String) : StringsResult()
}

sealed class SwarmPeersResult {
    data class Success(val peers: SwarmPeers) : SwarmPeersResult()
    data class Failure(val errorMessage: String) : SwarmPeersResult()
}