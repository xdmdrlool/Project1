package fr.clement.InterfaceGraphique;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class FormRect extends Form {

	private String type;
	private Color color;
	private int[] arg;


	public FormRect(Color color, int[] arg) {
		super(color, arg);
		this.type="RECT";
		this.setColor(color);
		this.setArg(arg);
		// TODO Auto-generated constructor stub
	}


	public void draw(Graphics graphic) {
		int x =this.arg[0];
		int y =this.arg[1];
		int width =this.arg[2];
		int height =this.arg[3];
		graphic.setColor(this.color);
		graphic.fillRect(x, y, width, height);
	}
	
	
	public String getType() {
		return type;
	}


	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}


	public int[] getArg() {
		return arg;
	}


	public void setArg(int[] arg) {
		this.arg = arg;
	}





}




