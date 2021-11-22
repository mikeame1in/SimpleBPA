package com.amelin.simplecrm.domain.orders.statemachine.state;

//temporary
public enum JobState {
    NEW(4), IN_WORK(2), WAIT(1), READY(3);

    private final int priority;

    JobState(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }

    public JobState findState(int value) {
        JobState[] states = {NEW, IN_WORK, WAIT, READY};
        JobState result = NEW;

        for (JobState state: states) {
            int priority = state.getPriority();
            if (priority == value) {
                result = state;
                break;
            }
        }

        return result;
    }
}
