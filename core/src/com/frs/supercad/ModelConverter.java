package com.frs.supercad;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.frs.supercad.modelviewer.ModelInfo;
import com.frs.supercad.modelviewer.ViewerScreen;


public class ModelConverter extends Game {

	private String TAG = ModelConverter.class.getName();

	public static ModelInfo modelInfo = new ModelInfo();

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug(TAG, "using openGL v" + Gdx.graphics.getGLVersion().getMajorVersion() + "."
				+ Gdx.graphics.getGLVersion().getMinorVersion());

		setScreen(new ViewerScreen());
	}
}
