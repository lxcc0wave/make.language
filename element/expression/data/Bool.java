package element.expression.data;

import visitor.ElementVisitor;

/**
 * bool型のデータを表します.
 *
 * @author sin
 * @version 2017.3.15
 */
public class Bool extends Data{
	/**
	 * 値を指定してbool型のデータを作ります.
	 *
	 * @param value 真理値(not {@code null})
	 */
	public Bool(Boolean value) {
		super(value);
	}
	/**
	 * コピーコンストラクタ.
	 * @param bool コピー元
	 */
	public Bool(Bool bool){
		super(bool.getValue());
	}

	public Boolean getValue(){
		return (Boolean)super.getValue();
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
		return new Bool(this);
	}

	public boolean equals(Bool x){
		if(x == null)
			return false;
		return this.getValue().equals(x.getValue());
	}

	@Override
	public boolean equals(Object x){
		if(! (x instanceof Bool))
			return false;
		return equals((Bool)x);
	}
	@Override
	public int compareTo(Data o) {
		int typec = this.getTypeSize() - o.getTypeSize();
		if(typec < 0)
			return -1;
		else if(typec > 0)
			return 1;
		// 同じデータ型
		boolean l = this.getValue(),
				r = ((Bool)o).getValue();
		if(!l &&r)
			return 1;
		else if(l && !r)
			return -1;
		else
			return 0;
	}
	@Override
	public int getTypeSize() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}
}
