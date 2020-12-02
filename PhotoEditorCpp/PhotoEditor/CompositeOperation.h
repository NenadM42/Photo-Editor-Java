#pragma once
#include "Operation.h"
class CompositeOperation :
	public Operation
{
	vector<pair<Operation*,int>> basicOperations;
public:
	CompositeOperation();
	~CompositeOperation();

	void addOperation(Operation *op,int arg)
	{
		basicOperations.push_back(make_pair(op,arg));
	}

	// Potpis kompozitivne funkcije, cuva se index proste funkcije i njen argument
	vector<int> getSignature() const;


	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;

};

