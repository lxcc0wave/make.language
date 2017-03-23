package element.statement;

import visitor.ElementVisitor;

/**
 * 空文を表します.
 * @author sin
 * @version 2017.3.15
 */
public class Empty implements Statement{

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "Empty";
	}
}
