package com.frs.supercad.modelviewer;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class ModelInfo implements Serializable {
	Vector3 scale;
	Vector3 translation;
	PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public ModelInfo(){

	}
	public ModelInfo(ModelInstance instance){
		scale = new Vector3();
		translation = new Vector3();
		instance.transform.getTranslation(translation);
		instance.transform.getScale(scale);
	}

	public void addPropertyChangeListener(PropertyChangeListener listner){
		this.propertyChangeSupport.addPropertyChangeListener(listner);
	}


	//getters and setters
	public Vector3 getScale() {
		return scale;
	}

	public void setScale(Vector3 scale) {
		propertyChangeSupport.firePropertyChange("scale",this.scale,scale);
		this.scale = scale;

	}

	public Vector3 getTranslation() {
		return translation;
	}

	public void setTranslation(Vector3 translation) {
		propertyChangeSupport.firePropertyChange("translation",this.translation,translation);
		this.translation = translation;
	}
}
