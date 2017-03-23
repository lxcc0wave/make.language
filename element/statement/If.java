package element.statement;

import element.Program;
import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * if文を表します.
 * @author sin
 * @version 2017.3.15
 */
public class If implements Statement{
	private Expression condition;
	private Program success, failure;

	public If(Expression condition, Program success, Program failure){
		this.condition = condition;
		this.success = success;
		this.failure = failure;
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
	 * 条件を満足する場合の処理を取得します.
	 * @return Program
	 */
	public Program getSuccess(){
		return success;
	}
	/**
	 * 条件を満足しない場合の処理を取得します.
	 * @return Program
	 */
	public Program getFailure(){
		return failure;
	}
}
