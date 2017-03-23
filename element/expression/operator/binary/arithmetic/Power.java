package element.expression.operator.binary.arithmetic;

import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * 二項の演算子**を表します.
 * @author sin
 * @version 2017.3.15
 */
public class Power extends ArithmeticBinaryOperator{
	public Power(Expression left, Expression right){
		super(left, right);
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "Power";
	}
}
