package element.expression.operator.unary.type;

import element.expression.Expression;
import element.expression.operator.unary.UnaryOperator;

/**
 * 単項型演算子を表します.
 * @author sin
 * @version 2017.3.15
 */
public abstract class TypeUnaryOperator extends UnaryOperator{

	public TypeUnaryOperator(Expression arg) {
		super(arg);
	}

}
