package element.statement;

import element.Program;
import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * While文を表します.
 *
 * @author sin
 * @version 2017.3.15
 */
public class While implements Statement{
	private Expression condition;
	private Program process;

	public While(Expression condition, Program process) {
		this.condition = condition;
		this.process = process;
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	/**
	 * 条件式を取得します.
	 * @return 条件式
	 */
	public Expression getCondition(){
		return condition;
	}
	/**
	 * ループ内の処理を取得します.
	 * @return Program
	 */
	public Program getProcess(){
		return process;
	}
}
