package io.github.some_example_name;


import com.badlogic.gdx.Game;
import io.github.some_example_name.screen.MenuScreen;


public class StarGame extends Game {
    @Override
    public void create () {
        setScreen(new MenuScreen(this));
    }
}
