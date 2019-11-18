package com.michalstepan.simpleshop.api;

import com.michalstepan.simpleshop.domain.Product;
import com.michalstepan.simpleshop.domain.dto.PlaceOrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static com.michalstepan.simpleshop.TestUtils.asJsonString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class OrderApiTests {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    public void placeOrderTest() throws Exception {
        final long firstProductId = createAndTestProductAdd("adidas jacket", 560);
        final long secondProductId = createAndTestProductAdd("nike shoes", 600);
        final long thirdProductId = createAndTestProductAdd("rebook laces", 60);

        final PlaceOrderDTO order = new PlaceOrderDTO();
        order.setBuyerEmail("michalstepan92@gmail.com");
        order.setProducts(List.of(firstProductId, secondProductId, thirdProductId));

        mockMvc.perform(
                post("/orders/place")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(order))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andDo(document("place-order"));
    }

    private long createAndTestProductAdd(String productName, long productPrice) throws Exception {
        Product toCreate = new Product();
        toCreate.setName(productName);
        toCreate.setPrice(BigDecimal.valueOf(productPrice));
        MockHttpServletResponse response = mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(toCreate))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        return Long.parseLong(response.getContentAsString());
    }
}
