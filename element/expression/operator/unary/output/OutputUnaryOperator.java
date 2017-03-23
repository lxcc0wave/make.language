package element.expression.operator.unary.output;

import element.expression.Expression;
import element.expression.operator.unary.UnaryOperator;

/**
 * 単項出力演算子を表します.
 * @author sin
 * @version 2017.3.17
 */
public abstract class OutputUnaryOperator extends UnaryOperator {

	public OutputUnaryOperator(Expression arg) {
		super(arg);
	}

}
