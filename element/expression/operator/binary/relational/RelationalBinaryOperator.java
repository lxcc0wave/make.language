package element.expression.operator.binary.relational;

import element.expression.Expression;
import element.expression.operator.binary.BinaryOperator;

/**
 * 二項関係演算子を表します.
 * @author sin
 * @version 2017.3.15
 */
public abstract class RelationalBinaryOperator extends BinaryOperator{
	public RelationalBinaryOperator(Expression left, Expression right){
		super(left, right);
	}
}
