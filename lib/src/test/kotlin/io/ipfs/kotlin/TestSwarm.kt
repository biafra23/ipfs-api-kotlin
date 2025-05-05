package io.ipfs.kotlin

import io.ipfs.kotlin.commands.StringsResult
import io.ipfs.kotlin.commands.SwarmAddrsResult
import io.ipfs.kotlin.commands.SwarmPeersResult
import io.ipfs.kotlin.model.Strings
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Ignore
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class TestSwarm : BaseIPFSWebserverTest() {

    @Test
    fun testSwarmConnect() {
        // setup
        server.enqueue(
            MockResponse().setBody("{\"Strings\":[\"success message\"]}\n").setResponseCode(200)
        )

        // invoke
        val connected = ipfs.swarm.connect("valid-address")

        // assert
        assertThat(connected).isNotNull()
        assertThat(connected).isEqualTo(StringsResult.Success(Strings(listOf("success message"))))

        assertThat(ipfs.lastError).isNull()

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).isEqualTo("/swarm/connect?arg=valid-address")
    }

    @Test
    fun testSwarmConnectWithError() {
        // setup
        server.enqueue(
            MockResponse().setBody("""{"Message":"error message","Code":0,"Type":"error"}""")
                .setResponseCode(500)
        )

        // invoke
        val connected = ipfs.swarm.connect("invalid-address")

        // assert
        assertThat(connected).isNotNull()
        assertThat(connected).isEqualTo(StringsResult.Failure("error message"))

        assertThat(ipfs.lastError).isNotNull()
        assertThat(ipfs.lastError!!.Message).isEqualTo("error message")
        assertThat(ipfs.lastError!!.Code).isEqualTo(0)

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).isEqualTo("/swarm/connect?arg=invalid-address")
    }

    @Test
    fun testSwarmPeers() {
        // setup
        server.enqueue(
            MockResponse().setBody(
                """{
                    "Peers": [
                        {
                          "Addr": "/ip4/104.156.227.102/tcp/4001",
                          "Peer": "12D3KooWBVFPutgEkmUAaox5TywUPy2NtaG3cpkXd3BvxgUc77Pk",
                          "Identify": {
                            "ID": "",
                            "PublicKey": "",
                            "Addresses": null,
                            "AgentVersion": "",
                            "Protocols": null
                          }
                        },
                        {
                          "Addr": "/ip4/104.233.244.44/udp/4001/quic-v1",
                          "Peer": "12D3KooWDAmEuj1QjsW6mhXoivB3X4CPQcPDwLCtAmQKWvP7LjAM",
                          "Identify": {
                            "ID": "",
                            "PublicKey": "",
                            "Addresses": null,
                            "AgentVersion": "",
                            "Protocols": null
                          }
                        }
                      ]
                    }"""
            ).setResponseCode(200)
        )

        // invoke
        val swarmPeersResult = ipfs.swarm.peers()

        // assert
        assertThat(swarmPeersResult).isNotNull()
        assertThat(swarmPeersResult).isInstanceOf(SwarmPeersResult.Success::class.java)
        assertThat((swarmPeersResult as SwarmPeersResult.Success).peers).isNotNull()
        assertThat((swarmPeersResult).peers.peers?.size).isEqualTo(2)
        assertThat((swarmPeersResult).peers.peers!![0].addr).isEqualTo("/ip4/104.156.227.102/tcp/4001")

        assertThat(ipfs.lastError).isNull()

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).isEqualTo("/swarm/peers")
    }

    @Test
    fun testSwarmAddrs() {
        // setup
        server.enqueue(
            MockResponse().setBody(
                """{
                    "Addrs": {
                        "key1": [
                          "value1",
                          "value2"
                        ],
                        "key2": [
                          "value3",
                          "value4"
                        ]
                      }
                    }"""
            ).setResponseCode(200)
        )

        // invoke
        val swarmAddrsResult = ipfs.swarm.addrs()

        // assert
        assertThat(swarmAddrsResult).isNotNull()
        assertThat(swarmAddrsResult).isInstanceOf(SwarmAddrsResult.Success::class.java)
        assertThat((swarmAddrsResult as SwarmAddrsResult.Success).addrsMap).isNotNull()
        assertThat((swarmAddrsResult).addrsMap.addrs["key1"]).size().isEqualTo(2)
        assertThat((swarmAddrsResult).addrsMap.addrs["key1"]).isEqualTo(listOf("value1", "value2"))
        assertThat((swarmAddrsResult).addrsMap.addrs["key2"]).size().isEqualTo(2)
        assertThat((swarmAddrsResult).addrsMap.addrs["key2"]).isEqualTo(listOf("value3", "value4"))

        assertThat(ipfs.lastError).isNull()

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).isEqualTo("/swarm/addrs")
    }
}