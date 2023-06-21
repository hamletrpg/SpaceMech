package com.spacemech.geeksontech.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.physics.box2d.Body;

public class B2dBodyComponent implements Component, Pool.Poolable {
    public Body body;
    public boolean isDead = false;

    @Override
    public void reset() {
        body = null;
        isDead = false;
    }
}
