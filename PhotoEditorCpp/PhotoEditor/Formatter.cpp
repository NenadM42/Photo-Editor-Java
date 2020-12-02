#include "Formatter.h"



Formatter::Formatter()
{
}


Formatter::~Formatter()
{
}

void Formatter::loadImage(string file_name)
{
	// Uzmemo ime formata
	string fileName = file_name;
	regex rx("[^.]*$");
	smatch match;
	regex_search(fileName, match, rx);

	string formatName = match[0];
	// U zavisnosti koji je format, taj formater iskoristimo i smestimo podatke u ovu klasu

	if (formatName == "bmp")
	{
		BMPFormatter bmpFormatter(file_name);
		height = bmpFormatter.getHeight();
		width = bmpFormatter.getWidth();
		bitmap = bmpFormatter.getBitmap();
	}
	else if (formatName == "pam")
	{
		PAMFormatter pamFormatter(file_name);
		height = pamFormatter.getHeight();
		width = pamFormatter.getWidth();
		bitmap = pamFormatter.getBitmap();
	}
	
}



void Formatter::loadImage(string fileName, vector<Operation*> basicOp)
{
	MyFrormatter myFormatter;
	myFormatter.importImage(fileName, basicOp);
	selections = myFormatter.getSelections();
	layers = myFormatter.getLayers();
	compositeOperation = myFormatter.getCompositeOperations();
	width = layers[0].getWidth();
	height = layers[0].getHeight();
}

void Formatter::exportImage(string fileName)
{
	// Uzmemo ime formata
	regex rx("[^.]*$");
	smatch match;
	regex_search(fileName, match, rx);

	string formatName = match[0];
	// U zavisnosti koji je format, taj formater iskoristimo i smestimo podatke u ovu klasu

	if (formatName == "bmp")
	{
		BMPFormatter bmpFormatter;
		bmpFormatter.setHeight(height);
		bmpFormatter.setWidth(width);
		bmpFormatter.setBitmap(bitmap);
		bmpFormatter.exportImage(fileName.c_str());
	}
	else if (formatName == "pam")
	{
		PAMFormatter pamFormatter;
		pamFormatter.setHeight(height);
		pamFormatter.setWidth(width);
		pamFormatter.setBitmap(bitmap);
		pamFormatter.exportImage(fileName);
	}
}
