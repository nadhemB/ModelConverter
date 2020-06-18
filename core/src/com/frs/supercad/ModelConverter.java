package com.frs.supercad;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.frs.supercad.modelviewer.ModelInfo;
import com.frs.supercad.modelviewer.ViewerScreen;
import com.frs.supercad.utilities.IOUtilities;
import com.thoughtworks.xstream.XStream;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;


public class ModelConverter extends Game {

	private String TAG = ModelConverter.class.getName();
	private String modelPath;
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

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public void export(String path, ModelInfo info){

		info.setTransform(modelInstance.transform);
		Vector3 scale = new Vector3();
		Quaternion rotation = new Quaternion();
		Vector3 translation = new Vector3();
		modelInstance.transform.getTranslation(translation);
		modelInstance.transform.getRotation(rotation);
		modelInstance.transform.getScale(scale);
		info.setScale(scale);
		info.setTranslation(translation);
		info.setRotation(rotation);

		FileOutputStream fos = null;
		File modelFile = new File(path + File.separator + "model.g3dj");
		File configFile = new File(path + File.separator + "info.xml");
		modelFile.getParentFile().mkdirs();
		try {
			modelFile.createNewFile();
			configFile.createNewFile();
			fos = new FileOutputStream(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		XStream xStream = new XStream();
		xStream.autodetectAnnotations(true);


		try {
			IOUtilities.copyFileUsingChannel(new File(modelPath),modelFile);
			info.getProperties().put("created", LocalDateTime.now());
			xStream.toXML(info,fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug(TAG, "using openGL v" + Gdx.graphics.getGLVersion().getMajorVersion() + "."
				+ Gdx.graphics.getGLVersion().getMinorVersion());

		setScreen(new ViewerScreen());
	}
}
