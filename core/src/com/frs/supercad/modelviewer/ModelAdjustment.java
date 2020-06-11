package com.frs.supercad.modelviewer;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.UUID;

@XStreamAlias("adjustment")
public class ModelAdjustment implements Serializable {

	String name = "";
	UUID uuid;
	Vector3 scale = new Vector3();
	Vector3 translation = new Vector3();
	Quaternion rotation = new Quaternion();

	@XStreamOmitField
	PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public ModelAdjustment(){

	}
	public ModelAdjustment(ModelInstance instance){

		instance.transform.getTranslation(translation);
		instance.transform.getScale(scale);
		instance.transform.getRotation(rotation);
	}

	public void addPropertyChangeListener(PropertyChangeListener listner){
		this.propertyChangeSupport.addPropertyChangeListener(listner);
	}

	public void generateUUID(){
		setUuid(UUID.randomUUID());
	}


	//getters and setters


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
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
