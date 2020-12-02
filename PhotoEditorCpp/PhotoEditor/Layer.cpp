#include "Layer.h"



Layer::~Layer()
{
}

vector<unsigned char> Layer::getPixelsBitmap()
{
	vector<unsigned char> bitmap;
	for (int i = 0; i < height; i++)
	{
		for (int j = 0; j < width; j++)
		{
			bitmap.push_back(pixels[i][j].getBlue());
			bitmap.push_back(pixels[i][j].getGreen());
			bitmap.push_back(pixels[i][j].getRed());
			bitmap.push_back(pixels[i][j].getAlfa());
		}
	}
	return bitmap;
}

void Layer::loadPixelsWithBitmap(vector<uint8_t>& bitmap, int oldWidth, int oldHeight)
{
	pixels.resize(height);
	for (int i = 0; i < height; i++)
	{
		pixels[i].resize(width * 4);
	}


	for (int i = 0; i < height; i++)
	{
		for (int j = 0; j < width; j++)
		{
			pixels[i][j].setBlue(bitmap[(i*width * 4 + j * 4) + 0]);
			pixels[i][j].setGreen(bitmap[(i*width * 4 + j * 4) + 1]);
			pixels[i][j].setRed(bitmap[(i*width * 4 + j * 4) + 2]);
			pixels[i][j].setAlfa(bitmap[(i*width * 4 + j * 4) + 3]);
		}
	}
}

void Layer::loadPixels()
{
	pixels.resize(height);
	for (int i = 0; i < height; i++)
	{
		pixels[i].resize(width * 4);
	}


	for (int i = 0; i < height; i++)
	{
		for (int j = 0; j < width; j++)
		{
			pixels[i][j].setBlue(bmpFormatter.getPixelAtPosition((i*width * 4 + j * 4) + 0));
			pixels[i][j].setGreen(bmpFormatter.getPixelAtPosition((i*width * 4 + j * 4) + 1));
			pixels[i][j].setRed(bmpFormatter.getPixelAtPosition((i*width * 4 + j * 4) + 2));
			pixels[i][j].setAlfa(bmpFormatter.getPixelAtPosition((i*width * 4 + j * 4) + 3));
		}
	}
}

void Layer::expandImage(int newWidth, int newHeight)
{
	newWidth = max(width, newWidth);
	newHeight = max(height, newHeight);
	pixels.resize(newHeight);


	for (int i = 0; i < newHeight; i++)
	{
		pixels[i].resize(newWidth);
	}

	for (int i = height; i < newHeight; i++)
	{
		for (int j = width; j < newWidth; j++)
		{
			pixels[i][j].setBlue(0);
			pixels[i][j].setGreen(0);
			pixels[i][j].setRed(0);
			pixels[i][j].setAlfa(0);
		}
	}

	height = newHeight;
	width = newWidth;

}

void Layer::createEmptyLayer()
{
	pixels.resize(height);
	for (int i = 0; i < pixels.size(); i++)
	{
		pixels[i].resize(width);
	}

	int r, g, b, a;
	cout << "Unesite r,g,b :\n";

	cin >> r >> g >> b;
	a = 0;


	for (int i = 0; i < height; i++)
	{
		for (int j = 0; j < width; j++)
		{
			pixels[i][j].setBlue(r);
			pixels[i][j].setGreen(g);
			pixels[i][j].setRed(b);
			pixels[i][j].setAlfa(a);
		}
	}
}
