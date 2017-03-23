package main;

import java.io.FileInputStream;
import java.io.InputStream;

import element.Program;
import parser.ElementParser;
import visitor.ElementDumpVisitor;
import visitor.ElementEvalVisitor;

/**
 * エントリポイント.
 * @author sin
 * @version 2017.3.17
 */
public class Main {
	/**
	 * インタプリタを提供します.
	 * @param in 入力ストリーム
	 * @param lex 字句解析結果を表示するか
	 * @param dump 構文木を表示するか
	 * @param eval 評価結果を表示するか
	 * @param out 自力の出力のみを出力するか
	 * @throws Exception 入出力エラー, 字句解析エラー, 構文解析エラー, 実行時エラーなど
	 */
	public static void repl(InputStream in, boolean lex, boolean dump, boolean eval, boolean out) throws Exception{
		ElementParser p = new ElementParser(in);
		ElementEvalVisitor ev = null;
		ElementDumpVisitor dv = null;

		p.printLex = lex;
		if(dump)
			dv = new ElementDumpVisitor();
		if(eval)
			ev = new ElementEvalVisitor();
		while(true){
			System.out.print(">");
			Program r = p.parse();
			if(r == null)
				break;
			if(dump){
				System.out.println("DUMP:");
				r.accept(dv);
			}
			if(eval){
				r.accept(ev);
				if(!out){
					System.out.println("EVAL:");
					System.out.println(ev.getValue() + " :: " + ev.getType().asDataTypeString());
				}
			}
		}
	}
	/**
	 * テキストソースを実行します.
	 * @param in 入力ストリーム
	 * @param lex 字句解析結果を表示するか
	 * @param dump 構文木を表示するか
	 * @param eval 評価結果を表示するか
	 * @param out 自力の出力のみを出力するか
	 * @throws Exception 入出力エラー, 字句解析エラー, 構文解析エラー, 実行時エラーなど
	 */
	public static void readFile(InputStream in, boolean lex, boolean dump, boolean eval, boolean out) throws Exception {
		ElementParser p = new ElementParser(in);
		ElementEvalVisitor ev = null;
		ElementDumpVisitor dv = null;

		p.printLex = lex;
		if(dump)
			dv = new ElementDumpVisitor();
		if(eval)
			ev = new ElementEvalVisitor();
		while(true){
			Program r = p.parse();
			if(r == null)
				break;
			if(dump){
				System.out.println("DUMP:");
				r.accept(dv);
			}
			if(eval){
				r.accept(ev);
				if(!out){
					System.out.println("EVAL:");
					System.out.println(ev.getValue() + " :: " + ev.getType().asDataTypeString());
				}
			}
		}
	}
	// e.g. let sum(n) = { let i = 0; let acc = 0; while(i <= n) { let acc = acc + i; let i = i + 1; }; acc; };
	// let f(x) = { let g(n, acc) = n <= 0 ? acc : g(n - 1, n * acc); g(x, 1); acc; };
	/**
	 * エントリポイント.
	 * @param args オプション引数
	 */
	public static void main(String[] args){
		if(args.length > 0 && args[0].contains("h")){
			help();
			return;
		}
		try{
			boolean lex = false, dump = false, eval = false, out = false;
			InputStream input = null;
			if(args.length == 0){
				eval = true;
				input = System.in;
				repl(input, lex, dump, eval, out);
			} else if(args.length == 1){
				lex = args[0].contains("l");
				dump = args[0].contains("d");
				eval = args[0].contains("e");
				out = args[0].contains("o");
				input = System.in;
				repl(input, lex, dump, eval, out);
			} else if(args.length == 2){
				lex = args[0].contains("l");
				dump = args[0].contains("d");
				eval = args[0].contains("e");
				out = args[0].contains("o");
				input = new FileInputStream(args[1]);
				readFile(input, lex, dump, eval, out);
			} else {
				help();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	private static void help(){
		System.out.println("引数0個 : インタプリタ, 評価結果を表示.");
		System.out.println("引数1個 : オプション付き. オプションはldeoの各文字が意味を持つ.");
		System.out.println("   l : 字句解析の結果を表示する.");
		System.out.println("   d : 構文木を表示する.");
		System.out.println("   e : 評価結果を表示する.");
		System.out.println("   o : 自動的な出力(EVAL:の部分)を表示しない.");
		System.out.println("   h : ヘルプ(this)を表示する.");
		System.out.println("引数2個 : ファイルからソースを読んで一気に実行.");
		System.out.println("   2つめのオプションはファイル名.");
	}
}
