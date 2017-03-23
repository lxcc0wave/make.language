package element.expression.operator.binary.logical;

import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * 二項論理演算子^, xorを表します.
 * @author sin
 * @version 2017.3.15
 */
public class Xor extends LogicalBinaryOperator{

	public Xor(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "Xor";
	}
}
