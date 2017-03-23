package element.expression;

import visitor.ElementVisitor;

/**
 * 変数を表します.
 * @author sin
 * @version 2017.3.15
 */
public class Variable implements Expression{
	private String variableName;

	public Variable(String variableName){
		this.variableName = variableName;
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}
	/**
	 * 変数名を取得します.
	 * @return 変数名
	 */
	public String getVariableName(){
		return variableName;
	}
}
