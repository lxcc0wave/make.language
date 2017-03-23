package visitor;

import element.Program;
import element.expression.Cast;
import element.expression.Expression;
import element.expression.FunctionCall;
import element.expression.Variable;
import element.expression.data.Bool;
import element.expression.data.Data;
import element.expression.data.Doub;
import element.expression.data.Int;
import element.expression.data.Str;
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
import element.statement.If;
import element.statement.While;

/**
 * ASTを出力する.
 *
 * @author sin
 * @version 2017.3.17
 */
public class ElementDumpVisitor implements ElementVisitor {
	/**
	 * インデントの深さ.
	 */
	private int level;

	public ElementDumpVisitor(){
		level = 0;
	}

	@Override
	public Data getValue(){
		return null;
	}
	/**
	 * インデントの深さに応じた空白の文字列を作ります.
	 * @param n インデントの深さ
	 * @return String
	 */
	private static String makeIndent(int n){
		String acc = "";
		for(int i = 0; i < n; i++)
			acc += "  ";
		return acc;
	}

	@Override
	public void visit(Program p) {
		/* no operation */
	}

	@Override
	public void visit(Bool p) {
		System.out.println(makeIndent(level) + "bool[" + p.getValue() + "]");
	}

	@Override
	public void visit(Doub p) {
		System.out.println(makeIndent(level) + "double[" + p.getValue() + "]");
	}

	@Override
	public void visit(Str p) {
		System.out.println(makeIndent(level) + "string[" + p.getValue() + "]");
	}

	@Override
	public void visit(Int p) {
		System.out.println(makeIndent(level) + "int[" + p.getValue() + "]");
	}

	@Override
	public void visit(Add p) {
		System.out.println(makeIndent(level) + "Add");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(Subtract p) {
		System.out.println(makeIndent(level) + "Subtract");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(Multiply p) {
		System.out.println(makeIndent(level) + "Multiply");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(Divide p) {
		System.out.println(makeIndent(level) + "Divide");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(Modulo p) {
		System.out.println(makeIndent(level) + "Modulo");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(Power p) {
		System.out.println(makeIndent(level) + "Power");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(And p) {
		System.out.println(makeIndent(level) + "And");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(Or p) {
		System.out.println(makeIndent(level) + "Or");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(Xor p) {
		System.out.println(makeIndent(level) + "Xor");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(Equal p) {
		System.out.println(makeIndent(level) + "Equal");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(Less p) {
		System.out.println(makeIndent(level) + "Less");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(Greater p) {
		System.out.println(makeIndent(level) + "Greater");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(NotEqual p) {
		System.out.println(makeIndent(level) + "NotEqual");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(LessEqual p) {
		System.out.println(makeIndent(level) + "LessEqual");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(GreaterEqual p) {
		System.out.println(makeIndent(level) + "GreaterEqual");
		level++;
		p.getLeft().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(Conditional p) {
		System.out.println(makeIndent(level) + "Conditional");
		level++;
		p.getLeft().accept(this);
		p.getCenter().accept(this);
		p.getRight().accept(this);
		level--;
	}

	@Override
	public void visit(Absolute p) {
		System.out.println(makeIndent(level) + "Absolute");
		level++;
		p.getArg().accept(this);
		level--;
	}

	@Override
	public void visit(Factorial p) {
		System.out.println(makeIndent(level) + "Factorial");
		level++;
		p.getArg().accept(this);
		level--;
	}

	@Override
	public void visit(Minus p) {
		System.out.println(makeIndent(level) + "Minus");
		level++;
		p.getArg().accept(this);
		level--;
	}

	@Override
	public void visit(Plus p) {
		System.out.println(makeIndent(level) + "Plus");
		level++;
		p.getArg().accept(this);
		level--;
	}

	@Override
	public void visit(Not p) {
		System.out.println(makeIndent(level) + "Not");
		level++;
		p.getArg().accept(this);
		level--;
	}

	@Override
	public void visit(TypeOf p) {
		System.out.println(makeIndent(level) + "Typeof");
		level++;
		p.getArg().accept(this);
		level--;
	}

	@Override
	public void visit(Cast p) {
		System.out.println(makeIndent(level) + "Cast");
		level++;
		System.out.println(makeIndent(level) + p.getType().asDataTypeString());
		p.getArg().accept(this);
		level--;
	}

	@Override
	public void visit(FunctionCall p) {
		System.out.println(makeIndent(level) + "FunctionCall");
		level++;
		System.out.println(makeIndent(level) + p.getFunctionName());
		for(Expression elem : p.getArgumentList()){
			elem.accept(this);
		}
		level--;
	}

	@Override
	public void visit(Variable p) {
		System.out.println(makeIndent(level) + "Variable[" + p.getVariableName() + "]");
	}

	@Override
	public void visit(Assign p) {
		System.out.println(makeIndent(level) + "Assign");
		level++;
		System.out.println(makeIndent(level) + p.getTargetName());
		p.getValue().accept(this);
		level--;
	}

	@Override
	public void visit(Block p) {
		System.out.println(makeIndent(level) + "Block");
		level++;
		for(Program elem : p){
			elem.accept(this);
		}
		level--;
	}

	@Override
	public void visit(Empty p) {
		System.out.println(makeIndent(level) + "Empty");
	}

	@Override
	public void visit(FunctionDefinition p) {
		System.out.println(makeIndent(level) + "FunctionDefinition");
		level++;
		System.out.println(makeIndent(level) + p.getFunctionName());
		for(String elem : p.getArgumentList()){
			System.out.println(makeIndent(level) + elem);
		}
		p.getProcess().accept(this);
		level--;
	}

	@Override
	public void visit(If p) {
		System.out.println(makeIndent(level) + "If");
		level++;
		p.getCondition().accept(this);
		p.getSuccess().accept(this);
		if(p.getFailure() != null){
			p.getFailure().accept(this);
		}
		level--;
	}

	@Override
	public void visit(While p) {
		System.out.println(makeIndent(level) + "While");
		level++;
		p.getCondition().accept(this);
		p.getProcess().accept(this);
		level--;
	}

	@Override
	public void visit(Print p) {
		System.out.println(makeIndent(level) + "Print");
		level++;
		p.getArg().accept(this);
		level--;
	}

	@Override
	public void visit(Scan p) {
		System.out.println(makeIndent(level) + "Scan");
	}

}
