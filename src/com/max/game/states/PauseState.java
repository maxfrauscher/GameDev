package com.max.game.states;

import com.max.game.util.KeyHandler;
import com.max.game.util.MouseHandler;
import com.max.game.util.Syso;

import java.awt.Graphics2D;

public class PauseState extends GameState {
    public PauseState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void update() {
        Syso.write("PAUSE!");
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

    }

    @Override
    public void render(Graphics2D g) {

    }
}
