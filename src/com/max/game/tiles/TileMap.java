package com.max.game.tiles;

import com.max.game.util.AABB;
import java.awt.Graphics2D;

public abstract class TileMap {
    public abstract void render(Graphics2D g, AABB cam);
}
