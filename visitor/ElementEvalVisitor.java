package visitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import element.Program;
import element.expression.Cast;
import element.expression.FunctionCall;
import element.expression.FunctionCallArgumentList;
import element.expression.Variable;
import element.expression.data.Bool;
import element.expression.data.Data;
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
import element.statement.Empty;
import element.statement.FunctionDefinition;
import element.statement.FunctionDefinitionArgumentList;
import element.statement.If;
import element.statement.While;
import parser.ElementParser;

// TODO:不必要な防御的コピーを取り除く
/**
 * ASTを評価する.<br>
 *
 * 演算子のそれぞれについて{@link element.expression.data.Data}の
 * データ型すべての多重定義が与えられます.
 *
 * それは演算子が見かけ上満たすような関係を満足するとは限りません.
 * 例えば文字列について
 * {@code a - b}, {@code a + (-b)}
 * は等しくありません.<br>
 *
 * getter/setter, コンストラクタはすべて防御的コピーによってやりとりします.<br>
 *
 * @author sin
 * @version 2017.3.17
 */
public class ElementEvalVisitor implements ElementVisitor {
	private Box box;
	private VariableTable variableTable;
	private FunctionTable functionTable;
	private Random r;

	/**
	 * 初期状態で作成する.<br>
	 * 空の関数テーブル, 変数テーブルを指定する.
	 * ]
	 */
	public ElementEvalVisitor(){
		this.variableTable = new VariableTable();
		this.functionTable = new FunctionTable();
		this.box = new Box(new Bool(false));
		this.r = new Random();
	}
	/**
	 * 変数テーブルと関数テーブルを指定して作る.
	 * @param variableTable 変数テーブル
	 * @param functionTable 関数テーブル
	 */
	public ElementEvalVisitor(VariableTable variableTable, FunctionTable functionTable){
		this.variableTable = new VariableTable(variableTable);
		this.functionTable = new FunctionTable(functionTable);
		box = new Box(new Bool(false));
		this.r = new Random();
	}
	/**
	 * コピーコンストラクタ.
	 * @param eval コピー元
	 */
	public ElementEvalVisitor(ElementEvalVisitor eval){
		this.variableTable = new VariableTable(eval.getVariableTable());
		this.functionTable = new FunctionTable(eval.getFunctionTable());
		this.box = new Box(eval.getValue());
		this.r = eval.r;
	}
	/**
	 * 変数テーブルの防御的コピーを取得する.
	 * @return VariableTable
	 */
	public VariableTable getVariableTable() {
		return new VariableTable(variableTable);
	}
	/**
	 * 変数テーブルを防御的コピーで設定する.
	 * @param variableTable VariableTable
	 */
	public void setVariableTable(VariableTable variableTable) {
		this.variableTable = new VariableTable(variableTable);
	}
	/**
	 * 関数テーブルの防御的コピーを取得する.
	 * @return FunctionTable
	 */
	public FunctionTable getFunctionTable() {
		return new FunctionTable(functionTable);
	}
	/**
	 * 関数テーブルを防御的コピーで設定する.
	 * @param functionTable FunctionTable
	 */
	public void setFunctionTable(FunctionTable functionTable) {
		this.functionTable = new FunctionTable(functionTable);
	}
	/**
	 * {@code x, y}いずれかの型が{@code target}と等しいとき{@code true}を返します.
	 * @param x Box
	 * @param y Box
	 * @param target Type
	 * @return boolean
	 * @see DataConverter#typeOr(Type, Type, Type)
	 */
	public static boolean typeOr(Box x, Box y, Type target){
		return DataConverter.typeOr(x.getType(), y.getType(), target);
	}

	/**
	 * 結果の防御的コピーを取得する.
	 *
	 * @return 結果
	 */
	@Override
	public Data getValue(){
		return box.getData().copy();
	}
	/**
	 * 結果のデータ型を取得する.
	 * @return データ型
	 */
	public Type getType(){
		return box.getType();
	}

	@Override
	public void visit(Program p) {
		/* no operation */
		System.err.println("visit nowhere!");
	}
	/**
	 * bool型のデータを読む.
	 */
	@Override
	public void visit(Bool p) {
		box = new Box(new Bool(p.getValue()));
	}
	/**
	 * double型のデータを読む.
	 */
	@Override
	public void visit(Doub p) {
		box = new Box(new Doub(p.getValue()));
	}
	/**
	 * string型のデータを読む.
	 */
	@Override
	public void visit(Str p) {
		box = new Box(new Str(p.getValue()));
	}
	/**
	 * int型のデータを読む.
	 */
	@Override
	public void visit(Int p) {
		box = new Box(new Int(p.getValue()));
	}

