#include "BasicOperations.h"


void AddConstant::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>> &pixels,const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		pixels[y][x].setRed(pixels[y][x].getRed() + value);
		pixels[y][x].setGreen(pixels[y][x].getGreen() + value);
		pixels[y][x].setBlue(pixels[y][x].getBlue() + value);
	}
}

void substractConstant::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		pixels[y][x].setRed(pixels[y][x].getRed() - value);
		pixels[y][x].setGreen(pixels[y][x].getGreen() - value);
		pixels[y][x].setBlue(pixels[y][x].getBlue() - value);
	}
}

void inverseSubstraction::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		pixels[y][x].setRed(value - pixels[y][x].getRed());
		pixels[y][x].setGreen(value - pixels[y][x].getGreen());
		pixels[y][x].setBlue(value - pixels[y][x].getBlue());
	}
}

void multiplyConstant::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		pixels[y][x].setRed(value * pixels[y][x].getRed());
		pixels[y][x].setGreen(value * pixels[y][x].getGreen());
		pixels[y][x].setBlue(value * pixels[y][x].getBlue());
	}
}

void divideConstant::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		pixels[y][x].setRed(pixels[y][x].getRed() / value);
		pixels[y][x].setGreen(pixels[y][x].getGreen() / value);
		pixels[y][x].setBlue(pixels[y][x].getBlue() / value);
	}
}

void inverseDivision::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		pixels[y][x].setRed(value / pixels[y][x].getRed());
		pixels[y][x].setGreen(value / pixels[y][x].getGreen());
		pixels[y][x].setBlue(value / pixels[y][x].getBlue());
	}
}

void Power::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		pixels[y][x].setRed(pow(pixels[y][x].getRed(),value));
		pixels[y][x].setGreen(pow(pixels[y][x].getGreen(),value));
		pixels[y][x].setBlue(pow(pixels[y][x].getBlue(),value));
	}
}

void Log::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		pixels[y][x].setRed(log(pixels[y][x].getRed()));
		pixels[y][x].setGreen(log(pixels[y][x].getGreen()));
		pixels[y][x].setBlue(log(pixels[y][x].getBlue()));
	}
}

void Abs::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		pixels[y][x].setRed(abs(pixels[y][x].getRed()));
		pixels[y][x].setGreen(abs(pixels[y][x].getGreen()));
		pixels[y][x].setBlue(abs(pixels[y][x].getBlue()));
	}
}

void Min::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		pixels[y][x].setRed(max(pixels[y][x].getRed(), value));
		pixels[y][x].setGreen(max(pixels[y][x].getGreen(), value));
		pixels[y][x].setBlue(max(pixels[y][x].getBlue(), value));
	}
}

void Max::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		pixels[y][x].setRed(min(pixels[y][x].getRed(), value));
		pixels[y][x].setGreen(min(pixels[y][x].getGreen(), value));
		pixels[y][x].setBlue(min(pixels[y][x].getBlue(), value));
	}
}

void Inversion::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		int Max = 255;

		pixels[y][x].setRed(Max - pixels[y][x].getRed());
		pixels[y][x].setGreen(Max - pixels[y][x].getGreen());
		pixels[y][x].setBlue(Max - pixels[y][x].getBlue());
	}
}

void GrayTone::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		int avg = (pixels[y][x].getRed() + pixels[y][x].getGreen() + pixels[y][x].getBlue()) / 3;

		pixels[y][x].setRed(avg);
		pixels[y][x].setGreen(avg);
		pixels[y][x].setBlue(avg);
	}
}

void BlackAndWhite::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;
		int avg = (pixels[y][x].getRed() + pixels[y][x].getGreen() + pixels[y][x].getBlue()) / 3;
		int value = avg < 127 ? 0 : 255;

		pixels[y][x].setRed(value);
		pixels[y][x].setGreen(value);
		pixels[y][x].setBlue(value);
	}
}

void Median::doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height)
{
	for (auto pixPosition : activePixels)
	{
		int y = pixPosition.first;
		int x = pixPosition.second;

		int counter = 1;

		vector<int> reds;
		vector<int> greens;
		vector<int> blues;


		int avgR = pixels[y][x].getRed();
		int avgG = pixels[y][x].getGreen();
		int avgB = pixels[y][x].getBlue();

		for (int i = x - 1; i < x + 1; i++)
		{
			if (isValidLocation(y - 1, i, height, width))
			{
				counter++;
				reds.push_back(pixels[y - 1][i].getRed());
				greens.push_back(pixels[y - 1][i].getGreen());
				blues.push_back(pixels[y - 1][i].getBlue());
			}
			if (isValidLocation(y + 1, i, height, width))
			{
				counter++;
				reds.push_back(pixels[y + 1][i].getRed());
				greens.push_back(pixels[y + 1][i].getGreen());
				blues.push_back(pixels[y + 1][i].getBlue());
			}
		}
		// Samo proverimo jos levo i desno
		if (isValidLocation(y, x - 1, height, width))
		{
			counter++;
			reds.push_back(pixels[y ][x-1].getRed());
			greens.push_back(pixels[y][x-1].getGreen());
			blues.push_back(pixels[y][x-1].getBlue());
		}
		if (isValidLocation(y, x + 1, height, width))
		{
			counter++;
			reds.push_back(pixels[y][x+1].getRed());
			greens.push_back(pixels[y][x+1].getGreen());
			blues.push_back(pixels[y][x+1].getBlue());
		}

		// Sortiranje pomocu lambda funkcije
		sort(reds.begin(), reds.end(), [](int a, int b) {
			return a < b; });
		sort(greens.begin(), greens.end(), [](int a, int b) {
			return a < b; });
		sort(blues.begin(), blues.end(), [](int a, int b) {
			return a < b; });

		int rValue = reds[reds.size() / 2];
		int gValue = greens[greens.size() / 2];
		int bValue = blues[blues.size() / 2];
		pixels[y][x].setRed(rValue);
		pixels[y][x].setGreen(gValue);
		pixels[y][x].setBlue(bValue);
	}
}
