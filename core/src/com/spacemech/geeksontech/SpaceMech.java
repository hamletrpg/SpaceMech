package com.spacemech.geeksontech;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.spacemech.geeksontech.views.MainGame;

public class SpaceMech extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	private MainGame mainGame;
	static final float PPM = 100;
	@Override
	public void create () {
		mainGame = new MainGame(this);

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
