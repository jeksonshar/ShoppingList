package com.jeksonshar.shoppinglist.repository;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseRepository implements Repository{


    private final Set<Listener> mListeners = new HashSet<>();

    @Override
    public void addListener(Listener listener) {
        mListeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        mListeners.remove(listener);
    }

    protected final void notifyListeners() {
        for (Listener listener : mListeners) {
            listener.onDataChanged();
        }
    }
}
