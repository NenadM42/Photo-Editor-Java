#pragma once

#include <vector>
using namespace std;

class Pixel
{
private:
	int red;
	int green;
	int blue;
	int alpha;
public:
	Pixel(int r = 0, int g = 0, int b = 0, int a = 0) : red(r), green(g), blue(b), alpha(a) {}
	~Pixel();

	int getRed() { return red; };
	int getGreen() { return green; }
	int getBlue() { return blue; }
	int getAlfa() { return alpha; }

	void setRed(int value)
	{
		if (value > 255)
			value = 255;
		if (value < 0)
			value = 0;
		red = value;
	}

	void setGreen(int value)
	{
		if (value > 255)
			value = 255;
		if (value < 0)
			value = 0;
		green = value;
	}

	void setBlue(int value)
	{
		if (value > 255)
			value = 255;
		if (value < 0)
			value = 0;
		blue = value;
	}

	void setAlfa(int value)
	{
		alpha = value;
	}

};

