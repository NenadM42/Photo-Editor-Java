#pragma once

#include <fstream>
#include <vector>
#include <iostream>

using namespace std;

class BMPFormatter
{
private:
#pragma pack(push, 1)
	struct BMPFileHeader
	{
		uint16_t file_type{ 0x4D42 };
		uint32_t file_size{ 0 };
		uint16_t unused1{ 0 };
		uint16_t unused2{ 0 };
		uint32_t offset_pixel_start{ 0x7A000000 };
	};

	struct BMPInfoHeader
	{
		uint32_t size{ 0 };
		int32_t width{ 0 };
		int32_t height{ 0 };
		uint16_t planes{ 1 };
		uint16_t bit_count{ 32 };
		uint32_t compression{ 0 };
		uint32_t size_image{ 0 };
		int32_t x_pixels_per_meter{ 0 };
		int32_t y_pixels_per_meter{ 0 };
		uint32_t colors_used{ 0 };
		uint32_t colors_important{ 0 };
	};

	struct BMPColorHeader
	{
		uint32_t red_mask{ 0x00ff0000 };
		uint32_t green_mask{ 0x0000ff00 };
		uint32_t blue_mask{ 0x000000ff };
		uint32_t alpha_mask{ 0xff000000 };
		uint32_t color_space_type{ 0x73524742 };
		uint32_t unused[16]{ 0 };
	};
#pragma pack(pop)

	BMPFileHeader file_header;
	BMPInfoHeader info_header;
	BMPColorHeader color_header;

public:
	vector<uint8_t> bitmap;

	BMPFormatter(string file_name)
	{
		loadImage(file_name);
	}

	BMPFormatter()
	{
		info_header.size = sizeof(BMPInfoHeader) + sizeof(BMPColorHeader);
	}

	~BMPFormatter();

	void setFileSize(int pixelsSize)
	{
		file_header.file_size = sizeof(BMPFileHeader) + sizeof(BMPInfoHeader) + sizeof(BMPColorHeader) + pixelsSize;
	}

	void setBitmap(vector<uint8_t> v)
	{
		bitmap = v;
	}

	void setWidth(int w)
	{
		info_header.width = w;
	}

	void setHeight(int h)
	{
		info_header.height = h;
	}

	int getWidth() const
	{
		return info_header.width;
	}

	int getHeight() const
	{
		return info_header.height;
	}

	unsigned short getPixelAtPosition(int pos) const
	{
		return bitmap[pos];
	}

	vector<uint8_t> getBitmap() const
	{
		return bitmap;
	}

	void loadImage(string f_n);

	void exportImage(const char* file_name);

};