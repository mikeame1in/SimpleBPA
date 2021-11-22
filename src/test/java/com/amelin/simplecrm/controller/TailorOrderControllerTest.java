package com.amelin.simplecrm.controller;

import com.amelin.simplecrm.dao.repository.UserRepo;
import com.amelin.simplecrm.domain.orders.Customer;
import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;
import com.amelin.simplecrm.dao.repository.CustomerRepo;
import com.amelin.simplecrm.dao.repository.TailorOrderRepo;
import com.amelin.simplecrm.domain.orders.ordertypes.WearDegree;
import com.amelin.simplecrm.service.orders.impl.TailorOrderService;
import com.amelin.simplecrm.service.users.UserServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-order-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-order-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails(value = "dru")
public class TailorOrderControllerTest {

    private static final Long TEST_CUSTOMER_ID = new Long(1);
    private static final Long TEST_ORDER_ID = new Long(1);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TailorOrderController controller;

    @Test
    public void testInitNewOrderForm() throws Exception {
        mockMvc.perform(get("/customers/{customerId}/tailorOrders/new", TEST_CUSTOMER_ID))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("orders/createOrUpdateOrderForm"))
                .andExpect(model().attributeExists("order"))
                .andExpect(xpath("//form[@action = '/customers/1/tailorOrders/new']").exists());
    }

    @Test
    public void testProcessNewOrderFormSuccess() throws Exception {
        mockMvc.perform(post("/customers/{customerId}/tailorOrders/new", TEST_CUSTOMER_ID)
                        .param("name", "jacket")
                        .param("country", "China")
                        .param("manufacturer", "PingPong")
                        .param("defects", "none"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testProcessNewOrderFormHasErrors() throws Exception {
        mockMvc.perform(post("/customers/{customerId}/tailorOrders/new", TEST_CUSTOMER_ID)
                        .param("name", "Betty")
                        .param("acceptanceDate", "2015-02-12"))

//                .andExpect(model().attributeHasNoErrors("owner"))
//                .andExpect(model().attributeHasErrors("pet"))
                .andExpect(model().attributeHasFieldErrors("order", "acceptanceDate"))
//                .andExpect(model().attributeHasFieldErrorCode("pet", "type", "required"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders/createOrUpdateOrderForm"));
    }

    @Test
    public void testInitUpdateForm() throws Exception {
        mockMvc.perform(get("/customers/{customerId}/tailorOrders/{orderId}/edit", TEST_CUSTOMER_ID, TEST_ORDER_ID))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andExpect(view().name("orders/createOrUpdateOrderForm"))
                .andExpect(model().attributeExists("order"))
                .andExpect(xpath("//form[@action = '/customers/1/tailorOrders/1/edit']").exists());
    }
}
