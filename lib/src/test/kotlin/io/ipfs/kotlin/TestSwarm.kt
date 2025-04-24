package io.ipfs.kotlin

import io.ipfs.kotlin.commands.StringsResult
import io.ipfs.kotlin.commands.SwarmPeersResult
import io.ipfs.kotlin.model.Strings
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
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
                """{"Peers":[{"Addr":"/ip4/104.156.227.102/tcp/4001","Peer":"12D3KooWBVFPutgEkmUAaox5TywUPy2NtaG3cpkXd3BvxgUc77Pk","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/104.233.244.44/udp/4001/quic-v1","Peer":"12D3KooWDAmEuj1QjsW6mhXoivB3X4CPQcPDwLCtAmQKWvP7LjAM","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/13.215.125.107/tcp/4001","Peer":"12D3KooWKzTX3KKzmSymGX9Xh4ef8vcJLywSFbmFioswUe2WrSRD","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/142.114.198.140/udp/4001/quic-v1","Peer":"12D3KooWJQ2f4DpZrYhtG3DkjyxHDrMMVs1sxWxG1TdTFBSuiBrY","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/143.198.149.42/tcp/4001","Peer":"12D3KooWLB6zRFsqqgv5rc37A6ohVEPWsZNQGLMBj4Wp7c9umYJ9","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/146.120.156.85/udp/14039/quic-v1","Peer":"12D3KooWR6DVUYb79AMgGybRfKTh5QZWefdHDpABmciUDfkvG1tG","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/158.69.108.65/udp/4001/quic-v1","Peer":"12D3KooWT22nBrDP2o6TjJd988hL95AqE8FFBnv1WwRuNNg8qXFT","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/172.104.215.180/udp/4001/quic-v1","Peer":"12D3KooWCdRL6LBSgLkXwXgFG6SbagtEFnop6v9Ho5veumQGakpS","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/176.209.4.140/udp/62805/quic-v1","Peer":"12D3KooWByJvNpSNpUgFexGkqDqTwwRdTY2mbaBsvWz3pTFKkGNY","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/178.62.113.226/tcp/4001","Peer":"QmZkiKCMYPmjKqub2hfpLqey1z1YJcVAHqtywNh5KqTd2n","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/183.134.100.230/tcp/4006","Peer":"12D3KooWP4uVJangJ69eYNPfCqZoxEBt77gtJ6UjwAnUo1LvQ7Tb","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/185.239.209.174/udp/4001/quic-v1","Peer":"12D3KooWJcSTv2bccaTHzsBF4upxc1HT3BmnfcR1AsFbR9DA8adm","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/188.240.228.37/udp/4001/quic-v1","Peer":"12D3KooWN3V3H2AjiPLGzs1H6CGAMrZHbmz5AwxfQjh18d7MHPm7","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/193.218.118.47/tcp/4001","Peer":"12D3KooWT3HEW4uoAVjUmYgTuU4Y5N4tdZWMgU3143bR5P3f4oqZ","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/195.62.52.200/udp/4001/quic-v1","Peer":"12D3KooWFDKszYmHoqyMMYjcwANHXLrFS7GmbHoUHjYEA1PqTsQ4","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/207.148.5.75/tcp/4001","Peer":"12D3KooWP6EMMwi6ed2k9J9R7iwzX1EWUeDY9VL4oNtuawnZQ662","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/207.180.210.63/tcp/4001","Peer":"QmeyUVzXYGDAxe8sMpaEaMq7boyfKotNMuyJSDLVfkSesL","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/208.83.239.32/udp/4001/quic-v1","Peer":"12D3KooWQfokXpiwCsMjCNjSAKZG9hfXtgjCTiUsSJUHxqeNsvzS","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/211.230.145.250/tcp/50571","Peer":"QmXh7fUNGgFzkUnqPnyAsyN51MVTqTLHYjVHq4ehXDhdqJ","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/217.76.56.163/udp/4001/quic-v1","Peer":"12D3KooWDcKMQ98n7cbccAjnWvLjWMBqDvnCxWSzkcVGMG29RnQM","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/38.242.223.109/tcp/4001","Peer":"12D3KooWPStMooRRDVcfZ2vJuFNwEtRWgLCKQdTGTbh8KQMKZZ8C","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/38.46.222.26/tcp/4001","Peer":"12D3KooWNDjkcNazoxoHwFCySPdBPTTdQDeFig29MHJVxCLesX6w","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/44.201.72.17/tcp/4001","Peer":"12D3KooWKmM7hCw3AbqP71RXR3Z2LZWyuAo1LxR3QmPWT3kWGgoq","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/54.176.40.224/tcp/4001","Peer":"12D3KooWA1memkwC8w5skF1n4wB8PTR1F6B2BLgja2UWdL9aLtNm","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/65.108.201.136/udp/4001/quic-v1","Peer":"12D3KooWCmCEFhcTKuoQM3U7WxhYphiKvNy5L9dcrfJk7N63giPx","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/78.31.71.211/udp/4001/quic-v1","Peer":"12D3KooWSEDakTZcgnZZreJptvPDLtia8bqDkWr6C1VMZifocRaA","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/82.64.20.85/tcp/30011","Peer":"12D3KooWAtnHmtP1R6srpcT3h8KQioWMzF9cPKbLnhZYW8yLZrCX","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/89.58.11.155/udp/4002/quic-v1","Peer":"12D3KooWEo3C5d51brdKGdcppXSDEnGcB48RtBrP9Yew1cnesj8f","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}},{"Addr":"/ip4/92.204.255.110/udp/4001/quic-v1","Peer":"12D3KooWRw2cnU8QmqtB2abhbMc89rqRkn5a4MVi8Wu8WgVUDg3U","Identify":{"ID":"","PublicKey":"","Addresses":null,"AgentVersion":"","Protocols":null}}]}"""
            ).setResponseCode(200)
        )

        // invoke
        val swarmPeersResult = ipfs.swarm.peers()

        // assert
        assertThat(swarmPeersResult).isNotNull()
        assertThat(swarmPeersResult).isInstanceOf(SwarmPeersResult.Success::class.java)
        assertThat((swarmPeersResult as SwarmPeersResult.Success).peers).isNotNull()
        assertThat((swarmPeersResult).peers.peers!![0].addr).isEqualTo("/ip4/104.156.227.102/tcp/4001")

        assertThat(ipfs.lastError).isNull()

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).isEqualTo("/swarm/peers")
    }
}