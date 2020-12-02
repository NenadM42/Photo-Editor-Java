#pragma once
#include "Rectangle.h"
#include <string>
#include <set>                                                                                             

class Selection
{
private:
	
	
	vector<Rectangle> rectangles;
	string name;
	bool active;

public:
	Selection(string _name,vector<Rectangle> rects, bool _active = true) : rectangles(rects), name(_name) , active(_active)
	{

	}

	Selection()
	{

	}

	~Selection();

	bool isActive() const
	{
		return active;
	}

	void setActiveStatus(bool activeStatus)
	{
		active = activeStatus;
	}

	string getName() const
	{
		return name;
	}

	vector<Rectangle> getRectangles() const
	{
		return rectangles;
	}
};

