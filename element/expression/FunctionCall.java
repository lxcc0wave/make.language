package element.expression;

import visitor.ElementVisitor;

/**
 * 関数呼び出しを表します.
 *
 * @author sin
 * @version 2017.3.15
 */
public class FunctionCall implements Expression {
	private String functionName;
	private FunctionCallArgumentList argumentList;

	public FunctionCall(String functionName, FunctionCallArgumentList argumentList){
		this.functionName = functionName;
		this.argumentList = argumentList;
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}
	/**
	 * 呼び出す関数の名前を取得します.
	 * @return 関数名
	 */
	public String getFunctionName(){
		return functionName;
	}
	/**
	 * 引数リストを取得します.
	 * @return 引数リスト
	 */
	public FunctionCallArgumentList getArgumentList(){
		return argumentList;
	}
}
