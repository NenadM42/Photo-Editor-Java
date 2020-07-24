package editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLFormatter extends Formatter{

	@Override
	public BufferedImage loadImage(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void exportImage(String path) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void importXML(Image img, String fileName)
	{
		try
		{
			img.layers.clear();
			img.operations.clear();
			img.clearSelections();
			
			
	         File inputFile = new File(fileName);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();

			
	         NodeList nList = doc.getElementsByTagName("Layer");

	         
	         for(int temp = 0;temp<nList.getLength();temp++)
	         {
	             Node nNode = nList.item(temp);
	             System.out.println("\nCurrent Element :" + nNode.getNodeName());
	             
	             
	             String active = nNode.getAttributes().getNamedItem("ActiveinOperations").getNodeValue();
	             
	             String activeInPhoto = nNode.getAttributes().getNamedItem("ActiveinPhoto").getNodeValue();
	             
	             String path = nNode.getAttributes().getNamedItem("Path").getNodeValue();

	             String visibility = nNode.getAttributes().getNamedItem("Visibility").getNodeValue();
	             
	            
	             Layer l = new Layer(path,new BMPFormatter());
	            
	             boolean b;
	             
	             if(active.equals("true"))
	            	 b = true;
	             else
	            	 b = false;
	             l.setActive(b);
	             
	             if(activeInPhoto.equals("true"))
	            	 b = true;
	             else
	            	 b = false;
	             l.setInFinalPhoto(b);
	             
	             l.setVisibility(Integer.parseInt(visibility));
	             
	             
	             img.addLayer(l);
	         }
	         
	         // Selekcije
	         
	         nList = doc.getElementsByTagName("Selection");
	         
	         System.out.println(nList.getLength() + " ovo je len??");
	         
	         NodeList nListRects = doc.getElementsByTagName("Rectangle");

	         int ct = 0;
	         for(int temp = 0;temp<nList.getLength();temp++)
	         {
	             Node nNode = nList.item(temp);
	             System.out.println("\nCurrent Element :" + nNode.getNodeName());
	             
	             
	             String name = nNode.getAttributes().getNamedItem("Name").getNodeValue();
	             
	             String numOfRectangles = nNode.getAttributes().getNamedItem("Numberofrectangles").getNodeValue();
	             
	             System.out.println("Obradjuje se: " + name);

	             //img.rectangles.clear();
	             img.rectangles.removeAll(img.rectangles);
	             
	             for(int i = 0;i<Integer.parseInt(numOfRectangles);i++)
	             {
	            	 
	            
	            	 System.out.println("Cnt je: " + ct);
	            	 Node nNode2 = nListRects.item(ct);
	            	 ct++;
	            	 
		             String y = nNode2.getAttributes().getNamedItem("Y").getNodeValue();
		             
		             String x = nNode2.getAttributes().getNamedItem("X").getNodeValue();
		             
		             String height = nNode2.getAttributes().getNamedItem("Height").getNodeValue();

		             String width = nNode2.getAttributes().getNamedItem("Width").getNodeValue();
		             
		             System.out.println("Ovo dobijamooooooooooooooooooooooo: ");
		             
		             System.out.println(x + " " + y);
		             
		             img.rectangles.add(new Rectangle(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(x) + Integer.parseInt(width),  
	            		 Integer.parseInt(y) + Integer.parseInt(height)));
	             }
	             
	             
	             img.addNewSelection(name);
	             
	            
	             //img.addRectangle(new Rectangle(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(x) + Integer.parseInt(width),  
	            //		 Integer.parseInt(y) + Integer.parseInt(height)));
	         }

	         
	         /*
	         nList = doc.getElementsByTagName("Rectangle");
	         
	         for(int temp = 0;temp<nList.getLength();temp++)
	         {
	             Node nNode = nList.item(temp);
	             System.out.println("\nCurrent Element :" + nNode.getNodeName());
	             
	             
	             String y = nNode.getAttributes().getNamedItem("Y").getNodeValue();
	             
	             String x = nNode.getAttributes().getNamedItem("X").getNodeValue();
	             
	             String height = nNode.getAttributes().getNamedItem("Height").getNodeValue();

	             String width = nNode.getAttributes().getNamedItem("Width").getNodeValue();
	             
	             img.addRectangle(new Rectangle(Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(x) + Integer.parseInt(width),  
	            		 Integer.parseInt(y) + Integer.parseInt(height)));
	         }
	         */
	         
	         
	         
	         ct = 0;
	         
	         nList = doc.getElementsByTagName("Composite");
	         NodeList nListValue = doc.getElementsByTagName("Value");

	         
	         img.operations.clear();
	         
	         
	         for(int temp = 0;temp<nList.getLength();temp++)
	         {
	             Node nNode = nList.item(temp);
	             System.out.println("\nCurrent Element :" + nNode.getNodeName());
	             
	             String name = nNode.getAttributes().getNamedItem("Name").getNodeValue();
	             
	             String numOfValues = nNode.getAttributes().getNamedItem("NumberOfValues").getNodeValue();
	             
	             
	             img.operations.clear();
	             
	             System.out.println("OVO JEE NOVA OPPPPPPPPPPPP " + name);

	             for(int i = 0;i<Integer.parseInt(numOfValues)/2;i++)
	             {
	            	 
	            	 Node nNode2 = nListValue.item(ct);
	            	 ct++;
	            	 
	            	 System.out.println("ct size: " + ct + ", a max je: " + nListValue.getLength());

	            	String val1 = nNode2.getAttributes().getNamedItem("val").getNodeValue();
	             	nNode2 = nListValue.item(ct);
	             	ct++;
	            	 System.out.println("ct size: " + ct + ", a max je: " + nList.getLength());

	             	String val2 = nNode2.getAttributes().getNamedItem("val").getNodeValue();
	             	
	             	
	             	
	             	//img.compositeMap.get(name).addOperation(new Operation(Integer.parseInt(val1),Integer.parseInt(val2)));
	             	
		            img.addOperation(new Operation(Integer.parseInt(val1),Integer.parseInt(val2)));
	             	

	             }
	             
	             img.addNewOperation(name);

	         }
			
			
		}catch(Exception e)
		{
			System.out.println("Neuspesno citanje xml-a!");
		}
	}
	
	public void exportXMLOperation(Image img, String fileName)
	{
		try
		{
	         DocumentBuilderFactory dbFactory =
	         DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.newDocument();

	         // root element
	         Element rootElement = doc.createElement("Photo");
	         doc.appendChild(rootElement);
	         
	         // Create basic info
	         
	         Element basicInfo = doc.createElement("BasicInfo");
	        
	         // Create attribute height
	         Attr photoHeight = doc.createAttribute("Height");         
	         StringBuilder s = new StringBuilder(img.getMaxHeight());
	  	         
	         System.out.println("HEIGHT JEEEEEEE: " + String.valueOf(img.getMaxHeight()));
	         
	         photoHeight.setValue(String.valueOf(img.getMaxHeight()));
	         basicInfo.setAttributeNode(photoHeight);
	         
	         // Create attribute width
	         Attr photoWidth = doc.createAttribute("Width");         
	         s = new StringBuilder(img.getMaxWidth());
	         photoWidth.setValue(String.valueOf(img.getMaxWidth()));     
	         basicInfo.setAttributeNode(photoWidth);
	         
	         // Create attribute number of layers
	         Attr numberOfLayers = doc.createAttribute("Numberoflayers");         
	         s = new StringBuilder(img.getNumberOfLayers());
	         numberOfLayers.setValue("1");     
	         basicInfo.setAttributeNode(numberOfLayers);

	         
	         // Create attribute numberOfSelections
	         Attr numberOfSelections = doc.createAttribute("Numberofselections");         
	         s = new StringBuilder(img.getMaxWidth());
	         numberOfSelections.setValue("1");     
	         basicInfo.setAttributeNode(numberOfSelections);
	         
	         // Create attribute numberOfComposite
	         Attr numberOfComposite = doc.createAttribute("Numerofcomposite");         
	         s = new StringBuilder(img.getMaxWidth());
	         numberOfComposite.setValue("1");     
	         basicInfo.setAttributeNode(numberOfComposite);
	         
	         rootElement.appendChild(basicInfo);
	         // Now layers
	         
	         // We save current photo
	         
	         img.exportImage("photoForOperation.bmp");
	         
	         
	         int cnt = 0;
	       
	         
	         System.out.println(img.getLayers().size());

	         //img.exportAllLayers("layer_saved");
	         
	         	        	
		    String path = "layer_saved" + cnt + ".bmp";

	      //  BMPFormatter bmp24 = new BMPFormatter();
	      //  bmp24.exportImage(path,layer.getBufferedImage());
	         

	         Element layerXML = doc.createElement("Layer");
	         
	         
	         
	         
	         // path for layer
	         cnt++;
	         
	         Attr pathXML = doc.createAttribute("Path");
	         pathXML.setValue("photoForOperation.bmp");
	         layerXML.setAttributeNode(pathXML);
	         
	         // visibility for layer
	         Attr visibilityXML = doc.createAttribute("Visibility");
	         visibilityXML.setValue("100");
	         layerXML.setAttributeNode(visibilityXML);
	         
	         // active in photo for layer
	         Attr activeInPhotoXML = doc.createAttribute("ActiveinPhoto");
	         activeInPhotoXML.setValue("true");
	         layerXML.setAttributeNode(activeInPhotoXML);
	         
	         
	         // active in operation photo for layer
	         Attr activeInOperationXML = doc.createAttribute("ActiveinOperations");
	         activeInOperationXML.setValue("true");
	         layerXML.setAttributeNode(activeInOperationXML);

	         basicInfo.appendChild(layerXML);
	         
	         
	         
	         
	         // Now selections
	         
	         Element selectionXML = doc.createElement("Selection");
	         
	         
	         
	         // Sel name atr
	         Attr selectionNameXML = doc.createAttribute("Name");
	         selectionNameXML.setValue("sel1");
	         selectionXML.setAttributeNode(selectionNameXML);
	         
	         // Sel active atr
	         Attr selectionActiveXML = doc.createAttribute("Active");
	         String selAct = "";
	         if(img.getCurrentSelection().size() == 0)
	         {
	        	 selAct = "false";
	         }else
	         {
	        	 selAct = "true";
	         }
	         
	         selectionActiveXML.setValue(selAct);
	         selectionXML.setAttributeNode(selectionActiveXML);
	         
	         // Sel num of rects atr
	         Attr selectionNumberOfRectsXML = doc.createAttribute("Numberofrectangles");
	         s = new StringBuilder(img.getCurrentSelection().size());
	         selectionNumberOfRectsXML.setValue(String.valueOf(img.getCurrentSelection().size()));
	         selectionXML.setAttributeNode(selectionNumberOfRectsXML);
	         
	         basicInfo.appendChild(selectionXML);

	         
	         // Now rects
	         
	         
	         for(Rectangle rect : img.getCurrentSelection())
	         {
	        	 Element rectXML = doc.createElement("Rectangle");
	        	 
	        	 
		         Attr rectY = doc.createAttribute("Y");
		         s = new StringBuilder(rect.getStartY());
		         rectY.setValue(String.valueOf(rect.getStartY()));
		         rectXML.setAttributeNode(rectY);

	        	 
		         Attr rectX = doc.createAttribute("X");
		         s = new StringBuilder(rect.getStartX());
		         rectX.setValue(String.valueOf(rect.getStartX()));
		         rectXML.setAttributeNode(rectX);
		         
		         Attr rectHeight = doc.createAttribute("Height");
		         s = new StringBuilder(rect.getHeight());
		         rectHeight.setValue(String.valueOf(rect.getHeight()));
		         rectXML.setAttributeNode(rectHeight);


		         Attr rectWidth = doc.createAttribute("Width");
		         s = new StringBuilder(rect.getWidth());
		         rectWidth.setValue(String.valueOf(rect.getWidth()));
		         rectXML.setAttributeNode(rectWidth);
	        	 	        	 	        	        	 
		         
		         
		         
		         
	        	 selectionXML.appendChild(rectXML);
	        	 
	         }
	         
	         // Now comopsite
	         
	         
	         
	         
	         Element compositeXML = doc.createElement("Composite");
	         
	         
	         Attr numOfValues = doc.createAttribute("NumberOfValues");
	         numOfValues.setValue(String.valueOf(img.getOperationList().size()*2));
	         compositeXML.setAttributeNode(numOfValues);
	         
	         
	         for(Operation op : img.getOperationList())
	         {
		         Element valueXML = doc.createElement("Value");
		         Attr valXML = doc.createAttribute("val");
		         valXML.setValue(String.valueOf(op.getId()));
		         
		         valueXML.setAttributeNode(valXML);
		         compositeXML.appendChild(valueXML);
		         
		         
		         valueXML = doc.createElement("Value");
		         valXML = doc.createAttribute("val");
		         valXML.setValue(String.valueOf(op.getValue()));
		         
		         valueXML.setAttributeNode(valXML);
		         compositeXML.appendChild(valueXML);
	         }
	         
	         basicInfo.appendChild(compositeXML);
	         
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	         transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	         transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

	         
	         fileName = fileName + ".xml";
	         
	         DOMSource source = new DOMSource(doc);
	         StreamResult result = new StreamResult(new File(fileName));
	         transformer.transform(source, result);    
	         
		}catch(Exception e)
		{

			
		}
	}
	
	
	
	public void exportXML(Image img, String fileName)
	{
		try
		{
	         DocumentBuilderFactory dbFactory =
	         DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.newDocument();

	         // root element
	         Element rootElement = doc.createElement("Photo");
	         doc.appendChild(rootElement);
	         
	         // Create basic info
	         
	         Element basicInfo = doc.createElement("BasicInfo");
	        
	         // Create attribute height
	         Attr photoHeight = doc.createAttribute("Height");         
	         StringBuilder s = new StringBuilder(img.getMaxHeight());
	  	         
	         System.out.println("HEIGHT JEEEEEEE: " + String.valueOf(img.getMaxHeight()));
	         
	         photoHeight.setValue(String.valueOf(img.getMaxHeight()));
	         basicInfo.setAttributeNode(photoHeight);
	         
	         // Create attribute width
	         Attr photoWidth = doc.createAttribute("Width");         
	         s = new StringBuilder(img.getMaxWidth());
	         photoWidth.setValue(String.valueOf(img.getMaxWidth()));     
	         basicInfo.setAttributeNode(photoWidth);
	         
	         // Create attribute number of layers
	         Attr numberOfLayers = doc.createAttribute("Numberoflayers");         
	         s = new StringBuilder(img.getNumberOfLayers());
	         numberOfLayers.setValue(String.valueOf(img.getNumberOfLayers()));     
	         basicInfo.setAttributeNode(numberOfLayers);

	         
	         // Create attribute numberOfSelections
	         Attr numberOfSelections = doc.createAttribute("Numberofselections");         
	         s = new StringBuilder(img.getMaxWidth());
	         numberOfSelections.setValue("1");     
	         basicInfo.setAttributeNode(numberOfSelections);
	         
	         // Create attribute numberOfComposite
	         Attr numberOfComposite = doc.createAttribute("Numerofcomposite");         
	         s = new StringBuilder(img.getMaxWidth());
	         numberOfComposite.setValue("1");     
	         basicInfo.setAttributeNode(numberOfComposite);
	         
	         rootElement.appendChild(basicInfo);
	         // Now layers
	         
	         // We save current photo
	         
	         //img.exportImage("photoForOperations.bmp");
	         
	         
	         int cnt = 0;
	       
	         
	         System.out.println(img.getLayers().size());

	         //img.exportAllLayers("layer_saved");
	         
	         
	         for(Layer layer : img.getLayers()) {
	        	 
	        	 
	        	if(layer.getInFinalPhoto() == false)
	        		continue;
	        	
		    String path = "layer_saved" + cnt + ".bmp";

	        BMPFormatter bmp24 = new BMPFormatter();
	        bmp24.exportImage(path,layer.getBufferedImage());
	         
		     System.out.println("Asa");

	         Element layerXML = doc.createElement("Layer");
	         
	         
	         
	         
	         // path for layer
	         cnt++;
	         
	         Attr pathXML = doc.createAttribute("Path");
	         pathXML.setValue(path);
	         layerXML.setAttributeNode(pathXML);
	         
	         // visibility for layer
	         Attr visibilityXML = doc.createAttribute("Visibility");
	         visibilityXML.setValue(String.valueOf(layer.getVisibility()));
	         layerXML.setAttributeNode(visibilityXML);
	         
	         // active in photo for layer
	         Attr activeInPhotoXML = doc.createAttribute("ActiveinPhoto");
	         activeInPhotoXML.setValue(String.valueOf(layer.getInFinalPhoto()));
	         layerXML.setAttributeNode(activeInPhotoXML);
	         
	         
	         // active in operation photo for layer
	         Attr activeInOperationXML = doc.createAttribute("ActiveinOperations");
	         activeInOperationXML.setValue(String.valueOf(layer.getActive()));
	         layerXML.setAttributeNode(activeInOperationXML);

	         basicInfo.appendChild(layerXML);
	         
	         
	         }
	         
	         // Now selections
	         
	         
	         HashMap<String, Selection> sels = img.getAllSelections();
	         
	         for(String selKey : sels.keySet())
	         {
	        	 
	         
	         
	         Element selectionXML = doc.createElement("Selection");
	         
	         
	         
	         // Sel name atr
	         Attr selectionNameXML = doc.createAttribute("Name");
	         selectionNameXML.setValue(selKey);
	         selectionXML.setAttributeNode(selectionNameXML);
	         
	         // Sel active atr
	         Attr selectionActiveXML = doc.createAttribute("Active");
	         String selAct = "";
	         if(img.getAllSelections().size() == 0)
	         {
	        	 selAct = "false";
	         }else
	         {
	        	 selAct = "true";
	         }
	         
	         selectionActiveXML.setValue(selAct);
	         selectionXML.setAttributeNode(selectionActiveXML);
	         
	         // Sel num of rects atr
	         Attr selectionNumberOfRectsXML = doc.createAttribute("Numberofrectangles");
	         s = new StringBuilder(img.getCurrentSelection().size());
	         selectionNumberOfRectsXML.setValue(String.valueOf(img.getAllSelections().get(selKey).rectangles.size()));
	         selectionXML.setAttributeNode(selectionNumberOfRectsXML);
	         
	         basicInfo.appendChild(selectionXML);

	         
	         // Now rects
	         
	         
	         for(Rectangle rect : sels.get(selKey).rectangles)
	         {
	        	 Element rectXML = doc.createElement("Rectangle");
	        	 
	        	 
		         Attr rectY = doc.createAttribute("Y");
		         s = new StringBuilder(rect.getStartY());
		         rectY.setValue(String.valueOf(rect.getStartY()));
		         rectXML.setAttributeNode(rectY);

	        	 
		         Attr rectX = doc.createAttribute("X");
		         s = new StringBuilder(rect.getStartX());
		         rectX.setValue(String.valueOf(rect.getStartX()));
		         rectXML.setAttributeNode(rectX);
		         
		         Attr rectHeight = doc.createAttribute("Height");
		         s = new StringBuilder(rect.getHeight());
		         rectHeight.setValue(String.valueOf(rect.getHeight()));
		         rectXML.setAttributeNode(rectHeight);


		         Attr rectWidth = doc.createAttribute("Width");
		         s = new StringBuilder(rect.getWidth());
		         rectWidth.setValue(String.valueOf(rect.getWidth()));
		         rectXML.setAttributeNode(rectWidth);
	        	 	        	 	        	        	 
		         
		         
		         
		         
	        	 selectionXML.appendChild(rectXML);
	        	 
	         }
	         
	         }
	         
	         
	         
	         
	         HashMap<String, CompositeOperation> compOps = img.getAllComposite();

	         
	         
	         for(String opKey : compOps.keySet())
	         {
	        	 
	         
	         
	         // Now comopsite
	         
	         Element compositeXML = doc.createElement("Composite");
	         
	         
	         Attr numOfValues = doc.createAttribute("NumberOfValues");
	         numOfValues.setValue(String.valueOf(compOps.get(opKey).operations.size()*2));
	         compositeXML.setAttributeNode(numOfValues);
	         
	         
	         Attr nm = doc.createAttribute("Name");
	         nm.setValue(opKey);
	         compositeXML.setAttributeNode(nm);

	         
	         
	         for(Operation op : compOps.get(opKey).operations)
	         {
		         Element valueXML = doc.createElement("Value");
		         Attr valXML = doc.createAttribute("val");
		         valXML.setValue(String.valueOf(op.getId()));
		         
		         valueXML.setAttributeNode(valXML);
		         compositeXML.appendChild(valueXML);
		         
		         
		         valueXML = doc.createElement("Value");
		         valXML = doc.createAttribute("val");
		         valXML.setValue(String.valueOf(op.getValue()));
		         
		         valueXML.setAttributeNode(valXML);
		         compositeXML.appendChild(valueXML);
	         }
	         
	         basicInfo.appendChild(compositeXML);
	         
	         }
	         
	         TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();
	         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	         transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	         transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

	         
	         fileName = fileName + ".xml";
	         
	         DOMSource source = new DOMSource(doc);
	         StreamResult result = new StreamResult(new File(fileName));
	         transformer.transform(source, result);    
	         
		}catch(Exception e)
		{

			
		}
	}

}
