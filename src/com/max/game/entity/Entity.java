package com.max.game.entity;

import com.max.game.graphics.Animation;
import com.max.game.graphics.Sprite;
import com.max.game.util.AABB;
import com.max.game.util.Camera;
import com.max.game.util.TileCollision;
import com.max.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {

    protected final int FALLEN = 4;
    protected final int UP = 3;
    protected final int DOWN = 2;
    protected final int RIGHT = 0;
    protected final int LEFT = 1;
    protected int currentAnimation;

    protected Animation ani;
    protected Sprite sprite;
    protected Vector2f pos;
    protected int size;

    protected boolean up;
    protected boolean down;
    protected boolean right;
    protected boolean left;
    protected boolean attack;
    protected boolean fallen;

    public boolean xCol = false;
    public boolean yCol = false;

    protected boolean sprint;
    protected boolean attackSpeed;
    protected boolean attackDuration;

    protected float dx;
    protected float dy;

    protected float speed = 3f;
    protected float maxSpeed = 4f;
    protected float sprintSpeed = 5f;

    protected float acc = 3f;
    protected float deacc = 0.3f;

    protected AABB hitBounds;
    protected AABB bounds;

    protected TileCollision tc;

    public Entity(Sprite sprite, Vector2f orgin, int size) {
        this.sprite = sprite;
        pos = orgin;
        this.size = size;

        bounds = new AABB(orgin, size, size);
        hitBounds = new AABB(orgin , size, size);
        hitBounds.setXOffset(size / 2);
        ani = new Animation();
        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);

        tc = new TileCollision(this);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setFallen(boolean b) { fallen = b; }
    public void setSize(int i) {
        size = i;
    }
    public void setMaxSpeed(float f) { maxSpeed = f; }
    public void setAcc(float f) { acc = f; }
    public void setDeacc(float f) { deacc = f; }

    public AABB getBounds() { return bounds; }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public Animation getAnimation() { return ani; }


    public void setAnimation(int i, BufferedImage[] frames, int delay) {
        currentAnimation = i;
        ani.setFrames(frames);
        ani.setDelay(delay);
    }

    private void setHitBoxDirection() {
        if(up) {
            hitBounds.setYOffset(-size / 2);
            hitBounds.setXOffset(0);
        }
        else if(down) {
            hitBounds.setYOffset(size / 2);
            hitBounds.setXOffset(0);
        }
        else if(left) {
            hitBounds.setXOffset(-size / 2);
            hitBounds.setYOffset(0);
        }
        else if(right) {
            hitBounds.setXOffset(size / 2);
            hitBounds.setYOffset(0);
        }
    }

    public void update() {
        animate();
        setHitBoxDirection();
        ani.update();
    }

    public void animate() {
        if(up) {
            if(currentAnimation != UP ||ani.getDelay() == -1) {
                setAnimation(UP, sprite.getSpriteArray(UP),5);
            }
        }
        else if(down) {
            if(currentAnimation != DOWN || ani.getDelay() == -1) {
                setAnimation(DOWN, sprite.getSpriteArray(DOWN),5);
            }
        }
        else if(left) {
            if(currentAnimation != LEFT || ani.getDelay() == -1) {
                setAnimation(LEFT, sprite.getSpriteArray(LEFT),5);
            }
        }
       else if(right) {
            if(currentAnimation != RIGHT || ani.getDelay() == -1) {
                setAnimation(RIGHT, sprite.getSpriteArray(RIGHT),5);
            }
        } else if(fallen) {
            if(currentAnimation != FALLEN || ani.getDelay() == -1) {
                setAnimation(FALLEN, sprite.getSpriteArray(FALLEN), 15);
            }

        } else {
            setAnimation(currentAnimation, sprite.getSpriteArray(currentAnimation), -1);
        }
    }

    public abstract void render(Graphics2D g);

    public int getSize() {
        return size;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public float getDeacc() {
        return deacc;
    }
}
