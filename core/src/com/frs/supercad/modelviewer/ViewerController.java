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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class ViewerController {

	Camera camera;
	CameraController cameraController;
	ModelBatch batch;
	ModelBuilder modelBuilder;
	ModelInstance grid;
	ModelInstance instance;

	ModelConverter mc = ModelConverter.instance;


	public ViewerController(){
		init();
		createBackground();
		mc.addPropertyChangeLstener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Gdx.app.debug(PropertyChangeListener.class.getName(),"catching event.."  );
				instance = new ModelInstance(ModelConverter.instance.getModel());
			}
		});


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
		Material material  = new Material(ColorAttribute.createDiffuse(Color.LIGHT_GRAY));
		long attrs = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;
		Model gridModel = modelBuilder.createLineGrid(100,100,10,10,material,attrs);
		grid = new ModelInstance(gridModel);
	}

	public ModelInstance getInstance() {
		return instance;
	}


}
