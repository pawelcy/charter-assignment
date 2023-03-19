package com.charter.purchase;

import com.charter.customer.Customer;
import com.charter.customer.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
class PurchaseControllerEndToEndTest {

  @Autowired private CustomerRepository customerRepository;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private PurchaseRepository purchaseRepository;

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Get Page Should Succeed with 200")
  @Test
  void givenGetPage_shouldSucceedWith200() throws Exception {
    var customer =
        new Customer()
            .setAge(53)
            .setEmailAddress("walter.white@example.com")
            .setFirstName("Walter")
            .setLastName("White");

    this.customerRepository.save(customer);

    var purchase =
        new Purchase()
            .setAmount(new BigDecimal(120))
            .setDateTime(LocalDateTime.of(2023, 3, 17, 15, 31, 3, 0))
            .setDescription("Some purchase.");

    customer.addPurchase(purchase);

    this.purchaseRepository.save(purchase);

    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/api/v1/purchase").accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].amount").value(120))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].customerId").value(1L))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].dateTime").value("2023-03-17T15:31:03"))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].description").value("Some purchase."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1L));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Get Page Should Succeed with 200")
  @Test
  void givenGetPageFilterByCustomerId_shouldSucceedWith200() throws Exception {
    var customer =
        new Customer()
            .setAge(53)
            .setEmailAddress("walter.white@example.com")
            .setFirstName("Walter")
            .setLastName("White");

    this.customerRepository.save(customer);

    var customer2 =
        new Customer()
            .setAge(20)
            .setEmailAddress("jesse.pinkman@example.com")
            .setFirstName("Jesse")
            .setLastName("Pinkman");

    this.customerRepository.save(customer2);

    var purchase =
        new Purchase()
            .setAmount(new BigDecimal(120))
            .setDateTime(LocalDateTime.of(2023, 3, 17, 15, 31, 3, 0))
            .setDescription("Some purchase.");

    customer.addPurchase(purchase);

    this.purchaseRepository.save(purchase);

    var purchase2 =
        new Purchase()
            .setAmount(new BigDecimal(100))
            .setDateTime(LocalDateTime.of(2023, 2, 17, 14, 21, 2, 0))
            .setDescription("Additional purchase.");

    customer2.addPurchase(purchase2);

    this.purchaseRepository.save(purchase2);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v1/purchase")
                .accept(MediaType.APPLICATION_JSON)
                .queryParam(
                    "customerId",
                    List.of(1L).parallelStream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "))))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].amount").value(120))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].customerId").value(1L))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].dateTime").value("2023-03-17T15:31:03"))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.content[0].description").value("Some purchase."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1L));
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Add Should Succeed with 201")
  @Test
  void givenAdd_shouldSucceedWith201() throws Exception {
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
            .put("amount", 120)
            .put("customerId", 1L)
            .put("dateTime", "2023-03-17T15:31:03")
            .put("description", "Some purchase.");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/purchase")
                .accept(MediaType.APPLICATION_JSON)
                .content(body.toPrettyString())
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(120))
        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").value("2023-03-17T15:31:03"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Some purchase."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));

    var purchaseList = this.purchaseRepository.findAll();

    Assertions.assertEquals(1, purchaseList.size());

    var purchase = purchaseList.get(0);

    Assertions.assertEquals(BigDecimal.valueOf(120), purchase.getAmount());
    Assertions.assertEquals(1L, purchase.getCustomer().getId());
    Assertions.assertEquals(LocalDateTime.of(2023, 3, 17, 15, 31, 3, 0), purchase.getDateTime());
    Assertions.assertEquals("Some purchase.", purchase.getDescription());
    Assertions.assertEquals(1L, purchase.getId());
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Add Should Failed with 404")
  @Test
  void givenAdd_shouldFailedWith404() throws Exception {
    var body =
        this.objectMapper
            .createObjectNode()
            .put("amount", 120)
            .put("customerId", 1L)
            .put("dateTime", "2023-03-17T15:31:03")
            .put("description", "Some purchase.");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/purchase")
                .accept(MediaType.APPLICATION_JSON)
                .content(body.toPrettyString())
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Update Should Succeed with 200")
  @Test
  void givenUpdate_shouldSucceedWith200ForExistingCustomer() throws Exception {
    var customer =
        new Customer()
            .setAge(53)
            .setEmailAddress("walter.white@example.com")
            .setFirstName("Walter")
            .setLastName("White");

    this.customerRepository.save(customer);

    var purchase =
        new Purchase()
            .setAmount(new BigDecimal(120))
            .setDateTime(LocalDateTime.of(2023, 3, 17, 15, 31, 3, 0))
            .setDescription("Some purchase.");

    customer.addPurchase(purchase);

    this.purchaseRepository.save(purchase);

    var body =
        this.objectMapper
            .createObjectNode()
            .put("amount", 90)
            .put("customerId", 1L)
            .put("dateTime", "2023-02-16T14:21:02")
            .put("description", "Additional purchase.");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/v1/purchase/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .content(body.toPrettyString())
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(90))
        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").value("2023-02-16T14:21:02"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Additional purchase."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));

    var foundPurchase = this.purchaseRepository.findByIdWithCustomer(1L).orElse(null);

    Assertions.assertNotNull(foundPurchase);
    Assertions.assertEquals(BigDecimal.valueOf(90), foundPurchase.getAmount());
    Assertions.assertEquals(1L, foundPurchase.getCustomer().getId());
    Assertions.assertEquals(
        LocalDateTime.of(2023, 2, 16, 14, 21, 2, 0), foundPurchase.getDateTime());
    Assertions.assertEquals("Additional purchase.", foundPurchase.getDescription());
    Assertions.assertEquals(1L, foundPurchase.getId());
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Update Should Succeed with 200 For New Customer")
  @Test
  void givenUpdate_shouldSucceedWith200ForNewCustomer() throws Exception {
    var customer =
        new Customer()
            .setAge(53)
            .setEmailAddress("walter.white@example.com")
            .setFirstName("Walter")
            .setLastName("White");

    this.customerRepository.save(customer);

    var customer2 =
        new Customer()
            .setAge(20)
            .setEmailAddress("jesse.pinkman@example.com")
            .setFirstName("Jesse")
            .setLastName("Pinkman");

    this.customerRepository.save(customer2);

    var purchase =
        new Purchase()
            .setAmount(new BigDecimal(120))
            .setDateTime(LocalDateTime.of(2023, 3, 17, 15, 31, 3, 0))
            .setDescription("Some purchase.");

    customer.addPurchase(purchase);

    this.purchaseRepository.save(purchase);

    var body =
        this.objectMapper
            .createObjectNode()
            .put("amount", 90)
            .put("customerId", 2L)
            .put("dateTime", "2023-02-16T14:21:02")
            .put("description", "Additional purchase.");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/v1/purchase/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .content(body.toPrettyString())
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(90))
        .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(2L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.dateTime").value("2023-02-16T14:21:02"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Additional purchase."))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));

    var foundPurchase = this.purchaseRepository.findByIdWithCustomer(1L).orElse(null);

    Assertions.assertNotNull(foundPurchase);
    Assertions.assertEquals(BigDecimal.valueOf(90), foundPurchase.getAmount());
    Assertions.assertEquals(2L, foundPurchase.getCustomer().getId());
    Assertions.assertEquals(
        LocalDateTime.of(2023, 2, 16, 14, 21, 2, 0), foundPurchase.getDateTime());
    Assertions.assertEquals("Additional purchase.", foundPurchase.getDescription());
    Assertions.assertEquals(1L, foundPurchase.getId());
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Update Should Failed With 404 For New Customer")
  @Test
  void givenUpdate_shouldFailedWith404ForNewCustomer() throws Exception {
    var customer =
        new Customer()
            .setAge(53)
            .setEmailAddress("walter.white@example.com")
            .setFirstName("Walter")
            .setLastName("White");

    this.customerRepository.save(customer);

    var purchase =
        new Purchase()
            .setAmount(new BigDecimal(120))
            .setDateTime(LocalDateTime.of(2023, 3, 17, 15, 31, 3, 0))
            .setDescription("Some purchase.");

    customer.addPurchase(purchase);

    this.purchaseRepository.save(purchase);

    var body =
        this.objectMapper
            .createObjectNode()
            .put("amount", 90)
            .put("customerId", 2L)
            .put("dateTime", "2023-02-16T14:21:02")
            .put("description", "Additional purchase.");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/v1/purchase/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .content(body.toPrettyString())
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Update Should Failed With 404")
  @Test
  void givenUpdate_shouldFailedWith404() throws Exception {
    var body =
        this.objectMapper
            .createObjectNode()
            .put("amount", 90)
            .put("customerId", 1L)
            .put("dateTime", "2023-02-16T14:21:02")
            .put("description", "Additional purchase.");

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.put("/api/v1/purchase/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .content(body.toPrettyString())
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
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

    var purchase =
        new Purchase()
            .setAmount(new BigDecimal(120))
            .setDateTime(LocalDateTime.of(2023, 3, 17, 15, 31, 3, 0))
            .setDescription("Some purchase.");

    customer.addPurchase(purchase);

    this.purchaseRepository.save(purchase);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/api/v1/purchase/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Delete Should Failed with 404")
  @Test
  void givenDelete_shouldFailedWith404() throws Exception {
    var id = 1L;

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.delete("/api/v1/purchase/{id}", id)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
