package calculator;

//calculator logic

public class CalculatorMain {
	
    private Double fistArgument;
    
    private Double secondArgument;
    
    private OperationList operation;
    
    public void setfistArgument(Double argFirst) {
    	this.fistArgument = argFirst;
    }
    
    public void setsecondArgument(Double argSecond) {
    	this.secondArgument = argSecond;
    }

	public Double getFistArgument() {
		return fistArgument;
	}

	public Double getSecondArgument() {
		return secondArgument;
	}

	public OperationList getOperation() {
		return operation;
	}

	public void setOperation(OperationList oper) {
		this.operation = oper;
	}
    
    public String calculation() {
    	
    	if(fistArgument != null && operation != null) {
    		switch (operation) {
    		
			case PLUS:
				fistArgument += secondArgument;
				break;

			case MINUS:
				fistArgument -= secondArgument;
				
			case DIVISION:
				fistArgument /= secondArgument;
				
			case MULTIPLY:
				fistArgument *= secondArgument;
			}
    	} return fistArgument.toString();
    }
    
    //Далее все для кнопки "ресет-а".
    
    void fistArgumentValueReset() {
    	fistArgument = null;
    }
    
    void secondArgumentValueReset() {
    	secondArgument = null;
    }
    
    void operationValueReset() {
    	operation = null;
    }
    
    public void valuesReset() {
    	
    	fistArgumentValueReset();
    	secondArgumentValueReset();
    	operationValueReset();
    	
    }

}
