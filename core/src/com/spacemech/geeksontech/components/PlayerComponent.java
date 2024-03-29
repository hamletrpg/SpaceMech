package com.spacemech.geeksontech.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {
    public boolean isDead = false;
    public float shootDelay = 0.5f;
    public float timeSinceLastShot = 0f;
    public OrthographicCamera cam = null;

    @Override
    public void reset() {
        cam = null;
        isDead = false;
        shootDelay = 0.5f;
        timeSinceLastShot = 0f;
    }
}
