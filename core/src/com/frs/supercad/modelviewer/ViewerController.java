package com.frs.supercad.modelviewer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
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
	Array<ModelInstance> objects = new Array<ModelInstance>();
	public static ViewerController controller = new ViewerController();
	private static ModelInstance box;
	boolean requireUpdate = true;

	ModelConverter mc = ModelConverter.instance;


	private ViewerController(){
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
		Material material  = new Material(ColorAttribute.createDiffuse(Color.LIGHT_GRAY));
		long attrs = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;
		Model gridModel = modelBuilder.createLineGrid(100,100,1,1,material,attrs);
		grid = new ModelInstance(gridModel);
		Model axisX = modelBuilder.createArrow(new Vector3(0,0,0),Vector3.X,
				new Material(ColorAttribute.createDiffuse(Color.RED)),attrs);
		Model axisY = modelBuilder.createArrow(new Vector3(0,0,0),Vector3.Y,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),attrs);
		Model axisZ = modelBuilder.createArrow(new Vector3(0,0,0),Vector3.Z,
				new Material(ColorAttribute.createDiffuse(Color.BLUE)),attrs);
		objects.add(new ModelInstance(axisX));
		objects.add(new ModelInstance(axisY));
		objects.add(new ModelInstance(axisZ));
	}

	public void analyseInstance(){
		if(requireUpdate){
			ModelInstance instance = ModelConverter.instance.getModelInstance();
			if(instance != null){
				BoundingBox box = new BoundingBox();
				instance.calculateBoundingBox(box);
				ModelInstance boxInstance;
				Vector3 center = new Vector3();
				box.getCenter(center);
				Vector3 translation = new Vector3();
				ModelConverter.instance.getModelInstance().transform.getTranslation(translation);
				Model sphere = modelBuilder.createSphere(0.1f,0.1f,0.1f,20,20,new Material(ColorAttribute.createDiffuse(Color.YELLOW)),VertexAttributes.Usage.Position);
				objects.add(new ModelInstance(sphere));
				requireUpdate = false;
			}

		}


	}

	public boolean isRequireUpdate() {
		return requireUpdate;
	}

	public void setRequireUpdate(boolean requireUpdate) {
		this.requireUpdate = requireUpdate;
	}

	public Vector3 getModelDimension() {
		ModelInstance instance = ModelConverter.instance.getModelInstance();
		BoundingBox box = new BoundingBox();
		instance.calculateBoundingBox(box);
		Vector3 dim = new Vector3();
		box.getDimensions(dim);
		return dim;
	}

    public void scale(Vector3 scale) {
		ModelInstance instance = ModelConverter.instance.getModelInstance();
		BoundingBox box = new BoundingBox();
		instance.calculateBoundingBox(box);
		Vector3 dimension = new Vector3();
		box.getDimensions(dimension);
		instance.nodes.get(0).scale.set(scale.x/dimension.x,scale.y/dimension.y,scale.z/dimension.z);
		instance.calculateTransforms();
		setRequireUpdate(true);
    }

	public void rotate(Vector3 axis, float rotationDegree) {
		ModelInstance instance = ModelConverter.instance.getModelInstance();
		instance.transform.rotate(axis,rotationDegree);
		setRequireUpdate(true);
	}

	public void translate(float translationX, float translationY, float translationZ) {
		ModelInstance instance = ModelConverter.instance.getModelInstance();
		instance.transform.translate(translationX,translationY,translationZ);
		setRequireUpdate(true);
	}
}
