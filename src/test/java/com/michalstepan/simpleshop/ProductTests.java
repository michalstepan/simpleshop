package com.michalstepan.simpleshop;

import com.michalstepan.simpleshop.domain.Product;
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

import static com.michalstepan.simpleshop.TestUtils.asJsonString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ProductTests {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();
    }

    @Test
    public void productGetTest() throws Exception {
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andDo(document("get-products"));
    }

    @Test
    public void productCreate() throws Exception {
        Product jack = new Product();
        jack.setName("jack");
        jack.setPrice(BigDecimal.valueOf(5));

        mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(jack))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andDo(document("create-product"));
    }

    @Test
    public void productUpdate() throws Exception {
        Product mug = new Product();
        mug.setName("mug");
        mug.setPrice(BigDecimal.valueOf(2));

        MockHttpServletResponse response = mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(mug))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(status().isOk())
                .andReturn().getResponse();

        String returnedId = response.getContentAsString();
        mug.setId(Long.valueOf(returnedId));

        mug.setPrice(BigDecimal.valueOf(3));
        mockMvc.perform(
                put("/products/" + mug.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(mug))
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(document("update-product"));
    }
}
