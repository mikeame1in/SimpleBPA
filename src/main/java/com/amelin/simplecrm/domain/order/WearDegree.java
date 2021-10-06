package com.amelin.simplecrm.domain.order;

public enum WearDegree {
    SLIGHT(30),
    INTERMEDIATE(50),
    HIGH(75);

    private final Integer percent;
    WearDegree(Integer percent){
        this.percent = percent;
    }
}
