package io.ipfs.kotlin.commands

import com.squareup.moshi.JsonAdapter
import io.ipfs.kotlin.IPFSConnection
import io.ipfs.kotlin.model.AddrsMap
import io.ipfs.kotlin.model.Strings
import io.ipfs.kotlin.model.SwarmPeers

class Swarm(val ipfs: IPFSConnection) {
    private val stringsAdapter: JsonAdapter<Strings> by lazy {
        ipfs.config.moshi.adapter(Strings::class.java)
    }
    private val swarmPeersAdapter: JsonAdapter<SwarmPeers> by lazy {
        ipfs.config.moshi.adapter(SwarmPeers::class.java)
    }
    private val addrsAdapter: JsonAdapter<AddrsMap> by lazy {
        ipfs.config.moshi.adapter(AddrsMap::class.java)
    }

    // Open connection to a given peer.
    fun connect(address: String): StringsResult {
        val httpResponse = ipfs.executeCmd("swarm/connect?arg=$address")
        if (httpResponse.isSuccessful) {
            val result = stringsAdapter.fromJson(httpResponse.body()!!.use {
                it.string()
            })
            return StringsResult.Success(result!!)
        } else {
            ipfs.setErrorByJSON(httpResponse.body()!!.use { responseBody ->
                responseBody.string()
            })
            return StringsResult.Failure(ipfs.lastError?.Message ?: "Unknown error")
        }
    }

    fun disconnect(address: String): StringsResult {
        val httpResponse = ipfs.executeCmd("swarm/disconnect?arg=$address")
        if (httpResponse.isSuccessful) {
            val result = stringsAdapter.fromJson(httpResponse.body()!!.use {
                it.string()
            })
            return StringsResult.Success(result!!)
        } else {
            ipfs.setErrorByJSON(httpResponse.body()!!.use { responseBody ->
                responseBody.string()
            })
            return StringsResult.Failure(ipfs.lastError?.Message ?: "Unknown error")
        }
    }

    /**
     * List peers connected to the local node.
     *
     * @param verbose [Boolean] - display all extra information. Required: no.
     * @param streams [Boolean] - Also list information about open streams for each peer. Required: no.
     * @param latency [Boolean] - Also list information about latency to each peer. Required: no.
     * @param direction [Boolean] - Also list information about the direction of connection. Required: no.
     * @param identify [Boolean] - Also list information about peers identify. Required: no.
     *
     * @return SwarmPeersResult
     */
    fun peers(
        verbose: Boolean = false,
        streams: Boolean = false,
        latency: Boolean = false,
        direction: Boolean = false,
        identify: Boolean = false
    ): SwarmPeersResult {
        val httpResponse =
            ipfs.executeCmd("swarm/peers?verbose=$verbose&streams=$streams&latency=$latency&direction=$direction&identify=$identify")
        if (httpResponse.isSuccessful) {
            val result = swarmPeersAdapter.fromJson(httpResponse.body()!!.use {
                it.string()
            })
            return SwarmPeersResult.Success(result!!)
        } else {
            ipfs.setErrorByJSON(httpResponse.body()!!.use { responseBody ->
                responseBody.string()
            })
            return SwarmPeersResult.Failure(ipfs.lastError?.Message ?: "Unknown error")
        }
    }

    // List known addresses. Useful for debugging.
    fun addrs(): SwarmAddrsResult {
        val httpResponse = ipfs.executeCmd("swarm/addrs")

        if (httpResponse.isSuccessful) {
            val result = addrsAdapter.fromJson(httpResponse.body()!!.use {
                it.string()
            })
            return SwarmAddrsResult.Success(result!!)
        } else {
            ipfs.setErrorByJSON(httpResponse.body()!!.use { it.string() })
            return SwarmAddrsResult.Failure(ipfs.lastError?.Message ?: "Unknown error")
        }
    }

    // List interface listening addresses.
    fun addrsListen(showId: Boolean): StringsResult {
        val httpResponse = ipfs.executeCmd("swarm/addrs/listen?showId=$showId")
        if (httpResponse.isSuccessful) {
            val result = stringsAdapter.fromJson(httpResponse.body()!!.use {
                it.string()
            })
            return StringsResult.Success(result!!)
        } else {
            ipfs.setErrorByJSON(httpResponse.body()!!.use { responseBody ->
                responseBody.string()
            })
            return StringsResult.Failure(ipfs.lastError?.Message ?: "Unknown error")
        }
    }

    // List local addresses.
    fun addrsLocal(showId: Boolean): StringsResult {
        val httpResponse = ipfs.executeCmd("swarm/addrs/local?showId=$showId")
        if (httpResponse.isSuccessful) {
            val result = stringsAdapter.fromJson(httpResponse.body()!!.use {
                it.string()
            })
            return StringsResult.Success(result!!)
        } else {
            ipfs.setErrorByJSON(httpResponse.body()!!.use { responseBody ->
                responseBody.string()
            })
            return StringsResult.Failure(ipfs.lastError?.Message ?: "Unknown error")
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

sealed class SwarmAddrsResult {
    data class Success(val addrsMap: AddrsMap) : SwarmAddrsResult()
    data class Failure(val errorMessage: String) : SwarmAddrsResult()
}