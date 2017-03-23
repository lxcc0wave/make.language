package element.expression.data;

import visitor.ElementVisitor;

/**
 * int型のデータを表します.
 *
 * @author sin
 * @version 2017.3.15
 */
public class Int extends Data{
	/**
	 * 値を指定してint型のデータを作ります.
	 *
	 * @param value 値(not {@code null})
	 */
	public Int(Integer value) {
		super(value);
	}
	/**
	 * コピーコンストラクタ/
	 * @param value コピー元
	 */
	public Int(Int value){
		super(value.getValue());
	}

	public Integer getValue(){
		return (Integer)super.getValue();
	}
	@Override
	public void accept(ElementVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString(){
		return this.getValue().toString();
	}
	@Override
	public Data copy() {
		return new Int(this);
	}

	public boolean equals(Int x){
		if(x == null)
			return false;
		return this.getValue().equals(x.getValue());
	}

	@Override
	public boolean equals(Object x){
		if(! (x instanceof Int))
			return false;
		return equals((Int)x);
	}
	@Override
	public int compareTo(Data o) {
		int typec = this.getTypeSize() - o.getTypeSize();
		if(typec < 0)
			return -1;
		else if(typec > 0)
			return 1;
		// 同じデータ型
		return this.getValue().compareTo(((Int)o).getValue());
	}
	@Override
	public int getTypeSize() {
		return 2;
	}
}
