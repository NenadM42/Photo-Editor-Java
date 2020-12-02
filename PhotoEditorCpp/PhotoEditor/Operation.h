#pragma once
#include "Selection.h"
#include "Layer.h"
#include <vector>


static int numberOfOperations=0;

class Operation
{
private:
	int index;

public:

	Operation()
	{
		index = numberOfOperations;
		numberOfOperations++;
	}
	~Operation();

	int getIndex() const
	{
		return index;
	}

	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>> &pixels,const int value, const int width, const int height) = 0;

};


