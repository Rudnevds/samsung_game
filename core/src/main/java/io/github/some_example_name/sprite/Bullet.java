package io.github.some_example_name.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.base.Sprite;
import io.github.some_example_name.math.Rect;

public class Bullet extends Sprite {
    private final Vector2 v = new Vector2();

    private Rect worldBounds;
    private int damage;
    private Sprite owner;

    public Bullet() {
        regions = new TextureRegion[1];
    }

    public void set(
        Sprite owner,
        TextureRegion region,
        Vector2 pos0,
        Vector2 v0,
        float height,
        Rect worldBounds,
        int damage
    ) {
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }
}
