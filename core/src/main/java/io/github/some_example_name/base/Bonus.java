package io.github.some_example_name.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.math.Rect;
import io.github.some_example_name.sprite.ElectricLine;
import io.github.some_example_name.sprite.MainShip;
import io.github.some_example_name.utils.BonusType;

public class Bonus extends Sprite{
    protected final Vector2 v0 = new Vector2();
    protected final Vector2 v = new Vector2();

    private static final Vector2 startV = new Vector2(0, -0.3f);

    private BonusType bonusType;
    private Sound bonusSound;

    protected float reloadTimer;
    protected float reloadInterval;

    protected Rect worldBounds;

    protected int hp;

    public Bonus(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void set(
        TextureRegion[] regions,
        Vector2 v0,
        float reloadInterval,
        Sound bonusSound,
        float height,
        int hp,
        BonusType bonusType
    ) {
        this.regions = regions;
        this.v0.set(v0);
        setHeightProportion(height);
        this.reloadInterval = reloadInterval;
        this.bonusSound = bonusSound;
        this.hp = hp;
        this.bonusType = bonusType;
        reloadTimer = 0f;
        v.set(startV);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
        }
        if (getTop() < worldBounds.getTop()) {
            v.set(v0);
        } else {
            reloadTimer = 0.8f * reloadInterval;
        }
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    public void action(MainShip ship, ElectricLine electricLine) {
        bonusSound.play();
        if (bonusType.equals(BonusType.MEDKIT)) {
            ship.addHP(hp);
        } else if (bonusType.equals(BonusType.SHIELD)) {
            electricLine.setDuration(hp);
            electricLine.activate();
        }
        destroy();
    }
}
