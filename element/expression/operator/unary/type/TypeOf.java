package element.expression.operator.unary.type;

import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * 単項型演算子typeofを表します.
 * @author sin
 * @version 2017.3.15
 */
public class TypeOf extends TypeUnaryOperator{

	public TypeOf(Expression arg) {
		super(arg);
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "TypeOf";
	}
}
