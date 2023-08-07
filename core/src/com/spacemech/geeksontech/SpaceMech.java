package com.spacemech.geeksontech;

import com.badlogic.gdx.Game;
import com.spacemech.geeksontech.views.MainGame;

public class SpaceMech extends Game {
	@Override
	public void create () {
		this.setScreen(new MainGame(this));
	}

	@Override
	public void dispose () {
	}
}