#include "PAMFormatter.h"


PAMFormatter::~PAMFormatter()
{
}

void PAMFormatter::loadImage(string file_name)
{
	ifstream file(file_name, ifstream::binary);
	string header;

	vector<int> values;
	regex rx("[0-9]+");
	smatch match;
	for (int i = 0; i < 4; i++)
	{
		getline(file, header);
		regex_search(header, match, rx);
		int value = stoi(match[0]);
		values.push_back(value);
	}
	width = values[1];
	height = values[2];
	depth = values[3];
	getline(file, header);
	getline(file, header);
	getline(file, header);

	bitmap.resize(width*height * 4);
	file.read((char*)bitmap.data(), bitmap.size());

	
	//file.read((char*)bitmap.data(), bitmap.size());
	//reverse(bitmap.begin(), bitmap.end());
	file.close();
}

void PAMFormatter::exportImage(string fileName)
{
	ofstream file(fileName, ios::binary | ios::out);

	file << "P7\n";
	file << "WIDTH ";
	file << width << "\n";
	file << "HEIGHT ";
	file << height << "\n";
	file << "DEPTH ";
	file << 4 << "\n";
	file << "MAXVAL ";
	file << 255 << "\n";
	file << "TUPLTYPE RGB_ALPHA" << "\n";
	file << "ENDHDR\n";

	file.write((const char*)bitmap.data(), bitmap.size());

	file.close();
}

