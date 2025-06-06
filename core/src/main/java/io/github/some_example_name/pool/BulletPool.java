package io.github.some_example_name.pool;

import io.github.some_example_name.base.SpritesPool;
import io.github.some_example_name.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

}
