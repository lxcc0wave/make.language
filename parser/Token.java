package parser;

/**
 * この言語が使用する記号のトークンの集まりです.
 *
 * @author sin
 * @version 2017.3.14
 * @see parser.ElementParser
 * @see visitor.ElementVisitor
 */
public enum Token {
	// 記号
	/**
	 * +
	 */
	PLUS,
	/**
	 * -
	 */
	MINUS,
	/**
	 * {@code *}
	 */
	AST,
	/**
	 * {@code **}
	 */
	POWER,
	/**
	 * {@code /}
	 */
	SLASH,
	/**
	 * %
	 */
	PERC,
	/**
	 * ^
	 */
	CARET,
	/**
	 * &lt;
	 */
	LESS,
	/**
	 * ==
	 */
	EQUAL,
	/**
	 * =
	 */
	ASSIGN,
	/**
	 * &gt;
	 */
	GRAET,
	/**
	 * &lt;=
	 */
	LESSEQ,
	/**
	 * &gt;=
	 */
	GREATEQ,
	/**
	 * !=
	 */
	NOTEQ,
	/**
	 * !
	 */
	EXCL,
	/**
	 * ?
	 */
	QUES,
	/**
	 * &amp;
	 */
	AMPER,
	/**
	 * |
	 */
	VERT,
	/**
	 * (
	 */
	LPAR,
	/**
	 * )
	 */
	RPAR,
	/**
	 * {
	 */
	LBRA,
	/**
	 * }
	 */
	RBRA,
	/**
	 * :
	 */
	COLON,
	/**
	 * ;
	 */
	SEMICOLON,
	/**
	 * ,
	 */
	COMMA,
	/**
	 * "
	 */
	DOBQUOT,
	/**
	 * \
	 */
	BSLASH,
	/**
	 * 識別子.
	 */
	IDENTIFIER,
	// 型
	/**
	 * 整数型.
	 */
	INTEGER,
	/**
	 * 実数型.
	 */
	DOUBLE,
	/**
	 * ブール型
	 */
	BOOL,
	/**
	 * 文字列型.
	 */
	STRING,
	// キーワード
	/**
	 * let.
	 */
	LET,
	/**
	 * if.
	 */
	IF,
	/**
	 * then.
	 */
	THEN,
	/**
	 * else.
	 */
	ELSE,
	/**
	 * true.
	 */
	TRUE,
	/**
	 * false.
	 */
	FALSE,
	/**
	 * while.
	 */
	WHILE,
	/**
	 * typeof.
	 */
	TYPEOF,
	/**
	 * int.
	 */
	TYPE_INT,
	/**
	 * double.
	 */
	TYPE_DOUBLE,
	/**
	 * bool.
	 */
	TYPE_BOOL,
	/**
	 * string.
	 */
	TYPE_STRING,
	/**
	 * and.
	 */
	AND,
	/**
	 * or.
	 */
	OR,
	/**
	 * xor.
	 */
	XOR,
	/**
	 * not.
	 */
	NOT,
	/**
	 * abs.
	 */
	ABS,
	/**
	 * print.
	 */
	PRINT,
	/**
	 * scan.
	 */
	SCAN,
	/**
	 * 初期状態.
	 */
	START
}
