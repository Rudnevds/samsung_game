package io.github.some_example_name.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import io.github.some_example_name.base.SpritesPool;
import io.github.some_example_name.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private final TextureAtlas atlas;
    private final Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas, Sound explosionSound) {
        this.atlas = atlas;
        this.explosionSound = explosionSound;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas, explosionSound);
    }
}
