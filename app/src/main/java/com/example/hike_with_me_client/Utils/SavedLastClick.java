package com.example.hike_with_me_client.Utils;

import android.content.Context;

public class SavedLastClick {
    private static SavedLastClick instance = null;
    private int position = -1;
    private SavedLastClick() {
    }

    public static void initSavedLastClick() {
        if (instance == null) {
            instance = new SavedLastClick();
        }
    }

    public static SavedLastClick getInstance() {
        return instance;
    }

    public int getPosition() {
        return position;
    }

    public SavedLastClick setPosition(int position) {
        this.position = position;
        return this;
    }

}
