#pragma once
#include <iostream>
#include <string>

using namespace std;;
class GIndeks
{

	friend ostream& operator<<(ostream &it, const GIndeks&)
	{
		return it << "Nevalidan indeks!\n";
	}
};

