package element.expression.operator.unary;

import element.expression.Expression;
import element.expression.operator.Operator;

/**
 * 単項演算子を表します.
 * @author sin
 * @version 2017.3.15
 */
public abstract class UnaryOperator implements Operator{
	private Expression arg;

	public UnaryOperator(Expression arg){
		this.arg = arg;
	}

	public Expression getArg(){
		return arg;
	}
}
