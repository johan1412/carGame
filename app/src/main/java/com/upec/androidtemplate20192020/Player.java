package com.upec.androidtemplate20192020;

import android.graphics.Rect;

public class Player {
    private Rect player;

    public Player(Rect rectangle) {
        this.player = rectangle;
    }

    public Rect getPlayer() {
        return player;
    }
}