	/**
	 * 加算.<br>
	 * String : 連接<br>
	 * Double : 数値の和<br>
	 * Int    : 数値の和<br>
	 * Bool   : 論理和<br>
	 *
	 * この演算は上位のデータ型の結果を返します.
	 */
	@Override
	public void visit(Add p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		if(typeOr(left, box, Type.STRING)){
			String a = DataConverter.convertToStr(left.getData()).getValue(),
					b = DataConverter.convertToStr(box.getData()).getValue();
			box = new Box(new Str(a + b));
		} else if(typeOr(left, box, Type.DOUBLE)){
			Double a = DataConverter.convertToDoub(left.getData()).getValue(),
					b = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(a + b));
		} else if(typeOr(left, box, Type.INT)){
			Integer a = DataConverter.convertToInt(left.getData()).getValue(),
					b = DataConverter.convertToInt(box.getData()).getValue();
			box = new Box(new Int(a + b));
		} else if(typeOr(left, box, Type.BOOL)){
			Boolean a = DataConverter.convertToBool(left.getData()).getValue(),
					b = DataConverter.convertToBool(box.getData()).getValue();
			box = new Box(new Bool(a || b));
		} else {
			box = null;
			System.err.println("Add:null!");
			System.err.println("left = " + left.getData() + ", right = " + box.getData());
		}
	}
	/**
	 * 文字列上の引き算を定義する.<br>
	 *
	 * もし{@code left}の接尾辞が{@code right}と一致したら
	 * それを{@code left}から取り除いたものを返す.<br>
	 *
	 * さもなければ{@code left}そのものを返す.
	 * @param left String
	 * @param right String
	 * @return String
	 * @see ElementEvalVisitor#removeSuffix(String, String)
	 */
	public static String subtract(String left, String right){
		return ElementEvalVisitor.removeSuffix(left, right);
	}
	/**
	 * 接尾辞を取り除く.<br>
	 * もし{@code suffix}を接尾辞として{@code str}を含むとき,
	 * これを取り除いた{@code str}を返す.
	 * さもなければ{@code str}そのままを返す.
	 * @param str 対象文字列
	 * @param suffix 接尾辞
	 * @return String
	 */
	public static String removeSuffix(String str, String suffix){
		if(str.length() < suffix.length())
			return str;
		for(int i = 0; i < str.length(); i++){
			if(str.substring(i).equals(suffix)){
				return str.substring(0, i);
			}
		}
		return str;
	}
	/**
	 * ブール値の引き算を定義する.<br>
	 *
	 * @param left Boolean
	 * @param right Boolean
	 * @return {@code left || !right}
	 */
	public static Boolean subtract(Boolean left, Boolean right){
		return left || !right;
	}
	/**
	 * 減算.<br>
	 * String : {@link ElementEvalVisitor#subtract(String, String)}<br>
	 * Double : 数値の差<br>
	 * Int    : 数値の差<br>
	 * Bool   : {@link ElementEvalVisitor#subtract(Boolean, Boolean)}<br>
	 * この演算は上位のデータ型の結果を返します.
	 */
	@Override
	public void visit(Subtract p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		if(typeOr(left, box, Type.STRING)){
			String a = DataConverter.convertToStr(left.getData()).getValue(),
					b = DataConverter.convertToStr(box.getData()).getValue();
			box = new Box(new Str(ElementEvalVisitor.subtract(a, b)));
		} else if(typeOr(left, box, Type.DOUBLE)){
			Double a = DataConverter.convertToDoub(left.getData()).getValue(),
					b = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(a - b));
		} else if(typeOr(left, box, Type.INT)){
			Integer a = DataConverter.convertToInt(left.getData()).getValue(),
					b = DataConverter.convertToInt(box.getData()).getValue();
			box = new Box(new Int(a - b));
		} else if(typeOr(left, box, Type.BOOL)){
			Boolean a = DataConverter.convertToBool(left.getData()).getValue(),
					b = DataConverter.convertToBool(box.getData()).getValue();
			box = new Box(new Bool(ElementEvalVisitor.subtract(a, b)));
		} else {
			box = null;
			System.err.println("Subtract:null!");
			System.err.println("left = " + left.getData() + ", right = " + box.getData());
		}
	}

	/**
	 * 文字列の乗算を定義する.<br>
	 *
	 * 例を示す.<br>
	 * {@code
	 * 	"abc" * "xy" = "axybxycxy";<br>
	 *  "" * string = "";<br>
	 *  string * "" = string;<br>
	 * }
	 * @param left String
	 * @param right String
	 * @return String
	 * @see ElementEvalVisitor#insertEachChar(String, String)
	 */
	public static String multiply(String left, String right){
		return ElementEvalVisitor.insertEachChar(left, right);
	}
	/**
	 * {@code target}の各文字のうしろに{@code insert}を
	 * 挿入したものを返す.
	 * @param target 対象文字列
	 * @param insert 挿入文字列
	 * @return String
	 */
	public static String insertEachChar(String target, String insert){
		String acc = "";
		for(int i = 0; i < target.length(); i++){
			acc += target.charAt(i) + insert;
		}
		return acc;
	}
	/**
	 * 乗算.<br>
	 * String : {@link ElementEvalVisitor#multiply(String, String)}<br>
	 * Double : 数値の積<br>
	 * Int    : 数値の積<br>
	 * Bool   : 論理積<br>
	 * この演算は上位のデータ型の結果を返します.
	 */
	@Override
	public void visit(Multiply p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		if(typeOr(left, box, Type.STRING)){
			String a = DataConverter.convertToStr(left.getData()).getValue(),
					b = DataConverter.convertToStr(box.getData()).getValue();
			box = new Box(new Str(ElementEvalVisitor.multiply(a, b)));
		} else if(typeOr(left, box, Type.DOUBLE)){
			Double a = DataConverter.convertToDoub(left.getData()).getValue(),
					b = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(a * b));
		} else if(typeOr(left, box, Type.INT)){
			Integer a = DataConverter.convertToInt(left.getData()).getValue(),
					b = DataConverter.convertToInt(box.getData()).getValue();
			box = new Box(new Int(a * b));
		} else if(typeOr(left, box, Type.BOOL)){
			Boolean a = DataConverter.convertToBool(left.getData()).getValue(),
					b = DataConverter.convertToBool(box.getData()).getValue();
			box = new Box(new Bool(a && b));
		} else {
			box = null;
			System.err.println("Multiply:null!");
			System.err.println("left = " + left.getData() + ", right = " + box.getData());
		}
	}

	/**
	 * 文字列の除算を定義する.<br>
	 * {@code left}に含まれる{@code right}の部分を
	 * 空文字列で置き換えます.
	 * 例を示す.<br>
	 * {@code "axybxycxy" / "xy" = "abc";
	 *  }
	 *
	 * @param left String
	 * @param right String
	 * @return String
	 */
	public static String divide(String left, String right){
		return left.replaceAll(right, "");
	}
	/**
	 * ブール値の除算を定義する.
	 *
	 * @param left Boolean
	 * @param right Boolean
	 * @return {@code left && !right}
	 */
	public static Boolean divide(Boolean left, Boolean right){
		return left && !right;
	}
	/**
	 * 除算.<br>
	 * String : {@link ElementEvalVisitor#divide(String, String)}<br>
	 * Double : 数値の商<br>
	 * Int    : 数値の商<br>
	 * Bool   : {@link ElementEvalVisitor#divide(Boolean, Boolean)}<br>
	 * この演算は上位のデータ型の結果を返します.
	 */
	@Override
	public void visit(Divide p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		if(typeOr(left, box, Type.STRING)){
			String a = DataConverter.convertToStr(left.getData()).getValue(),
					b = DataConverter.convertToStr(box.getData()).getValue();
			box = new Box(new Str(ElementEvalVisitor.divide(a, b)));
		} else if(typeOr(left, box, Type.DOUBLE)){
			Double a = DataConverter.convertToDoub(left.getData()).getValue(),
					b = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(a / b));
		} else if(typeOr(left, box, Type.INT)){
			Integer a = DataConverter.convertToInt(left.getData()).getValue(),
					b = DataConverter.convertToInt(box.getData()).getValue();
			box = new Box(new Int(a / b));
		} else if(typeOr(left, box, Type.BOOL)){
			Boolean a = DataConverter.convertToBool(left.getData()).getValue(),
					b = DataConverter.convertToBool(box.getData()).getValue();
			box = new Box(new Bool(ElementEvalVisitor.divide(a, b)));
		} else {
			box = null;
			System.err.println("Divide:null!");
			System.err.println("left = " + left.getData() + ", right = " + box.getData());
		}
	}

	/**
	 * 文字列型の剰余を定義します.<br>
	 * もし{@code right in left}ならば{@code left},
	 * さもなければ空文字列を返します.
	 * @param left String
	 * @param right String
	 * @return String
	 */
	public static String modulo(String left, String right){
		if(left.contains(right))
			return left;
		return "";
	}
	/**
	 * ブール値の剰余を定義する.
	 *
	 * @param left Boolean
	 * @param right Boolean
	 * @return 否定論理和
	 */
	public static Boolean modulo(Boolean left, Boolean right){
		return !(left || right);
	}
	/**
	 * 剰余.<br>
	 * String : {@link ElementEvalVisitor#modulo(String, String)}<br>
	 * Double : 数値の剰余<br>
	 * Int    : 数値の剰余<br>
	 * Bool   : {@link ElementEvalVisitor#modulo(Boolean, Boolean)}<br>
	 * この演算は上位のデータ型の結果を返します.
	 */
	@Override
	public void visit(Modulo p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		if(typeOr(left, box, Type.STRING)){
			String a = DataConverter.convertToStr(left.getData()).getValue(),
					b = DataConverter.convertToStr(box.getData()).getValue();
			box = new Box(new Str(ElementEvalVisitor.modulo(a, b)));
		} else if(typeOr(left, box, Type.DOUBLE)){
			Double a = DataConverter.convertToDoub(left.getData()).getValue(),
					b = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(a % b));
		} else if(typeOr(left, box, Type.INT)){
			Integer a = DataConverter.convertToInt(left.getData()).getValue(),
					b = DataConverter.convertToInt(box.getData()).getValue();
			box = new Box(new Int(a % b));
		} else if(typeOr(left, box, Type.BOOL)){
			Boolean a = DataConverter.convertToBool(left.getData()).getValue(),
					b = DataConverter.convertToBool(box.getData()).getValue();
			box = new Box(new Bool(ElementEvalVisitor.modulo(a, b)));
		} else {
			box = null;
			System.err.println("Modulo:null!");
			System.err.println("left = " + left.getData() + ", right = " + box.getData());
		}
	}

	/**
	 * 文字列型の累乗を定義する.<br>
	 * @param left String
	 * @param right String
	 * @return {@code multiply(left + right, right)}
	 * @see ElementEvalVisitor#multiply(String, String)
	 */
	public static String power(String left, String right){
		return multiply(left + right, right);
	}

	/**
	 * ブール値の累乗を定義する.<br>
	 * @param left Boolean
	 * @param right Boolean
	 * @return 論理包含{@code !left || right}
	 */
	public static Boolean power(Boolean left, Boolean right){
		return !left || right;
	}
	/**
	 * 累乗.<br>
	 * String : {@link ElementEvalVisitor#power(String, String)}<br>
	 * Double : 数値の累乗<br>
	 * Int    : 数値の累乗<br>
	 * Bool   : {@link ElementEvalVisitor#power(Boolean, Boolean)}<br>
	 * この演算は上位のデータ型の結果を返します.
	 */
	@Override
	public void visit(Power p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		if(typeOr(left, box, Type.STRING)){
			String a = DataConverter.convertToStr(left.getData()).getValue(),
					b = DataConverter.convertToStr(box.getData()).getValue();
			box = new Box(new Str(ElementEvalVisitor.power(a, b)));
		} else if(typeOr(left, box, Type.DOUBLE)){
			Double a = DataConverter.convertToDoub(left.getData()).getValue(),
					b = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(Math.pow(a, b)));
		} else if(typeOr(left, box, Type.INT)){
			Integer a = DataConverter.convertToInt(left.getData()).getValue(),
					b = DataConverter.convertToInt(box.getData()).getValue();
			box = new Box(new Int((int) Math.pow(a, b)));
		} else if(typeOr(left, box, Type.BOOL)){
			Boolean a = DataConverter.convertToBool(left.getData()).getValue(),
					b = DataConverter.convertToBool(box.getData()).getValue();
			box = new Box(new Bool(ElementEvalVisitor.power(a, b)));
		} else {
			box = null;
			System.err.println("Power:null!");
			System.err.println("left = " + left.getData() + ", right = " + box.getData());
		}
	}

	/**
	 * 文字列型の論理積を定義する.<br>
	 * 共通部分があれば最も長いものを返す.
	 * さもなければ空文字列を返す.
	 * @param left String
	 * @param right String
	 * @return String
	 * @see ElementEvalVisitor#getCommonString(String, String)
	 */
	public static String and(String left, String right){
		return ElementEvalVisitor.getCommonString(left, right);
	}
	/**
	 * 最長の共通部分文字列を返す.
	 * @param left String
	 * @param right String
	 * @return String
	 */
	public static String getCommonString(String left, String right){
		String st, lg;
		if(left.length() < right.length()){
			st = left; lg = right;
		} else {
			st = right; lg = left;
		}
		List<String> substrings = ElementEvalVisitor.segments(st);
		// 長い順にソート
		substrings.sort((l, r) -> r.length() - l.length());
		for(String sub : substrings){
			if(lg.contains(sub))
				return sub;
		}
		return "";
	}
	/**
	 * {@code str}のすべての部分文字列のリストを取得する.
	 * @param str String
	 * @return 部分文字列のリスト
	 */
	public static List<String> segments(String str){
		List<String> list = new ArrayList<String>();
		if(str.isEmpty())
			return list;
		for(int i = 1; i <= str.length(); i++)
			list.add(str.substring(0, i));
		// 先頭文字を消去して再帰
		list.addAll(segments(str.substring(1)));
		return list;
	}
	/**
	 * 論理積.<br>
	 * String : {@link ElementEvalVisitor#and(String, String)}<br>
	 * Double : 数値の積<br>
	 * Int    : 数値の積<br>
	 * Bool   : 論理積<br>
	 * この演算は下位のデータ型の結果を返します.
	 */
	@Override
	public void visit(And p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		if(typeOr(left, box, Type.BOOL)){
			Boolean a = DataConverter.convertToBool(left.getData()).getValue(),
					b = DataConverter.convertToBool(box.getData()).getValue();
			box = new Box(new Bool(a && b));
		} else if(typeOr(left, box, Type.INT)){
			Integer a = DataConverter.convertToInt(left.getData()).getValue(),
					b = DataConverter.convertToInt(box.getData()).getValue();
			box = new Box(new Int(a * b));
		} else if(typeOr(left, box, Type.DOUBLE)){
			Double a = DataConverter.convertToDoub(left.getData()).getValue(),
					b = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(a * b));
		} else if(typeOr(left, box, Type.STRING)){
			String a = DataConverter.convertToStr(left.getData()).getValue(),
					b = DataConverter.convertToStr(box.getData()).getValue();
			box = new Box(new Str(ElementEvalVisitor.and(a, b)));
		} else {
			box = null;
			System.err.println("And:null!");
			System.err.println("left = " + left.getData() + ", right = " + box.getData());
		}
	}

	/**
	 * 論理和.<br>
	 * String : 連接<br>
	 * Double : 数値の和<br>
	 * Int    : 数値の和<br>
	 * Bool   : 論理和<br>
	 * この演算は下位のデータ型の結果を返します.
	 */
	@Override
	public void visit(Or p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		if(typeOr(left, box, Type.BOOL)){
			Boolean a = DataConverter.convertToBool(left.getData()).getValue(),
					b = DataConverter.convertToBool(box.getData()).getValue();
			box = new Box(new Bool(a || b));
		} else if(typeOr(left, box, Type.INT)){
			Integer a = DataConverter.convertToInt(left.getData()).getValue(),
					b = DataConverter.convertToInt(box.getData()).getValue();
			box = new Box(new Int(a + b));
		} else if(typeOr(left, box, Type.DOUBLE)){
			Double a = DataConverter.convertToDoub(left.getData()).getValue(),
					b = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(a + b));
		} else if(typeOr(left, box, Type.STRING)){
			String a = DataConverter.convertToStr(left.getData()).getValue(),
					b = DataConverter.convertToStr(box.getData()).getValue();
			box = new Box(new Str(a + b));
		} else {
			box = null;
			System.err.println("Or:null!");
			System.err.println("left = " + left.getData() + ", right = " + box.getData());
		}
	}
	/**
	 * 整数型の排他的論理和を定義する.
	 * @param left Integer
	 * @param right Integer
	 * @return {@code left * right - (left + right)}
	 */
	public static Integer xor(Integer left, Integer right){
		return left * right - (left + right);
	}
	/**
	 * 実数型の排他的論理和を定義する.
	 * @param left Double
	 * @param right Double
	 * @return {@code left * right - (left + right)}
	 */
	public static Double xor(Double left, Double right){
		return left * right - (left + right);
	}
	/**
	 * 文字列型の排他的論理和を定義する.<br>
	 * 最長の共通部分文字列を探し,
	 * それを{@code left}から取り除いたものと
	 * {@code right}から取り除いたものを連接する.
	 * @param left String
	 * @param right String
	 * @return String
	 */
	public static String xor(String left, String right){
		String common = ElementEvalVisitor.getCommonString(left, right);
		return left.replaceAll(common, "") + right.replaceAll(common, "");
	}
	/**
	 * 排他的論理和.<br>
	 * String : {@link ElementEvalVisitor#xor(String, String)}<br>
	 * Double : {@link ElementEvalVisitor#xor(Double, Double)}<br>
	 * Int    : {@link ElementEvalVisitor#xor(Integer, Integer)}<br>
	 * Bool   : 排他的論理和<br>
	 * この演算は下位のデータ型の結果を返します.
	 */
	@Override
	public void visit(Xor p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		if(typeOr(left, box, Type.BOOL)){
			Boolean a = DataConverter.convertToBool(left.getData()).getValue(),
					b = DataConverter.convertToBool(box.getData()).getValue();
			box = new Box(new Bool(a ^ b));
		} else if(typeOr(left, box, Type.INT)){
			Integer a = DataConverter.convertToInt(left.getData()).getValue(),
					b = DataConverter.convertToInt(box.getData()).getValue();
			box = new Box(new Int(ElementEvalVisitor.xor(a, b)));
		} else if(typeOr(left, box, Type.DOUBLE)){
			Double a = DataConverter.convertToDoub(left.getData()).getValue(),
					b = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(ElementEvalVisitor.xor(a, b)));
		} else if(typeOr(left, box, Type.STRING)){
			String a = DataConverter.convertToStr(left.getData()).getValue(),
					b = DataConverter.convertToStr(box.getData()).getValue();
			box = new Box(new Str(ElementEvalVisitor.xor(a, b)));
		} else {
			box = null;
			System.err.println("Xor:null!");
			System.err.println("left = " + left.getData() + ", right = " + box.getData());
		}
	}
	/**
	 * 型と値が一致するときに{@code true}を返します.
	 */
	@Override
	public void visit(Equal p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		boolean judge = left.getData().equals(box.getData());
		box = new Box(new Bool(judge));
	}
	/**
	 * 左側の値が右側の値より小さいとき{@code true}を返します.
	 */
	@Override
	public void visit(Less p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		int comp = left.getData().compareTo(box.getData());
		boolean judge = comp < 0;
		box = new Box(new Bool(judge));
	}
	/**
	 * 左側の値が右側の値より大きいとき{@code true}を返します.
	 */
	@Override
	public void visit(Greater p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		int comp = left.getData().compareTo(box.getData());
		boolean judge = comp > 0;
		box = new Box(new Bool(judge));
	}
	/**
	 * 型または値が一致しないとき{@code true}を返します.
	 */
	@Override
	public void visit(NotEqual p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		boolean judge = left.getData().equals(box.getData());
		box = new Box(new Bool(!judge));
	}
	/**
	 * {@link ElementEvalVisitor#visit(Less)}と{@link ElementEvalVisitor#visit(Equal)}のどちらかが
	 * {@code true}を返すとき{@code true}を返します.
	 */
	@Override
	public void visit(LessEqual p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		int comp = left.getData().compareTo(box.getData());
		boolean judge = comp <= 0;
		box = new Box(new Bool(judge));
	}
	/**
	 * {@link ElementEvalVisitor#visit(Greater)}と{@link ElementEvalVisitor#visit(Equal)}のどちらかが
	 * {@code true}を返すとき{@code true}を返します.
	 */
	@Override
	public void visit(GreaterEqual p) {
		p.getLeft().accept(this);
		Box left = new Box(box);
		p.getRight().accept(this);
		int comp = left.getData().compareTo(box.getData());
		boolean judge = comp >= 0;
		box = new Box(new Bool(judge));
	}
	/**
	 * 条件演算子.<br>
	 * 条件式の部分は{@code bool}型に変換してから評価します.
	 */
	@Override
	public void visit(Conditional p) {
		p.getLeft().accept(this);
		if(DataConverter.convertToBool(box.getData()).getValue()){
			p.getCenter().accept(this);
		} else {
			p.getRight().accept(this);
		}
	}
	/**
	 * 絶対値.<br>
	 * String : 文字列の長さ<br>
	 * Double : 数値の絶対値<br>
	 * Int    : 数値の絶対値<br>
	 * Bool   : {@code true}のとき1, そうでないとき0<br>
	 */
	@Override
	public void visit(Absolute p) {
		p.getArg().accept(this);
		switch(box.getType()){
		case BOOL:
			box = new Box(new Int( ((Bool)box.getData()).getValue() ? 1 : 0 ));
			break;
		case INT:
			box = new Box(new Int(Math.abs( ((Int)box.getData()).getValue() )));
			break;
		case DOUBLE:
			box = new Box(new Doub(Math.abs( ((Doub)box.getData()).getValue() )));
			break;
		case STRING:
			box = new Box(new Int( ((Str)box.getData()).getValue().length() ));
			break;
		default:
			box = new Box(new Bool(false));
			break;
		}
	}

	/**
	 * 階乗を計算します.<br>
	 * {@code ElementEvalVisitor#factorial(n, 1)}です.
	 *
	 * @param n Integer
	 * @return もし{@code n <= 0}ならば1を返す
	 * @see ElementEvalVisitor#factorial(int, int)
	 */
	public static int factorial(int n){
		return factorial(n, 1);
	}
	/**
	 * 階乗を計算します.
	 * @param n Integer
	 * @param acc 累積器
	 * @return Integer
	 */
	public static int factorial(int n, int acc){
		return n <= 0 ? acc : factorial(n - 1, n * acc);
	}

	/**
	 * 文字列型の階乗です.<br>
	 * {@link ElementEvalVisitor#multiply(String, String)}
	 * を利用しています.<br>
	 * {@literal ElementEvalVisitor#factorial(str)}です.<br>
	 *
	 * {@literal factorial :: String -> String}<br>
	 * {@literal factorial []     = ""}<br>
	 * {@literal factorial (x:xs) = multiply (x:xs) (factorial xs)}<br>
	 *
	 * @param str String
	 * @return String
	 * @see ElementEvalVisitor#factorial(String,String)
	 */
	public static String factorial(String str){
		return factorial(str, "");
	}
	/**
	 * 文字列の階乗です.
	 * @param n String
	 * @param acc 累積器
	 * @return String
	 */
	public static String factorial(String n, String acc){
		if(n.length() == 0)
			return ElementEvalVisitor.reverse(acc);
		return factorial(n.substring(1), ElementEvalVisitor.multiply(n, acc));
	}
	/**
	 * 階乗.<br>
	 * String : {@link ElementEvalVisitor#factorial(String)}<br>
	 * Double : 小数点以下を切り捨てて計算<br>
	 * Int    : 数値の階乗<br>
	 * Bool   : 操作はなし<br>
	 */
	@Override
	public void visit(Factorial p) {
		p.getArg().accept(this);
		switch(box.getType()){
		case BOOL:
			box = new Box(new Bool(((Bool)box.getData()).getValue()));
			break;
		case INT:
			box = new Box(new Int(ElementEvalVisitor.factorial( ((Int)box.getData()).getValue() )));
			break;
		case DOUBLE:
			box = new Box(new Doub((double)ElementEvalVisitor.factorial((int)Math.floor(((Doub)box.getData()).getValue()))));
			break;
		case STRING:
			box = new Box(new Str(ElementEvalVisitor.factorial(((Str)box.getData()).getValue())));
			break;
		default:
			box = new Box(new Bool(false));
			break;
		}
	}

	/**
	 * 文字列を反転する.<br>
	 * {@code reverse(str, "")}と等しい.
	 * @param str String(not {@code null})
	 * @return String
	 * @see ElementEvalVisitor#reverse(String, String)
	 */
	public static String reverse(String str){
		return reverse(str, "");
	}
	/**
	 * 文字列を反転する.
	 * @param str String
	 * @param acc 累積器
	 * @return String
	 */
	public static String reverse(String str, String acc){
		if(str.length() == 0)
			return acc;
		char top = str.charAt(0);
		String reminder = str.substring(1);
		return reverse(reminder, top + acc);
	}

	/**
	 * cdr(str).
	 *
	 * <p>もし長さが0なら空文字列を返す.</p>
	 * @param str String
	 * @return String
	 */
	public static String minus(String str){
		if(str.length() == 0)
			return "";
		return str.substring(1);
	}
	/**
	 * 負号.<br>
	 * String : cdr. {@link ElementEvalVisitor#minus(String)}<br>
	 * Double : 符号の反転<br>
	 * Int    : 符号の反転<br>
	 * Bool   : 論理否定<br>
	 */
	@Override
	public void visit(Minus p) {
		p.getArg().accept(this);
		switch(box.getType()){
		case BOOL:
			box = new Box(new Bool( !((Bool)box.getData()).getValue() ));
			break;
		case INT:
			box = new Box(new Int( -((Int)box.getData()).getValue() ));
			break;
		case DOUBLE:
			box = new Box(new Doub( -((Doub)box.getData()).getValue() ));
			break;
		case STRING:
			box = new Box(new Str( ElementEvalVisitor.minus( ((Str)box.getData()).getValue() ) ));
			break;
		default:
			box = new Box(new Bool(false));
			break;
		}
	}
	/**
	 * car(str).
	 *
	 * <p>もし長さが0ならば空文字列を返す.</p>
	 * @param str String
	 * @return String
	 */
	public static String plus(String str){
		if(str.length() == 0)
			return "";
		return str.substring(0, 1);
	}

	/**
	 * 正符号.<br>
	 * String : {@link ElementEvalVisitor#plus(String)}
	 */
	@Override
	public void visit(Plus p) {
		p.getArg().accept(this);
		if(box.getType() == Type.STRING){
			box = new Box(new Str( ElementEvalVisitor.plus( ((Str)box.getData()).getValue() ) ));
		}
	}

	/**
	 * 論理否定.<br>
	 * ブール型に変換してから論理否定を取ります.
	 */
	@Override
	public void visit(Not p) {
		p.getArg().accept(this);
		Bool b = DataConverter.convertToBool(box.getData());
		box = new Box(new Bool(!b.getValue()));
	}
	/**
	 * typeof演算子.<br>
	 *
	 * データ型を文字列として取得する.
	 */
	@Override
	public void visit(TypeOf p) {
		p.getArg().accept(this);
		String result = null;
		switch(box.getType()){
		case BOOL:
			result = "bool";
			break;
		case INT:
			result = "int";
			break;
		case DOUBLE:
			result = "double";
			break;
		case STRING:
			result = "string";
			break;
		default:
			result = "none";
			break;
		}
		box = new Box(new Str(result));
	}

	/**
	 * 明示的型変換.
	 */
	@Override
	public void visit(Cast p) {
		p.getArg().accept(this);
		box = new Box(DataConverter.convert(box.getData(), p.getType()));
	}

	/* ライブラリ関数 BEGIN */
	/* true -> 処理成功 */
	/**
	 * 標準一様分布に従う乱数を返します.<br>
	 * 引数は0個です.
	 * @param p call
	 * @return 処理成功か
	 * @see Random#nextDouble()
	 */
	public boolean random(FunctionCall p){
		if(p.getArgumentList().size() == 0){
			double result = r.nextDouble();
			box = new Box(new Doub(result));
			return true;
		}
		return false;
	}
	/**
	 * 正弦関数です.<br>
	 * 引数は1個です.
	 * @param p call
	 * @return 処理成功か
	 * @see Math#sin(double)
	 */
	public boolean sin(FunctionCall p){
		if(p.getArgumentList().size() == 1){
			p.getArgumentList().get(0).accept(this);
			double s = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(Math.sin(s)));
			return true;
		}
		return false;
	}
	/**
	 * 逆正弦関数です.<br>
	 * 引数は1個です.
	 * @param p call
	 * @return 処理成功か
	 * @see Math#asin(double)
	 */
	public boolean arcsin(FunctionCall p){
		if(p.getArgumentList().size() == 1){
			p.getArgumentList().get(0).accept(this);
			double s = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(Math.asin(s)));
			return true;
		}
		return false;
	}
	/**
	 * 指数関数です.<br>
	 * 引数は1個です.
	 * @param p call
	 * @return 処理成功か
	 * @see Math#exp(double)
	 */
	public boolean exp(FunctionCall p){
		if(p.getArgumentList().size() == 1){
			p.getArgumentList().get(0).accept(this);
			double s = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(Math.exp(s)));
			return true;
		}
		return false;
	}
	/**
	 * 対数関数です.<br>
	 * 引数は1個です.
	 * @param p call
	 * @return 処理成功か
	 * @see Math#log(double)
	 */
	public boolean log(FunctionCall p){
		if(p.getArgumentList().size() == 1){
			p.getArgumentList().get(0).accept(this);
			double s = DataConverter.convertToDoub(box.getData()).getValue();
			box = new Box(new Doub(Math.log(s)));
			return true;
		}
		return false;
	}

	/**
	 * 変数テーブルにstring型で変数を定義します.
	 * @param p call
	 * @return 処理成功か
	 * @see visitor.VariableTable
	 */
	public boolean defvar(FunctionCall p){
		if(p.getArgumentList().size() == 2){
			p.getArgumentList().get(0).accept(this);
			String name = DataConverter.convertToStr(box.getData()).getValue();
			p.getArgumentList().get(1).accept(this);
			Data val = box.getData();
			variableTable.put(name, val);
			return true;
		}
		return false;
	}
	/**
	 * 変数テーブルからstring型で変数を参照します.
	 * @param p call
	 * @return 処理成功か
	 * @see visitor.VariableTable
	 */
	public boolean refvar(FunctionCall p){
		if(p.getArgumentList().size() == 1){
			p.getArgumentList().get(0).accept(this);
			String name = DataConverter.convertToStr(box.getData()).getValue();
			Variable v = new Variable(name);
			v.accept(this);
			return true;
		}
		return false;
	}
	/**
	 * 関数テーブルからstring型で関数を参照します.<br>
	 * 第一引数が参照する関数名, 残りの引数が参照する関数の引数です.
	 * @param p call
	 * @return 処理成功か
	 * @see visitor.FunctionTable
	 */
	public boolean call(FunctionCall p){
		FunctionCallArgumentList list = p.getArgumentList();
		list.get(0).accept(this);
		String name = DataConverter.convertToStr(box.getData()).getValue();
		FunctionCallArgumentList nextList = new FunctionCallArgumentList();
		for(int i = 1; i < list.size(); i++)
			nextList.add(list.get(i));
		FunctionCall call = new FunctionCall(name, nextList);
		call.accept(this);
		return true;
	}
	/**
	 * 文字列を構文解析して評価する.
	 * @param p call
	 * @return 処理成功か
	 * @see parser.ElementParser#lex()
	 */
	public boolean eval(FunctionCall p){
		if(p.getArgumentList().size() == 1){
			p.getArgumentList().get(0).accept(this);
			String arg = DataConverter.convertToStr(box.getData()).getValue();
			try{
				ElementParser parser = new ElementParser(arg);
				Program r = parser.parse();
				r.accept(this);
				return true;
			} catch (Exception exception){
				return false;
			}
		}
		return false;
	}
	/**
	 * 文字列を構文解析して表示する.
	 * @param p call
	 * @return 処理成功か
	 * @see parser.ElementParser#lex()
	 */
	public boolean dump(FunctionCall p){
		if(p.getArgumentList().size() == 1){
			p.getArgumentList().get(0).accept(this);
			String arg = DataConverter.convertToStr(box.getData()).getValue();
			try{
				ElementParser parser = new ElementParser(arg);
				Program r = parser.parse();
				r.accept(new ElementDumpVisitor());
				return true;
			} catch (Exception exception){
				return false;
			}
		}
		return false;
	}
	/**
	 * 文字列を字句解析して表示する.
	 * @param p call
	 * @return 処理成功か
	 * @see parser.ElementParser#lex()
	 */
	public boolean lex(FunctionCall p){
		if(p.getArgumentList().size() == 1){
			p.getArgumentList().get(0).accept(this);
			String arg = DataConverter.convertToStr(box.getData()).getValue();
			try{
				ElementParser parser = new ElementParser(arg);
				parser.lex();
				box = new Box(new Bool(true));
				return true;
			} catch(Exception exception){
				return false;
			}
		}
		return false;
	}
	/**
	 * 変数テーブルの中身を出力する.
	 * @param p call
	 * @return 処理成功か
	 * @see visitor.VariableTable
	 */
	public boolean vars(FunctionCall p){
		if(p.getArgumentList().size() == 0){
			for(Entry<String, Data> elem : this.variableTable.entrySet()){
				Box b = new Box(elem.getValue());
				System.out.println(elem.getKey() + " = "
			+ DataConverter.convertToStr(b.getData()).getValue()
			+ " :: " + b.getType().asDataTypeString());
			}
			box = new Box(new Bool(true));
			return true;
		}
		return false;
	}
	/**
	 * 関数テーブルの中身を出力する.
	 * @param p call
	 * @return 処理成功か
	 * @see visitor.FunctionTable
	 */
	public boolean funcs(FunctionCall p){
		if(p.getArgumentList().size() == 0){
			for(Entry<String, Map<FunctionDefinitionArgumentList,Program>> elem : this.functionTable.entrySet()){
				for(Entry<FunctionDefinitionArgumentList, Program> fentry : elem.getValue().entrySet()){
					if(fentry.getKey().size() == 0){
						System.out.println(elem.getKey() + "() :");
					} else {
						System.out.println(elem.getKey() +
								"(" + fentry.getKey().stream().reduce((a, b) -> a + ", " + b).get() +") :");
					}
					fentry.getValue().accept(new ElementDumpVisitor());
				}
			}
			box = new Box(new Bool(true));
			return true;
		}
		return false;
	}
	/* ライブラリ関数 END */

	/**
	 * 関数呼び出し.<br>
	 *
	 * 手順は次のようなものです.
	 * <ol>
	 * <li>ライブラリ関数のマッチングをする.</li>
	 * <li>関数テーブルに呼び出す関数名があるか探す.</li>
	 * <li>見つかった関数に呼び出す関数と同じ引数の数のものがあるか探す.</li>
	 * <li>変数テーブルと関数テーブルをコピーする.</li>
	 * <li>関数の仮引数と呼び出すときの引数を位置によって対応付ける.</li>
	 * <li>呼び出す側の引数の値を呼び出される側の引数名の変数として変数テーブルに登録する.</li>
	 * <li>新たにElementEvalVisitorを作り, 関数テーブルと引数テーブルを渡す.</li>
	 * <li>見つけた関数の本体に作ったVisitorをacceptさせる.</li>
	 * <li>Visitorの結果をこのVisitorの結果とする.</li>
	 * </ol>
	 * @see ElementEvalVisitor#sin(FunctionCall)
	 * @see ElementEvalVisitor#arcsin(FunctionCall)
	 * @see ElementEvalVisitor#exp(FunctionCall)
	 * @see ElementEvalVisitor#log(FunctionCall)
	 * @see ElementEvalVisitor#defvar(FunctionCall)
	 * @see ElementEvalVisitor#refvar(FunctionCall)
	 * @see ElementEvalVisitor#call(FunctionCall)
	 * @see ElementEvalVisitor#eval(FunctionCall)
	 * @see ElementEvalVisitor#dump(FunctionCall)
	 * @see ElementEvalVisitor#lex(FunctionCall)
	 * @see ElementEvalVisitor#vars(FunctionCall)
	 * @see ElementEvalVisitor#funcs(FunctionCall)
	 */
	@Override
	public void visit(FunctionCall p) {
		String name = p.getFunctionName();

		/* ライブラリ関数呼び出し */
		/* 戻り値がtrueならば呼び出し成功 */
		if(name.equals("random") && random(p)){
			return;
		} else if(name.equals("sin") && sin(p)){
			return;
		} else if(name.equals("arcsin") && arcsin(p)){
			return;
		} else if(name.equals("exp") && exp(p)){
			return;
		} else if(name.equals("log") && log(p)){
			return;
		} else if(name.equals("refvar") && refvar(p)){
			return;
		} else if(name.equals("call") && call(p)){
			return;
		} else if(name.equals("defvar") && defvar(p)){
			return;
		} else if(name.equals("eval") && eval(p)){
			return;
		} else if(name.equals("dump") && dump(p)){
			return;
		} else if(name.equals("lex") && lex(p)){
			return;
		} else if(name.equals("vars") && vars(p)){
			return;
		} else if(name.equals("funcs") && funcs(p)){
			return;
		}

	    Map<FunctionDefinitionArgumentList, Program> funcs = functionTable.get(name);
	    if(funcs == null){ // method missing, 関数名
	    	box = new Box(new Bool(false));
	    	return;
	    }

	    FunctionCallArgumentList callerArgs = p.getArgumentList();
	    FunctionDefinitionArgumentList calleeArgs = null;
	    Program proc = null;
	    for(Entry<FunctionDefinitionArgumentList, Program> func : funcs.entrySet()){
	    	if(callerArgs.size() == func.getKey().size()){
	    		proc = func.getValue();
	    		calleeArgs = func.getKey();
	    		break;
	    	}
	    }
	    if(proc == null){ // method missing, 引数の数
	    	box = new Box(new Bool(false));
	    	return;
	    }

	    FunctionTable ft = new FunctionTable(functionTable);
	    VariableTable vt = new VariableTable(variableTable);

	    for(int i = 0; i < callerArgs.size(); i++){
	    	String varName = calleeArgs.get(i);
	    	callerArgs.get(i).accept(this);
	    	Data val = box.getData();
	    	vt.put(varName, val);
	    }

	    ElementEvalVisitor exec = new ElementEvalVisitor(vt, ft);
	    proc.accept(exec);

	    Data result = exec.getValue();
	    if(result == null){
	    	System.err.println("FunctionCall:null!");
	    	System.err.println("function name = " + name);
	    	System.err.println("argument num = " + callerArgs.size());
	    	return;
	    }
	    exec = null;
	    vt = null;
	    ft = null;
	    box = new Box(result);
	}

	/**
	 * 変数参照.<br>
	 * 変数テーブルに登録されていない変数名ならば
	 * {@code false}とします.
	 */
	@Override
	public void visit(Variable p) {
		Data val = variableTable.get(p.getVariableName());
		if(val != null){
			box = new Box(val.copy());
		} else { // variable missing
			box = new Box(new Bool(false));
		}
	}
	/**
	 * 代入文.
	 */
	@Override
	public void visit(Assign p) {
		p.getValue().accept(this);
		variableTable.put(p.getTargetName(), box.getData());
	}

	/**
	 * 複文.<br>
	 * 逐次実行します.
	 */
	@Override
	public void visit(Block p) {
		for(Program elem : p){
			elem.accept(this);
		}
	}

	@Override
	public void visit(Empty p) {
		/* no operation */
	}

	/**
	 * 関数定義.<br>
	 * 同名で同じ引数の数をもつ関数は上書きします.
	 */
	@Override
	public void visit(FunctionDefinition p) {
		String name = p.getFunctionName();
		FunctionDefinitionArgumentList args = p.getArgumentList();
		Program proc = p.getProcess();
		// Map<FunctionDefinitionArgumentList, Program> fs = functionTable.get(name);
		if(!functionTable.containsKey(name)){
			functionTable.put(name, new HashMap<FunctionDefinitionArgumentList, Program>());
		}
		functionTable.get(name).put(args, proc);
		box = new Box(new Str(name));
	}

	/**
	 * If文.<br>
	 * 条件式の値は{@code bool}型に変換して評価します.
	 */
	@Override
	public void visit(If p) {
		p.getCondition().accept(this);
		if(DataConverter.convertToBool(box.getData()).getValue()){
			p.getSuccess().accept(this);
		} else if(p.getFailure() != null){
			p.getFailure().accept(this);
		}
	}
	/**
	 * While文.<br>
	 * 条件式の値は{@code bool}型に変換して評価します.<br>
	 * この文の値は条件部でない部分で最後に評価されたものです.
	 */
	@Override
	public void visit(While p) {
		Data result = null;
		boolean first = true;
		while(true){
			p.getCondition().accept(this);
			if(!DataConverter.convertToBool(box.getData()).getValue()){
				if(!first)
					box = new Box(result);
				break;
			}
			first = false;
			p.getProcess().accept(this);
			result = box.getData(); // 退避
		}
	}

	/**
	 * print演算子.<br>
	 * string型に引数を変換して{@literal System.out.println}します.<br>
	 * この演算子の値は, 引数の値と同じです.
	 *
	 */
	@Override
	public void visit(Print p) {
		p.getArg().accept(this);
		String result = DataConverter.convertToStr(box.getData()).getValue();
		System.out.println(result);
	}

	/**
	 * scan演算子.<br>
	 * 標準入力から1行読み, string型として受け取ります.
	 *
	 * @see BufferedReader#readLine()
	 */
	@Override
	public void visit(Scan p){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = br.readLine();
			box = new Box(new Str(line));
		} catch(IOException exception){
			box = new Box(new Bool(false));
		}
	}
	/**
	 * データと型の組.<br>
	 * この型はimmutableです.<br>
	 * コンストラクタとgetterは防御的コピーによりやり取りします.<br>
	 * @author sin
	 * @version 2017.3.16
	 */
	public class Box{
		private Data data;
		private Type type;

		public Box(Data data){
			this.data = data.copy();
			this.type = DataConverter.getType(data);
		}

		public Box(Box box){
			this.data = box.getData().copy();
			this.type = box.getType();
		}

		public Data getData(){
			return data.copy();
		}
		public Type getType(){
			return type;
		}
	}
	/**
	 * データ型の変換規則を実装します.
	 * @author sin
	 * @version 2017.3.16
	 * @see element.expression.data.Data
	 */
	public static class DataConverter{
		public static Type getType(Data data){
			if(data instanceof Int){
				return Type.INT;
			}
			if(data instanceof Doub){
				return Type.DOUBLE;
			}
			if(data instanceof Str){
				return Type.STRING;
			}
			if(data instanceof Bool){
				return Type.BOOL;
			}
			System.err.println("getType:null!");
			System.err.println("data = " + data);
			return null;
		}
		public static Bool convertToBool(Data data){
			Boolean val = false;
			if(data instanceof Int){
				val = ((Int)data).getValue() != 0;
			} else if(data instanceof Doub){
				val = ((Doub)data).getValue() != 0.0;
			} else if(data instanceof Str){
				val = ((Str)data).getValue() != "";
			} else if(data instanceof Bool){
				val = ((Bool)data).getValue();
			}
			return new Bool(val);
		}
		public static Int convertToInt(Data data){
			Integer val = 0;
			if(data instanceof Bool){
				val = ((Bool)data).getValue() ? 1 : 0;
			} else if(data instanceof Doub){
				val = (int) Math.floor(((Doub)data).getValue());
			} else if(data instanceof Str){
				val = Integer.parseInt(((Str)data).getValue());
			} else if(data instanceof Int){
				val = ((Int)data).getValue();
			}
			return new Int(val);
		}
		public static Doub convertToDoub(Data data){
			Double val = 0.0;
			if(data instanceof Bool){
				val = ((Bool)data).getValue() ? 1.0 : 0.0;
			} else if(data instanceof Int){
				val = ((Int)data).getValue() + 0.0;
			} else if(data instanceof Str){
				val = Double.parseDouble(((Str)data).getValue());
			} else if(data instanceof Doub){
				val = ((Doub)data).getValue();
			}
			return new Doub(val);
		}
		public static Str convertToStr(Data data){
			String val = "";
			if(data instanceof Bool){
				val = String.valueOf(((Bool)data).getValue());
			} else if(data instanceof Int){
				val = ((Int)data).getValue() + "";
			} else if(data instanceof Doub){
				val = ((Doub)data).getValue() + "";
			} else if(data instanceof Str){
				val = ((Str)data).getValue();
			}
			return new Str(val);
		}
		/**
		 * {@code (type)data}を実行します.
		 * @param data キャスト対象
		 * @param type 目的の型
		 * @return  キャスト結果
		 */
		public static Data convert(Data data, Type type){
			if(type == Type.INT){
				return convertToInt(data);
			}
			if(type == Type.DOUBLE){
				return convertToDoub(data);
			}
			if(type == Type.BOOL){
				return convertToBool(data);
			}
			if(type == Type.STRING){
				return convertToStr(data);
			}
			System.err.println("convert:null!");
			System.err.println("data = " + data + ", type = " + type);
			return null;
		}
		/**
		 * {@code left, right}のどちらか一方でも{@code target}と等しいならば
		 * {@code true}を返します.
		 * @param left Type
		 * @param right Type
		 * @param target Type
		 * @return typeor
		 */
		public static boolean typeOr(Type left, Type right, Type target){
			return left.equals(target) || right.equals(target);
		}
		/**
		 * {@code left, right}のどちらか一方だけが{@code target}と等しいならば
		 * {@code true}を返します.
		 * @param left Type
		 * @param right Type
		 * @param target Type
		 * @return typexor
		 */
		public static boolean typeXor(Type left, Type right, Type target){
			return (left.equals(target) && !right.equals(target)) ||
					(!left.equals(target) && right.equals(target));
		}
	}
}
