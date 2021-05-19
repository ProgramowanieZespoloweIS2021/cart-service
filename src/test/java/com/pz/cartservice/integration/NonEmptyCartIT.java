package com.pz.cartservice.integration;

import com.jayway.jsonpath.JsonPath;
import com.pz.cartservice.CartServiceApplication;
import com.pz.cartservice.carts.domain.entity.Offer;
import com.pz.cartservice.carts.domain.repository.OfferRepository;
import com.pz.cartservice.utils.SampleOffersProvider;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = CartServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@Transactional
public class NonEmptyCartIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private OfferRepository offerRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void canCreateShoppingCartAndAddItemsToIt() throws Exception {

        // setup offer repository mock
        List<Offer> sampleOffers = new SampleOffersProvider().get();
        Mockito.when(offerRepository.get(1L)).thenReturn(Optional.of(sampleOffers.get(0)));
        Mockito.when(offerRepository.get(2L)).thenReturn(Optional.of(sampleOffers.get(1)));
        Mockito.when(offerRepository.get(3L)).thenReturn(Optional.of(sampleOffers.get(2)));

        // send request to create empty shopping cart and return its ID
        MvcResult createCartResult = mockMvc
                .perform(get("/carts"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // get ID of created cart from the response
        long createdCartId = Long.parseLong(createCartResult.getResponse().getContentAsString());

        // request body for adding offer with ID 1 to the cart
        String firstCartItemJson = """
                {
                    "offerId": 1,
                    "tierId": 2,
                    "description": "First cart item"
                }
                """;

        // request body for adding offer with ID 3 to the cart
        String secondCartItemJson = """
                {
                    "offerId": 3,
                    "tierId": 6,
                    "description": "Second cart item"
                }
                """;

        // add first item to the cart
        mockMvc
                .perform(post("/carts/" + createdCartId + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(firstCartItemJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // add second item to the cart
        mockMvc
                .perform(post("/carts/" + createdCartId + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(secondCartItemJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // compute expected cart parameters
        double expectedTotalPrice = sampleOffers.get(0).getTierById(2L).getPrice()
                .add(sampleOffers.get(2).getTierById(6L).getPrice()).doubleValue();
        int expectedNumberOfItems = 2;

        // check if the cart has been created and contains all added items
        mockMvc
                .perform(get("/carts/" + createdCartId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(createdCartId))
                .andExpect(jsonPath("$.totalPrice", is(closeTo(expectedTotalPrice, 0.01))))
                .andExpect(jsonPath("$.items", Matchers.hasSize(expectedNumberOfItems)))
                .andExpect(jsonPath("$.items[*].offerId", Matchers.containsInAnyOrder(1, 3)))
                .andReturn();
    }


    @Test
    public void canRemoveItemFromShoppingCart() throws Exception {

        // setup offer repository mock
        List<Offer> sampleOffers = new SampleOffersProvider().get();
        Mockito.when(offerRepository.get(1L)).thenReturn(Optional.of(sampleOffers.get(0)));
        Mockito.when(offerRepository.get(2L)).thenReturn(Optional.of(sampleOffers.get(1)));
        Mockito.when(offerRepository.get(3L)).thenReturn(Optional.of(sampleOffers.get(2)));

        // send request to create empty shopping cart and return its ID
        MvcResult createCartResult = mockMvc
                .perform(get("/carts"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // get ID of created cart from the response
        long createdCartId = Long.parseLong(createCartResult.getResponse().getContentAsString());

        // request body for adding offer with ID 2 to the cart
        String firstCartItemJson = """
                {
                    "offerId": 2,
                    "tierId": 4,
                    "description": "First cart item"
                }
                """;

        // request body for adding offer with ID 3 to the cart
        String secondCartItemJson = """
                {
                    "offerId": 3,
                    "tierId": 6,
                    "description": "Second cart item"
                }
                """;

        // add first item to the cart
        mockMvc
                .perform(post("/carts/" + createdCartId + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(firstCartItemJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // add second item to the cart
        mockMvc
                .perform(post("/carts/" + createdCartId + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(secondCartItemJson))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // check if the cart has been created and contains all added items
        MvcResult getCartWithItemsResult = mockMvc
                .perform(get("/carts/" + createdCartId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.items", Matchers.hasSize(2)))
                .andReturn();

        // extract one of the item IDs from the cart
        String getCartWithItemsResponse = getCartWithItemsResult.getResponse().getContentAsString();
        Long itemId = JsonPath.parse(getCartWithItemsResponse).read("$.items.[0].id", Long.class);

        // remove first item from the cart
        mockMvc
                .perform(delete("/carts/" + createdCartId + "/items/" + itemId))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        // check if cart contains only one item (the one that has not been removed)
        mockMvc
                .perform(get("/carts/" + createdCartId))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.items", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.items[*].id", Matchers.not(Matchers.containsInAnyOrder(itemId))))
                .andReturn();
    }


}
