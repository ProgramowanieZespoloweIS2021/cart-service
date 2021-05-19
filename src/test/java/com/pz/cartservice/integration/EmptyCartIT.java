package com.pz.cartservice.integration;

import com.pz.cartservice.CartServiceApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        classes = CartServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@Transactional
public class EmptyCartIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void canCreateAndAccessEmptyShoppingCart() throws Exception {

        // send request to create empty shopping cart and return its ID
        MvcResult createCartResult = mockMvc
                .perform(get("/carts"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // get ID of created cart from the response
        long createdCartId = Long.parseLong(createCartResult.getResponse().getContentAsString());

        // check if the cart has been created and contains no items
        mockMvc
                .perform(get("/carts/" + createdCartId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(createdCartId))
                .andExpect(jsonPath("$.totalPrice").value(BigDecimal.ZERO))
                .andExpect(jsonPath("$.items").isEmpty())
                .andReturn();
    }

}
