package element.expression;

import element.expression.data.Type;
import visitor.ElementVisitor;

/**
 * キャスト(明示的型変換)を表します.
 * @author sin
 * @version 2017.3.15
 */
public class Cast implements Expression{
	private Type type;
	private Expression arg;

	public Cast(Type type, Expression arg){
		this.type = type;
		this.arg = arg;
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	/**
	 * キャスト先の型を取得します.
	 * @return Type
	 */
	public Type getType(){
		return type;
	}
	/**
	 * キャスト元を取得します.
	 * @return Expression
	 */
	public Expression getArg(){
		return arg;
	}

	@Override
	public String toString(){
		return "Cast";
	}
}
