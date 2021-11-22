package com.amelin.simplecrm.dto;

import com.amelin.simplecrm.domain.orders.statemachine.event.JobEvent;

public class JobEventDto {
    private JobEvent currentEvent;

    public JobEventDto() {
    }

    public JobEventDto(JobEvent currentEvent) {
        this.currentEvent = currentEvent;
    }

    public JobEvent getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(JobEvent currentEvent) {
        this.currentEvent = currentEvent;
    }
}
