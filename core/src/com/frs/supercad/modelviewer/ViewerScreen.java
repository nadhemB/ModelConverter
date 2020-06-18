package com.frs.supercad.modelviewer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.frs.supercad.ModelConverter;

import static com.badlogic.gdx.graphics.GL20.*;

public class ViewerScreen implements Screen {

	ViewerController controller = ViewerController.controller;

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		Gdx.gl20.glClearColor(0.4f,0.4f,0.4f,1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		if(controller.requireUpdate){
			controller.analyseInstance();
		}
		controller.cameraController.update();

		controller.batch.begin(controller.camera);
		controller.batch.render(controller.grid);
		for(ModelInstance axis : controller.objects){
			controller.batch.render(axis);
		}
		if(ModelConverter.instance.getModelInstance() != null){
			controller.batch.render(ModelConverter.instance.getModelInstance());
		}
		controller.batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
