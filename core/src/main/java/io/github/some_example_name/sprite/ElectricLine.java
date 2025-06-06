package io.github.some_example_name.sprite;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import io.github.some_example_name.base.Sprite;
import io.github.some_example_name.math.Rect;

public class ElectricLine extends Sprite {
    private static final float NORMAL_HEIGHT = 0.05f;
    private static final float BOTTOM_MARGIN = 0.25f;
    private static int heightCounter = 0;
    private final Music music;
    private int shieldInterval;

    private boolean active;
    protected float shieldTimer;

    public ElectricLine(TextureAtlas atlas, Music electricLineSound) {
        super(atlas.findRegion("electro_line"));
        this.music = electricLineSound;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(NORMAL_HEIGHT);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
        setLeft(worldBounds.getLeft());
    }

    @Override
    public void update(float delta) {
        if (isActive()) {
            setHeightProportion(NORMAL_HEIGHT + ((++heightCounter % 2) * -1) * 0.02f);
            shieldTimer += delta;
            if (shieldTimer > shieldInterval) {
                deActivate();
            }
        }
    }

    @Override
    public boolean isCollision(Rect rect) {
        return !(
            rect.getRight() < getLeft()
                || rect.getLeft() > getRight()
                || rect.getBottom() > (getTop() - getHalfHeight())
                || rect.getTop() < (pos.y - getHeight())
        );
    }

    public boolean isActive() {
        return active;
    }

    public void deActivate() {
        this.active = false;
        music.stop();
    }

    public void activate() {
        this.shieldTimer = 0;
        music.play();
        active = true;
    }

    public void setDuration(int duration) {
        this.shieldInterval = duration;
    }
}
