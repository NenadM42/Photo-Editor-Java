#pragma once
#include <vector>

using namespace std;

class Rectangle
{
private:
	int height;
	int width;
	int startPositionY;
	int startPositoinX;
public:

	Rectangle(int h, int w, int sY, int sX) : height(h), width(w), startPositionY(sY), startPositoinX(sX)
	{
	
	}

	~Rectangle();

	int getHeight() const
	{
		return height;
	}

	int getWidth() const
	{
		return width;
	}

	int getStartPositionY() const
	{
		return startPositionY;
	}

	int getStartPositionX() const
	{
		return startPositoinX;
	}

};

