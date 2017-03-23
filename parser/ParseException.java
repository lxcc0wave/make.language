package parser;

/**
 * 構文解析における例外です.
 *
 * @author sin
 * @version 2017.3.14
 */
public class ParseException extends Exception{
	public ParseException(){
		super();
	}
	public ParseException(String message){
		super(message);
	}
}
