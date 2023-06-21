package com.spacemech.geeksontech.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class StateComponent implements Component, Pool.Poolable {
    public static final int STATE_NORMAL = 0;
    public static final int STATE_MOVING = 0;
    public static final int STATE_HIT = 0;

    private int state = 0;
    private float time = 0.0f;
    private boolean isLooping = true;

    public void set(int newState) {
        state = newState;
        time = 0.0f;
    }

    public int get() {
        return state;
    }

    @Override
    public void reset() {
        state = 0;
        time = 0.0f;
        isLooping = true;
    }
}
