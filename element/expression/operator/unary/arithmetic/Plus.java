package element.expression.operator.unary.arithmetic;

import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * 単項算術演算子+を表します.
 * @author sin
 * @version 2017.3.15
 */
public class Plus extends ArithmeticUnaryOperator{

	public Plus(Expression arg) {
		super(arg);
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "Plus";
	}
}
