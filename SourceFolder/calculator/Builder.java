package calculator;

public interface Builder<T extends Number> {
	
	public void addNumber(String someThing);
	
	public void deleteNumber();
	
	public boolean emptyValueOfNumberOrNot();
	
	public T getNumber();
}
