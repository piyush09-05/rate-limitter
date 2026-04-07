package com.example.rate_limitter.rate_limitter_poc.models;


import org.springframework.stereotype.Component;


public class WindowData {
    private int count;
    private long windowStartTime;

    public WindowData( long windowStartTime) {
        this.count = 1;
        this.windowStartTime = windowStartTime;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public void setWindowStartTime(long windowStartTime) {
        this.windowStartTime = windowStartTime;
    }
    public void incrementCount() {
        this.count++;
    }
    public int getCount() {
        return count;
    }
    public long getWindowStartTime() {
        return windowStartTime;
    }
    public void reset(long newWindowStartTime) {
        this.count = 0;
        this.windowStartTime = newWindowStartTime;
    }
}
