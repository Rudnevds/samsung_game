package io.github.some_example_name.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;



import java.util.List;
import io.github.some_example_name.base.Font;
import io.github.some_example_name.base.BaseScreen;
import io.github.some_example_name.base.Bonus;
import io.github.some_example_name.math.Rect;
import io.github.some_example_name.pool.BonusPool;
import io.github.some_example_name.pool.BulletPool;
import io.github.some_example_name.pool.EnemyPool;
import io.github.some_example_name.pool.ExplosionPool;
import io.github.some_example_name.sprite.Background;
import io.github.some_example_name.sprite.Bullet;
import io.github.some_example_name.sprite.ElectricLine;
import io.github.some_example_name.sprite.EnemyShip;
import io.github.some_example_name.sprite.GameOver;
import io.github.some_example_name.sprite.MainShip;
import io.github.some_example_name.sprite.NewGameButton;
import io.github.some_example_name.sprite.Star;
import io.github.some_example_name.utils.BonusEmitter;
import io.github.some_example_name.utils.EnemyEmitter;


public class GameScreen extends BaseScreen {
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private static final int STAR_COUNT = 64;
    private static final float MARGIN = 0.01f;

    private Texture bg;
    private TextureAtlas atlas;
    private TextureAtlas bonusAtlas;

    private Background background;
    private Star[] stars;
    private MainShip mainShip;
    private ElectricLine electricLine;

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private BonusPool bonusPool;

    private EnemyEmitter enemyEmitter;
    private BonusEmitter bonusEmitter;

    private GameOver gameOver;
    private NewGameButton newGameButton;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHP;
    private StringBuilder sbLevel;

    private Sound laserSound;
    private Sound bulletSound;
    private Sound bonusSound;
    private Sound explosionSound;
    private Music electricLineSound;
    private Music music;

    private int frags;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/wall.jpg");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bonusAtlas = new TextureAtlas("textures/bonus.tpack");

        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        bonusSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bonus.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        electricLineSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/electro.wav"));
        electricLineSound.setLooping(true);
        electricLineSound.setVolume(0.9f);

        newGameButton = new NewGameButton(atlas, this);
        gameOver = new GameOver(atlas);

        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds);
        bonusPool = new BonusPool(worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);
        electricLine = new ElectricLine(bonusAtlas, electricLineSound);

        enemyEmitter = new EnemyEmitter(atlas, enemyPool, worldBounds, bulletSound);
        bonusEmitter = new BonusEmitter(bonusAtlas, bonusPool, worldBounds, bonusSound);

        gameOver = new GameOver(atlas);
        newGameButton = new NewGameButton(atlas, this);

        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.02f);
        sbFrags = new StringBuilder();
        sbHP = new StringBuilder();
        sbLevel = new StringBuilder();

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    public void startNewGame() {
        frags = 0;
        mainShip.startNewGame();
        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        bonusPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        electricLine.resize(worldBounds);
        newGameButton.resize(worldBounds);
        gameOver.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bonusAtlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        bonusPool.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
        electricLineSound.dispose();
        music.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchDown(touch, pointer, button);
        } else {
            newGameButton.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchUp(touch, pointer, button);
        } else {
            newGameButton.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
            electricLine.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            bonusPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
            bonusEmitter.generate(delta);
        }
    }

    private void checkCollisions() {
        if (mainShip.isDestroyed()) {
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            float minDst = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemyShip.pos) < minDst) {
                enemyShip.destroy();
                mainShip.damage(enemyShip.getBulletDamage() * 2);
            } else if (electricLine.isActive() && electricLine.isCollision(enemyShip)) {
                enemyShip.destroy();
                frags++;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() != mainShip) {
                if (electricLine.isActive() && electricLine.isCollision(bullet)) {
                    bullet.destroy();
                } else if (mainShip.isCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            } else {
                for (EnemyShip enemyShip : enemyShipList) {
                    if (enemyShip.isCollision(bullet)) {
                        enemyShip.damage(bullet.getDamage());
                        bullet.destroy();
                        if (enemyShip.isDestroyed()) {
                            frags++;
                        }
                    }
                }
            }
        }
        for (Bonus bonus : bonusPool.getActiveObjects()) {
            if (bonus.isCollision(mainShip)) {
                bonus.action(mainShip, electricLine);
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        bonusPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            bonusPool.drawActiveSprites(batch);
            if (electricLine.isActive()) {
                electricLine.draw(batch);
            }
        } else {
            gameOver.draw(batch);
            newGameButton.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + MARGIN, worldBounds.getTop() - MARGIN);
        sbHP.setLength(0);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - MARGIN, Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - MARGIN, worldBounds.getTop() - MARGIN, Align.right);
    }
}
