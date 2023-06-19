package com.spacemech.geeksontech.controller;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

public class KeyboardController implements InputProcessor {
    public boolean left, right, up, down;
    public boolean isMouse1Down, isMouse2Down, isMouse3Down;
    public boolean isDragged;
    public Vector2 mouseLocation = new Vector2(0, 0);

    @Override
    public boolean keyDown(int keyCode) {
        boolean keyProcessed = false;
        switch (keyCode) {
            case Keys.LEFT:
                left = true;
                keyProcessed = true;
                break;
            case Keys.RIGHT:
                right = true;
                keyProcessed = true;
                break;
            case Keys.UP:
                up = true;
                keyProcessed = true;
                break;
            case Keys.DOWN:
                down = true;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyUp(int keyCode) {
        boolean keyProcessed = false;
        switch (keyCode) {
            case Keys.LEFT:
                left = false;
                keyProcessed = true;
                break;
            case Keys.RIGHT:
                right = false;
                keyProcessed = true;
                break;
            case Keys.UP:
                up = false;
                keyProcessed = true;
                break;
            case Keys.DOWN:
                down = false;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == 0){
            isMouse1Down = true;
        }else if(button == 1){
            isMouse2Down = true;
        }else if(button == 2){
            isMouse3Down = true;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragged = false;
        //System.out.println(button);
        if(button == 0){
            isMouse1Down = false;
        }else if(button == 1){
            isMouse2Down = false;
        }else if(button == 2){
            isMouse3Down = false;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        isDragged = true;
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
