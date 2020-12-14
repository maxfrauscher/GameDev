package com.max.game.entity;

import com.max.game.graphics.Sprite;
import com.max.game.states.PlayState;
import com.max.game.util.AABB;
import com.max.game.util.Camera;
import com.max.game.util.Syso;
import com.max.game.util.Vector2f;

import java.awt.*;

public class Enemy extends Entity {

    private AABB sense;
    private int r;
    private boolean destroyed = false;

    private Camera cam;

    public Enemy(Camera cam, Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size);

        this.cam = cam;

        acc = 1f;
        maxSpeed = 2f;
        r = 200;

        bounds.setWidth(42);
        bounds.setHeight(20);
        bounds.setXOffset(12);
        bounds.setYOffset(40);

        sense = new AABB(new Vector2f(orgin.x + size / 2 - r / 2, orgin.y + size / 2 - r / 2), r);
    }

    public void setRadius(int i) {
        r = i;
    }

    public void move(Player player) {
        if(sense.colCircleBox(player.getBounds())) {
            if(pos.y > player.pos.y + 1) {
                dy -= acc;
                up = true;
                down = false;
                if(dy < -maxSpeed) {
                    dy = -maxSpeed;
                }
            } else if(pos.y < player.pos.y - 1) {
                dy += acc;
                down = true;
                up = false;
                if(dy > maxSpeed) {
                    dy = maxSpeed;
                }
            } else {
                dy = 0;
                up = false;
                down = false;
            }
            if(pos.x > player.pos.x + 1) {
                dx -= acc;
                left = true;
                right = false;
                if(dx < -maxSpeed) {
                    dx = -maxSpeed;
                }
            } else if(pos.x < player.pos.x - 1) {
                dx += acc;
                right = true;
                left = false;
                if(dx > maxSpeed) {
                    dx = maxSpeed;
                }
            } else {
                dx = 0;
                left = false;
                right = false;
            }
        } else {
            up = false;
            down = false;
            left = false;
            right = false;
            dx = 0;
            dy = 0;
        }
    }

    private void destroy() {
        destroyed = true;
    }

    public void update(Player player) {
        if(cam.getBoundsOnScreen().collides(this.bounds)) {
            if(destroyed == false) {
                super.update();
                move(player);
                if(!fallen) {
                    if(!tc.collisionTile(dx, 0)) {
                        sense.getPos().x += dx;
                        pos.x += dx;
                    }
                    if(!tc.collisionTile(0, dy)) {
                        sense.getPos().y += dy;
                        pos.y += dy;
                    }
                } else {
                    destroy();
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(cam.getBoundsOnScreen().collides(this.bounds)) {
            if (destroyed == false) {
                g.setColor(Color.GREEN);
                g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());

                g.setColor(Color.BLUE);
                g.drawOval((int) (sense.getPos().getWorldVar().x), (int) sense.getPos().getWorldVar().y, r, r);

                g.drawImage(ani.getImage(), (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, size, size, null);
            }
        }
    }
}
