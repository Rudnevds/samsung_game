package io.github.some_example_name.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import io.github.some_example_name.base.BaseButton;
import io.github.some_example_name.math.Rect;
import io.github.some_example_name.screen.GameScreen;

public class PlayButton  extends BaseButton {
    private final Game game;

    private static final float HEIGHT = 0.25f;
    private static final float PADDING = 0.03f;

    public PlayButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        setLeft(worldBounds.getLeft() + PADDING);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
