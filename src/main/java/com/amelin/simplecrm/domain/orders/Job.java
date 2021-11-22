package com.amelin.simplecrm.domain.orders;

import com.amelin.simplecrm.domain.BaseEntity;
import com.amelin.simplecrm.domain.orders.ordertypes.TailorOrder;
import com.amelin.simplecrm.domain.orders.statemachine.state.JobState;
import com.amelin.simplecrm.domain.users.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "jobs")
public class Job extends BaseEntity {

    @Column(name = "start_date")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "acceptance_date")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completionDate;

    @Column(name = "note")
    private String note;

    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "performer_id")
    private User performer;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "job_state")
    private JobState state;

    public Job() {}

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public JobState getState() {
        return state;
    }

    public void setState(JobState state) {
        this.state = state;
    }
}
