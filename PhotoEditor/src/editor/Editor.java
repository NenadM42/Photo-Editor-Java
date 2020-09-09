package editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Editor extends JFrame{

	public enum Mode{LAYERS,IMAGE};
	
	Mode mode = Mode.LAYERS;
	
	private Image img;
	
	
	

	
	private JButton loadImageButton = new JButton("Load Layer");
	private JTextField loadImageTextField = new JTextField();
	private JPanel loadLayerPanel;
	
	private JButton rightButton = new JButton(">>>");
	private JButton leftButton = new JButton("<<<");

	private JButton saveButton = new JButton("Save");
	private JTextField nameField = new JTextField();

	private JLabel rectnagleLabelX = new JLabel("X: ");
	private JLabel rectnagleLabelY = new JLabel("Y: ");
	private JButton addRectangleButton = new JButton("Add Rectangle");
	private JTextField rectangleTextFieldX = new JTextField();
	private JTextField rectangleTextFieldY = new JTextField();
	
	private JButton editImageButton = new JButton("Edit Image");
	
	private JPanel layerPanel = null;
	private JSlider visibilitySlider = null;
	private JCheckBox activeCheckBox = null;
	private JCheckBox inPhotoCheckBox = null;
	private JButton deleteLayerButton = new JButton("Delete Layer");
	
	private JPanel bottomPanel;
	
	private JButton exportButton = new JButton("Export Image");
	private JButton saveWorkspaceButton = new JButton("Save Workspace");
	private JButton loadWorkspaceButton = new JButton("Load Workspace");

	
	
	private JPanel saveAndExportPanel = new JPanel();
	
	private JButton layerViewButton = new JButton("Layers");
	private JButton imageViewButton = new JButton("Image");
	
	private JPanel buttonMenuPanel;
	private JPanel activePanel = null;
	
	
	private JDialog saveDialog;
	private JDialog saveWorkspaceDialog;
	private JDialog loadWorkspaceDialog;
	
	private JButton saveWorkspace = new JButton("Save");
	
	private JButton loadWorkspace = new JButton("Load");
	

	
	private JButton saveSelectionButton = new JButton("Save Selection");
	private JComboBox selComboBox = new JComboBox();
	private JTextField selectionTextField = new JTextField();
	
	private JButton applySelectionButton = new JButton("Apply Selection");
	
	private JButton emptyLayerButton = new JButton("Empty Layer");
	
	private JTextField xEmptyLayerTextField = new JTextField("X");
	private JTextField yEmptyLayerTextField = new JTextField("Y");

	
	
	private JButton saveOperationButton = new JButton("Save Operation");
	private JComboBox opComboBox = new JComboBox();
	private JTextField operationTextField = new JTextField();
	private JButton loadOperationButton = new JButton("Load Operation");

	
	private BufferedImage currentImage = null;
	
	
	
	
	
	
	// Operations
	private JPanel operationPanel = null;
	
	private ButtonGroup operationsCheckBoxGroup = new ButtonGroup();
	
	private JSlider operationValueSlider = new JSlider(0,100,100);
	
	private JCheckBox addConstantCheckBox = new JCheckBox("Add");
	private JCheckBox substractConstantCheckBox = new JCheckBox("Substract");
	private JCheckBox invSubCheckBox = new JCheckBox("Inverse substract");
	private JCheckBox mulCheckBox = new JCheckBox("Multiply");
	private JCheckBox divideCheckBox = new JCheckBox("Divide");
	private JCheckBox inverseDivisionCheckBox = new JCheckBox("Inverse Division");
	private JCheckBox powerCheckBox = new JCheckBox("Power");
	private JCheckBox logCheckBox = new JCheckBox("Log");
	private JCheckBox absCheckBox = new JCheckBox("Abs");
	private JCheckBox minCheckBox = new JCheckBox("Min");
	private JCheckBox maxCheckBox = new JCheckBox("Max");
	private JCheckBox InversionCheckBox = new JCheckBox("Inversion");
	private JCheckBox grayToneCheckBox = new JCheckBox("Gray Tone");
	private JCheckBox blackAndWhiteCheckBox = new JCheckBox("Black And White");
	private JCheckBox medianCheckBox = new JCheckBox("Median");
	
	private JButton addOperationButton = new JButton("Add Operation");
	private JButton removeAllOperationsButton = new JButton("Remove All Operations");
	private JButton applyOperationsButton = new JButton("Apply Operations");
	
	private JLabel operationValue = new JLabel("Operation Value");

	private JTextArea operationsLabel = new JTextArea();
	
	
	private HashMap<String,String> opMap = new HashMap<String,String>();

	//-------------------------------------------------
	
	
	private int mousePressedStartX;
	private int mousePressedStartY;
	private int mousePressedEndX;
	private int mousePressedEndY;

	

	public Editor()
	{
		img = new Image();
		this.setSize(1000,1000);
		this.setVisible(true);
		//this.add(loadImageButton,BorderLayout.SOUTH);
		
		//this.setBorder( new EmptyBorder( 3, 3, 3, 3 ) )

		
		addButtons();
		setButtons();
		addButtonListeners();
		addLoadImagePanel();
		setMouseListeners();
		createRectanglePanel();
		createLayerPanel();
		createButtonMenu();
		createBottomPanel();
		focusOnLayers();
		addPanels();
		setSlidersListeners();
		addOperationPanel();
		addSaveAndExportPanel();
		initOperationHashMap();
		
		
		
		img.addEmptyLayer(300, 300);
		
		
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
				  
				  
				if(img.getNumberOfLayers() > 0)
				{
					
				
				  
			    int confirmed = JOptionPane.showConfirmDialog(null, 
			        "Do you want to export your image?", "Exit Program Message Box",
			        JOptionPane.YES_NO_OPTION);


			    
			    if (confirmed == JOptionPane.YES_OPTION) {
                    
					createExportImageDialog();

			    	//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			    }else
			    {
			    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			    }
			    
			  }
			  }
			});

	}
	
	
	private void initOperationHashMap()
	{
		opMap.put("0", "addConstant");
		opMap.put("1", "subConst");
		opMap.put("2", "invSub");
		opMap.put("3", "mulConst");
		opMap.put("4", "divConst");
		opMap.put("5", "invDiv");
		opMap.put("6", "power");
		opMap.put("7", "log");
		opMap.put("8", "abs");
		opMap.put("9", "minObj");
		opMap.put("10", "maxObj");
		opMap.put("11", "inversion");
		opMap.put("12", "grayTone");
		opMap.put("13", "blackAndWhite");
		opMap.put("14", "median");


	}
	
	private void addSaveAndExportPanel() {
		//saveAndExportPanel.setLayout(new BoxLayout(saveAndExportPanel,BoxLayout.Y_AXIS));
		saveAndExportPanel.add(this.saveWorkspaceButton);
		saveAndExportPanel.add(this.loadWorkspaceButton);
		saveAndExportPanel.add(this.exportButton);

	}

	private void addOperationPanel() {
		// TODO Auto-generated method stub
		operationPanel = new JPanel();
		
		operationPanel.setLayout(new BoxLayout(operationPanel,BoxLayout.Y_AXIS));
		
		operationPanel.add(addConstantCheckBox);
		operationPanel.add(substractConstantCheckBox); 
		operationPanel.add(invSubCheckBox);
		operationPanel.add(mulCheckBox);
		operationPanel.add(divideCheckBox);
		operationPanel.add(inverseDivisionCheckBox);
		operationPanel.add(powerCheckBox);
		operationPanel.add(logCheckBox);
		operationPanel.add(absCheckBox);
		operationPanel.add(minCheckBox);
		operationPanel.add(maxCheckBox);
		operationPanel.add(InversionCheckBox);
		operationPanel.add(grayToneCheckBox); 
		operationPanel.add(blackAndWhiteCheckBox); 
		operationPanel.add(medianCheckBox);
		
		
		
		operationPanel.add(operationValue);

		operationPanel.add(operationValueSlider);
		
		operationPanel.add(addOperationButton);
		
		 operationPanel.add(removeAllOperationsButton);		
		 operationPanel.add(applyOperationsButton);
		 operationPanel.add(selComboBox);
		 operationPanel.add(selectionTextField);
		 operationPanel.add(saveSelectionButton);
		 operationPanel.add(applySelectionButton);
		 
		 operationPanel.add(opComboBox);
		 operationPanel.add(operationTextField);
		 operationPanel.add(saveOperationButton);
		 operationPanel.add(this.loadOperationButton);

		 
		
		operationsLabel.setEditable(false);
		operationPanel.add(this.operationsLabel);
		
		operationsCheckBoxGroup.add(addConstantCheckBox);
		operationsCheckBoxGroup.add(substractConstantCheckBox);
		operationsCheckBoxGroup.add(invSubCheckBox);
		operationsCheckBoxGroup.add(mulCheckBox);
		operationsCheckBoxGroup.add(divideCheckBox);
		operationsCheckBoxGroup.add(inverseDivisionCheckBox);
		operationsCheckBoxGroup.add(powerCheckBox);
		operationsCheckBoxGroup.add(logCheckBox);
		operationsCheckBoxGroup.add(absCheckBox);
		operationsCheckBoxGroup.add(minCheckBox);
		operationsCheckBoxGroup.add(maxCheckBox);
		operationsCheckBoxGroup.add(InversionCheckBox);
		operationsCheckBoxGroup.add(grayToneCheckBox);
		operationsCheckBoxGroup.add(blackAndWhiteCheckBox);
		operationsCheckBoxGroup.add(medianCheckBox);
		
		
		
		operationPanel.setVisible(true);
	}

	private void addPanels() {
		// TODO Auto-generated method stub
		this.add(bottomPanel,BorderLayout.SOUTH);
	}

	private void createBottomPanel() {
		// TODO Auto-generated method stub
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.Y_AXIS));
		
		//layerPanel.setBackground(Color.darkGray);
		//loadLayerPanel.setBackground(Color.darkGray);

		bottomPanel.add(this.layerPanel);
		bottomPanel.add(this.loadLayerPanel);
		bottomPanel.add(this.xEmptyLayerTextField);
		bottomPanel.add(this.yEmptyLayerTextField);
		bottomPanel.add(this.emptyLayerButton);

		
		
		
	}

	private void createButtonMenu() {
		
		buttonMenuPanel = new JPanel();
		
		buttonMenuPanel.add(this.layerViewButton);
		buttonMenuPanel.add(this.imageViewButton);
		buttonMenuPanel.setBackground(Color.BLACK);
		
		layerViewButton.setBackground(Color.CYAN);
		
		this.add(buttonMenuPanel,BorderLayout.NORTH);
	
	}

	private void addButtons()
	{
		this.add(rightButton,BorderLayout.EAST);
		this.add(leftButton,BorderLayout.WEST);
	}
	
	private void addImage(String path)
	{
		//path = "pic.bmp";
		JPanel imagePanel = img.gImage(path);
		if(imagePanel == null)
			return;
		this.add(imagePanel,BorderLayout.CENTER);
		
		 this.setVisible(false);
		 this.setVisible(true);
	}
	
	private void loadLayer(String path)
	{
		img.addLayer(path);
		
		if(activePanel == null)
		{
			showLayer();
		}
	}
	
	
	
	private void createLayerPanel()
	{
		layerPanel = new JPanel();
		this.activeCheckBox = new JCheckBox("Active");
		this.visibilitySlider = new JSlider(0,100,100);
		this.inPhotoCheckBox = new JCheckBox("In Final Picture");

		layerPanel.add(visibilitySlider);
		layerPanel.add(activeCheckBox);
		//layerPanel.add(inPhotoCheckBox);
		layerPanel.add(this.deleteLayerButton);
		
		activeCheckBox.setSelected(true);
		
		
		
		
		
	//	this.add(panel,BorderLayout.SOUTH);
		
		this.activeCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(activeCheckBox.isSelected() == true)
				{
					img.setCurrentLayerActive(true);
				}else
				{
					img.setCurrentLayerActive(false);
				}
			}
			
		});
		
	}
	

	
	private void showLayer()
	{
		
		if(img.getLayersSize() == 0)
		{			
			if(activePanel != null)
			{
				this.remove(activePanel);
				activePanel = null;
			}

			this.repaint();
			this.invalidate();
			this.validate();
		
			
			return;
		}
		
		BufferedImage bufferedImg = img.getCurrentLayerBufferedImage();
		
		ImageIcon icon = new ImageIcon(bufferedImg);
		JLabel label = new JLabel(icon);

		JPanel panel = new JPanel();
		panel.setSize(200,200);
		panel.add(label);
		panel.setVisible(true);
		
		if(activePanel != null)
		{
			this.remove(activePanel);
		}
		
		activePanel = panel;
		
		this.add(panel,BorderLayout.CENTER);
		
		// Postavimo slidere i checkBoxove
		
		this.visibilitySlider.setValue(img.getCurrentLayerVisibility());
	
		this.repaint();
		this.invalidate();
		this.validate();
	}
	
	
	private void showPicture()
	{
		//BufferedImage bufferedImg = img.mergeLayers();
		
		currentImage = img.mergeLayers();
		
		ImageIcon icon = new ImageIcon(currentImage);
		JLabel label = new JLabel(icon);

		JPanel panel = new JPanel();
		panel.setSize(200,200);
		panel.add(label);
		panel.setVisible(true);
		
		if(activePanel != null)
		{
			this.remove(activePanel);
		}
		
		activePanel = panel;
				
		setImageMouseListener();
		
		this.add(panel,BorderLayout.WEST);
			
		
		Graphics g = currentImage.getGraphics();
		
		g.setColor(Color.BLUE);
		
		//g.drawRect(0, 0, bufferedImg.getWidth(), bufferedImg.getHeight());

		g.drawRect(0, 0, currentImage.getWidth()-1, currentImage.getHeight()-1);
		//g.drawLine(0, bufferedImg.getHeight(), bufferedImg.getWidth(), bufferedImg.getHeight());

		//g.drawLine(0, bufferedImg.getHeight(), bufferedImg.getWidth(), bufferedImg.getHeight());
		//g.drawLine(bufferedImg.getWidth(), 0, bufferedImg.getWidth(), bufferedImg.getHeight());

		
		
		this.repaint();
		this.invalidate();
		this.validate();
	}
	
	
	private void showEditedPicture()
	{
		//BufferedImage bufferedImg = img.mergeLayers();
		BMPFormatter bmp24 = new BMPFormatter();
		currentImage = bmp24.loadImage("novaSlikaZaEditor.bmp");
		
		//currentImage = img.getImagePath("novaSlikaZaEditor.bmp");
		
		ImageIcon icon = new ImageIcon(currentImage);
		JLabel label = new JLabel(icon);

		JPanel panel = new JPanel();
		panel.setSize(200,200);
		panel.add(label);
		panel.setVisible(true);
		
		if(activePanel != null)
		{
			this.remove(activePanel);
		}
		
		activePanel = panel;
				
		setImageMouseListener();
		
		this.add(panel,BorderLayout.WEST);
			
		
		Graphics g = currentImage.getGraphics();
		
		g.setColor(Color.BLUE);
		
		//g.drawRect(0, 0, bufferedImg.getWidth(), bufferedImg.getHeight());

		g.drawRect(0, 0, currentImage.getWidth()-1, currentImage.getHeight()-1);
		//g.drawLine(0, bufferedImg.getHeight(), bufferedImg.getWidth(), bufferedImg.getHeight());

		//g.drawLine(0, bufferedImg.getHeight(), bufferedImg.getWidth(), bufferedImg.getHeight());
		//g.drawLine(bufferedImg.getWidth(), 0, bufferedImg.getWidth(), bufferedImg.getHeight());

		
		
		this.repaint();
		this.invalidate();
		this.validate();
	}
	
	private void setSlidersListeners() 
	{
		this.visibilitySlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				
				if(img.getNumberOfLayers() > 0)
				{
					int value = visibilitySlider.getValue();
					img.setCurrentLayerVisibility(value);
				}
			}
			
		});
	}
	
	
	private void setMouseListeners()
	{
		
	}
	
	private void setImageMouseListener()
	{
		activePanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.getX() + " " + arg0.getY());
				mousePressedStartX = arg0.getX();
				 mousePressedStartY = arg0.getY();
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(arg0.getX() + " " + arg0.getY());
				mousePressedEndX = arg0.getX();
				mousePressedEndY = arg0.getY();
				
				img.addRectangle(mousePressedStartX, mousePressedStartY, mousePressedEndX, mousePressedEndY);
				
				drawSelections();
				//addSelection();
			}
			
		});
	}
	
	private void drawSelections()
	{
		
		
		
		Graphics g = this.currentImage.getGraphics();
		
		g.setColor(Color.YELLOW);
		
		List<Rectangle> rects = img.getCurrentSelection();
		
		
		for(Rectangle rect : rects)
		{
			System.out.println(rect.getStartX() + " - startX");
			g.drawRect(rect.getStartX(), rect.getStartY(), rect.getEndX() - rect.getStartX(), rect.getEndY() - rect.getStartY());
		}
		this.repaint();
		this.invalidate();
		this.validate();
	}
	
	private void addSelection()
	{
		Graphics g = this.currentImage.getGraphics();
		
		g.setColor(Color.YELLOW);
		
		g.drawRect(this.mousePressedStartX, this.mousePressedStartY, this.mousePressedEndX - this.mousePressedStartX, this.mousePressedEndY - this.mousePressedStartY);
		
		this.repaint();
		this.invalidate();
		this.validate();

	}
	
	
	private void createRectanglePanel()
	{
		/*
		JPanel panel = new JPanel();
		panel.add(rectnagleLabelX);
		panel.add(rectnagleLabelY);
		panel.add(rectangleTextFieldX);
		panel.add(rectangleTextFieldY);
		panel.add(this.addRectangleButton);
		
		this.add(panel,BorderLayout.SOUTH);
		*/
	}
	
	
	private void setButtons()
	{
		loadImageButton.setSize(50,200);
		
		
	}
	
	
	private void setTextFields()
	{
		
	}
	
	
	private void nextLayer()
	{

		if(img.getLayersSize() > 0)
		{
			this.activeCheckBox.setSelected(img.getCurrentLayerActive());
			img.selectNextLayer();
			showLayer();
		}
	}
	
	private void prevLayer()
	{
		if(img.getLayersSize() > 0)
		{
			this.activeCheckBox.setSelected(img.getCurrentLayerActive());
			img.selectPreviousLayer();
			showLayer();
		}
	}
	
	
	private void focusOnImage()
	{
		imageViewButton.setBackground(Color.CYAN);
		layerViewButton.setBackground(Color.WHITE);
		
		
		
		this.repaint();
		this.invalidate();
		this.validate();

	}
	
	private void focusOnLayers()
	{
		imageViewButton.setBackground(Color.WHITE);
		layerViewButton.setBackground(Color.CYAN);
		
		if(img.getLayersSize() > 0)
		this.activeCheckBox.setSelected(img.getCurrentLayerActive());

		
		this.repaint();
		this.invalidate();
		this.validate();

	}
	
	private void createLoadWorkspaceDialog() 
	{
		JFrame f = new JFrame();
		saveDialog = new JDialog(f,"Export Image",true);
		//dialog.setSize(300,300);
		
		
		JLabel nameLabel = new JLabel("Name of file");
		JPanel panel = new JPanel();
		
		
		saveDialog.setLayout( new BoxLayout(saveDialog.getContentPane(),BoxLayout.Y_AXIS) );  

		
		saveDialog.add(nameLabel);
        saveDialog.add(nameField);
        saveDialog.add(panel);
        saveDialog.add(this.loadWorkspace);
        
     
        saveDialog.setSize(200,150);
        saveDialog.setResizable(false);
        saveDialog.setVisible(true);

		System.out.println("HELPPPPP");
	}
	
	private void createSaveWorkspaceDialog() 
	{
		JFrame f = new JFrame();
		saveDialog = new JDialog(f,"Export Image",true);
		//dialog.setSize(300,300);
		
		
		JLabel nameLabel = new JLabel("Name of xml file(Must have .xml extension):");
		JPanel panel = new JPanel();
		
		
		saveDialog.setLayout( new BoxLayout(saveDialog.getContentPane(),BoxLayout.Y_AXIS) );  

		
		saveDialog.add(nameLabel);
        saveDialog.add(nameField);
        saveDialog.add(panel);
        saveDialog.add(this.saveWorkspace);
        
     
        saveDialog.setSize(200,150);
        saveDialog.setResizable(false);
        saveDialog.setVisible(true);

		System.out.println("HELPPPPP");
	}
	
	private void createExportImageDialog() 
	{
		JFrame f = new JFrame();
		saveDialog = new JDialog(f,"Export Image",true);
		//dialog.setSize(300,300);
		
		
		JLabel nameLabel = new JLabel("Image Name:");
		JLabel formatLabel = new JLabel("Image Format:");
		JPanel panel = new JPanel();
		JCheckBox bmpCheckBox = new JCheckBox("BMP");
		JCheckBox pamCheckBox = new JCheckBox("PAM");
		
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(bmpCheckBox);
		bg.add(pamCheckBox);
		
		
		
		panel.add(bmpCheckBox);
		panel.add(pamCheckBox);
		
		saveDialog.setLayout( new BoxLayout(saveDialog.getContentPane(),BoxLayout.Y_AXIS) );  

		
		saveDialog.add(nameLabel);
        saveDialog.add(nameField);
        saveDialog.add(formatLabel);
        saveDialog.add(panel);
        saveDialog.add(saveButton);
        
     
        saveDialog.setSize(200,150);
        saveDialog.setResizable(false);
        saveDialog.setVisible(true);

		System.out.println("HELPPPPP");
	}
	

	
	private void writeOperations()
	{
		List<Operation> ops = img.getOperationList();

		operationsLabel.setText("");
		
		for(Operation o : ops)
		{
			operationsLabel.setText(operationsLabel.getText() + " | " + opMap.get(String.valueOf(o.getId())) + "-" + o.getValue() + "\n");
		}
		
	}
	
	private void addNewOperation()
	{
		if(this.addConstantCheckBox.isSelected())
		{
			img.addOperation(new Operation(0,this.operationValueSlider.getValue()));
		}else if(this.substractConstantCheckBox.isSelected())
		{
			img.addOperation(new Operation(1,this.operationValueSlider.getValue()));

		}else if(this.invSubCheckBox.isSelected())
		{
			img.addOperation(new Operation(2,this.operationValueSlider.getValue()));

		}else if(this.mulCheckBox.isSelected())
		{
			img.addOperation(new Operation(3,this.operationValueSlider.getValue()));

		}else if(this.divideCheckBox.isSelected())
		{
			img.addOperation(new Operation(4,this.operationValueSlider.getValue()));

		}else if(this.inverseDivisionCheckBox.isSelected())
		{
			img.addOperation(new Operation(5,this.operationValueSlider.getValue()));

		}else if(this.powerCheckBox.isSelected())
		{
			img.addOperation(new Operation(6,this.operationValueSlider.getValue()));
		}else if(this.logCheckBox.isSelected())
		{
			img.addOperation(new Operation(7,this.operationValueSlider.getValue()));

		}else if(this.absCheckBox.isSelected())
		{
			img.addOperation(new Operation(8,this.operationValueSlider.getValue()));

		}else if(this.minCheckBox.isSelected())
		{
			img.addOperation(new Operation(9,this.operationValueSlider.getValue()));

		}else if(this.maxCheckBox.isSelected())
		{
			img.addOperation(new Operation(10,this.operationValueSlider.getValue()));

		}else if(this.InversionCheckBox.isSelected())
		{
			img.addOperation(new Operation(11,this.operationValueSlider.getValue()));

		}else if(this.grayToneCheckBox.isSelected())
		{
			img.addOperation(new Operation(12,this.operationValueSlider.getValue()));

		}else if(this.blackAndWhiteCheckBox.isSelected())
		{
			img.addOperation(new Operation(13,this.operationValueSlider.getValue()));

		}else if(this.medianCheckBox.isSelected())
		{
			img.addOperation(new Operation(14,this.operationValueSlider.getValue()));

		}
		
		writeOperations();
	}
	
	
	private void getEditedPhoto()
	{	
		
		//img.saveXML("test");
	
		img.xmlOperation("test");
		
		String fileCpp = "C:\\Users\\nenad\\source\\repos\\PhotoEditor\\Debug\\PhotoEditor.exe " 
				+ "Nothing " + "Nothing2 ";
		
		Runtime runtime = Runtime.getRuntime();
		try
		{
			System.out.println("Starting process...");
			Process process = runtime.exec(fileCpp);
			System.out.println("Waiting to finish...");
			process.waitFor();
			System.out.println("Process finished...");
		}catch(Exception e)
		{
			System.out.println("Process failed!");
			return;
		}
		
		
		showEditedPicture();
		
			
		
		
	
	}
	
	private void updateScreen()
	{
		showPicture();
		
		//img.operations.clear();
		//img.getCurrentSelection().clear();
		
		this.writeOperations();
		this.drawSelections();
		this.updateSel();
		this.updateOp();
		
		this.repaint();
		this.invalidate();
		this.validate();
		
	}
	
	private void updateSel() {
		selComboBox.removeAllItems();
		HashMap<String,Selection> selectionMap = img.getAllSelections();
		
		for(String s : selectionMap.keySet())
		{
			selComboBox.addItem(s);
		}
		
		
	}
	
	private void updateOp() {
		this.opComboBox.removeAllItems();
		
		HashMap<String,CompositeOperation> compositeMap = img.getAllComposite();
		
		for(String s : compositeMap.keySet())
		{
			opComboBox.addItem(s);
		}
		
		
	}

	
	private void addButtonListeners()
	{
		this.emptyLayerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try
				{
				img.addEmptyLayer(Integer.parseInt(xEmptyLayerTextField.getText()), Integer.parseInt(yEmptyLayerTextField.getText()));
				}catch(Exception e)
				{
				    int confirmed = JOptionPane.showConfirmDialog(null, 
					        "Wrong format!", "Error",
					        JOptionPane.OK_CANCEL_OPTION);

					System.out.println("Pogresni brojevi!");
				}
			}
			
		});
		
		this.loadOperationButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String s = opComboBox.getSelectedItem().toString();

				img.setNewOperation(s);
				
				updateScreen();
				
			}
			
		});
		
		this.saveOperationButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				img.addNewOperation(operationTextField.getText());
				updateOp();
			}
			
		});
		
		applySelectionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				String s = selComboBox.getSelectedItem().toString();
				
				System.out.println(s + "--- ovo je proba?");
				
				img.setNewSelection(s);
				updateScreen();
				
			}
			
		});
		
		saveSelectionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				img.addNewSelection(selectionTextField.getText());
				updateSel();
			}
			
		});
		
		this.loadWorkspace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String s = nameField.getText() + ".xml";
				img.loadXML(s);
				saveDialog.dispose();
				updateScreen();
				
			}
			
		});
		
		saveWorkspace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				
				img.saveXML(nameField.getText());
				
				saveDialog.dispose();
				
			}
			
		});
		
		
		saveWorkspaceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				createSaveWorkspaceDialog();
			}
			
		});
		
		
		this.loadWorkspaceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				createLoadWorkspaceDialog();
				
			}
			
		});
		
		this.applyOperationsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			
				
				getEditedPhoto();
				
			}
			
		});
		
		this.removeAllOperationsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				img.removeAllOperations();
				writeOperations();
			}
			
		});
		
		this.addOperationButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				addNewOperation();
				
				
			}
			
		});
		
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println(nameField.getText());
				
				img.setCurrentImage(currentImage);
				if(img.exportImage(nameField.getText()))
					saveDialog.dispose();
			}
			
		});
		
		this.exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				createExportImageDialog();
			}
			
		});
		
		this.deleteLayerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				img.deleteCurrentLayer();
				showLayer();
			}
			
		});
		
		
		
		this.imageViewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(img.getLayersSize() > 0)
				{
					showPicture();
					remove(leftButton);
					remove(rightButton);
					remove(bottomPanel);					
					add(operationPanel,BorderLayout.EAST);

					add(saveAndExportPanel,BorderLayout.SOUTH);
					//add(exportButton,BorderLayout.EAST);
					
					img.removeCurrentSelection();
					
					focusOnImage();
				}
			}
			
		});
		
		
		this.layerViewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(img.getLayersSize() > 0)
				{
					showLayer();
					remove(exportButton);
					remove(operationPanel);
					remove(saveAndExportPanel);
					add(leftButton,BorderLayout.WEST);
					add(rightButton,BorderLayout.EAST);
					add(bottomPanel,BorderLayout.SOUTH);
					focusOnLayers();
					
				}
			}
			
		});
		
		
		loadImageButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			System.out.println("U listneru!\n");
			
			 //addImage(loadImageTextField.getText());
			loadLayer(loadImageTextField.getText());
			}
			
		});
		
		
		this.rightButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nextLayer();
			}
			
		});
		
		this.leftButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				prevLayer();
				
				
			}
			
		});
		
	}
	
	
	private void addLoadImagePanel()
	{
		loadLayerPanel = new JPanel();
		loadLayerPanel.setLayout(new BorderLayout());
		//loadLayerPanel.add(editImageButton,BorderLayout.WEST);
		loadLayerPanel.add(loadImageTextField,BorderLayout.NORTH);
		loadLayerPanel.add(loadImageButton,BorderLayout.CENTER);
		// Stavimo ga na desnu stranu
		//this.add(panel,BorderLayout.SOUTH);
		
		
		
	}
	
}
