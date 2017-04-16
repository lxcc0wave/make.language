package parser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import element.Program;
import element.expression.Cast;
import element.expression.Expression;
import element.expression.FunctionCall;
import element.expression.FunctionCallArgumentList;
import element.expression.Variable;
import element.expression.data.Bool;
import element.expression.data.Doub;
import element.expression.data.Int;
import element.expression.data.Str;
import element.expression.data.Type;
import element.expression.operator.binary.arithmetic.Add;
import element.expression.operator.binary.arithmetic.Divide;
import element.expression.operator.binary.arithmetic.Modulo;
import element.expression.operator.binary.arithmetic.Multiply;
import element.expression.operator.binary.arithmetic.Power;
import element.expression.operator.binary.arithmetic.Subtract;
import element.expression.operator.binary.logical.And;
import element.expression.operator.binary.logical.Or;
import element.expression.operator.binary.logical.Xor;
import element.expression.operator.binary.relational.Equal;
import element.expression.operator.binary.relational.Greater;
import element.expression.operator.binary.relational.GreaterEqual;
import element.expression.operator.binary.relational.Less;
import element.expression.operator.binary.relational.LessEqual;
import element.expression.operator.binary.relational.NotEqual;
import element.expression.operator.input.Scan;
import element.expression.operator.ternary.Conditional;
import element.expression.operator.unary.arithmetic.Absolute;
import element.expression.operator.unary.arithmetic.Factorial;
import element.expression.operator.unary.arithmetic.Minus;
import element.expression.operator.unary.arithmetic.Plus;
import element.expression.operator.unary.logical.Not;
import element.expression.operator.unary.output.Print;
import element.expression.operator.unary.type.TypeOf;
import element.statement.Assign;
import element.statement.Block;
import element.statement.FunctionDefinition;
import element.statement.FunctionDefinitionArgumentList;
import element.statement.If;
import element.statement.Statement;
import element.statement.While;

/**
 * 構文解析器です.
 * @author sin
 * @version 2017.4.8
 */
public class ElementParser {
	/**
	 * 改行コード.<br>
	 * CR, CR+LF, LFの3通りのみ考慮する.
	 */
	private final String NEW_LINE;
	private boolean newlineIsCR, newlineIsLF, newlineIsCRLF;
	private final void checkNewLine(){
		if(NEW_LINE.equals("\n")){
			newlineIsLF = true;
		} else if(NEW_LINE.equals("\r")){
			newlineIsCR = true;
		} else {
			newlineIsCRLF = true;
		}
	}
	/**
	 * 行番号.
	 */
	private int lineNumber;
	/**
	 * 入力ストリーム.
	 */
	private BufferedReader br;
	/**
	 * 注目している文字.
	 */
	private char currChar;
	/**
	 * 注目しているトークン.
	 */
	private Token currToken;
	/**
	 * トークンの値.
	 */
	private String tokenValue;
	/**
	 * 先読み済かどうか.<br>
	 * {@code true}のとき先読み済.
	 */
	private boolean wait;
	/**
	 * 字句解析を見せるかどうか.
	 */
	public boolean printLex = false;
	/**
	 * ストリームの終端かどうか.
	 */
	public boolean isEnd = false;

	/**
	 * 入力ストリームを指定して構文解析器を作ります.
	 *
	 * @param in 入力ストリーム
	 */
	public ElementParser(InputStream in){
		br = new BufferedReader(new InputStreamReader(in));
		currChar = '\0';
		currToken = Token.START;
		tokenValue = "";
		NEW_LINE = System.getProperty("line.separator");
		lineNumber = 1;
		checkNewLine();
	}

