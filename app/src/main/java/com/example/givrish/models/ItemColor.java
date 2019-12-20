package com.example.givrish.models;

public class ItemColor {
	private  String colorID;
	private  String colorName;

public ItemColor(String colorID, String colorName) {
	this.colorID = colorID;
	this.colorName = colorName;
}

public String getColorID() {
	return colorID;
}

public String getColorName() {
	return colorName;
}

@Override
public String toString() {
	return  colorName;
}
}
