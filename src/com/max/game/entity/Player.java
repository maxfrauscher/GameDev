package com.max.game.entity;

import com.max.game.GamePanel;
import com.max.game.graphics.Sprite;
import com.max.game.states.PlayState;
import com.max.game.util.KeyHandler;
import com.max.game.util.MouseHandler;
import com.max.game.util.Syso;
import com.max.game.util.Vector2f;

import java.awt.*;

public class Player extends Entity{


    public Player(Sprite sprite, Vector2f orgin, int size) {
        super(sprite, orgin, size);
        acc = 2f;
        //deacc = 1f;
        maxSpeed = 3f;
        sprintSpeed = 4.5f;
        bounds.setWidth(42);
        bounds.setHeight(20);
        bounds.setXOffset(12);
        bounds.setYOffset(40);

    }

    public void sprint() {
        if(sprint) {
            speed = sprintSpeed;
        } else {
            speed = maxSpeed;
        }
    }

    public void move() {
        if(up) {
            dy -= acc;
            if(dy < -speed) {
                dy = -speed;
            }
        } else {
            if(dy < 0) {
                dy += deacc;
                if(dy > 0) {
                    dy = 0;
                }
            }
        }
        if(down) {
            dy += acc;
            if(dy > speed) {
                dy = speed;
            }
        } else {
            if(dy > 0) {
                dy -= deacc;
                if(dy < 0) {
                    dy = 0;
                }
            }
        }
        if(left) {
            dx -= acc;
            if(dx < -speed) {
                dx = -speed;
            }
        } else {
            if(dx < 0) {
                dx += deacc;
                if(dx > 0) {
                    dx = 0;
                }
            }
        }
        if(right) {
            dx += acc;
            if(dx > speed) {
                dx = speed;
            }
        } else {
            if(dx > 0) {
                dx -= deacc;
                if(dx < 0) {
                    dx = 0;
                }
            }
        }
    }

    private void resetPosition() {
        Syso.write("RESETING Player... ");
        pos.x = GamePanel.width / 2 - 32;
        PlayState.map.x = 0;

        pos.y = GamePanel.height / 2 - 32;
        PlayState.map.y = 0;

        setAnimation(RIGHT, sprite.getSpriteArray(RIGHT), 10);
    }

    public void update(Enemy enemy) {
        super.update();

        if(attack && hitBounds.collides(enemy.getBounds())) {
            Syso.write("Iv been hit.");
        }

        if(!fallen) {
            move();
            sprint();
            if (!tc.collisionTile(dx, 0)) {
                //PlayState.map.x += dx;
                pos.x += dx;
                xCol = false;
            } else {
                xCol = true;
            }
            if (!tc.collisionTile(0, dy)) {
                //PlayState.map.y += dy;
                yCol = false;
                pos.y += dy;
            } else {
                yCol = true;
            }
        } else {
            xCol = true;
            yCol = true;
            if(ani.hasPlayedOnce()) {
                resetPosition();
                fallen = false;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), (int) bounds.getWidth(), (int) bounds.getHeight());
        g.drawImage(ani.getImage(), (int) (pos.getWorldVar().x), (int) (pos.getWorldVar().y), size, size , null);
        //g.drawImage(ani.getImage(), (int) (pos.x), (int) (pos.y), size, size , null);

        if(attack) {
            g.setColor(Color.RED);
            g.drawRect((int) (hitBounds.getPos().getWorldVar().x + hitBounds.getXOffset()), (int) (hitBounds.getPos().getWorldVar().y + hitBounds.getYOffset()), (int) hitBounds.getWidth(), (int) hitBounds.getHeight() );
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        if(mouse.getButton() == 1) {
            Syso.write("Player: " + pos.x + ", " + pos.y);
        }

        if(!fallen) {
            if(key.up.down) {
                up = true;
            } else {
                up = false;
            }
            if(key.down.down) {
                down = true;
            } else {
                down = false;
            }
            if(key.left.down) {
                left = true;
            } else {
                left = false;
            }
            if(key.right.down) {
                right = true;
            } else {
                right = false;
            }

            if(key.attack.down) {
                attack = true;
            } else {
                attack = false;
            }

            if(up && down) {
                up = false;
                down = false;
            }
            if(left && right) {
                left = false;
                right = false;
            }

            if(key.sprint.down) {
                sprint = true;
            } else {
                sprint = false;
            }
        } else {
            up = false;
            down = false;
            left = false;
            right = false;
        }
    }
}