	/**
	 * 文字列を指定して構文解析器を作ります.
	 *
	 * @param in 入力文字列
	 * @throws UnsupportedEncodingException UTF-8がサポートされていない場合
	 * @see String#getBytes(String)
	 */
	public ElementParser(String in) throws UnsupportedEncodingException {
		byte[] bs = in.getBytes("UTF-8");
		br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(bs)));
		currChar = '\0';
		currToken = Token.START;
		tokenValue = "";
		NEW_LINE = System.getProperty("line.separator");
		lineNumber = 1;
		checkNewLine();
	}

	/**
	 * 字句解析をします.
	 *
	 * @throws Exception 入出力エラーもしくは字句解析エラー
	 */
	public void lex() throws Exception{
		nextToken();
		while(currToken != Token.SEMICOLON){
			System.out.println("Lex : Token, Val = " + currToken + ", " + tokenValue);
			nextToken();
		}
	}
	/**
	 * 行番号を数えます.
	 */
	private void countLine(){
		if(newlineIsCR && currChar == '\r'){
			lineNumber++;
		} else if(newlineIsLF && currChar == '\n'){
			lineNumber++;
		} else if(newlineIsCRLF){
			if(currChar == '\r'){
				CRchecked = true;
			} else if(CRchecked && currChar == '\n'){
				lineNumber++;
				CRchecked = false;
			} else {
				CRchecked = false;
			}
		}
	}
	private boolean CRchecked;
	/**
	 * 次の文字を読む.
	 *
	 * @throws IOException 入出力の失敗が発生したとき
	 */
	private void nextChar() throws IOException{
		if(wait){
			wait = false;
			return;
		}
		int c = br.read();
		isEnd = c == -1;
		currChar = (char)c;
		countLine();
	}
	/**
	 * 1文字戻す.
	 */
	private void rewind(){
		wait = true;
	}

	/**
	 * 次のトークンを読む.
	 *
	 * @throws IOException 入出力の失敗が発生したとき
	 * @throws NoSuchTokenException 解釈できない文字を読んだとき
	 * @see parse.Token
	 */
	private void nextToken() throws IOException, NoSuchTokenException{
		do{
			nextChar();
		} while(Character.isWhitespace(currChar));
		switch(currChar){
		case '+':
			currToken = Token.PLUS;
			break;
		case '-':
			currToken = Token.MINUS;
			break;
		case '*':
			currToken = Token.AST;
			nextChar();
			if(currChar == '*'){
				currToken = Token.POWER;
			} else {
				rewind();
			}
			break;
		case '/':
			currToken = Token.SLASH;
			break;
		case '%':
			currToken = Token.PERC;
			break;
		case '&':
			currToken = Token.AMPER;
			break;
		case '|':
			currToken = Token.VERT;
			break;
		case '^':
			currToken = Token.CARET;
			break;
		case '!':
			currToken = Token.EXCL;
			nextChar();
			if(currChar == '='){
				currToken = Token.NOTEQ;
			} else {
				rewind();
			}
			break;
		case '?':
			currToken = Token.QUES;
			break;
		case '<':
			currToken = Token.LESS;
			nextChar();
			if(currChar == '='){
				currToken = Token.LESSEQ;
			} else {
				rewind();
			}
			break;
		case '=':
			currToken = Token.ASSIGN;
			nextChar();
			if(currChar == '='){
				currToken = Token.EQUAL;
			} else {
				rewind();
			}
			break;
		case '>':
			currToken = Token.GRAET;
			nextChar();
			if(currChar == '='){
				currToken = Token.GREATEQ;
			} else {
				rewind();
			}
			break;
		case '(':
			currToken = Token.LPAR;
			break;
		case ')':
			currToken = Token.RPAR;
			break;
		case '{':
			currToken = Token.LBRA;
			break;
		case '}':
			currToken = Token.RBRA;
			break;
		case ':':
			currToken = Token.COLON;
			break;
		case ',':
			currToken = Token.COMMA;
			break;
		case ';':
			currToken = Token.SEMICOLON;
			break;
		default:
			if(currChar == '"'){
				currToken = Token.STRING;
				readString();
			} else if(Character.isDigit(currChar)){
				currToken = Token.INTEGER;
				readNumber();
			} else if(Character.isAlphabetic(currChar)){
				currToken = Token.IDENTIFIER;
				readIdentifier();
			} else {
				throw new NoSuchTokenException("Line:" + lineNumber + ", ElementParser|currToken="+currToken+",currChar="+currChar);
			}
		}

		/* LEXER : 現在のトークン */
		if(printLex)
			System.out.println("Line:" + lineNumber + ", currToken = " + currToken + ", tokenValue = " + tokenValue);
	}

	/**
	 * 構文規則Number ::= Integer | Double<br>
	 * Integer ::= ["0" - "9"]+<br>
	 * Double ::= Integer "." Integer<br>
	 * に従う文字列を読む.
	 *
	 * @throws IOException 入出力の失敗が発生したとき
	 */
	private void readNumber() throws IOException{
		tokenValue = "" + currChar;
		while(true){
			nextChar();
			if(!Character.isDigit(currChar))
				break;
			tokenValue += currChar;
		}
		if(currChar == '.'){
			currToken = Token.DOUBLE;
			tokenValue += '.';
			while(true){
				nextChar();
				if(!Character.isDigit(currChar))
					break;
				tokenValue += currChar;
			}
		}
		rewind();
	}
	/**
	 * 構文規則String ::= "char*"に従う文字列を読む.
	 *
	 * @throws IOException 入出力の失敗が発生したとき
	 */
	private void readString() throws IOException{
		boolean escape = false;
		tokenValue = "";
		while(true){
			nextChar();
			if(!escape && currChar == '"'){
				break;
			}
			else if(!escape && currChar == '\\')
				escape = true;
			else {
				tokenValue += currChar;
				if(escape)
					escape = false;
			}
		}
	}
	//文字列をとりあえず切っておいて, マッチング
	/**
	 * 構文規則identifier ::= ALPHABETIC (ALPHABETIC | DIGIT)*に従う文字列を読む.<br>
	 *
	 * もしキーワードを発見すれば, それを表すトークンとする.
	 *
	 * @throws IOException 入出力の失敗が発生したとき
	 * @see Character#isAlphabetic(char)
	 * @see Character#isDigit(char)
	 */
	private void readIdentifier() throws IOException{
		String acc = "" + currChar;
		// キーワードと同時に
		while(true){
			/*if(acc.equals("let")){
				currToken = Token.LET;
				return;
			} else if(acc.equals("if")){
				currToken = Token.IF;
				return;
			} else if(acc.equals("else")){
				currToken = Token.ELSE;
				return;
			} else if(acc.equals("then")){
				currToken = Token.THEN;
				return;
			} else if(acc.equals("while")){
				currToken = Token.WHILE;
				return;
			} else if(acc.equals("true")){
				currToken = Token.TRUE;
				return;
			} else if(acc.equals("false")){
				currToken = Token.FALSE;
				return;
			} else if(acc.equals("int")){
				currToken = Token.TYPE_INT;
				return;
			} else if(acc.equals("double")){
				currToken = Token.TYPE_DOUBLE;
				return;
			} else if(acc.equals("bool")){
				currToken = Token.TYPE_BOOL;
				return;
			} else if(acc.equals("string")){
				currToken = Token.TYPE_STRING;
				return;
			} else if(acc.equals("typeof")){
				currToken = Token.TYPEOF;
				return;
			} else if(acc.equals("and")){
				currToken = Token.AND;
				return;
			} else if(acc.equals("or")){
				currToken = Token.OR;
				return;
			} else if(acc.equals("xor")){
				currToken = Token.XOR;
				return;
			} else if(acc.equals("not")){
				currToken = Token.NOT;
				return;
			} else if(acc.equals("abs")){
				currToken = Token.ABS;
				return;
			} else if(acc.equals("print")){
				currToken = Token.PRINT;
				return;
			} else if(acc.equals("scan")){
				currToken = Token.SCAN;
				return;
			}*/
			nextChar();
			if(!Character.isAlphabetic(currChar) && !Character.isDigit(currChar)) // 数字追加 @ 2017.3.18
				break;
			acc += currChar;
		}
		// 予約語の判定方法を変える @ 2017.4.7
		if(acc.equals("let")){
			currToken = Token.LET;
			rewind();
			return;
		} else if(acc.equals("if")){
			currToken = Token.IF;
			rewind();
			return;
		} else if(acc.equals("else")){
			currToken = Token.ELSE;
			rewind();
			return;
		} else if(acc.equals("then")){
			currToken = Token.THEN;
			rewind();
			return;
		} else if(acc.equals("while")){
			currToken = Token.WHILE;
			rewind();
			return;
		} else if(acc.equals("true")){
			currToken = Token.TRUE;
			rewind();
			return;
		} else if(acc.equals("false")){
			currToken = Token.FALSE;
			rewind();
			return;
		} else if(acc.equals("int")){
			currToken = Token.TYPE_INT;
			rewind();
			return;
		} else if(acc.equals("double")){
			currToken = Token.TYPE_DOUBLE;
			rewind();
			return;
		} else if(acc.equals("bool")){
			currToken = Token.TYPE_BOOL;
			rewind();
			return;
		} else if(acc.equals("string")){
			currToken = Token.TYPE_STRING;
			rewind();
			return;
		} else if(acc.equals("typeof")){
			currToken = Token.TYPEOF;
			rewind();
			return;
		} else if(acc.equals("and")){
			currToken = Token.AND;
			rewind();
			return;
		} else if(acc.equals("or")){
			currToken = Token.OR;
			rewind();
			return;
		} else if(acc.equals("xor")){
			currToken = Token.XOR;
			rewind();
			return;
		} else if(acc.equals("not")){
			currToken = Token.NOT;
			rewind();
			return;
		} else if(acc.equals("abs")){
			currToken = Token.ABS;
			rewind();
			return;
		} else if(acc.equals("print")){
			currToken = Token.PRINT;
			rewind();
			return;
		} else if(acc.equals("scan")){
			currToken = Token.SCAN;
			rewind();
			return;
		}
		tokenValue = acc;
		rewind();
	}

	public Program parse() throws Exception{
		do{
			nextChar();
		} while(Character.isWhitespace(currChar));
		rewind();
		if(isEnd){
			return null;
		}
		// 空白, rewind, isEnd added @ 2017.3.18
		nextToken();
		Program p = Prog();
		if(currToken != Token.SEMICOLON)
			throw new ParseException("Line:" + lineNumber +", Ends error : currToken=" + currToken + ", currChar=" + currChar);
		return p;
	}
	// needs ;
	private Program Prog() throws Exception{
		if(	currToken == Token.IF ||
				currToken == Token.WHILE ||
				currToken == Token.LET){
			Statement s = Stmt();
			return s;
		}
		if(currToken == Token.LBRA){
			Block b = Blk();
			return b;
		}
		Expression e = Expr();
		return e;
	}
	private Statement Stmt() throws Exception{
		// if
		if(currToken == Token.IF){
			nextToken();
			if(currToken != Token.LPAR)
				throw new ParseException("Line:" + lineNumber + ",Invalid if:lpar.");
			nextToken();
			Expression cond = Expr();
			if(currToken != Token.RPAR)
				throw new ParseException("Line:" + lineNumber +", Invalid if:rpar.");
			nextToken();
			if(currToken != Token.THEN)
				throw new ParseException("Line:" + lineNumber +", Invalid if:then.");
			nextToken();
			Program s = Prog();
			if(currToken != Token.ELSE)
				return new If(cond, s, null);
			nextToken();
			Program e = Prog();
			return new If(cond, s, e);
		}
		// while
		if(currToken == Token.WHILE){
			nextToken();
			if(currToken != Token.LPAR)
				throw new ParseException("Line:" + lineNumber +", Invalid while:(.");
			nextToken();
			Expression cond = Expr();
			if(currToken != Token.RPAR)
				throw new ParseException("Line:" + lineNumber +", Invalid while:).");
			nextToken();
			Program proc = Prog();
			return new While(cond, proc);
		}
		// let
		if(currToken == Token.LET){
			nextToken();
			if(currToken != Token.IDENTIFIER)
				throw new ParseException("Line:" + lineNumber +", Invalid let");
			String name = tokenValue;
			nextToken();
			// function def
			if(currToken == Token.LPAR){
				nextToken();
				FunctionDefinitionArgumentList args = new FunctionDefinitionArgumentList();
				while(currToken != Token.RPAR){
					if(currToken != Token.IDENTIFIER)
						throw new ParseException("Line:" + lineNumber +", Invalid let.defun:arglist.");
					args.add(tokenValue);
					nextToken();
					if(currToken == Token.COMMA)
						nextToken();
				}
				nextToken();
				if(currToken != Token.ASSIGN)
					throw new ParseException("Line:" + lineNumber +", Invalid let.defun:no =.");
				nextToken();
				Program proc = Prog();
				/*if(currToken != Token.SEMICOLON)
					throw new ParseException("Invalid let.defun:no ;.");
				nextToken();*/
				return new FunctionDefinition(name, args, proc);
			}
			// variable def
			if(currToken != Token.ASSIGN)
				throw new ParseException("Line:" + lineNumber +", Invalid let.defvar:no =.");
			nextToken();
			Expression val = Expr();
			/*if(currToken != Token.SEMICOLON)
				throw new ParseException("Invalid let.defvar:no ;.");
			nextToken();*/
			return new Assign(name, val);
		}

		throw new ParseException("Line:" + lineNumber +", Syntax error: currToken, currChar = " + currToken + ", " + currChar);
	}
	private Block Blk() throws Exception{
		nextToken();
		Block b = new Block();
		while(currToken != Token.RBRA){
			b.add(Prog());
			if(currToken != Token.SEMICOLON)
				throw new ParseException("Line:" + lineNumber +", Invalid block.");
			nextToken();
		}
		nextToken();
		return b;
	}
	private Expression Expr() throws Exception{
		Expression x = C1();
		if(currToken != Token.QUES)
			return x;
		nextToken();
		Expression s = Expr();
		if(currToken != Token.COLON)
			throw new ParseException("Line:" + lineNumber +", Invalid conditional.");
		nextToken();
		Expression f = Expr();
		return new Conditional(x, s, f);
	}
	private Expression C1() throws Exception{
		Expression x = C2();
		while(currToken == Token.VERT || currToken == Token.OR){
			nextToken();
			x = new Or(x, C2());
		}
		return x;
	}
	private Expression C2() throws Exception{
		Expression x = C3();
		while(currToken == Token.AMPER || currToken == Token.AND){
			nextToken();
			x = new And(x, C3());
		}
		return x;
	}
	private Expression C3() throws Exception{
		Expression x = C4();
		while(currToken == Token.CARET || currToken == Token.XOR){
			nextToken();
			x = new Xor(x, C4());
		}
		return x;
	}
	private Expression C4() throws Exception{
		Expression x = E1();
		while(true){
			if(currToken == Token.LESS){
				nextToken();
				x = new Less(x, E1());
			} else if(currToken == Token.EQUAL){
				nextToken();
				x = new Equal(x, E1());
			} else if(currToken == Token.GRAET){
				nextToken();
				x = new Greater(x, E1());
			} else if(currToken == Token.LESSEQ){
				nextToken();
				x = new LessEqual(x, E1());
			} else if(currToken == Token.NOTEQ){
				nextToken();
				x = new NotEqual(x, E1());
			} else if(currToken == Token.GREATEQ){
				nextToken();
				x = new GreaterEqual(x, E1());
			} else {
				return x;
			}
		}
	}
	private Expression E1() throws Exception{
		Expression x = E2();
		while(true){
			if(currToken == Token.PLUS){
				nextToken();
				x = new Add(x, E2());
			} else if(currToken == Token.MINUS){
				nextToken();
				x = new Subtract(x, E2());
			} else {
				return x;
			}
		}
	}
	private Expression E2() throws Exception{
		Expression x = E3();
		while(currToken == Token.PERC){
			nextToken();
			x = new Modulo(x, E3());
		}
		return x;
	}
	private Expression E3() throws Exception{
		Expression x = E4();
		while(true){
			if(currToken == Token.AST){
				nextToken();
				x = new Multiply(x, E4());
			} else if(currToken == Token.SLASH){
				nextToken();
				x = new Divide(x, E4());
			} else {
				return x;
			}
		}
	}
	private Expression E4() throws Exception{
		Expression x = E5();
		while(currToken == Token.POWER){
			nextToken();
			x = new Power(x, E5());
		}
		return x;
	}
	private Expression E5() throws Exception{
		Expression x = C5();
		if(currToken == Token.EXCL){
			nextToken();
			x = new Factorial(x);
		}
		return x;
	}
	private Expression C5() throws Exception{
		int count = 0;
		while(currToken == Token.EXCL || currToken == Token.NOT){
			nextToken();
			count++;
		}
		Expression x = E6();
		for(int i = 0; i < count; i++)
			x = new Not(x);
		return x;
	}
	private Expression E6() throws Exception{
		if(currToken == Token.PLUS){
			nextToken();
			return new Plus(E6());
		}
		if(currToken == Token.MINUS){
			nextToken();
			return new Minus(E6());
		}
		return E7();
	}
	private Expression E7() throws Exception{
		// Data
		if(currToken == Token.INTEGER){
			String s = tokenValue;
			nextToken();
			return new Int(new Integer(s));
		}
		if(currToken == Token.DOUBLE){
			String s = tokenValue;
			nextToken();
			return new Doub(new Double(s));
		}
		if(currToken == Token.STRING){
			String s = tokenValue;
			nextToken();
			return new Str(s);
		}
		if(currToken == Token.TRUE){
			nextToken();
			return new Bool(true);
		}
		if(currToken == Token.FALSE){
			nextToken();
			return new Bool(false);
		}

		// ( expression ), CAST
		if(currToken == Token.LPAR){
			nextToken();

			Type type = null;
			switch(currToken){
			case TYPE_INT:
				type = Type.INT;
				break;
			case TYPE_DOUBLE:
				type = Type.DOUBLE;
				break;
			case TYPE_STRING:
				type = Type.STRING;
				break;
			case TYPE_BOOL:
				type = Type.BOOL;
				break;
			default:
				break;
			}
			// CAST
			if(type != null){
				nextToken();
				if(currToken != Token.RPAR)
					throw new ParseException("Line:" + lineNumber +", Invalid cast:).");
				nextToken();
				Expression x = Expr();
				return new Cast(type, x);
			}

			Expression x = Expr();
			if(currToken != Token.RPAR)
				throw new ParseException("Line:" + lineNumber +", Not matching paren.");
			nextToken();
			return x;
		}
		// abs( expression )
		if(currToken == Token.ABS){
			nextToken();
			if(currToken != Token.LPAR)
				throw new ParseException("Line:" + lineNumber +", Invalid abs:(.");
			nextToken();
			Expression x = Expr();
			if(currToken != Token.RPAR)
				throw new ParseException("Line:" + lineNumber +", Invalid abs:).");
			nextToken();
			return new Absolute(x);
		}
		// print( expression )
		if(currToken == Token.PRINT){
			nextToken();
			if(currToken != Token.LPAR)
				throw new ParseException("Line:" + lineNumber +", Invalid print:(.");
			nextToken();
			Expression x = Expr();
			if(currToken != Token.RPAR)
				throw new ParseException("Line:" + lineNumber +", Invalid print:).");
			nextToken();
			return new Print(x);
		}
		// scan()
		if(currToken == Token.SCAN){
			nextToken();
			if(currToken != Token.LPAR)
				throw new ParseException("Line:" + lineNumber +", Invalid scan:(.");
			nextToken();
			if(currToken != Token.RPAR)
				throw new ParseException("Line:" + lineNumber +", Invalid scan:).");
			nextToken();
			return new Scan();
		}
		// typeof( expression )
		if(currToken == Token.TYPEOF){
			nextToken();
			if(currToken != Token.LPAR)
				throw new ParseException("Line:" + lineNumber +", Invalid typeof:(.");
			nextToken();
			Expression x = Expr();
			if(currToken != Token.RPAR)
				throw new ParseException("Line:" + lineNumber +", Invalid typeof:).");
			nextToken();
			return new TypeOf(x);
		}

		// identifier, functioncall
		if(currToken == Token.IDENTIFIER){
			String name = tokenValue;
			nextToken();
			if(currToken != Token.LPAR)
				return new Variable(name);
			nextToken();
			FunctionCallArgumentList args = new FunctionCallArgumentList();
			while(currToken != Token.RPAR){
				args.add(Expr());
				if(currToken == Token.COMMA)
					nextToken();
			}
			nextToken();
			return new FunctionCall(name, args);
		}

		throw new ParseException("Line:" + lineNumber +", Syntax error: currToken, currChar = " + currToken + ", " + currChar);
	}
}
