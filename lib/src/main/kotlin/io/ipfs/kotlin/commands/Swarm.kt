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
//                val result = it.string()
//                //println("swarm/peers: $result")
//                result
                """{"Peers":[{"Addr":"/ip4/1.34.195.13/tcp/4001","Peer":"12D3KooWGjbqEFd4x5fjoxGpJ4dVWC1ANetz5bBpidEumexFyNme","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/100.42.190.40/tcp/4001","Peer":"12D3KooWSmQa5Gvo4mVygcBZXWp3xbw7cNgrYGC8kF5fibqrUWsW","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/104.131.131.82/udp/4001/quic-v1","Peer":"QmaCpDMGvV2BGHeYERUEnRQAwe3N8SzbUtfsmvsqQLuvuJ","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/107.170.254.5/udp/4001/quic-v1","Peer":"12D3KooWLJ2DwWAMd2a3s5JabdVKM1srt7D2TnwjCjr4hwnqk6zh","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/108.61.214.59/udp/4001/quic-v1","Peer":"12D3KooWQtVX3CnFkHDKUoTqUcCxSGAYQiApgJrfTeo7srQ3Eypw","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/109.123.230.243/udp/4001/quic-v1","Peer":"12D3KooWDAoGKyncHefhPTTyE5TkJfA2Q6CJyTVnTxr5rFVXmTb4","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/109.123.240.148/udp/4001/quic-v1","Peer":"12D3KooWFGrZmQFKK7Lz7MwL4DAvP8wf86ie3QiBbUE5vQQhxErE","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/121.131.148.84/tcp/54097","Peer":"QmbXntevpoa8oVpTu2VvVdNWCWyp8hmWkPUFaw7aAWt7Mu","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/137.184.243.187/tcp/3000/ws","Peer":"Qma8ddFEQWEU8ijWvdxXm3nxU7oHsRtCykAaVz8WUYhiKn","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/144.126.130.93/tcp/4001","Peer":"12D3KooWFNhP7Y3qyYfhg9Th5jeYayqmLkoNcoW5X862vERCyc9u","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/144.126.139.249/udp/4001/quic-v1","Peer":"12D3KooWAah3WpgXR4eGhvxBXoFiAUa5GmkHdCCavt2Dwj1kYQM5","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/144.91.105.95/udp/4001/quic-v1","Peer":"12D3KooWCe61Hip7QVvwpVDMfN8t54kxPbQCiGAkf3GoNJkBbdjv","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/157.173.201.26/udp/4001/quic-v1","Peer":"12D3KooWPgFq5bQevV1gs7FX8QPpzrFrYhWU7Hp1jBzn4Si3JJhv","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/157.90.181.211/udp/4001/quic-v1","Peer":"12D3KooWGaxbu3g9Zhp3B78JtYXPXrUZPwhkLmeaKexrJk4KDFMD","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/158.220.98.162/udp/4001/quic-v1","Peer":"12D3KooWQJBeYBnvBeKv8AhUmZH1m5ADzAzdzsfeGpxAtuSD3SEM","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/162.55.199.94/tcp/4001","Peer":"12D3KooWQmN4tT3KMq3QPZqTr8zqK5RvSFsqh4qKeVh4v9afimr3","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/167.86.97.163/tcp/9000","Peer":"QmaHUjumq6GrFmaSczYqR7w8GUfH4YWWJUbhFXfUjBPvGH","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/172.104.228.137/udp/4001/quic-v1","Peer":"12D3KooWG9UcagbN7A7JRUj93xeAPofx9S8TvvjCpE2m3a5UDBLU","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/185.239.209.146/udp/4001/quic-v1","Peer":"12D3KooWLopk3b912HboV9fjrqkPTgPZ9EvQjgMsrC5ybr2zrofS","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/186.233.184.126/udp/4001/quic-v1","Peer":"12D3KooWPEw26xaeD9d1DDmeoouvRQfPNyfP5ADKoJrU9EitCkGE","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/193.24.208.214/udp/4001/quic-v1","Peer":"12D3KooWDezcWqNJWUkvodu4DBobpZ9qzVWsDPJxbegcvPUxSfiH" ,"Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/193.42.36.215/udp/4001/quic-v1","Peer":"12D3KooWJHRbxeJf98iQnEhrfQdBuZGJyik4bAiCmURah87rtSnm","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/201.247.243.254/tcp/42455","Peer":"12D3KooWDYxGrCLjAJMhjR7fFwcKhbzR6x7iKCzNX6mpmRqY5NCf","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/207.148.113.122/tcp/4001","Peer":"12D3KooWSaUg1gmMK9L6dQ4VnLuYf3kfDftGTh3YqZ3hUqNwzQM6","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/207.180.248.155/tcp/4001","Peer":"QmQM28aMYcaNp9cHS4FSLM5CreyMBx88PDvh5mHFbt12Pv","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/23.94.203.11/udp/4001/quic-v1","Peer":"12D3KooWSXEUxAiRQ3bbJ3mnatNP2jv5EyUoBw7Jsquj3hReRheR","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/35.233.163.180/udp/4001/quic-v1","Peer":"12D3KooWKTa8E8WvWhzEwDAqJFTCCF4phpaCfUkyBnawqFGM7V7o","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/38.97.62.156/tcp/4001","Peer":"12D3KooWDVN4xDVJG2eUieJeC5ofXrzy6gjNqotog2NZGraw9GQw","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/45.32.179.61/tcp/4001","Peer":"12D3KooWAr5UTq8St5eSUgiTQzxJjWunk3EHmNunUzZHMoyHLCLL","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/45.76.27.102/tcp/4001","Peer":"12D3KooWN7GC3WM2tiMHTUvwXcezpyYccR7Snu892TUuWhTzhanS","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/45.77.41.189/udp/4001/quic-v1","Peer":"12D3KooWLoAn44j597ESGpo43PYbikb2MKsQvVhmXEUVUsfNvY5v","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/51.158.61.105/tcp/4001","Peer":"12D3KooWQy4waxqtD54aSiFSU9a8fcrpp6tQf69w1JXA9xbhurnp","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/51.159.103.9/tcp/4001","Peer":"12D3KooWNMsv8YWV7knic3dCxDYuDPcH6ESSL7kt2J3PVBPjeQKi","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/51.91.18.70/tcp/4001","Peer":"12D3KooWSPgD3zD7mPjxLBeWQ6XrgD1KwkQywirPHtz8mw6Tv7eA","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/58.214.50.116/udp/20729/quic-v1","Peer":"12D3KooWKSkxYTnMfejnigrbHSTUBfTiRZiFdm9Wad5iGDSY5qck","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/62.141.40.245/udp/4001/quic-v1","Peer":"12D3KooWFX2jS34GivCavvSK3pcCWphwmqwTJKvVRM5Hxx2R7muw","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/62.171.171.78/tcp/4001","Peer":"12D3KooWLJQKVYarjTYWcW39NhcKZdFZDmmqUQ2fTDD6zrv3cdYQ","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/62.210.158.227/tcp/4001","Peer":"12D3KooWE2FuDNgiRgTnCxWnF76C7reHvo1eC8wRSUt16rUhnw96","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/65.108.130.28/tcp/4001","Peer":"12D3KooWPd23xnfxnmfhAesrPGkesgdvnXSyP1JWEeYNJZfamJe2","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/65.109.106.155/udp/4001/quic-v1","Peer":"12D3KooWEAHmxZt5vMeQwf4sYWUL73rjrURBLwSYJiwek6LRkzEA","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/65.109.158.229/tcp/4001","Peer":"12D3KooWMevqkGsBavfaybZinCgMKkVY5pwGpTNTFF41Tpmbqaem","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/65.21.92.252/udp/4001/quic-v1","Peer":"12D3KooWAg6FvPAyjEcd1ibo4jfD4nSzcGomCFomtauus7yveq9B","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/77.50.76.34/udp/14947/quic-v1","Peer":"12D3KooWNLR3wHAZu1wzjQGy5wXYnMwk99MSMJXWmwgtubxrKPJD","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/90.247.86.103/udp/44560/quic-v1","Peer":"12D3KooWFLXjfNvfvKifdwmDQL5v2xBPSeRua6ZfiyEQ8npQo3aP","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/91.107.201.86/udp/4001/quic-v1","Peer":"12D3KooWSHiXxE3QFCYKm9xV4xB9FebRXU3s4nse2oq7ofnP8G4f","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/93.25.51.54/tcp/4001","Peer":"12D3KooWGnSs8U6XT1ffRES8tbzLVVkQq5FQ1drTCJWqLUxke9M7","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/94.175.132.1/udp/26761/quic-v1","Peer":"12D3KooWDXyT3hQ9n1HQC5gjXCPgjQz694w4FoR52D4ZVXHbF5yD","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/95.111.228.164/udp/4001/quic-v1","Peer":"12D3KooWLAtHCEEGAvVXa6Lp8uDFR9a5RHKa8vWuo5UsgMAzodxN","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/95.217.83.224/udp/4001/quic-v1","Peer":"12D3KooWRd7oK2GF45ZQJZEwUVHDAkE6Ms72ov6MAcJWZucW1Lt2","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/95.220.16.72/udp/63912/quic-v1","Peer":"12D3KooWAMtadaXmUmsc3avD7ZKEsVTPtF7RxV3F3HJq1iDhQqmL","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/95.47.196.63/udp/13658/quic-v1","Peer":"12D3KooWASBo1a5pzRht96CLbSbzUQBXpu4qA6kk6g37dwqc2srv","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/98.84.247.28/udp/4001/quic-v1","Peer":"12D3KooWNeUUdYj9VQxNXrXFhF545sRoV38oW1i5hmhC6wpmnVVN","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}}]}"""

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