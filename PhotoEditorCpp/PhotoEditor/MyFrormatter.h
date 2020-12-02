#pragma once

#include <fstream>
#include <iostream>
#include "tinyxml2.h"
#include <vector>
#include <map>
#include "Layer.h"
#include "Selection.h"
#include "Rectangle.h"
#include "CompositeOperation.h"

using namespace tinyxml2;
using namespace std;

class MyFrormatter
{
private:
	map<string, Selection> selections;
	vector<Layer> layers;
	vector<CompositeOperation*> compositeOperations;
public:
	MyFrormatter();
	~MyFrormatter();

	map<string, Selection> getSelections()
	{
		return selections;
	}

	vector<Layer> getLayers()
	{
		return layers;
	}

	vector<CompositeOperation*> getCompositeOperations() const
	{
		return compositeOperations;
	}


	void exportImage(vector<Layer> layers,
		map<string, Selection> selections,vector<CompositeOperation*> compositeOperations ,int height, int width, string fileName);

	void importImage(string file_name, vector<Operation*> basicOperations);
};

