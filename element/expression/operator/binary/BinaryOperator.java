package element.expression.operator.binary;

import element.expression.Expression;
import element.expression.operator.Operator;

/**
 * 二項演算子を表します.
 *
 * @author sin
 * @version 2017.3.15
 */
public abstract class BinaryOperator implements Operator{
	private Expression left, right;

	public BinaryOperator(Expression left, Expression right){
		this.left = left;
		this.right = right;
	}

	public Expression getLeft(){
		return left;
	}

	public Expression getRight(){
		return right;
	}
}
