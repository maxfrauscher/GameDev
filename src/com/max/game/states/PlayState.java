package com.max.game.states;

import com.max.game.GamePanel;
import com.max.game.entity.Enemy;
import com.max.game.entity.Player;
import com.max.game.graphics.Font;
import com.max.game.graphics.Sprite;
import com.max.game.tiles.TileManager;
import com.max.game.util.*;

import java.awt.Graphics2D;

public class PlayState extends GameState {

    private Font font;
    private Player player;
    private Enemy enemy;
    private TileManager tm;
    private Camera cam;

    public static Vector2f map;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        map = new Vector2f();
        Vector2f.setWorldVar(map.x, map.y);

        cam = new Camera(new AABB(new Vector2f(0, 0), GamePanel.width + 64, GamePanel.height + 64));

        tm = new TileManager("tile/tilemap.xml", cam);

        enemy = new Enemy(cam, new Sprite("enemy/dog 2.png", 48, 48), new Vector2f(0 + (GamePanel.width / 2) - 16 + 150,0 + (GamePanel.height / 2) - 16 + 150), 32);
        enemy.setRadius(200);
        enemy.getBounds().setWidth(42/2);
        enemy.getBounds().setHeight(20/2);
        enemy.getBounds().setXOffset(12/2);
        enemy.getBounds().setYOffset(40/2);


        player = new Player(new Sprite("entity/linkformatted.png"), new Vector2f(0 + (GamePanel.width / 2) - 32 ,0 + (GamePanel.height / 2) - 32 ), 64);
        cam.target(player);
    }

    public Enemy getEnemy() {
        return enemy;
    }

    @Override
    public void update() {
        Vector2f.setWorldVar(map.x, map.y);

        player.update(enemy);
        enemy.update(player);
        cam.update();
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        key.escape.tick();

        player.input(mouse, key);
        cam.input(mouse, key);

        if(key.escape.clicked) {
            if(gsm.getState(GameStateManager.PAUSE)) {
                gsm.pop(GameStateManager.PAUSE);
            } else {
                gsm.add(GameStateManager.PAUSE);
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        tm.render(g);
        Sprite.drawArray(g,GamePanel.oldFrameCount + " FPS", new Vector2f(GamePanel.width - 192, 32), 32, 24);
        player.render(g);
        enemy.render(g);
        cam.render(g);
    }
}
