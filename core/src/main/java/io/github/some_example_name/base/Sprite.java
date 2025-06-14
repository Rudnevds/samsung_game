package io.github.some_example_name.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import io.github.some_example_name.math.Rect;
import io.github.some_example_name.utils.Regions;

public class Sprite  extends Rect {
    protected float angle;
    protected float scale = 1;
    protected TextureRegion[] regions;
    protected int frame;
    private boolean destroyed;

    public Sprite() {
    }

    public Sprite(TextureRegion region) {
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        this.regions = Regions.split(region, rows, cols, frames);
    }

    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void resize(Rect worldBounds) {

    }

    public void update(float delta) {

    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void destroy() {
        destroyed = true;
    }

    public void flushDestroy() {
        destroyed = false;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
            regions[frame],
            getLeft(), getBottom(),
            halfWidth, halfHeight,
            getWidth(), getHeight(),
            scale, scale,
            angle
        );
    }

    public boolean isCollision(Rect rect) {
        return !(
            rect.getRight() < getLeft()
                || rect.getLeft() > getRight()
                || rect.getBottom() > getTop()
                || rect.getTop() < pos.y
        );
    }
}
