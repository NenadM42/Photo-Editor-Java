#include <iostream>
#include <fstream>
#include <vector>
#include "Image.h"
#include "PAMFormatter.h"
#include "tinyxml2.h"
#include "MyFrormatter.h"
#include "BasicOperations.h"
#include "CompositeOperation.h"
#include "GIndeks.h"

using namespace std;
using namespace tinyxml2;


void firstMenu()
{
	cout << "-----PHOTO EDITOR-----\n";
	cout << "-----UCITAVANJE PRVOG SLOJA-----\n";
	cout << "1 --- Ucitavanje slike u prvi sloj\n";
	cout << "2 --- Prazan prvi sloj\n";
}

void mainMenu()
{
	cout << "-----PHOTO EDITOR-----\n";
	cout << "-----GLAVNI MENI-----\n";
	cout << "1 --- Dodaj sloj\n";
	cout << "2 --- Izaberi aktivne slojeve(Inicijalno svi slojevi su aktivni)\n";
	cout << "3 --- Izaberi slojeve koji ucestvuju u finalnoj slici\n";
	cout << "4 --- Napravi novu selekciju\n";
	cout << "5 --- Izaberi aktivne selekcije\n";
	cout << "6 --- Izvrsite neku operaciju nad selektovanim pikselima\n";
	cout << "7 --- Popuni selekciju\n";
	cout << "8 --- Izbrisi selekciju\n";
	cout << "9 --- Izbrisi layer\n";
	cout << "10 --- Eksportuj sliku\n";
	cout << "11 --- Ucitaj sacuvano okruzenje(MyFormatter)\n";
	cout << "12 --- Napravi kompozitivnu funkciju\n";
	cout << "13 --- Izvrsi kompozitivnu funkciju\n";
	cout << "14 --- Sacuvaj kompozitivnu funkciju\n";
	cout << "15 --- Ucitaj kompozitivnu funkciju\n";
	cout << "16 --- Sacuvaj celo okruzenje\n";

}

void layerMenu()
{
	cout << "-----PHOTO EDITOR-----\n";
	cout << "-----SLOJ MENI-----\n";
	cout << "1 --- Ucitaj odmah sliku u sloj\n";
	cout << "2 --- Prazan sloj\n";
}

void OperationMenu()
{
	cout << "-----PHOTO EDITOR-----\n";
	cout << "-----MENI OPERACIJA-----\n";
	cout << "1 --- Dodaj konstantu\n";
	cout << "2 --- Oduzmi konstantu\n";
	cout << "3 --- Inverzno oduzimanje\n";
	cout << "4 --- Pomnozi sa konstantom\n";
	cout << "5 --- Podeli sa konstantom\n";
	cout << "6 --- Inverzno deljenje\n";
	cout << "7 --- Na stepen\n";
	cout << "8 --- Logaritam\n";
	cout << "9 --- Abs\n";
	cout << "10 --- Postavi na minimum\n";
	cout << "11 --- Postavi na maksimum\n";
	cout << "12 --- Inverzija\n";
	cout << "13 --- Nijanse sive\n";
	cout << "14 --- Crno Bela\n";
	cout << "15 --- Medijana\n";
}

