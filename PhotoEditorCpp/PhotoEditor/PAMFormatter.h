#pragma once

#include <string>
#include <fstream>
#include <iostream>
#include <vector>
#include <regex>

using namespace std;

class PAMFormatter
{
private:
	int width;
	int height;
	int depth;
	vector<uint8_t> bitmap;
public:
	PAMFormatter(string file_name)
	{
		loadImage(file_name);
	}

	PAMFormatter()
	{

	}

	~PAMFormatter();
	
	

	void setBitmap(vector<uint8_t> _bitmap)
	{
		bitmap = _bitmap;
	}

	void setHeight(int h) 
	{
		height = h;
	}

	int setWidth(int w) 
	{
		return width = w;
	}

	void loadImage(string file_name);

	void exportImage(string fileName);

	vector<uint8_t> getBitmap() const
	{
		return bitmap;
	}

	int getWidth() const
	{
		return width;
	}

	int getHeight() const
	{
		return height;
	}

	unsigned short getPixelAtPosition(int pos)
	{
		return bitmap[pos];
	}



};

