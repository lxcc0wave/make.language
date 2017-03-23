package element.expression.operator.unary.logical;

import element.expression.Expression;
import element.expression.operator.unary.UnaryOperator;

/**
 * 単項論理演算子を表します.
 * @author sin
 * @version 2017.3.15
 */
public abstract class LogicalUnaryOperator extends UnaryOperator{
	public LogicalUnaryOperator(Expression arg){
		super(arg);
	}
}
