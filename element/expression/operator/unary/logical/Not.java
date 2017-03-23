package element.expression.operator.unary.logical;

import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * 単項論理演算子!, notを表します.
 * @author sin
 * @version 2017.3.15
 */
public class Not extends LogicalUnaryOperator {

	public Not(Expression arg) {
		super(arg);
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "Not";
	}
}
