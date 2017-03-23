package element.statement;

import java.util.ArrayList;

import element.Program;
import visitor.ElementVisitor;

/**
 * 複文を表します.
 * @author sin
 * @version 2017.3.15
 */
public class Block extends ArrayList<Program> implements Statement{
	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}
}
