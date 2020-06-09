package com.frs.supercad.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;

public class ModelAsset {
	private AssetManager assetManager;
	public static ModelAsset instance = new ModelAsset();
	private ModelAsset(){
		assetManager = new AssetManager();
	}



	public Model load(String path){
		Gdx.app.log(ModelAsset.class.getName()," loading model from " + path);
		assetManager.load(path, Model.class);
		assetManager.finishLoading();
		Gdx.app.log(ModelAsset.class.getName()," finsihed loading " + path);
		Model model = assetManager.get(path,Model.class);
		return model;
	}
}
