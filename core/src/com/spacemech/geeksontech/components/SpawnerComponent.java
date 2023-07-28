package com.spacemech.geeksontech.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class SpawnerComponent implements Component, Pool.Poolable {
    public float x = 0;
    public float y = 0;
    public float xVelocity;
    public float yVelocity;



    @Override
    public void reset() {
        x = 0;
        y = 0;
        xVelocity = 0;
        yVelocity = 0;
    }
}
