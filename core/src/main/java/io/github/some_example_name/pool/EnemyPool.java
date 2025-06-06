package io.github.some_example_name.pool;

import io.github.some_example_name.base.SpritesPool;
import io.github.some_example_name.math.Rect;
import io.github.some_example_name.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {
    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;
    private final Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, worldBounds);
    }
}
