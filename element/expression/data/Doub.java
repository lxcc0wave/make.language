package element.expression.data;

import visitor.ElementVisitor;

/**
 * double型のデータを表します.
 *
 * @author sin
 * @version 2017.3.15
 */
public class Doub extends Data {
	/**
	 * 値を指定してdouble型のデータを作ります.
	 *
	 * @param value 値(not {@code null})
	 */
	public Doub(Double value) {
		super(value);
	}

	/**
	 * コピーコンストラクタ.
	 * @param doub コピー元
	 */
	public Doub(Doub doub){
		super(doub.getValue());
	}
	public Double getValue(){
		return (Double)super.getValue();
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
		return new Doub(this);
	}

	public boolean equals(Doub x){
		if(x == null)
			return false;
		return this.getValue().equals(x.getValue());
	}

	@Override
	public boolean equals(Object x){
		if(! (x instanceof Doub))
			return false;
		return equals((Doub)x);
	}

	@Override
	public int compareTo(Data o) {
		int typec = this.getTypeSize() - o.getTypeSize();
		if(typec < 0)
			return -1;
		else if(typec > 0)
			return 1;
		// 同じデータ型
		return this.getValue().compareTo(((Doub)o).getValue());
	}

	@Override
	public int getTypeSize() {
		return 3;
	}
}
