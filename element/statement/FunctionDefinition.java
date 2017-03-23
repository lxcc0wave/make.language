package element.statement;

import element.Program;
import visitor.ElementVisitor;

/**
 * 関数定義文を表します.
 * @author sin
 * @version 2017.3.15
 */
public class FunctionDefinition implements Statement {
	private String functionName;
	private FunctionDefinitionArgumentList argumentList;
	private Program process;

	public FunctionDefinition(String functionName, FunctionDefinitionArgumentList argumentList, Program process) {
		this.functionName = functionName;
		this.argumentList = argumentList;
		this.process = process;
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}
	/**
	 * 関数名を取得します.
	 * @return 関数名
	 */
	public String getFunctionName() {
		return functionName;
	}
	/**
	 * 仮引数リストを取得します.
	 * @return 仮引数リスト.
	 */
	public FunctionDefinitionArgumentList getArgumentList() {
		return argumentList;
	}
	/**
	 * 処理内容を取得します.
	 * @return Program
	 */
	public Program getProcess() {
		return process;
	}
}
