#include "CompositeOperation.h"



CompositeOperation::CompositeOperation()
{
}


CompositeOperation::~CompositeOperation()
{
}

vector<int> CompositeOperation::getSignature() const
{
	vector<int> signature;
	for (int i = 0; i < basicOperations.size(); i++)
	{
		signature.push_back(basicOperations[i].first->getIndex());
		signature.push_back(basicOperations[i].second);
	}
	return signature;
}

void CompositeOperation::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (int i = 0; i < basicOperations.size(); i++)
	{
		basicOperations[i].first->doOperation(activePixels, pixels, basicOperations[i].second,width,height);
	}
}
