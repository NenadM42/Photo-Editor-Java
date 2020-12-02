#include "MyFrormatter.h"


MyFrormatter::MyFrormatter()
{
}


MyFrormatter::~MyFrormatter()
{
}

void MyFrormatter::exportImage(vector<Layer> layers,
	map<string, Selection> selections,vector<CompositeOperation*> compositeOperations,int height, int width,string fileName)
{
	XMLDocument xmlDoc;
	XMLNode *node = xmlDoc.NewElement("Photo");

	xmlDoc.InsertFirstChild(node);

	// Basic info about image
	XMLElement *xmlBasicInfo = xmlDoc.NewElement("BasicInfo");
	xmlBasicInfo->SetAttribute("Height", height);
	xmlBasicInfo->SetAttribute("Width", width);
	xmlBasicInfo->SetAttribute("Numberoflayers", layers.size());
	xmlBasicInfo->SetAttribute("Numberofselections", selections.size());
	xmlBasicInfo->SetAttribute("Numerofcomposite", compositeOperations.size());

	node->InsertFirstChild(xmlBasicInfo);

	// We go trough ever layer and save some info

	int cnt = 0;
	for (auto lay : layers)
	{
		XMLElement *layerInfo = xmlDoc.NewElement("Layer");

		string layerName = "layer_" + fileName + to_string(cnt) + ".bmp";
		cnt++;

		layerInfo->SetAttribute("Path", layerName.c_str());
		layerInfo->SetAttribute("Visibility", lay.getVisibility());
		layerInfo->SetAttribute("ActiveinPhoto", lay.isInFinalImage());
		layerInfo->SetAttribute("ActiveinOperations", lay.isActive());
		
		xmlBasicInfo->InsertEndChild(layerInfo);

		BMPFormatter bmpFormatter;
		bmpFormatter.setHeight(height);
		bmpFormatter.setWidth(width);
		bmpFormatter.setBitmap(lay.getPixelsBitmap());
		bmpFormatter.exportImage(layerName.c_str());
	}

	// Now we insert selections

	for (auto sel : selections)
	{
		vector<Rectangle> rects = sel.second.getRectangles();
		XMLElement *selectionInfo = xmlDoc.NewElement("Selection");
		selectionInfo->SetAttribute("Name", sel.second.getName().c_str());
		selectionInfo->SetAttribute("Active", sel.second.isActive());
		selectionInfo->SetAttribute("Numberofrectangles", rects.size());
		
		for (auto rect : rects)
		{
			XMLElement *rectInfo = xmlDoc.NewElement("Rectangle");
			rectInfo->SetAttribute("Y", rect.getStartPositionY());
			rectInfo->SetAttribute("X", rect.getStartPositionX());
			rectInfo->SetAttribute("Height", rect.getHeight());
			rectInfo->SetAttribute("Width", rect.getWidth());
			selectionInfo->InsertEndChild(rectInfo);
		}
		xmlBasicInfo->InsertEndChild(selectionInfo);
	}

	// Komopzitivne ubacimo
	
	for (auto comp : compositeOperations)
	{
		vector<int> signature = comp->getSignature();

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
	}
	xmlDoc.SaveFile(fileName.c_str());
}

