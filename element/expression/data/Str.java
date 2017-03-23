package element.expression.data;

import visitor.ElementVisitor;

/**
 * string型のデータを表します.
 *
 * @author sin
 * @version 2017.3.15
 */
public class Str extends Data{
	/**
	 * 値を指定してstring型のデータを作ります.
	 *
	 * @param value 文字列(not {@code null})
	 */
	public Str(String value) {
		super(value);
	}

	/**
	 * コピーコンストラクタ.
	 * @param str コピー元
	 */
	public Str(Str str){
		super(str.getValue());
	}
	public String getValue(){
		return (String)super.getValue();
	}

	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return "" + this.getValue() + "";
	}

	@Override
	public Data copy() {
		return new Str(this);
	}

	public boolean equals(Str x){
		if(x == null)
			return false;
		return this.getValue().equals(x.getValue());
	}

	@Override
	public boolean equals(Object x){
		if(! (x instanceof Str))
			return false;
		return equals((Str)x);
	}

	@Override
	public int compareTo(Data o) {
		int typec = this.getTypeSize() - o.getTypeSize();
		if(typec < 0)
			return -1;
		else if(typec > 0)
			return 1;
		// 同じデータ型
		return this.getValue().compareTo(((Str)o).getValue());
	}

	@Override
	public int getTypeSize() {
		return 4;
	}
}
