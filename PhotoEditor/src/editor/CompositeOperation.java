package editor;

import java.util.ArrayList;
import java.util.List;

public class CompositeOperation {

	List<Operation> operations = new ArrayList<Operation>();

	
	public CompositeOperation() 
	{
		
	}
	
	public CompositeOperation(List<Operation> ops)
	{
		operations = ops;
	}
	
	
	
	public void addOperation(Operation op)
	{
		operations.add(op);
	}
	
	
}
