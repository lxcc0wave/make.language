package element.expression.operator.ternary;

import element.expression.Expression;
import element.expression.operator.Operator;

/**
 * 三項演算子を表します.
 *
 * @author sin
 * @version 2017.3.15
 */
public abstract class TernaryOperator implements Operator{
	private Expression left, center, right;

	public TernaryOperator(Expression left, Expression center, Expression right){
		this.left = left;
		this.center = center;
		this.right = right;
	}

	public Expression getLeft(){
		return left;
	}

	public Expression getCenter(){
		return center;
	}

	public Expression getRight(){
		return right;
	}
}
