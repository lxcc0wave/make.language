package element.expression.operator.binary.arithmetic;

import element.expression.Expression;
import element.expression.operator.binary.BinaryOperator;

/**
 * 二項算術演算子を表します.
 *
 * @author sin
 * @version 2017.3.15
 */
public abstract class ArithmeticBinaryOperator extends BinaryOperator{
	public ArithmeticBinaryOperator(Expression left, Expression right) {
		super(left, right);
	}

}
