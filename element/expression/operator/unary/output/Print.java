package element.expression.operator.unary.output;

import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * 単項出力演算子print(_)を表します.
 * @author sin
 * @version 2017.3.17
 */
public class Print extends OutputUnaryOperator {
	public Print(Expression arg) {
		super(arg);
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "Print";
	}
}
