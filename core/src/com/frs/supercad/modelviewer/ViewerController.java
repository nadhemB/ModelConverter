package com.frs.supercad.modelviewer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.frs.supercad.ModelConverter;
import com.sun.j3d.utils.applet.MainFrame;


public class ViewerController {

	Camera camera;
	CameraController cameraController;
	ModelBatch batch;
	ModelBuilder modelBuilder;
	ModelInstance grid;
	public static ModelInstance instance;



	public ViewerController(){
		init();
		createBackground();
	}

	void init(){
		camera = new PerspectiveCamera(62, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.up.set(0,1,0);
		camera.far = 1000;
		camera.near = 0.5f;
		camera.position.set(5,5,5);
		camera.lookAt(0,0,0);
		camera.update();
		cameraController = new CameraController(camera);
		Gdx.input.setInputProcessor(cameraController);

		batch = new ModelBatch();

		modelBuilder = new ModelBuilder();

	}

	public void createBackground(){
		/*Pixmap pixy = DrawingHelper.createGridTexture(720,720,100,100,10);

		Material material  = new Material(TextureAttribute.createDiffuse(new Texture(pixy)));
		long attrs = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates ;
		Model gridModel = modelBuilder.createRect(-10,0,-10,
													10,0,-10,
													10,0,10,
													-10,0,10,
													0,1,0,material,attrs);*/
		Material material  = new Material(ColorAttribute.createDiffuse(Color.DARK_GRAY));
		long attrs = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;
		Model gridModel = modelBuilder.createLineGrid(100,100,10,10,material,attrs);
		grid = new ModelInstance(gridModel);
	}


	public static void createModelInstance(Model model){
			instance = new ModelInstance(model);
			updateModelProperty();

	}

	public static void updateModelProperty(){
		ModelInfo newInfo = new ModelInfo(instance);
		ModelConverter.modelInfo.setScale(newInfo.getScale());
		ModelConverter.modelInfo.setTranslation(newInfo.getTranslation());
	}



}
