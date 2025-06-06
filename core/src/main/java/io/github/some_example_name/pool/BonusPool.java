package io.github.some_example_name.pool;

import io.github.some_example_name.base.Bonus;
import io.github.some_example_name.base.SpritesPool;
import io.github.some_example_name.math.Rect;

public class BonusPool extends SpritesPool<Bonus> {

    private final Rect worldBounds;

    public BonusPool(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    protected Bonus newObject() {
        return new Bonus(worldBounds);
    }
}
