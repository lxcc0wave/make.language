package element;

import visitor.ElementVisitor;

/**
 * この言語の構文木ノードが実装するルートインターフェースです.
 *
 * @author sin
 * @version 2017.3.14
 */
public interface Program {
	/**
	 * {@code visitor.ElementVisitor}によって処理を実行する.
	 *
	 * @param v visitor
	 */
	void accept(ElementVisitor v);
}
