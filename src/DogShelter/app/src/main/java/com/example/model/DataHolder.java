package com.example.model;

public class DataHolder {
    private static final DataHolder ourInstance = new DataHolder();

    public static DataHolder getInstance() {
        return ourInstance;
    }

    private DataHolder() {
    }

    private DogDetailsInfo DogDetailsInfo;

    public DogDetailsInfo getDogDetailsInfo() {
        return DogDetailsInfo;
    }

    public void setmDogDetailsInfo(DogDetailsInfo DogDetailsInfo) {
        this.DogDetailsInfo = DogDetailsInfo;
    }
}
