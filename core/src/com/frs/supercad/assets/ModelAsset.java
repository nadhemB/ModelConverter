package com.frs.supercad.assets;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.frs.supercad.ModelConverter;

public class ModelAsset {
	private AssetManager assetManager;
	public static ModelAsset instance = new ModelAsset();
	private ModelAsset(){
		assetManager = new AssetManager(new AbsoluteFileHandleResolver());
	}

	public Model load(String path){
		String pathX = path.replace("\\", "/");
		Gdx.app.log(ModelAsset.class.getName()," loading model from " + pathX);
		assetManager.load(pathX, Model.class);
		assetManager.finishLoading();
		Gdx.app.log(ModelAsset.class.getName()," finsihed loading " + pathX);
		Model model = assetManager.get(pathX,Model.class);
		ModelConverter.instance.setModel(model);
		return model;
	}

	public void exportModel(ModelInstance instance){

	}
}
