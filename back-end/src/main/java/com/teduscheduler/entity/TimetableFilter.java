package com.teduscheduler.entity;

public class TimetableFilter {
    private boolean allowConflict;
    private boolean emptyDay;
    private int emptyDayCount;

    public boolean isAllowConflict() {
        return allowConflict;
    }

    public void setAllowConflict(boolean allowConflict) {
        this.allowConflict = allowConflict;
    }

    public boolean isEmptyDay() {
        return emptyDay;
    }

    public void setEmptyDay(boolean emptyDay) {
        this.emptyDay = emptyDay;
    }

    public int getEmptyDayCount() {
        return emptyDayCount;
    }

    public void setEmptyDayCount(int emptyDayCount) {
        this.emptyDayCount = emptyDayCount;
    }
}
