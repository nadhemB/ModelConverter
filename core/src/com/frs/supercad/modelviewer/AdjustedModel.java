package com.frs.supercad.modelviewer;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.frs.supercad.utilities.IOUtilities;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AdjustedModel {

	private String  modelPath;
	public ModelAdjustment info = new ModelAdjustment();
	public ModelInstance instance;

	public AdjustedModel(){}
	public AdjustedModel(String path,ModelInstance instance){
		this.modelPath = path;
		this.instance = instance;
		ModelAdjustment infoX = new ModelAdjustment(instance);
		info.setScale(infoX.getScale());
		info.setTranslation(infoX.getTranslation());
		info.setRotation(infoX.getRotation());

	}

	public void export(String path){

		FileOutputStream fos = null;
		File modelFile = new File(path + File.separator + "model.g3dj");
		File configFile = new File(path + File.separator + "adjustment.xml");
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
			info.properties.put("created","lklklklklk");
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

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	public ModelAdjustment getInfo() {
		return info;
	}

	public void setInfo(ModelAdjustment info) {
		this.info = info;
	}

	public  ModelInstance getInstance() {
		return instance;
	}

	public void setInstance(ModelInstance instance) {
		this.instance = instance;
		ModelAdjustment infoX = new ModelAdjustment(instance);
		info.setRotation(infoX.getRotation());
		info.setTranslation(infoX.getTranslation());
		info.setScale(infoX.getScale());
	}

	public void applyTransforms() {
			instance.transform.setToScaling(info.getScale());
			instance.transform.translate(info.getTranslation());
			instance.transform.rotate(info.getRotation());
	}
}