int main(int argc, char **argv)
{
	Image img;
	AddConstant *addConstant = new AddConstant();
	substractConstant *subConst = new substractConstant();
	inverseSubstraction *invSub = new inverseSubstraction();
	multiplyConstant *mulConst = new multiplyConstant();
	divideConstant *divConst = new divideConstant();
	inverseDivision *invDiv = new inverseDivision();
	Power *power = new Power();
	Log *log = new Log();
	Abs *abs = new Abs();
	Min *minObj = new Min();
	Max *maxObj = new Max();
	Inversion *inversion = new Inversion();
	GrayTone *grayTone = new GrayTone();
	BlackAndWhite *blackAndWhite = new BlackAndWhite();
	Median *median = new Median();






	vector<Operation*> operationsVector;
	operationsVector.push_back(addConstant);
	operationsVector.push_back(subConst);
	operationsVector.push_back(invSub);
	operationsVector.push_back(mulConst);
	operationsVector.push_back(divConst);
	operationsVector.push_back(invDiv);
	operationsVector.push_back(power);
	operationsVector.push_back(log);
	operationsVector.push_back(abs);
	operationsVector.push_back(minObj);
	operationsVector.push_back(maxObj);
	operationsVector.push_back(inversion);
	operationsVector.push_back(grayTone);
	operationsVector.push_back(blackAndWhite);
	operationsVector.push_back(median);



	img.setBasicOperations(operationsVector);

	
			string putanja = "test.xml";
		
			
			img.loadSavedSetup(putanja);
		
			img.applyCompositeOperation(0);


			img.exportImage("H:\\Lab_practice_lab3\\PhotoEditor\\novaSlikaZaEditor.bmp");

			




	return 0;



	cout << "Izaberite mod:\n 1 -> Kompozitivna funkcija i obrada zeljene slike\n 2 -> Meni\n";
	int x;
	cin >> x;

	if (x == 1)
	{
		cout << "Unesite putanju do slike: \n";
		string putanja;

		while (1)
		{
			cin >> putanja;

			FILE *file = fopen(putanja.c_str(), "rb");

			regex rx("[^.]*$");
			smatch match;
			regex_search(putanja, match, rx);

			string extension = match[0];
			if (file == nullptr || (extension != "bmp" && extension != "pam"))
			{
				cout << "Ne postoji ovaj fajl ili je pogresnog formata(Moze samo bmp i pam)\n";
				continue;
			}
			else
			{
				fclose(file);
				break;
			}

		}

		cout << "Unesite vidljivost slike(0-100): \n";
		double vidljivost;
		cin >> vidljivost;
		if (vidljivost < 0)
			vidljivost = 0;
		if (vidljivost > 100)
			vidljivost = 100;
		img.addLayer(putanja, vidljivost);
		cout << "Slika je ucitana!\n";


		cout << "Unesite putanju do kompozitivne funkicje:\n";
		

		while (1)
		{
			cin >> putanja;

			FILE *file = fopen(putanja.c_str(), "rb");

			regex rx("[^.]*$");
			smatch match;
			regex_search(putanja, match, rx);

			int L = putanja.size();
			string extension = match[0];

			cout << extension << "\n";

			if (file == nullptr || (extension != "xml"))
			{
				cout << "Ne postoji ovaj fajl ili je pogresnog formata(Moze xml)\n";
				continue;
			}
			else
			{
				fclose(file);
				break;
			}

		}

		img.loadCompositeFunction(putanja);

		img.applyCompositeOperation(0);


		img.exportImage("ProveraPrvogDela.bmp");

		cout << "Slika je ispisana i zove se ProveraPrvogDela.bmp\n";

		cout << "Sada sledi meni!\n";
	}

	while (1)
	{
		firstMenu();
		int x;
		cin >> x;
		if (x == 1)
		{
			cout << "Unesite putanju do slike: \n";
			string putanja;
			
			while (1)
			{
				cin >> putanja;

				FILE *file = fopen(putanja.c_str(), "rb");

				regex rx("[^.]*$");
				smatch match;
				regex_search(putanja, match, rx);
				
				string extension = match[0];
				if (file == nullptr || (extension != "bmp" && extension != "pam"))
				{
					cout << "Ne postoji ovaj fajl ili je pogresnog formata(Moze samo bmp i pam)\n";
					continue;
				}
				else
				{
					fclose(file);
					break;
				}

			}
				
			cout << "Unesite vidljivost slike(0-100): \n";
			double vidljivost;
			cin >> vidljivost;
			if (vidljivost < 0)
				vidljivost = 0;
			if (vidljivost > 100)
				vidljivost = 100;
			img.addLayer(putanja,vidljivost);
			cout << "Slika je ucitana!\n";
			break;
		}
		else if (x == 2)
		{
			cout << "Unesite visinu slike:\n";
			int visina;
			while (1)
			{
				cin >> visina;
				if (visina <= 0)
				{
					cout << "Unesite ponovo!\n Mora biti pozitivan broj!\n";
				}
				else
				{
					break;
				}
			}
			cout << "Unesite sirinu slike:\n";
			int sirina;
			while (1)
			{
				cin >> sirina;
				if (sirina <= 0)
				{
					cout << "Unesite ponovo!\n Mora biti pozitivan broj!\n";
				}
				else
				{
					break;
				}
			}

			cout << "Unesite vidljivost slike(0-100):\n";
			double vidljivost;
			cin >> vidljivost;
			if (vidljivost < 0)
				vidljivost = 0;
			if (vidljivost > 100)
				vidljivost = 100;
			img.addLayer(sirina,visina,vidljivost);
			break;
		}
		else
		{
			cout << "Unesite validnu opciju!\n";
		}
	}

	while (1)
	{
		mainMenu();
		int main_x;
		cin >> main_x;

		if (main_x == 1)
		{
			
				layerMenu();
				int x;
				cin >> x;


				if (x == 1)
				{
					cout << "Unesite putanju do slike: \n";
					string putanja;

					while (1)
					{
						cin >> putanja;

						FILE *file = fopen(putanja.c_str(), "rb");

						regex rx("[^.]*$");
						smatch match;
						regex_search(putanja, match, rx);

						string extension = match[0];
						if (file == nullptr || (extension != "bmp" && extension != "pam"))
						{
							cout << "Ne postoji ovaj fajl ili je pogresnog formata(Moze samo bmp i pam)\n";
							continue;
						}
						else
						{
							fclose(file);
							break;
						}

					}
					cout << "Unesite vidljivost(0-100):\n";
					double vidljivost;
					cin >> vidljivost;
					if (vidljivost < 0)
						vidljivost = 0;
					if (vidljivost > 100)
						vidljivost = 100;
					img.addLayer(putanja, vidljivost);
				}
				else if (x == 2)
				{
					cout << "Unesite visinu slike:\n";
					int visina;
					while (1)
					{
						cin >> visina;
						if (visina <= 0)
						{
							cout << "Unesite ponovo!\n Mora biti pozitivan broj!\n";
						}
						else
						{
							break;
						}
					}
					cout << "Unesite sirinu slike:\n";
					int sirina;
					while (1)
					{
						cin >> sirina;
						if (sirina <= 0)
						{
							cout << "Unesite ponovo!\n Mora biti pozitivan broj!\n";
						}
						else
						{
							break;
						}
					}
					cout << "Unesite vidljivost sloja(1-100):\n";
					double vidljivost;
					cin >> vidljivost;
					img.addLayer(sirina, visina, vidljivost);
				}
				else
				{
					cout << "Unesite validnu opciju!\n";
				}
			
		}
		else if (main_x == 2)
		{
			// Stavimo aktivnost svim slojevima
			cout << "Sledi unos aktivnosti za sve layere.\n Ukoliko je layer aktivan unesite 1, a ukoliko nije unesite 0!\n";
			cout << "Ako unesete bilo koju vrednost sem ove racunace se da je layer aktivan!\n";
			vector<int> activeLayers;
			activeLayers.clear();
			for (int i = 0; i < img.getNumberOfLayers(); i++)
			{
				cout << "Aktivnost za layer " << (i + 1) << ":\n";
				int x;
				cin >> x;
				if (x != 0 && x != 1)
					x = 1;
				activeLayers.push_back(x);
			}
			// Sada dodamo vrednosti layerima
			img.setLayerActivity(activeLayers);
		}
		else if (main_x == 3)
		{
			cout << "Sledi unos vidljivosti u finalnoj slici za sve layere.\n Ukoliko je vidljiv aktivan unesite 1, a ukoliko nije unesite 0!\n";
			cout << "Ako unesete bilo koju vrednost sem ove racunace se da je layer aktivan!\n";
			vector<int> activeLayers;
			for (int i = 0; i < img.getNumberOfLayers(); i++)
			{
				cout << "Aktivnost za layer " << (i + 1) << ":\n";
				int x;
				cin >> x;
				if (x != 0 && x != 1)
					x = 1;
				activeLayers.push_back(x);
			}
			// Sada dodamo vrednosti layerima
			img.setLayerInFinalImage(activeLayers);
		}
		else if (main_x == 4) // Pravljenje selekcije
		{
			cout << "Unesite naziv nove selekcije:\n";
			string selectionName;
			cin >> selectionName;
			cout << "Unesite broj pravougaonika za ovu selekciju:\n";
			int rectNum;
			cin >> rectNum;
			cout << "Sada unesite informacije za njih:\n";
			vector<Rectangle> rects;
			for (int i = 0; i < rectNum; i++)
			{
				int startY, startX, rectHeight, rectWidth;
				
				while (1)
				{
					cout << "Unesite y kordinatu pocetka:\n";
					cin >> startY;
					if (startY < 0 || startY > img.getHeight())
					{
						cout << "Vrednost izvan opsega trenutnih dimenzija slike!\n";
					}
					else
					{
						break;
					}
				}
				while (1)
				{
					cout << "Unesite x kordinatu pocetka:\n";
					cin >> startX;
					if (startX < 0 || startY > img.getWidth())
					{
						cout << "Vrednost izvan opsega trenutnih dimenzija slike!\n";
					}
					else
					{
						break;
					}
				}
				while (1)
				{
					cout << "Unesite visinu:\n";
					cin >> rectHeight;
					if (startY + rectHeight > img.getHeight())
					{
						cout << "Vrednost izvan opsega trenutnih dimenzija slike!\n";
					}
					else
					{
						break;
					}
				}
				while (1)
				{
					cout << "Unesite sirinu pocetka:\n";
					cin >> rectWidth;
					if (startX + rectWidth > img.getWidth())
					{
						cout << "Vrednost izvan opsega trenutnih dimenzija slike!\n";
					}
					else
					{
						break;
					}
				}
				rects.push_back(Rectangle(rectHeight, rectWidth, startY, startX));
			}
			Selection selection(selectionName, rects);
			img.addSelection(selection);
		}
		else if (main_x == 5)
		{
		string selectionName;
		cout << "Unesite ime selekcije za koju zelite da postavite status aktivnosti:\n";
		cin >> selectionName;
		cout << "Unesite 1 da aktivirate selekciju, a 0 da je deaktivirate(Ako unesete bilo koju drugu vrednost selekcija ce se aktivirati!\n";
		bool status;
		cin >> status;
		if (status != 0 && status != 1)
			status = 1;
		img.setSelectionActiveStatus(selectionName, status);
		}
		else if (main_x == 6)
		{
			OperationMenu();
			int x;
			while (1)
			{
				cin >> x;
				if (x < 1 || x > 15)
				{
					cout << "Nevalidna vrednost!\n";
					continue;
				}
				break;
			}

			cout << "X JE : " << x << "\n";
			x--;
			Operation *op = operationsVector[x];
			int value = 0;
			if ((x >= 0 && x <= 6 ) || (x == 9 || x == 10))
			{
				cout << "Unesite konstantu:\n";
				cin >> value;
			}
			img.applyOperation(op, value);

		}
		else if (main_x == 7)
		{
			cout << "Unesite ime selekcije koju zelite da popunite:\n";
			string name;
			cin >> name;
			cout << "Unesite redom r, g, b i alfa komponente:\n";
			int r, g, b, a;
			cin >> r >> g >> b >> a;
			try
			{
				img.fillSelection(name, r, g, b, a);
			}
			catch (GIndeks &g)
			{
				cout << g;
			}
		}
		else if (main_x == 8)
		{
			string name;
			cout << "Unesite ime selekcije koju zelite da obrisete:\n";
			cin >> name;
			img.deleteSelection(name);
		}
		else if (main_x == 9)
		{
			int index;
			cout << "Unesite index layera kojeg zelite da obrisete:(1-n)\n";
			cin >> index;
			index--;
			img.deleteLayer(index);
		}
		else if (main_x == 10)
		{
			cout << "Unesite naziv izlazne slike i ekstenziju(.pam ili .bmp)\n";
			string path;
			cin >> path;
			img.exportImage(path);
		}
		else if (main_x == 11)
		{
			string putanja;
			cout << "Unesite putanju do sacuvanog okruzenja:\n";
			while (1)
			{
				cin >> putanja;
				cout << "AA";
				FILE *file = fopen(putanja.c_str(), "rb");

				regex rx("[^.]*$");
				smatch match;
				regex_search(putanja, match, rx);

				string extension = match[0];
				if (file == nullptr || (extension != "xml"))
				{
					cout << "Ne postoji ovaj fajl ili je pogresnog formata(Moze xml)\n";
					continue;
				}
				else
				{
					fclose(file);
					break;
				}

			}
			img.loadSavedSetup(putanja);
		}
		else if (main_x == 12)
		{
			CompositeOperation *compositeOperation = new CompositeOperation();
			cout << "Unesite broj osnovnih operacije koje zelite da ubacite u kompozitivnu:\n";
			int n;
			cin >> n;

			OperationMenu();

			for (int i = 0; i < n; i++)
			{
				cout << "Unesite redni broj operacije\n";
				int x;

				while (1)
				{
					cin >> x;
					if (x < 1 || x > 15)
					{
						cout << "Nevalidna vrednost!\n";
						continue;
					}
					break;
				}

				//cout << "X JE : " << x << "\n";
				x--;
				Operation *op = operationsVector[x];
				int value = 0;
				if ((x >= 0 && x <= 6) || (x == 9 || x == 10))
				{
					cout << "Unesite konstantu:\n";
					cin >> value;
				}
				compositeOperation->addOperation(op, value);
			}
			img.addCompositeOperation(compositeOperation);
			cout << "Kompozitivna funkcija uspesno napravljena!\n";
		}
		else if (main_x == 13)
		{
			cout << "Unesite redni broj kompozitivne funkcije:\n";
			int x;
			cin >> x;
			x--;
			img.applyCompositeOperation(x);
		}
		else if (main_x == 14)
		{
			string name;
			cout << "Unesite kako zelite da nazovete kompozitivnu funkicju\n";
			cin >> name;

			cout << "Unesite index kompozitivne funkcije";
			int x;

			while (1)
			{
				cin >> x;
				x--;
				if (x < 0 || x > img.getNumberOfCompositeFunction())
				{
					cout << "Nevalidna vrednost!\n";
					continue;
				}
				break;
			}


			img.saveCompositeFunctions(name,x);
		}
		else if (main_x == 15)
		{
			cout << "Unesite putanju do kompozitivne funkicje:\n";
			string putanja;
			
			while (1)
			{
				cin >> putanja;

				FILE *file = fopen(putanja.c_str(), "rb");

				regex rx("[^.]*$");
				smatch match;
				regex_search(putanja, match, rx);

				int L = putanja.size();
				string extension = match[0];

				cout << extension << "\n";

				if (file == nullptr || (extension != "xml"))
				{
					cout << "Ne postoji ovaj fajl ili je pogresnog formata(Moze xml)\n";
					continue;
				}
				else
				{
					fclose(file);
					break;
				}

			}

			img.loadCompositeFunction(putanja);
		}
		else if (main_x == 16)
		{
			cout << "Unesite naziv fajla gde cete cuvati(Mora imati .xml ekstenziju):\n";
			string putanja;
			
			while (1)
			{
				cin >> putanja;
				regex rx("[^.]*$");
				smatch match;
				regex_search(putanja, match, rx);
				string extension = match[0];

				if (extension != "xml")
				{
					cout << "Pogresna ekstenzija!\n";
					continue;
				}
				break;
			}
			
			img.saveSetup(putanja);
		}
	}

	system("pause");
	return 0;
}