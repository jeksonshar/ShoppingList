package com.jeksonshar.shoppinglist.repository;

import android.content.Context;

import com.jeksonshar.shoppinglist.repository.room.RoomRepository;

public class RepositoryProvider {

    private static Repository instance;

    private RepositoryProvider() {
    }

    public static Repository getInstance(Context context){
        if (instance == null) {
            instance = new RoomRepository(context);
        }
        return instance;
    }
}
