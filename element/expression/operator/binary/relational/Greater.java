package element.expression.operator.binary.relational;

import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * 二項関係演算子&gt;を表します.
 * @author sin
 * @version 2017.3.15
 */
public class Greater extends RelationalBinaryOperator{

	public Greater(Expression left, Expression right) {
		super(left, right);
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "Greater";
	}
}
