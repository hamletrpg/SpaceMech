package com.spacemech.geeksontech.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class HealthComponent implements Component, Pool.Poolable {
    public int health = 100;

    @Override
    public void reset() {
        health = 100;
    }

}
