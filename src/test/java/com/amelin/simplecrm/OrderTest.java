package com.amelin.simplecrm;

import com.amelin.simplecrm.domain.order.Article;
import com.amelin.simplecrm.domain.order.Customer;
import com.amelin.simplecrm.domain.order.Order;
import com.amelin.simplecrm.repos.CustomerRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

    @Autowired
    private static CustomerRepo customerRepo;

    private Map<String, String> allRequestParams;

    @Before
    public void prepare() {
        allRequestParams = new HashMap<>();
        allRequestParams.put("acceptanceDate", "2020-07-23");
        allRequestParams.put("completionDate", "2020-07-28");
        allRequestParams.put("orderNumber", "58");
        allRequestParams.put("firstName", "Александр");
        allRequestParams.put("lastName","Иванов");
        allRequestParams.put("middleName", "Иванович");
        allRequestParams.put("phone", "88002000600");
        allRequestParams.put("name", "Куртка");
        allRequestParams.put("email", "petrov@mail.ru");
        allRequestParams.put("manufacturer", "PingPong");
        allRequestParams.put("country", "Китай");
        allRequestParams.put("defects", "Царапина");
        allRequestParams.put("wearDegree", "50");
    }

    @Test
    public void addOrder() {
        customerRepo.save(new Customer(allRequestParams));
    }

//    @Test
//    public void addOrderTest() {
//
//        Order.insert(allRequestParams);
//
//        Assert.assertNotNull(Order.find(58));
//    }

}
