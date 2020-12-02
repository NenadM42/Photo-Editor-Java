#pragma once

#include "Layer.h"
#include "Selection.h"
#include <map>
#include <algorithm>
#include "Formatter.h"
#include "MyFrormatter.h"
#include "CompositeOperation.h"

using namespace std;

class Image
{
private:
	vector<Layer> layers;
	map<string, Selection> selections;
	map<pair<int, int>, bool> activePixels;

	vector<CompositeOperation*> compositeOperations;
	vector<Operation*> basicOperations;

	int width;
	int height;
	//BasicOperation basicOperation;

	int normalizeValue(int val);

	vector<pair<int, int>> getActivePixelsCoordinates();


	bool activeSelectionExists();


	int getPixelsSize()
	{
		return width * 4 * height * sizeof(short);
	}

public:

	Image(string file_name, double visibility = 100)
	{
		width = 0;
		height = 0;

		// Pravimo prvi layer
		addLayer(file_name, visibility);
	}

	Image(int w,int h, double visibility) : width(w),height(h)
	{
		// Pravimo prvi layer
		addLayer(w, h);
	}	

	Image()
	{

	}
	
	~Image();

	void setBasicOperations(vector<Operation*> _basicOperations)
	{
		basicOperations = _basicOperations;
	}

	void addCompositeOperation(CompositeOperation *op)
	{
		compositeOperations.push_back(op);
	}

	void applyCompositeOperation(int index)
	{
		if (index < 0 || index > compositeOperations.size())
		{
			cout << "Nevalidan index!\n" << " " << compositeOperations.size() << "-> Max index\n";
			return;
		}
		applyOperation(compositeOperations[index]);
	}

	void saveCompositeFunctions(string fileName,int index);

	void loadCompositeFunction(string fileName);

	int getWidth() const
	{
		return width;
	}

	int getHeight() const
	{
		return height;
	}

	int setWidth(int w)
	{
		width = w;
	}

	int setHeight(int h)
	{
		height = h;
	}

	int getNumberOfCompositeFunction() const
	{
		return compositeOperations.size();
	}

	void setLayers(vector<Layer> _layers)
	{
		layers = _layers;
	}

	void setSelections(map<string, Selection> _selections)
	{
		selections = _selections;
	}

	void setCompositeOperation(vector<CompositeOperation*> _composits)
	{
		compositeOperations = _composits;
	}

	void setSelectionActiveStatus(string name, bool activeStatus);

	int getNumberOfLayers() const
	{
		return layers.size();
	}

	void fillSelection(string name, int r, int g, int b, int a);

	void deleteLayer(int index);

	void deleteSelection(const string name);

	void setLayerActivity(vector<int> layerActive);

	void setLayerInFinalImage(vector<int> layerActive);

	void addSelection(Selection sel);

	void applyOperation(Operation *operation, const int value = 0);


	void exportImage(string fileName);

	
	// Za layer sa putanjom do slike
	void addLayer(string file_name, double visibility = 100);

	void addLayer(int w, int h, double visibility = 100);

	void loadSavedSetup(const string path);

	void saveSetup(string path);



};