package io.github.some_example_name.screen;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;



import io.github.some_example_name.base.BaseScreen;
import io.github.some_example_name.math.Rect;
import io.github.some_example_name.sprite.Background;
import io.github.some_example_name.sprite.ExitButton;
import io.github.some_example_name.sprite.PlayButton;
import io.github.some_example_name.sprite.Star;


public class MenuScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

    private final Game game;

    private Texture bg;
    private TextureAtlas atlas;

    private Background background;
    private Star[] stars;
    private ExitButton exitButton;
    private PlayButton playButton;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/wall.jpg");
        atlas = new TextureAtlas("textures/menuAtlas.tpack");

        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        exitButton = new ExitButton(atlas);
        playButton = new PlayButton(atlas, game);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        exitButton.resize(worldBounds);
        playButton.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        exitButton.touchDown(touch, pointer, button);
        playButton.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        exitButton.touchUp(touch, pointer, button);
        playButton.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        exitButton.draw(batch);
        playButton.draw(batch);
        batch.end();
    }
}
