package calculator;

public enum OperationList {

    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVISION("/");

	String titl;
	
	OperationList(String someTitl){
		this.titl = someTitl;
	}

	public String getTitl() {
		return titl;
	}

}
