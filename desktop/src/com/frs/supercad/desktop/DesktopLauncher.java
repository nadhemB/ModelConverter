package com.frs.supercad.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.frs.supercad.ModelConverter;
import com.frs.supercad.modelviewer.ViewerScreen;

import javax.swing.*;

public class DesktopLauncher {
	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 1024;
		config.samples = 5;


		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame(config);
			}
		});


	}
}
