package com.spacemech.geeksontech.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {
    public boolean isDead = false;
    public float shootDelay = 0.5f;
    public float timeSinceLastShot = 0f;

    @Override
    public void reset() {
        isDead = false;
        shootDelay = 0.5f;
        timeSinceLastShot = 0f;
    }
}
