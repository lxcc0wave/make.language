package element.expression.operator.input;

import visitor.ElementVisitor;

/**
 * 入力演算子scan()を表します.
 * @author sin
 * @version 2017.3.17
 */
public class Scan implements InputOperator{
	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "Scan";
	}
}
