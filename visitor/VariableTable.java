package visitor;

import java.util.HashMap;
import java.util.Map;

import element.expression.data.Data;

/**
 * 変数テーブルです.
 * @author sin
 * @version 2017.3.15
 */
public class VariableTable extends HashMap<String, Data>{
	/**
	 * 空の変数テーブルを作ります.
	 */
	public VariableTable(){
		super();
	}
	/**
	 * コピーコンストラクタ.
	 * @param map コピー元
	 */
	public VariableTable(Map<? extends String, ? extends Data> map){
		super(map);
	}
}
