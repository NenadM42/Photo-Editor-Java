#pragma once

#include "Pixel.h"
#include "BMPFormatter.h"
#include "PAMFormatter.h"
#include <algorithm>

using namespace std;

class Layer
{
private:
	int width;
	int height;
	
	bool active = 1;
	bool inImage = 1;

	double visibility;
	BMPFormatter bmpFormatter;
	//PAMFormatter pamFormatter;

	vector<vector<Pixel>> pixels;
public:
	Layer(int w, int h, double v = 100) : width(w), height(h), visibility(v)
	{
		createEmptyLayer();
		//printPixels();
	}

	Layer(BMPFormatter formatter) : bmpFormatter(formatter)
	{
		width = formatter.getWidth();
		height = formatter.getHeight();
		loadPixels();
	}

	Layer(int h, int w, vector<uint8_t> bitmap, int oldWidth, int oldHeight, double v = 100,bool actInOp = true,bool actInImg = true) : height(h), width(w), visibility(v), active(actInOp),inImage(actInImg)
	{
		loadPixelsWithBitmap(bitmap,oldWidth,oldHeight);
	}



	~Layer();

	int getWidth() const
	{
		return width;
	}

	int getHeight() const
	{
		return height;
	}

	bool isInFinalImage() const
	{
		return inImage;
	}

	void setInImage(bool status)
	{
		inImage = status;
	}

	double getVisibility() const
	{
		return visibility;
	}

	bool isActive() const
	{
		return active;
	}

	void setActiveStatus(bool activeStatus)
	{
		active = activeStatus;
	}

	void setPixels(vector<vector<Pixel>> new_pixels)
	{
		pixels = new_pixels;
	}

	void setVisibility(double value)
	{
		visibility = value;
	}

	double getVisibility()
	{
		return visibility;
	}

	vector<vector<Pixel>> &getPixels()
	{
		return pixels;
	}

	int getPixelsSize() const
	{
		return width * height * 4 * sizeof(short);
	}

	vector<unsigned char> getPixelsBitmap();

	void loadPixelsWithBitmap(vector<uint8_t> &bitmap, int oldWidth, int oldHeight);

	void loadPixels();

	// Prosirimo sliku
	void expandImage(int newWidth, int newHeight);


	void createEmptyLayer();
};