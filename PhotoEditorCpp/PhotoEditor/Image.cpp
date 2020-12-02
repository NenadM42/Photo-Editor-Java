#include "Image.h"

int Image::normalizeValue(int val)
{
	if (val > 255)
		return 255;
	else if (val < 0)
		return 0;
	else
		return val;
}

Image::~Image()
{
}

void Image::saveCompositeFunctions(string fileName,int index)
{
	XMLDocument xmlDoc;
	XMLNode *node = xmlDoc.NewElement("Composite");
	xmlDoc.InsertFirstChild(node);

	XMLElement *xmlBasicInfo = xmlDoc.NewElement("BasicInfo");
	node->InsertFirstChild(xmlBasicInfo);

	vector<int> signature = compositeOperations[index]->getSignature();

	//Sad ispisemo kompozitnu-fju
	XMLElement *compositeInfo = xmlDoc.NewElement("Composite");
	compositeInfo->SetAttribute("NumberOfValues", signature.size());

	for (auto val : signature)
	{
		XMLElement *compositeValues = xmlDoc.NewElement("Value");
		compositeValues->SetAttribute("val", val);
		compositeInfo->InsertEndChild(compositeValues);
	}
	xmlBasicInfo->InsertEndChild(compositeInfo);

	fileName += ".fun.xml";
	xmlDoc.SaveFile(fileName.c_str());
}

void Image::loadCompositeFunction(string fileName)
{
	CompositeOperation *op = new CompositeOperation();

	XMLDocument xmlDoc;

	xmlDoc.LoadFile(fileName.c_str());

	XMLNode *node = xmlDoc.FirstChild();

	if (node == nullptr)
	{
		cout << "LOSE OTVORENO\n";
		return;
	}
	XMLElement *basicInfo = node->FirstChildElement("BasicInfo");

	XMLElement *funInfo = basicInfo->FirstChildElement("Composite");

	const XMLAttribute *numInfo = funInfo->FindAttribute("NumberOfValues");
	int num;
	numInfo->QueryIntValue(&num);


	XMLElement *values = funInfo->FirstChildElement("Value");
	const XMLAttribute *valueNum;
	for (int i = 0; i < num/2; i++)
	{
		int val1, val2;
		valueNum = values->FindAttribute("val");
		valueNum->QueryIntValue(&val1);
		values = values->NextSiblingElement("Value");
		
		valueNum = values->FindAttribute("val");
		valueNum->QueryIntValue(&val2);
		values = values->NextSiblingElement("Value");

		op->addOperation(basicOperations[val1], val2);
	}

	compositeOperations.push_back(op);
}	

#include "GIndeks.h"

void Image::fillSelection(string name, int r, int g, int b,int a)
{
	if (selections.find(name) == selections.end())
	{
		throw GIndeks();
	}

	if (r < 0)
		r = 0;
	else if (r > 255)
		r = 255;

	if (g < 0)
		g = 0;
	else if (g > 255)
		g = 255;
	
	if (b < 0)
		b = 0;
	else if (b > 255)
		b = 255;

	if (a < 0)
		a = 0;
	else if (a > 255)
		a = 255;

	for (auto &layer : layers)
	{
		vector<vector<Pixel>> &pix = layer.getPixels();
		
		vector<Rectangle> rectangles = selections[name].getRectangles();

		for (auto rect : rectangles)
		{
			int startY = rect.getStartPositionY();
			int startX = rect.getStartPositionX();
			int height = rect.getHeight();
			int width = rect.getWidth();

			for (int y = startY; y < startY + height; y++)
			{
				for (int x = startX; x < startX + width; x++)
				{
					pix[y][x].setRed(r);
					pix[y][x].setGreen(g);
					pix[y][x].setBlue(b);
					pix[y][x].setAlfa(a);
				}
			}
		}
	}
}

void Image::deleteLayer(int index)
{
	if (index < 0 || index > layers.size() - 1)
	{
		throw GIndeks();
	}
	layers.erase(layers.begin() + index);
}

void Image::deleteSelection(const string name)
{
	selections.erase(name);
}

void Image::setSelectionActiveStatus(string name, bool activeStatus)
{
	if (selections.find(name) == selections.end())
	{
		throw GIndeks();
	}
	else
	{
		selections[name].setActiveStatus(activeStatus);
	}
}

void Image::setLayerActivity(vector<int> layerActive)
{
	for (int i = 0; i < getNumberOfLayers(); i++)
	{
		layers[i].setActiveStatus(layerActive[i]);
	}
}

void Image::setLayerInFinalImage(vector<int> layerActive)
{
	for (int i = 0; i < getNumberOfLayers(); i++)
	{
		layers[i].setInImage(layerActive[i]);
	}
}

void Image::addSelection(Selection sel)
{
	if (selections.find(sel.getName()) != selections.end())
	{
		cout << "Selekcija vec postoji!\n";
	}
	else
	{
		selections.insert_or_assign(sel.getName(), sel);
	}
}

void Image::applyOperation(Operation * operation, const int value)
{
	activePixels.clear();
	// Nadjemo sve aktivne piksele
	vector<pair<int, int>> activePixelsCoordinates = getActivePixelsCoordinates();
	// Izvrsimo operaciju na svakom aktivnom layeru
	for (auto &layer : layers)
	{
		vector<vector<Pixel>> &pix = layer.getPixels();
		if (layer.isActive())
			operation->doOperation(activePixelsCoordinates, pix, value, width, height);

		// Normalizujemo rgb
		for (auto pixPosition : activePixelsCoordinates)
		{
			int y = pixPosition.first;
			int x = pixPosition.second;

			int val = normalizeValue(pix[y][x].getRed());
			pix[y][x].setRed(val);

			val = normalizeValue(pix[y][x].getGreen());
			pix[y][x].setGreen(val);

			val = normalizeValue(pix[y][x].getBlue());
			pix[y][x].setBlue(val);
		}
	}
}

