package com.frs.supercad.modelviewer;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

@XStreamAlias("Model info")
public class ModelInfo implements Serializable {

	String modelName = "";
	Vector3 scale = new Vector3();
	Vector3 translation = new Vector3();
	Quaternion rotation = new Quaternion();

	@XStreamOmitField
	PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public ModelInfo(){

	}
	public ModelInfo(ModelInstance instance){

		instance.transform.getTranslation(translation);
		instance.transform.getScale(scale);
		instance.transform.getRotation(rotation);
	}

	public void addPropertyChangeListener(PropertyChangeListener listner){
		this.propertyChangeSupport.addPropertyChangeListener(listner);
	}


	//getters and setters


	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

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

	public Quaternion getRotation() {
		return rotation;
	}

	public void setRotation(Quaternion rotation) {
		propertyChangeSupport.firePropertyChange("rotation",this.rotation,rotation);
		this.rotation = rotation;
	}
}
