package com.frs.supercad.modelviewer;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;


@XStreamAlias("adjustment")
public class ModelInfo implements Serializable {


	UUID uuid;
	String name;
	float width,height,depth;
	Vector3 scale = new Vector3();
	Vector3 translation = new Vector3();
	Quaternion rotation = new Quaternion();
	Properties properties = new Properties();
	Matrix4 transform = new Matrix4();

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

	public void generateUUID(){
		setUuid(UUID.randomUUID());
	}




	//getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		String oldValue = this.name;
		this.name = name;
		propertyChangeSupport.firePropertyChange("name",oldValue,name);
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		UUID oldValue = this.uuid;
		this.uuid = uuid;
		propertyChangeSupport.firePropertyChange("uuid",oldValue,this.uuid);
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		float oldValue = this.width;
		this.width = width;
		propertyChangeSupport.firePropertyChange("width",oldValue,width);
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		float oldValue = this.height;
		this.height = height;
		propertyChangeSupport.firePropertyChange("height",oldValue,height);

	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		float oldValue = this.depth;
		this.depth = depth;
		propertyChangeSupport.firePropertyChange("depth",oldValue,depth);
	}

	public Vector3 getScale() {
		return scale;
	}

	public void setScale(Vector3 scale) {
		this.scale = scale;
	}

	public Vector3 getTranslation() {
		return translation;
	}

	public void setTranslation(Vector3 translation) {
		this.translation = translation;
	}

	public Quaternion getRotation() {
		return rotation;
	}

	public void setRotation(Quaternion rotation) {
		this.rotation = rotation;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Matrix4 getTransform() {
		return transform;
	}

	public void setTransform(Matrix4 transform) {
		this.transform = transform;
	}

}
