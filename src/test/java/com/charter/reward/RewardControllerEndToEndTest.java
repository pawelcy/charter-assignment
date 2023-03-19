package com.charter.reward;

import com.charter.customer.Customer;
import com.charter.customer.CustomerRepository;
import com.charter.purchase.Purchase;
import com.charter.purchase.PurchaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
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
class RewardControllerEndToEndTest {

  @Autowired private CustomerRepository customerRepository;

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private PurchaseRepository purchaseRepository;

  @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
  @DisplayName("Get List Should Succeed with 200")
  @Test
  void givenGetList_shouldSucceedWith200() throws Exception {
    var customer =
        new Customer()
            .setAge(53)
            .setEmailAddress("walter.white@example.com")
            .setFirstName("Walter")
            .setLastName("White");

    this.customerRepository.save(customer);

    var now = LocalDateTime.now();

    var purchase =
        new Purchase()
            .setAmount(new BigDecimal(120))
            .setDateTime(now)
            .setDescription("Some purchase.");

    customer.addPurchase(purchase);

    this.purchaseRepository.save(purchase);

    var purchase2 =
        new Purchase()
            .setAmount(new BigDecimal(120))
            .setDateTime(now)
            .setDescription("Some purchase.");

    customer.addPurchase(purchase2);

    this.purchaseRepository.save(purchase2);

    var nowMinusOneMonth = now.minusMonths(1);

    var purchase3 =
        new Purchase()
            .setAmount(new BigDecimal(100))
            .setDateTime(nowMinusOneMonth)
            .setDescription("Some purchase.");

    customer.addPurchase(purchase3);

    this.purchaseRepository.save(purchase3);

    var nowMinusTwoMonth = now.minusMonths(2);

    var purchase4 =
        new Purchase()
            .setAmount(new BigDecimal(50))
            .setDateTime(nowMinusTwoMonth)
            .setDescription("Some purchase.");

    customer.addPurchase(purchase4);

    this.purchaseRepository.save(purchase4);

    var nowMinusThreeMonth = now.minusMonths(3);

    var purchase5 =
        new Purchase()
            .setAmount(new BigDecimal(120))
            .setDateTime(nowMinusThreeMonth)
            .setDescription("Some purchase.");

    customer.addPurchase(purchase5);

    this.purchaseRepository.save(purchase5);

    this.mockMvc
        .perform(MockMvcRequestBuilders.get("/api/v1/reward").accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.content()
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].customerId").value(1L))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].rewardMonths.length()").value(3))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.[0].rewardMonths[0].month")
                .value(nowMinusTwoMonth.getMonthValue()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].rewardMonths[0].points").value(0))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.[0].rewardMonths[0].year")
                .value(nowMinusTwoMonth.getYear()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.[0].rewardMonths[1].month")
                .value(nowMinusOneMonth.getMonthValue()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].rewardMonths[1].points").value(50))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.[0].rewardMonths[1].year")
                .value(nowMinusOneMonth.getYear()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.[0].rewardMonths[2].month")
                .value(now.getMonthValue()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].rewardMonths[2].points").value(180))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.[0].rewardMonths[2].year").value(now.getYear()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].totalPoints").value(230));
  }
}
