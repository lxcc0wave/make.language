package element.expression.operator.binary.logical;

import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * 二項論理演算子&amp;andを表します.
 * @author sin
 * @version 2017.3.15
 */
public class And extends LogicalBinaryOperator {

	public And(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "And";
	}
}
