package io.ipfs.kotlin.model

import com.squareup.moshi.Json

data class SwarmPeers(
    @Json(name = "Peers")
    val peers: List<Peer>? = null
)

data class Peer(
    @Json(name = "Addr")
    val addr: String? = null,
    @Json(name = "Direction")
    val direction: Int? = null,
    @Json(name = "Identify")
    val identify: Identify? = null,
    @Json(name = "Latency")
    val latency: String? = null,
    @Json(name = "Muxer")
    val muxer: String? = null,
    @Json(name = "Peer")
    val peer: String? = null,
    @Json(name = "Streams")
    val streams: List<Stream>? = null
)

data class Identify(
    @Json(name = "Addresses")
    val addresses: List<String>? = null,
    @Json(name = "AgentVersion")
    val agentVersion: String? = null,
    @Json(name = "ID")
    val id: String? = null,
    @Json(name = "Protocols")
    val protocols: List<String>? = null,
    @Json(name = "PublicKey")
    val publicKey: String? = null
)

data class Stream(
    @Json(name = "Protocol")
    val protocol: String? = null
)