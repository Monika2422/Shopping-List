package com.monika.shoppinglist.content;

/**
 * Created by Mónika on 2016. 11. 10..
 */

public class Object {
    public enum Status {
        active,
        archived
    }

    private String mId;
    private String mName;
    private int mQuant;
    private Status mStatus = Status.active;
    private long mUpdated;

    public Object() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getQuant(){
        return mQuant;
    }

    public void setQuant(int quant){
        mQuant = quant;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public long getUpdated() {
        return mUpdated;
    }

    public void setUpdated(long updated) {
        mUpdated = updated;
    }
}
