package io.github.some_example_name.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.github.some_example_name.base.Sprite;
import io.github.some_example_name.math.Rect;

public class Background  extends Sprite {
    public Background(Texture texture) {
        super(new TextureRegion(texture));
    }

    @Override
    public void resize(Rect worldBounds) {
        this.pos.set(worldBounds.pos);
        setHeightProportion(worldBounds.getHeight());
    }
}
