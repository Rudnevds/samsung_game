package io.github.some_example_name.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import io.github.some_example_name.base.BaseButton;
import io.github.some_example_name.math.Rect;
import io.github.some_example_name.screen.GameScreen;

public class NewGameButton  extends BaseButton {
    private final GameScreen gameScreen;

    private static final float HEIGHT = 0.05f;
    private static final float TOP_MARGIN = -0.012f;

    public NewGameButton(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setTop(TOP_MARGIN);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}
