package com.amelin.simplecrm.service;

import com.amelin.simplecrm.domain.Order;
import com.amelin.simplecrm.domain.orders.Job;
import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;
import com.amelin.simplecrm.domain.orders.statemachine.state.JobState;
import com.amelin.simplecrm.domain.orders.statemachine.state.OrderState;
import com.amelin.simplecrm.domain.payments.Payment;
import com.amelin.simplecrm.dao.repository.TailorOrderRepo;
import com.amelin.simplecrm.service.orders.impl.TailorOrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TailorOrderServiceTest{
    private static final Long TEST_ORDER_ID = new Long(1);

    @Autowired
    private TailorOrderService orderService;

    @MockBean
    private TailorOrderRepo orders;

    private TailorOrder order;

    @Before
    public void setup() {
        order = new TailorOrder();
//        order.setId(TEST_ORDER_ID);
//        order.setState(OrderState.NEW);
        order.setName("jacket");

        Mockito.doReturn(order).when(orders).getById(TEST_ORDER_ID);
        Mockito.doReturn(order).when(orders).save((TailorOrder) order);
    }

    @Test
    public void ShouldCreateNewOrder() {
        Order order = orderService.createOrder("tailor");
        assertTrue(order.isNew() & order.getClass() == TailorOrder.class);
    }

    @Test
    public void ShouldSaveNewOrder() {
        Order order = orderService.saveOrder(this.order);
        assertTrue(order.getState().equals(OrderState.NEW));
    }

    @Test
    public void AddNewJobToOrderTest() {
        Job job = new Job();
        job.setState(JobState.NEW);
        job.setNote("test");

        this.order.addJob(job);

        Order order = orderService.saveOrder(this.order);
        assertTrue(order.getState().equals(OrderState.NEW));
    }

    @Test
    public void ShouldDefineOrderStateHowInWork() {
        Job job1 = new Job();
        job1.setState(JobState.IN_WORK);
        job1.setNote("test1");

        Job job2 = new Job();
        job2.setState(JobState.NEW);
        job2.setNote("test2");

        this.order.addJob(job1);
        this.order.addJob(job2);

        Order order = orderService.saveOrder(this.order);
        assertTrue(order.getState().equals(OrderState.IN_WORK));
    }

    @Test
    public void ShouldDefineOrderStateHowWait() {
        Job job1 = new Job();
        job1.setState(JobState.WAIT);
        job1.setNote("test1");

        Job job2 = new Job();
        job2.setState(JobState.NEW);
        job2.setNote("test2");

        Job job3 = new Job();
        job3.setState(JobState.IN_WORK);
        job3.setNote("test3");

        this.order.addJob(job1);
        this.order.addJob(job2);
        this.order.addJob(job3);

        Order order = orderService.saveOrder(this.order);
        assertTrue(order.getState().equals(OrderState.WAIT));
    }

    @Test
    public void ShouldDefineOrderStateHowWaitPayment() {
        Job job1 = new Job();
        job1.setState(JobState.READY);
        job1.setNote("test1");
        job1.setPrice(2000);

        Job job2 = new Job();
        job2.setState(JobState.READY);
        job2.setNote("test2");
        job2.setPrice(3000);

        this.order.addJob(job1);
        this.order.addJob(job2);

        Order order = orderService.saveOrder(this.order);
        assertTrue(order.getState().equals(OrderState.WAIT_PAYMENT));
    }

    @Test
    public void ShouldDefineOrderStateHowFinish() {
        TailorOrderWrapper wrapper = new TailorOrderWrapper();

        Mockito.doReturn(wrapper).when(orders).save((TailorOrderWrapper) wrapper);

        Job job1 = new Job();
        job1.setState(JobState.READY);
        job1.setNote("test1");
        job1.setPrice(2000);

        Job job2 = new Job();
        job2.setState(JobState.READY);
        job2.setNote("test2");
        job2.setPrice(3000);

        wrapper.addJob(job1);
        wrapper.addJob(job2);

        Payment payment = new Payment();
        payment.setSum(5000);

        wrapper.addPayment(payment);

        Order order = orderService.saveOrder(wrapper);
        assertTrue(order.getState().equals(OrderState.FINISH));
    }
}
