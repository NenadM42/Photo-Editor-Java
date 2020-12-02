#include "BMPFormatter.h"


BMPFormatter::~BMPFormatter()
{
}

void BMPFormatter::loadImage(string f_n)
{
	string file_name = f_n;
	ifstream input(file_name, ios_base::binary);

	input.read((char*)&file_header, sizeof(file_header));
	input.read((char*)&info_header, sizeof(info_header));
	input.read((char*)&color_header, sizeof(color_header));

	input.seekg(file_header.offset_pixel_start, input.beg);
	bitmap.resize(info_header.width * info_header.height * 4);
	input.read((char*)bitmap.data(), bitmap.size());

	input.close();


	vector<uint8_t> bitmapHelp;
	bitmapHelp.resize(info_header.width*info_header.height * 4);


	/*			for 32 bit
			bitmapHelp[y*info_header.width * 4 + x * 4 + 0] = bitmap[y2*info_header.width * 4 + x * 4 + 2];
			bitmapHelp[y*info_header.width * 4 + x * 4 + 1] = bitmap[y2*info_header.width * 4 + x * 4 + 1];
			bitmapHelp[y*info_header.width * 4 + x * 4 + 2] = bitmap[y2*info_header.width * 4 + x * 4 + 0];
			bitmapHelp[y*info_header.width * 4 + x * 4 + 3] = bitmap[y2*info_header.width * 4 + x * 4 + 3];
	*/

	

	for (int y = info_header.height - 1, y2 = 0; y >= 0; y--, y2++)
	{
		for (int x = 0; x < info_header.width; x++)
		{
			bitmapHelp[y*info_header.width * 4 + x * 4 + 0] = bitmap[y2*info_header.width * 3 + x * 3 + 2];
			bitmapHelp[y*info_header.width * 4 + x * 4 + 1] = bitmap[y2*info_header.width * 3 + x * 3 + 1];
			bitmapHelp[y*info_header.width * 4 + x * 4 + 2] = bitmap[y2*info_header.width * 3 + x * 3 + 0];
			bitmapHelp[y*info_header.width * 4 + x * 4 + 3] = 255;
		}
	}
	bitmap = bitmapHelp;
}

void BMPFormatter::exportImage(const char * file_name)
{
	ofstream of{ file_name, ios_base::binary };
	file_header.offset_pixel_start = sizeof(BMPFileHeader) + sizeof(BMPInfoHeader) + sizeof(BMPColorHeader);
	if (of)
	{

		vector<uint8_t> bitmapHelp;
		bitmapHelp.resize(info_header.width*info_header.height * 4);

		for (int y = info_header.height - 1, y2 = 0; y >= 0; y--, y2++)
		{
			for (int x = 0; x < info_header.width; x++)
			{
				bitmapHelp[y*info_header.width * 4 + x * 4 + 0] = bitmap[y2*info_header.width * 4 + x * 4 + 2];
				bitmapHelp[y*info_header.width * 4 + x * 4 + 1] = bitmap[y2*info_header.width * 4 + x * 4 + 1];
				bitmapHelp[y*info_header.width * 4 + x * 4 + 2] = bitmap[y2*info_header.width * 4 + x * 4 + 0];
				bitmapHelp[y*info_header.width * 4 + x * 4 + 3] = bitmap[y2*info_header.width * 4 + x * 4 + 3];
			}
		}
		bitmap = bitmapHelp;

		of.write((const char*)&file_header, sizeof(file_header));
		of.write((const char*)&info_header, sizeof(info_header));
		of.write((const char*)&color_header, sizeof(color_header));
		of.write((const char*)bitmap.data(), bitmap.size());
	}
	else
	{
		std::cout << "Ne otvara fajl!\n";
	}

	of.close();
}
