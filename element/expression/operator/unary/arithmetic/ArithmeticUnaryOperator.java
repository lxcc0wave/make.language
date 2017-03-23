package element.expression.operator.unary.arithmetic;

import element.expression.Expression;
import element.expression.operator.unary.UnaryOperator;

/**
 * 単項算術演算子を表します.
 * @author sin
 * @version 2017.3.15
 */
public abstract class ArithmeticUnaryOperator extends UnaryOperator {

	public ArithmeticUnaryOperator(Expression arg) {
		super(arg);
	}

}
