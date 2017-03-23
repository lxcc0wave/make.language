package element.expression.operator.binary.logical;

import element.expression.Expression;
import element.expression.operator.binary.BinaryOperator;

/**
 * 二項論理演算子を表します.
 * @author sin
 * @version 2017.3.15
 */
public abstract class LogicalBinaryOperator extends BinaryOperator {
	public LogicalBinaryOperator(Expression left, Expression right){
		super(left, right);
	}
}
