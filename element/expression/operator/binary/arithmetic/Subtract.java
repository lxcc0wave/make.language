package element.expression.operator.binary.arithmetic;

import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * 二項の-演算子を表します.
 * @author sin
 * @version 2017.3.15
 */
public class Subtract extends ArithmeticBinaryOperator {

	public Subtract(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "Subtract";
	}
}
