package element.expression.operator.ternary;

import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * 条件演算子を表します.
 *
 * @author sin
 * @version 2017.3.15
 */
public class Conditional extends TernaryOperator {
	public Conditional(Expression left, Expression center, Expression right){
		super(left, center, right);
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "Conditional";
	}
}
