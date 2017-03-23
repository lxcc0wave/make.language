package parser;

/**
 * 処理できないトークンを発見したときに発生します.
 *
 * @author sin
 * @version 2017.3.14
 */
public class NoSuchTokenException extends ParseException{
	public NoSuchTokenException(){
		super();
	}
	public NoSuchTokenException(String message){
		super(message);
	}
}
