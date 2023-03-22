package com.justmambo

import com.justmambo.plugins.Item
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

/**
 * @project com.justmambo.bamboo
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sun Mar 2023
 */
class OrderRouteTest {

    @Test
    fun `given orders route, it should return, an empty list`() = testApplication {
        val client = createClient {
            install(ContentNegotiation) { json() }
        }
        client.get("orders").apply {
            val responseBody = bodyAsText()
            val list = Json.decodeFromString<List<Item>>(responseBody)
            assertEquals(expected = HttpStatusCode.OK, actual = status)
            assertEquals(expected = emptyList(), actual = list)
        }
    }

    @Test
    fun `given orders route, when user creates an item, it saves successfully`() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.post("orders") {
            contentType(ContentType.Application.Json) // accept json
            setBody(Item("burger", 1)) // this is the json
        }
        assertEquals(expected = HttpStatusCode.OK, response.status)
        assertEquals(expected = "Saved Successfully", response.bodyAsText())
    }

    @Test
    fun `given orders route, when user creates an item, it exists in the list`() = testApplication {
        val item = Item("burger", 1)
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        client.post("orders") {
            contentType(ContentType.Application.Json) // accept json
            setBody(item) // this is the json
        }
        client.get("orders").apply {
            val response = bodyAsText() // here is the reponse as string
            val items: List<Item> = Json.decodeFromString(response) // encode to list of Items
            assertContains(items, item) // am checking if my item exists in the items
        }

    }

}