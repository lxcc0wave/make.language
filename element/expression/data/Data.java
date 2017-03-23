package element.expression.data;

import element.expression.Expression;

/**
 * データ型を総称するクラスです.<br>
 * このクラスのサブクラスは{@link Object#equals(Object)}
 * メソッドをオーバーライドすべきです.
 *
 * @author sin
 * @version 2017.3.16
 */
public abstract class Data implements Expression, Comparable<Data>{
	private Object value;

	/**
	 * 値をセットしてデータを作ります.
	 *
	 * @param value 値(not {@code null})
	 */
	public Data(Object value){
		this.value = value;
	}
	/**
	 * 値を取得します.
	 *
	 * @return 値(not {@code null})
	 */
	public Object getValue(){
		return value;
	}
	/**
	 * コピーコンストラクタ.
	 * @return ディープコピー
	 */
	public abstract Data copy();
	/**
	 * 型の大きさを取得します.
	 * @return 型の大きさ
	 */
	public abstract int getTypeSize();
}
