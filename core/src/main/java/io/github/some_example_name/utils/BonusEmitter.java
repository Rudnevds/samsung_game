package io.github.some_example_name.utils;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.base.Bonus;
import io.github.some_example_name.math.Rect;
import io.github.some_example_name.pool.BonusPool;

public class BonusEmitter {
    private static final float GENERATE_INTERVAL = 4f;

    private static final float MEDKIT_HEIGHT = 0.08f;
    private static final int MEDKIT_HP = 25;

    private static final float SHIELD_HEIGHT = 0.06f;
    private static final int SHIELD_HP = 5;

    private static final float BONUS_RELOAD_INTERVAL = 15f;

    private final Vector2 medkitV = new Vector2(0, -0.4f);
    private final Vector2 shieldV = new Vector2(0, -0.4f);

    private final Rect worldBounds;
    private final Sound bonusSound;

    private float generateTimer;

    private final TextureRegion[] medkitRegions;
    private final TextureRegion[] shieldRegions;

    private final BonusPool bonusPool;


    public BonusEmitter(TextureAtlas atlas, BonusPool bonusPool, Rect worldBounds, Sound bonusSound) {
        this.bonusPool = bonusPool;
        this.worldBounds = worldBounds;
        this.bonusSound = bonusSound;
        medkitRegions = Regions.split(atlas.findRegion("medkit"), 1, 1, 1);
        shieldRegions = Regions.split(atlas.findRegion("shield"), 1, 1, 1);
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= GENERATE_INTERVAL) {
            generateTimer = 0f;
            Bonus bonus = bonusPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                bonus.set(
                    medkitRegions,
                    medkitV,
                    BONUS_RELOAD_INTERVAL,
                    bonusSound,
                    MEDKIT_HEIGHT,
                    MEDKIT_HP,
                    BonusType.MEDKIT);
            } else {
                bonus.set(
                    shieldRegions,
                    shieldV,
                    BONUS_RELOAD_INTERVAL,
                    bonusSound,
                    SHIELD_HEIGHT,
                    SHIELD_HP,
                    BonusType.SHIELD);
            }
            bonus.pos.x = MathUtils.random(
                worldBounds.getLeft() + bonus.getHalfWidth(),
                worldBounds.getRight() - bonus.getHalfWidth()
            );
            bonus.setBottom(worldBounds.getTop());
        }
    }
}
