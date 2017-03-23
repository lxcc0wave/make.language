package element.statement;

import element.expression.Expression;
import visitor.ElementVisitor;

/**
 * 代入文を表します.
 * @author sin
 * @version 2017.3.15
 */
public class Assign implements Statement{
	private String targetName;
	private Expression value;

	public Assign(String targetName, Expression value){
		this.targetName = targetName;
		this.value = value;
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}
	/**
	 * 代入先の変数名を取得します.
	 * @return 変数名
	 */
	public String getTargetName(){
		return targetName;
	}
	/**
	 * 代入する値を取得します.
	 * @return 代入する値
	 */
	public Expression getValue(){
		return value;
	}
}
