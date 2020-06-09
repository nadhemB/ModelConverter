package com.frs.supercad.utilities;

import com.badlogic.gdx.graphics.Pixmap;

public class DrawingHelper {


	public static Pixmap createGridTexture(int width, int height, int x, int y, int division){
		Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
		pixmap.setColor(235/255f, 235/255f, 235/255f, 1);
		pixmap.fill();
		int pointV;
		int pointH;
		float scaleW = width/x;
		float scaleH = height/y;
		// Draw a blue-colored horizental line on square
		for(int i = 0; i < 2* x  ; i ++){
			pointV = Math.round(i * scaleW);
			pixmap.setColor(55/255f, 55/255f, 55/255f, 0.5f);
			if(i%division == 0)
				pixmap.setColor(10/255f, 10/255f, 10/255f, 0.8f);
			pixmap.drawLine(pointV, 0, pointV, height);
		}
		for(int i = 0; i < 2 * y  ; i ++){
			pointH = Math.round(i * scaleH);
			pixmap.setColor(55/255f, 55/255f, 55/255f, 0.5f);
			if(i%division == 0)
				pixmap.setColor(10/255f, 10/255f, 10/255f, 0.8f);
			pixmap.drawLine(0,pointH, width, pointH);
		}
		pixmap.setFilter(Pixmap.Filter.BiLinear);
		return pixmap;
	}

}
