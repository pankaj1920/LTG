package com.svgptechnologies.ltg.Json.DriverJson.CallClickCount;

public class CallClickCountData {

    private String status;
    private String did;
    private int count;

    public CallClickCountData(String status, String did, int count) {
        this.status = status;
        this.did = did;
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
