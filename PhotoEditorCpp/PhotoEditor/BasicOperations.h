#pragma once
#include "Operation.h"

class AddConstant :
	public Operation
{
public:
	AddConstant()
	{

	}
	~AddConstant()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class substractConstant :
	public Operation
{
private:
public:
	substractConstant()
	{

	}
	~substractConstant()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class inverseSubstraction :
	public Operation
{
public:
	inverseSubstraction()
	{

	}
	~inverseSubstraction()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class multiplyConstant :
	public Operation
{
public:
	multiplyConstant()
	{

	}
	~multiplyConstant()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;

};

class divideConstant :
	public Operation
{
public:
	divideConstant()
	{

	}
	~divideConstant()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class inverseDivision :
	public Operation
{
public:
	inverseDivision()
	{

	}
	~inverseDivision()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class Power :
	public Operation
{
public:
	Power()
	{

	}
	~Power()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class Log :
	public Operation
{
public:
	Log()
	{

	}
	~Log()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class Abs :
	public Operation
{
public:
	Abs()
	{

	}
	~Abs()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class Min :
	public Operation
{
public:
	Min()
	{

	}
	~Min()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class Max :
	public Operation
{
public:
	Max()
	{

	}
	~Max()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class Inversion :
	public Operation
{
public:
	Inversion()
	{

	}
	~Inversion()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class GrayTone :
	public Operation
{
public:
	GrayTone()
	{

	}
	~GrayTone()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class BlackAndWhite :
	public Operation
{
public:
	BlackAndWhite()
	{

	}
	~BlackAndWhite()
	{

	}
	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};

class Median :
	public Operation
{
private:

	bool isValidLocation(int y, int x, int height, int width)
	{
		return (y >= 0 && y < height && x >= 0 && x < width);
	}

public:
	Median()
	{

	}
	~Median()
	{

	}


	// Inherited via Operation
	virtual void doOperation(vector<pair<int, int>> activePixels, vector<vector<Pixel>>& pixels, const int value, const int width, const int height) override;
};