package com.amelin.simplecrm.controller;

import com.amelin.simplecrm.domain.orders.Customer;
import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;
import com.amelin.simplecrm.domain.orders.ordertypes.WearDegree;
import com.amelin.simplecrm.dao.repository.CustomerRepo;
import com.amelin.simplecrm.dao.repository.TailorOrderRepo;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    private static final Long TEST_CUSTOMER_ID = new Long(1);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepo customers;

    @MockBean
    private TailorOrderRepo orders;

    private Customer mike;

    @BeforeEach
    void setup() {
        mike = new Customer();
        mike.setId(TEST_CUSTOMER_ID);
        mike.setFirstName("Mike");
        mike.setLastName("Jonson");
        mike.setMiddleName("Petrovich");
        mike.setPhone("899922233344");
        mike.setEmail("jons@mail.com");

        TailorOrder order = new TailorOrder();
        order.setAcceptanceDate(LocalDate.now());
        order.setName("Jacket");
        order.setCountry("China");
        order.setManufacturer("Ping Pong");
        order.setDefects("hole");

        WearDegree slight = new WearDegree();
        slight.setName("slight");

        order.setWearDegree(slight);

        mike.setOrders(new ArrayList<>(Collections.singleton(order)));

        given(this.customers.findByLastName(eq("Jonson"), any(Pageable.class)))
                .willReturn(new PageImpl<Customer>(Lists.newArrayList(mike)));

        given(this.customers.findAll(any(Pageable.class)))
                .willReturn(new PageImpl<Customer>(Lists.newArrayList(mike)));

        given(this.customers.findById(TEST_CUSTOMER_ID))
                .willReturn(java.util.Optional.ofNullable(mike));

//        given(this.orders.findByArticleId(jacket.getId()))
//                .willReturn(Collections.singletonList(order));
    }

    @Test
    void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/customers/new"))

                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customer"))
                .andExpect(view().name("customers/createOrUpdateCustomerForm"));
    }

    @Test
    void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post("/customers/new")
                        .param("firstName", "Joe")
                        .param("lastName", "Bloggs")
                        .param("middleName", "Mark")
                        .param("email", "joe@ya.ru")
                        .param("phone", "01316761638"))

                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testProcessCreationFormHasErrors() throws Exception {
        mockMvc.perform(post("/customers/new")
                        .param("firstName", "Joe")
                        .param("lastName", "Bloggs")
                        .param("email", "joe@ya.ru"))

                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("customer"))
                .andExpect(model().attributeHasFieldErrors("customer", "middleName"))
                .andExpect(model().attributeHasFieldErrors("customer", "phone"))
                .andExpect(view().name("customers/createOrUpdateCustomerForm"));
    }

    @Test
    void testInitFindForm() throws Exception {
        mockMvc.perform(get("/customers/find"))

                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customer"))
                .andExpect(view().name("customers/findCustomers"));
    }

    @Test
    void testProcessFindFormSuccess() throws Exception {
        Page<Customer> tasks = new PageImpl<Customer>(Lists.newArrayList(mike, new Customer()));
        Mockito.when(this.customers.findByLastName(anyString(), any(Pageable.class))).thenReturn(tasks);
        mockMvc.perform(get("/customers?page=1"))

                .andExpect(status().isOk())
                .andExpect(view().name("customers/customersList"));
    }

    @Test
    void testProcessFindFormByLastName() throws Exception {
        Page<Customer> tasks = new PageImpl<Customer>(Lists.newArrayList(mike));
        Mockito.when(this.customers.findByLastName(eq("Jonson"), any(Pageable.class))).thenReturn(tasks);
        mockMvc.perform(get("/customers?page=1")
                        .param("lastName", "Jonson"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/" + TEST_CUSTOMER_ID));
    }

    @Test
    void testProcessFindFormNoCustomersFound() throws Exception {
        Page<Customer> tasks = new PageImpl<Customer>(Lists.newArrayList());
        Mockito.when(this.customers.findByLastName(eq("Unknown Surname"), any(Pageable.class))).thenReturn(tasks);
        mockMvc.perform(get("/customers?page=1")
                        .param("lastName", "Unknown Surname"))

                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("customer", "lastName"))
                .andExpect(model().attributeHasFieldErrorCode("customer", "lastName", "notFound"))
                .andExpect(view().name("customers/findCustomers"));
    }

    @Test
    void testInitUpdateOwnerForm() throws Exception {
        mockMvc.perform(get("/customers/{customerId}/edit", TEST_CUSTOMER_ID))

                .andExpect(status().isOk())
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attribute("customer", hasProperty("lastName", is("Jonson"))))
                .andExpect(model().attribute("customer", hasProperty("firstName", is("Mike"))))
                .andExpect(model().attribute("customer", hasProperty("middleName", is("Petrovich"))))
                .andExpect(model().attribute("customer", hasProperty("email", is("jons@mail.com"))))
                .andExpect(model().attribute("customer", hasProperty("phone", is("899922233344"))))
                .andExpect(view().name("customers/createOrUpdateCustomerForm"));
    }

    @Test
    void testProcessUpdateOwnerFormSuccess() throws Exception {
        mockMvc.perform(post("/customers/{customerId}/edit", TEST_CUSTOMER_ID)
                        .param("firstName", "Joe")
                        .param("lastName", "Bloggs")
                        .param("middleName", "Mark")
                        .param("email", "joe@ya.ru")
                        .param("phone", "01316761638"))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/" + TEST_CUSTOMER_ID));
    }

    @Test
    void testProcessUpdateOwnerFormHasErrors() throws Exception {
        mockMvc.perform(post("/customers/{customerId}/edit", TEST_CUSTOMER_ID)
                        .param("firstName", "Joe")
                        .param("lastName", "Bloggs")
                        .param("email", "joe@ya.ru"))

                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("customer"))
                .andExpect(model().attributeHasFieldErrors("customer", "middleName"))
                .andExpect(model().attributeHasFieldErrors("customer", "phone"))
                .andExpect(view().name("customers/createOrUpdateCustomerForm"));
    }

    @Test
    void testShowOwner() throws Exception {
        mockMvc.perform(get("/customers/{customerId}", TEST_CUSTOMER_ID))

                .andExpect(status().isOk())
                .andExpect(model().attribute("customer", hasProperty("lastName", is("Jonson"))))
                .andExpect(model().attribute("customer", hasProperty("firstName", is("Mike"))))
                .andExpect(model().attribute("customer", hasProperty("middleName", is("Petrovich"))))
                .andExpect(model().attribute("customer", hasProperty("email", is("jons@mail.com"))))
                .andExpect(model().attribute("customer", hasProperty("phone", is("899922233344"))))
//                .andExpect(model().attribute("customer", hasProperty("articles", not(empty()))))
//                .andExpect(model().attribute("customer", hasProperty("articles", new BaseMatcher<List<Article>>() {
//                    @Override
//                    public boolean matches(Object item) {
//                        @SuppressWarnings("unchecked")
//                        List<Article> articles = (List<Article>) item;
//                        Article article = articles.get(0);
//                        if (article.getOrders().isEmpty()) {
//                            return false;
//                        }
//                        return true;
//                    }
//
//                    @Override
//                    public void describeTo(Description description) {
//                        description.appendText("Mike did not have any tailorOrders");
//                    }
//                })))
                .andExpect(view().name("customers/customerDetails"));
    }
}
