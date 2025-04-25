package io.ipfs.kotlin

import io.ipfs.kotlin.commands.PinLsResultResult
import okhttp3.mockwebserver.MockResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TestPins : BaseIPFSWebserverTest() {

    @Test
    fun testAddPin() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Pins\":[\"QmPFDyWdL6yjz92jdc6bzWXHKVvydAhgTzhefSmmkDXzSZ\"]}"))

        // invoke
        val result = ipfs.pins.add("QmPFDyWdL6yjz92jdc6bzWXHKVvydAhgTzhefSmmkDXzSZ")

        // assert
        assertThat(result).isEqualTo(true)

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).startsWith("/pin/add/QmPFDyWdL6yjz92jdc6bzWXHKVvydAhgTzhefSmmkDXzSZ")

    }

    @Test
    fun testAddPinFail() {
        // setup
        server.enqueue(MockResponse().setBody("{\"Message\":\"pin: invalid ipfs ref path\",\"Code\":0}"))

        // invoke
        val result = ipfs.pins.add("foo")

        // assert
        assertThat(result).isEqualTo(false)

        val executedRequest = server.takeRequest();
        assertThat(executedRequest.path).startsWith("/pin/add/foo")

        assertThat(ipfs.lastError!!.Message).isEqualTo("pin: invalid ipfs ref path")

    }

    @Test
    fun testLsPin() {
        // setup
        server.enqueue(MockResponse().setBody("""{
  "PinLsList": {
    "Keys": {
      "<string>": {
        "Name": "<string>",
        "Type": "<string>"
      }
    }
  },
  "PinLsObject": {
    "Cid": "<string>",
    "Name": "<string>",
    "Type": "<string>"
  }
}

"""))

        // invoke
        val result = ipfs.pins.ls()

        // assert
        assertThat(result).isNotNull()
        assertThat(result).isInstanceOf(PinLsResultResult.Success::class.java)
        assertThat((result as PinLsResultResult.Success).pinLsResult).isNotNull()
        assertThat((result).pinLsResult.pinLsList?.keys?.keys?.size).isEqualTo(1)
//        assertThat((result).pinLsResult.pinLsList?.keys?.keys?.[0].name).isEqualTo("<string>")
 //       assertThat((result).pinLsResult.peers!![0].addr).isEqualTo("/ip4/104.156.227.102/tcp/4001")

        assertThat(ipfs.lastError).isNull()

        val executedRequest = server.takeRequest()
        assertThat(executedRequest.path).isEqualTo("/pin/ls")
    }
}