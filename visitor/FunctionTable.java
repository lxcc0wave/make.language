package visitor;

import java.util.HashMap;
import java.util.Map;

import element.Program;
import element.statement.FunctionDefinitionArgumentList;

/**
 * 関数テーブルです.<br>
 * 型は<br>
 * 関数名 -&gt; 引数リスト -&gt; 処理内容<br>
 * です.
 * @author sin
 * @version 2017.3.15
 */
public class FunctionTable extends HashMap<String, Map<FunctionDefinitionArgumentList, Program>>{
	/**
	 * 空の関数テーブルを作ります.
	 */
	public FunctionTable(){
		super();
	}
	/**
	 * コピーコンストラクタ.
	 * @param map コピー元
	 */
	public FunctionTable(Map<? extends String, ? extends Map<FunctionDefinitionArgumentList, Program>> map){
		super(map);
	}
}
