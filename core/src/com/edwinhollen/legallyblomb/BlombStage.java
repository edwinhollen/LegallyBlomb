package com.edwinhollen.legallyblomb;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Edwin on 9/17/2016.
 */
public class BlombStage extends Stage {
    private AssetManager assets;
    private AssetDescriptor<Sound> hit;
    private AssetDescriptor<Texture> before, after;

    public BlombStage(Viewport viewport, Batch batch) {
        super(viewport, batch);

        assets = new AssetManager();

        hit = new AssetDescriptor<Sound>("legally hit.mp3", Sound.class);
        before = new AssetDescriptor<Texture>("before1.jpg", Texture.class);
        after = new AssetDescriptor<Texture>("after1.jpg", Texture.class);

        assets.load(hit);
        assets.load(before);
        assets.load(after);
        assets.finishLoading();

        Group picGroup = new Group();
        final Image beforeImage = new Image(assets.get(before));
        final Image afterImage = new Image(assets.get(after));

        picGroup.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        beforeImage.setSize(picGroup.getWidth(), picGroup.getHeight());
        afterImage.setSize(picGroup.getWidth(), picGroup.getHeight());

        picGroup.addActor(beforeImage);
        picGroup.addActor(afterImage);

        afterImage.setVisible(false);

        picGroup.addListener(new ActorGestureListener(){
            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                beforeImage.setVisible(false);
                afterImage.setVisible(true);
                assets.get(hit).play(1.0f);
                super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                beforeImage.setVisible(true);
                afterImage.setVisible(false);
                super.touchUp(event, x, y, pointer, button);
            }

            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {

                super.tap(event, x, y, count, button);
            }
        });

        addActor(picGroup);

    }

    @Override
    public void act() {
        super.act();
        assets.update();
    }

    @Override
    public void dispose() {
        assets.dispose();
        super.dispose();

    }
}
