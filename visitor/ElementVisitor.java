package visitor;

import element.Program;
import element.expression.Cast;
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
 * この言語の抽象構文木にしたがって処理をします.
 *
 * @author sin
 * @version 2017.3.17
 */
public interface ElementVisitor {
	/*:
	 * もし有効な値を持っていればそれを返す.
	 *
	 * @return 有効な値(もしなければ{@code null})
	 */
	Data getValue();
	/**
	 * 呼ばれるべきでないメソッド.<br>
	 * {@link element.Program}のすべての具象クラスについて<br>
	 * <i>visit</i>メソッドを用意していない場合に呼ばれることがある.
	 * @param p AST
	 */
	void visit(Program p);
	// Data
	void visit(Bool p);
	void visit(Doub p);
	void visit(Str p);
	void visit(Int p);
	// arithmetic binary
	void visit(Add p);
	void visit(Subtract p);
	void visit(Multiply p);
	void visit(Divide p);
	void visit(Modulo p);
	void visit(Power p);
	// logical binary
	void visit(And p);
	void visit(Or p);
	void visit(Xor p);
	// relational binary
	void visit(Equal p);
	void visit(Less p);
	void visit(Greater p);
	void visit(NotEqual p);
	void visit(LessEqual p);
	void visit(GreaterEqual p);
	// ternary
	void visit(Conditional p);
	// arithmetic unary
	void visit(Absolute p);
	void visit(Factorial p);
	void visit(Minus p);
	void visit(Plus p);
	// logical unary
	void visit(Not p);
	// type unary
	void visit(TypeOf p);
	// output unary
	void visit(Print p);
	// input
	void visit(Scan p);

	void visit(Cast p);
	void visit(FunctionCall p);
	void visit(Variable p);

	// statement
	void visit(Assign p);
	void visit(Block p);
	void visit(Empty p);
	void visit(FunctionDefinition p);
	void visit(If p);
	void visit(While p);
}
