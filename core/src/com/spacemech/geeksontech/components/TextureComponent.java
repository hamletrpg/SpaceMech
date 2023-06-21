package com.spacemech.geeksontech.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;

public class TextureComponent implements Component, Pool.Poolable {
    public TextureRegion region = null;
    public float offsetX = 0;
    public float offsetY = 0;

    @Override
    public void reset() {
        region = null;
    }
}
