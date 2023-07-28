package com.spacemech.geeksontech.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

public class EnemyComponent implements Component, Pool.Poolable {
    public static enum Type {
        ENEMYSHIP,
        ALIEN
    }
    public boolean isDead = false;
    public float shootDelay = 0.5f;
    public float timeSinceLastShot = 0f;
    public float xPostCenter = -1;
    public float xVel = 0;
    public float yVel = 0;
    public Type enemyType = Type.ENEMYSHIP;

    @Override
    public void reset() {
        isDead = false;
        shootDelay = 0.5f;
        timeSinceLastShot = 0f;
    }
}
