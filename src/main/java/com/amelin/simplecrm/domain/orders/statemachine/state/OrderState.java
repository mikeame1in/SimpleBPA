package com.amelin.simplecrm.domain.orders.statemachine.state;

public enum OrderState {
    NEW(4), IN_WORK(2), WAIT(1), WAIT_PAYMENT(3), FINISH;

    private int priority;

    OrderState() {

    }

    OrderState(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public static OrderState findState(int value) {
        OrderState[] states = {NEW, IN_WORK, WAIT, WAIT_PAYMENT};
        OrderState result = NEW;

        for (OrderState state: states) {
            int priority = state.getPriority();
            if (priority == value) {
                result = state;
                break;
            }
        }

        return result;
    }
}
