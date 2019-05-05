package calculator;

import java.util.regex.Pattern;

public class WorkWithNumbers implements Builder<Double>{
	
	Double number = (double) 0;
	Double degree = (double) 0;
	boolean emptyOrNot;
	boolean separator;
	
    private Pattern numbPattern = Pattern.compile("[0-9]");
    private Pattern separatorPattern = Pattern.compile("[//.,//,]");
    
///////////////////////////////////////////////////////////////////////////////////////////////////////////////просто разделитель, для лучшей читаемости кода
    
    private boolean chekIfNumber(String text) { // Check the string, number or not
    	
        return numbPattern.matcher(text).find();
        
    }
    
    private boolean checkIfSeparator(String text) {
    	
        return separatorPattern.matcher(text).find();
        
    }
	
    
    private void addNewNumber(String someTxt) {	//Calculating result when add new number.
    	
        if (!separator) {
        	number = number * Math.pow(10d, degree) + Double.valueOf(someTxt);
        	degree = 1d;
        } else {
        	number += Math.pow(10d, degree) * Double.valueOf(someTxt);
        	degree--;
        }
    	
    }
	
	@Override
	public void addNumber(String someTxt) {
		
		if(chekIfNumber(someTxt)) {
			
			addNewNumber(someTxt);
			emptyOrNot = false;
			
		} else if(!separator && checkIfSeparator(someTxt)) {
			
			degree = (double) -1;
			separator = true;
			emptyOrNot = false;
			
		}
	}


	@Override
	public void deleteNumber() {
		// TODO Auto-generated method stub
		number = 0d;
		degree = 0d;
		emptyOrNot = true;
		separator = false;
	}


	@Override
	public boolean emptyValueOfNumberOrNot() {
		// TODO Auto-generated method stub
		return emptyOrNot;
	}


	@Override
	public Double getNumber() {
		// TODO Auto-generated method stub
		return number;
	}

	@Override
	public String toString() {
		return number.toString();
	}
	
	
}