vector<pair<int, int>> Image::getActivePixelsCoordinates()
{
	vector<pair<int, int>> activePixelsCoordinates;
	activePixelsCoordinates.clear();


	// Znaci svi su aktivni
	if (activeSelectionExists() == false)
	{
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				if (activePixels[make_pair(y, x)] == 0)
				{
					activePixels[make_pair(y, x)] = 1;
					activePixelsCoordinates.push_back(make_pair(y, x));
				}
			}
		}
		return activePixelsCoordinates;
	}

	// Uzmemo selektovane piksele
	for (auto &sel : selections)
	{
		// Ako je aktivna onda je obradimo
		if (sel.second.isActive())
		{
			vector<Rectangle> rectangles = sel.second.getRectangles();
			// Oznacimo aktivne piksele
			for (auto rect : rectangles)
			{
				int selectionHeight = rect.getHeight();
				int selectionWidth = rect.getWidth();
				int startY = rect.getStartPositionY();
				int startX = rect.getStartPositionX();


				cout << "startY: " << startY << " startX: " << startX << "\n";
 
				for (int y = startY; y < startY + selectionHeight; y++)
				{
					for (int x = startX; x < startX + selectionWidth; x++)
					{
						// Ako do sada nije oznacen piksel, dodamo ga u vektor
						if (activePixels[make_pair(y, x)] == 0)
						{
							activePixels[make_pair(y, x)] = 1;
							activePixelsCoordinates.push_back(make_pair(y, x));
						}
					}
				}
			}
		}
	}
	return activePixelsCoordinates;
}



bool Image::activeSelectionExists()
{
	for (auto sel : selections)
	{
		if (sel.second.isActive() == true)
			return true;
	}
	return false;
}

void Image::exportImage(string fileName)
{
	Formatter formatter;

	BMPFormatter bmpFormatter;


	formatter.setWidth(width);
	formatter.setHeight(height);

	vector<unsigned char> pixelsBitmap;

	vector < vector<Pixel>> firstPixels = layers[0].getPixels();
	

	// Obrada fotografije, tj spajanje u jednu
	for (int i = 0; i < height; i++)
	{
		for (int j = 0; j < width; j++)
		{
			double currentVisibility = 1.0;
			double finalRed = 0;
			double finalGreen = 0;
			double finalBlue = 0;
			double alpha;
			for (int k = 0; k < getNumberOfLayers(); k++)
			{
				if (layers[k].isInFinalImage() == false)
					continue;
				
				double layerVisibility = layers[k].getVisibility();
				layerVisibility /= 100.f;
				alpha = layers[k].getPixels()[i][j].getAlfa();
				alpha /= 255.f;

				double A = currentVisibility * layerVisibility * alpha;

				finalRed += A * (double)layers[k].getPixels()[i][j].getRed();
				finalGreen += A * (double)layers[k].getPixels()[i][j].getGreen();
				finalBlue += A * (double)layers[k].getPixels()[i][j].getBlue();
				currentVisibility -=  A;
			}
			firstPixels[i][j].setRed(finalRed);
			firstPixels[i][j].setGreen(finalGreen);
			firstPixels[i][j].setBlue(finalBlue);
			firstPixels[i][j].setAlfa((1-currentVisibility)*255.f);


		}
	}

	for (int i = 0; i < height; i++)
	{
		for (int j = 0; j < width; j++)
		{
			pixelsBitmap.push_back(firstPixels[i][j].getBlue());
			pixelsBitmap.push_back(firstPixels[i][j].getGreen());
			pixelsBitmap.push_back(firstPixels[i][j].getRed());
			pixelsBitmap.push_back(firstPixels[i][j].getAlfa());
		}
	}
	formatter.setBitmap(pixelsBitmap);
	formatter.exportImage(fileName);
}

void Image::addLayer(string file_name, double visibility)
{
	Formatter formatter;
	formatter.loadImage(file_name);
	int w = max(width, formatter.getWidth());
	int h = max(height, formatter.getHeight());

	if (h > height || w > width)
	{
		for (auto &it : layers)
		{
			it.expandImage(w, h);
		}
	}

	width = max(width, w);
	height = max(height, h);

	cout << width << " " << height << "\n";


	int oldWidth = formatter.getWidth();
	int oldHeight = formatter.getHeight();

	layers.push_back(Layer(height, width, formatter.getBitmap(), oldWidth, oldHeight, visibility));
}

void Image::addLayer(int w, int h, double visibility)
{

	// Ekspandujemo ako treba
	if (h > height || w > width)
	{
		for (auto &it : layers)
		{
			it.expandImage(w, h);
		}
	}
	width = max(width, w);
	height = max(height, h);

	layers.push_back(Layer(width, height, visibility));
}

void Image::loadSavedSetup(const string path)
{
	Formatter formatter;
	formatter.loadImage(path,basicOperations);
	selections = formatter.getSelections();
	layers = formatter.getLayers();
	width = formatter.getWidth();
	height = formatter.getHeight();
	compositeOperations = formatter.getCompositeOperation();
}

void Image::saveSetup(string path)
{
	MyFrormatter myFormatter;
	myFormatter.exportImage(layers, selections, compositeOperations, height, width, path);
}
