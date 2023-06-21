package com.spacemech.geeksontech.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class TypeComponent implements Component, Pool.Poolable {
    public static final int PLAYER = 0;
    public static final int ENEMY = 1;
    public static final int BULLET = 2;
    public static final int OTHER = 3;
    public int type = OTHER;

    @Override
    public void reset() {
        type = OTHER;
    }
}
