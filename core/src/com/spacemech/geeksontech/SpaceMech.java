package com.spacemech.geeksontech;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.spacemech.geeksontech.views.MainGame;

public class SpaceMech extends Game {
	private MainGame mainGame;
	static final float PPM = 100;
	@Override
	public void create () {
		this.setScreen(new MainGame(this));
	}
	
	@Override
	public void dispose () {
	}
}
