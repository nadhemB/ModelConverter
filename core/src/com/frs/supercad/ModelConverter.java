package com.frs.supercad;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.frs.supercad.modelviewer.AdjustedModel;
import com.frs.supercad.modelviewer.ViewerScreen;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class ModelConverter extends Game {

	private String TAG = ModelConverter.class.getName();

	public  Model model = new Model();
	public 	ModelInstance modelInstance = null;

	public PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public static ModelConverter instance = new ModelConverter();

	private ModelConverter(){ }


	public void addPropertyChangeLstener(PropertyChangeListener listener){
		this.propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public Model getModel() {
		return model;
	}

	public  void setModel(Model model) {
		Model oldValue = this.model;
		this.model = model;
		this.propertyChangeSupport.firePropertyChange("model",oldValue,this.model);
	}

	public ModelInstance getModelInstance() {
		return modelInstance;
	}

	public void setModelInstance(ModelInstance modelInstance) {
		this.modelInstance = modelInstance;
	}

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug(TAG, "using openGL v" + Gdx.graphics.getGLVersion().getMajorVersion() + "."
				+ Gdx.graphics.getGLVersion().getMinorVersion());

		setScreen(new ViewerScreen());
	}
}
