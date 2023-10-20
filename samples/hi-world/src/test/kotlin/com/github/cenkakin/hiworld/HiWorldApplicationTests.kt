package com.github.cenkakin.hiworld

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class HiWorldApplicationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Should return hi`() {
        mockMvc.get("/hi").andExpect {
            status { isOk() }
            content { json("""{ "message": "Hi?" }""") }
        }
    }
}