void MyFrormatter::importImage(string fileName,vector<Operation*> basicOperations)
{
	int height;
	int width;
	int numberOfLayers;
	int numberOfSelections;
	int numberOfOperations;
	XMLDocument xmlDoc;

	xmlDoc.LoadFile(fileName.c_str());

	XMLNode *node = xmlDoc.FirstChild();

	if (node == nullptr)
	{
		cout << "XML dokument ne postoji!\n";
		return;
	}
	
	XMLElement *basicInfo = node->FirstChildElement("BasicInfo");

	const XMLAttribute *xmlHeight = basicInfo->FindAttribute("Height");
	xmlHeight->QueryIntValue(&height);
	const XMLAttribute *xmlWidth = basicInfo->FindAttribute("Width");
	xmlWidth->QueryIntValue(&width);
	const XMLAttribute *xmlLayersNumber = basicInfo->FindAttribute("Numberoflayers");
	xmlLayersNumber->QueryIntValue(&numberOfLayers);
	const XMLAttribute *xmlSelectionsNumber = basicInfo->FindAttribute("Numberofselections");
	xmlSelectionsNumber->QueryIntValue(&numberOfSelections);
	const XMLAttribute *xmlOperationsNumber = basicInfo->FindAttribute("Numerofcomposite");
	xmlOperationsNumber->QueryIntValue(&numberOfOperations);


	XMLElement* xmlLayer = basicInfo->FirstChildElement("Layer");


	for (int i = 0; i < numberOfLayers; i++)
	{
		int visibility;
		bool activeInPhoto, activeInOperation;
		string path;

		const XMLAttribute *xmlVisibility = xmlLayer->FindAttribute("Visibility");
		xmlVisibility->QueryIntValue(&visibility);
		const XMLAttribute *xmlActiveInPhoto = xmlLayer->FindAttribute("ActiveinPhoto");
		xmlActiveInPhoto->QueryBoolValue(&activeInPhoto);
		const XMLAttribute *xmlActiveInOperation = xmlLayer->FindAttribute("ActiveinOperations");
		xmlActiveInOperation->QueryBoolValue(&activeInOperation);
		const XMLAttribute *xmlPath = xmlLayer->FindAttribute("Path");
		path = xmlPath->Value();

		FILE *file = fopen(path.c_str(), "rb");
		if (file == nullptr)
		{
			fclose(file);
			continue;
		}
		fclose(file);

		BMPFormatter bmpFormatter(path);


		layers.push_back(Layer(bmpFormatter.getHeight(), bmpFormatter.getWidth(),bmpFormatter.getBitmap(),bmpFormatter.getWidth(),bmpFormatter.getWidth(),visibility,activeInOperation,activeInPhoto));

		xmlLayer = xmlLayer->NextSiblingElement("Layer");
	}

	XMLElement *xmlSelection = basicInfo->FirstChildElement("Selection");

	for (int i = 0; i < numberOfSelections; i++)
	{
		string name;
		bool active;
		int numberOfRects;

		const XMLAttribute *xmlName = xmlSelection->FindAttribute("Name");
		name = xmlName->Value();
		const XMLAttribute *xmlActive = xmlSelection->FindAttribute("Active");
		xmlActive->QueryBoolValue(&active);
		const XMLAttribute *xmlNumberOfRects = xmlSelection->FindAttribute("Numberofrectangles");
		xmlNumberOfRects->QueryIntValue(&numberOfRects);

		vector<Rectangle> rects;

		XMLElement *xmlRect = xmlSelection->FirstChildElement("Rectangle");
		for(int i = 0; i < numberOfRects; i++)
		{
			int startY, startX, width, height;
			const XMLAttribute *xmlY = xmlRect->FindAttribute("Y");
			xmlY->QueryIntValue(&startY);
			const XMLAttribute *xmlX = xmlRect->FindAttribute("X");
			xmlX->QueryIntValue(&startX);
			const XMLAttribute *xmlWidth = xmlRect->FindAttribute("Width");
			xmlWidth->QueryIntValue(&width);
			const XMLAttribute *xmlHeight = xmlRect->FindAttribute("Height");
			xmlHeight->QueryIntValue(&height);

			//cout << startY << " " << startX << "\n";

			rects.push_back(Rectangle(height, width, startY, startX));
			xmlRect = xmlRect->NextSiblingElement("Rectangle");
		}
		selections[name] = Selection(name, rects, active);
		xmlSelection = xmlSelection->NextSiblingElement("Selection");
	}

	XMLElement *funInfo = basicInfo->FirstChildElement("Composite");

	//vector<Operation*>compositeOperations;
	
	for (int i = 0; i < numberOfOperations; i++)
	{
		CompositeOperation *op = new CompositeOperation();

		const XMLAttribute *numInfo = funInfo->FindAttribute("NumberOfValues");
		int num;
		numInfo->QueryIntValue(&num);


		XMLElement *values = funInfo->FirstChildElement("Value");
		const XMLAttribute *valueNum;


		for (int i = 0; i < num / 2; i++)
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

		funInfo = funInfo->NextSiblingElement("Composite");
		compositeOperations.push_back(op);
	}

}


