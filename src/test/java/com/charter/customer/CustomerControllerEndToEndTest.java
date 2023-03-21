package com.charter.customer;

import com.charter.commons.exception.NotFoundRuntimeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("junit")
@AutoConfigureMockMvc
@SpringBootTest
class CustomerControllerEndToEndTest {

  @Autowired private CustomerRepository customerRepository;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Get Page Should Succeed With 200")
  @Test
  void givenGetPage_shouldSucceedWith200() throws Exception {
    var customer =
        new Customer()
            .setAge(53)
            .setEmailAddress("walter.white@example.com")
            .setFirstName("Walter")
            .setLastName("White");

    this.customerRepository.save(customer);

    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/api/v1/customer").accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].age").value(53))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].emailAddress")
                .value("walter.white@example.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].firstName").value("Walter"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].lastName").value("White"));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Add Should Succeed With 201")
  @Test
  void givenAdd_shouldSucceedWith201() throws Exception {
    var body =
        this.objectMapper
            .createObjectNode()
            .put("age", 53)
            .put("emailAddress", "walter.white@example.com")
            .put("firstName", "Walter")
            .put("lastName", "White");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .content(body.toPrettyString())
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(53))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.emailAddress").value("walter.white@example.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Walter"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("White"));

    var customerList = this.customerRepository.findAll();

    Assertions.assertEquals(1, customerList.size());

    var customer = customerList.get(0);

    Assertions.assertEquals(53, customer.getAge());
    Assertions.assertEquals("walter.white@example.com", customer.getEmailAddress());
    Assertions.assertEquals("Walter", customer.getFirstName());
    Assertions.assertEquals(1L, customer.getId());
    Assertions.assertEquals("White", customer.getLastName());
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Update Should Succeed With 200")
  @Test
  void givenUpdate_shouldSucceedWith200() throws Exception {
    var customer =
        new Customer()
            .setAge(53)
            .setEmailAddress("walter.white@example.com")
            .setFirstName("Walter")
            .setLastName("White");

    this.customerRepository.save(customer);

    var body =
        this.objectMapper
            .createObjectNode()
            .put("age", 20)
            .put("emailAddress", "jesse.pinkman@example.com")
            .put("firstName", "Jesse")
            .put("lastName", "Pinkman");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/v1/customer/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .content(body.toPrettyString())
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.emailAddress").value("jesse.pinkman@example.com"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Jesse"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Pinkman"));

    var foundCustomer = this.customerRepository.findById(1L).orElse(null);

    Assertions.assertNotNull(foundCustomer);
    Assertions.assertEquals(20, foundCustomer.getAge());
    Assertions.assertEquals("jesse.pinkman@example.com", foundCustomer.getEmailAddress());
    Assertions.assertEquals("Jesse", foundCustomer.getFirstName());
    Assertions.assertEquals(1L, foundCustomer.getId());
    Assertions.assertEquals("Pinkman", foundCustomer.getLastName());
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Update Should Failed With 404")
  @Test
  void givenUpdate_shouldFailedWith404() throws Exception {
    var id = 1L;

    var body =
        this.objectMapper
            .createObjectNode()
            .put("age", 20)
            .put("emailAddress", "jesse.pinkman@example.com")
            .put("firstName", " Jesse")
            .put("lastName", "Pinkman");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/v1/customer/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .content(body.toPrettyString())
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof NotFoundRuntimeException))
        .andExpect(
            result ->
                Assertions.assertEquals(
                    String.format("Customer with id: %d not found", id),
                    result.getResolvedException().getMessage()));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Delete Should Succeed with 204")
  @Test
  void givenDelete_shouldSucceedWith204() throws Exception {
    var customer =
        new Customer()
            .setAge(53)
            .setEmailAddress("walter.white@example.com")
            .setFirstName("Walter")
            .setLastName("White");

    this.customerRepository.save(customer);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/api/v1/customer/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Delete Should Failed With 404")
  @Test
  void givenDelete_shouldFailedWith404() throws Exception {
    var id = 1L;

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/api/v1/customer/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(
            result ->
                Assertions.assertTrue(
                    result.getResolvedException() instanceof NotFoundRuntimeException))
        .andExpect(
            result ->
                Assertions.assertEquals(
                    String.format("Customer with id: %d not found", id),
                    result.getResolvedException().getMessage()));
  }
}
