#pragma once

#include "PAMFormatter.h"
#include "BMPFormatter.h"
#include "MyFrormatter.h"
#include <regex>


class Formatter
{
private:
	int width;
	int height;
	vector<uint8_t> bitmap;
	vector<Layer> layers;
	map<string, Selection> selections;
	vector<CompositeOperation*> compositeOperation;

public:
	Formatter();
	~Formatter();

	vector<Layer> getLayers()
	{
		return layers;
	}

	map<string, Selection> getSelections()
	{
		return selections;
	}

	vector<CompositeOperation*> getCompositeOperation() const
	{
		return compositeOperation;
	}

	int getHeight() const
	{
		return height;
	}

	int getWidth() const
	{
		return width;
	}

	void setHeight(int h)
	{
		height = h;
	}

	void setWidth(int w)
	{
		width = w;
	}

	void setBitmap(vector<uint8_t> _bitmap)
	{
		bitmap = _bitmap;
	}

	vector<uint8_t> getBitmap() const
	{
		return bitmap;
	}

	void loadImage(string file_name);

	void loadImage(string file_name, vector<Operation*> basicOp);

	void exportImage(string fileName);
	
};

