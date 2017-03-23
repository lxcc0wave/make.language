package element.expression.data;

/**
 * この言語のデータ型です
 * @author sin
 * @version 2017.3.16
 */
public enum Type {
	/**
	 * 整数型
	 */
	INT{
		@Override
		public String asDataTypeString(){
			return "int";
		}
	},
	/**
	 * ブール型
	 */
	BOOL{
		@Override
		public String asDataTypeString(){
			return "bool";
		}
	},
	/**
	 * 実数型
	 */
	DOUBLE{
		@Override
		public String asDataTypeString(){
			return "double";
		}
	},
	/**
	 * 文字列型
	 */
	STRING{
		@Override
		public String asDataTypeString(){
			return "string";
		}
	};

	/**
	 * コンソールに型を出力するときに使います.
	 * @return 型
	 */
	public abstract String asDataTypeString();
}