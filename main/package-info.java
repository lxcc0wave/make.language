/**
 * この言語のインタプリタとソースファイル形式の実行を提供します.
 * <h3>実行時のオプション</h3>
 * <h4>オプションなし</h4>
 *   オプション<i><b>e</b></i>つきでインタプリタを実行します.
 * <h4>オプション1個</h4>
 *   オプションの文字列が含む文字によって動作を変更します.
 *   <dl>
 *     <dt>l</dt>
 *       <dd>字句解析の結果を表示します.</dd>
 *     <dt>d</dt>
 *       <dd>構文木を表示します.</dd>
 *     <dt>e</dt>
 *       <dd>評価結果を表示します.</dd>
 *     <dt>o</dt>
 *       <dd>自動的な出力(EVAL:の部分)を表示しません.</dd>
 *     <dt>h</dt>
 *       <dd>ヘルプ(this)を表示します.</dd>
 *   </dl>
 * <h4>オプション2個</h4>
 *   ファイルからソースを読んで実行します.<br>
 *   2個目のオプションはファイルパスです.<br>
 * <h3>プログラムの例</h3>
 * <h4>1</h4>
 * 配列.
 * <pre>
 * let sum(n) = n &lt;= 0 ? 0 : sum(n - 1) + n;
 * print("input an integer");
 * let n = (int)scan();
 * {
 *   let i = 0;
 *   while(i &lt; n){
 *     defvar("x" + i, 10 + sum(i + 1));
 *     let i = i + 1;
 *   };
 *   {
 *     let i = 0;
 *     while(i &lt; n){
 *       print("x" + i + " = " + refvar("x" + i));
 *       let i = i + 1;<br>
 *     };
 *   };
 * };
 * </pre>
 *
 * <h4>2</h4>
 * 文字列の分割.
 * <pre>
 * let car(str) = +str;
 * let cdr(str) = -str;
 * let getFirst(str, split) =
 *  if(abs(str) == 0) then ""
 *  else if(abs(split) == 0) then str
 *  else if(car(str) == split) then ""
 *  else car(str) + getFirst(cdr(str), split);
 * let getSecond(str, split) =
 *  if(abs(str) == 0) then ""
 *  else if(abs(split) == 0) then str
 *  else if(car(str) == split) then cdr(str)
 *  else getSecond(cdr(str), split);
 *
 * print("input a string separated by space");
 * let ln = scan();
 * while(ln){
 *  print(getFirst(ln, " "));
 *  let ln = getSecond(ln, " ");
 * };
 * </pre>
 *
 * <h4>3</h4>
 * REPL.
 * <pre>
 * let repl = "while(true) print(eval(scan()));";
 * eval(repl);
 * </pre>
 *
 * <h4>4</h4>
 * 畳みこみ.
 * <pre>
 * let foreach(src, length, func) = {
 *   let i = 0;
 *   while(i &lt; length){
 *     call(func, refvar(src + i));
 *     let i = i + 1;
 *   };
 * };
 * let inject(src, length, init, func) = {
 *   let i = 0;
 *   let acc = init;
 *   while(i &lt; length){
 *     let acc = call(func, acc, refvar(src + i));
 *     let i = i + 1;
 *   };
 *   acc;
 * };
 * let p(x) = print(x + " :: " + typeof(x));
 * let pa(array, length) = foreach(array, length, "p");
 * {
 *   let x0 = 1;
 *   let x1 = 2;
 *   let x2 = 3;
 *   let x3 = 4;
 *   print("start:");
 *   pa("x", 4);
 *   let plus(x, y) = x + y;
 *   let mult(x, y) = x * y;
 *   let response = inject("x", 4, 0, "plus");
 *   print("sum = " + response + " :: " + typeof(response));
 *   let response = inject("x", 4, 1, "mult");
 *   print("prod = " + response + " :: " + typeof(response));
 * };
 * </pre>
 *
 * <h4>5</h4>
 * 文字列, 写像, 畳みこみ, 選択
 * <pre>
 * let car(str) = +str;
 * let cdr(str) = -str;
 * let inject(str, func) =
 *   if(abs(str) == 0) then ""
 *   else if(abs(func) == 0) then str
 *   else call(func, car(str), inject(cdr(str), func));
 * let map(str, func) =
 *   if(abs(str) == 0) then ""
 *   else if(abs(func) == 0) then str
 *   else call(func, car(str)) + map(cdr(str), func);
 * let filter(str, pred) =
 *   if(abs(str) == 0) then ""
 *   else if(abs(pred) == 0) then str
 *   else if(call(pred, car(str))) then car(str) + filter(cdr(str), pred)
 *   else filter(cdr(str), pred);
 * let toUpper(c) =
 *   if(c == "a") then "A"
 *   else if(c == "b") then "B"
 *   else if(c == "c") then "C"
 *   else if(c == "d") then "D"
 *   else if(c == "e") then "E"
 *   else if(c == "f") then "F"
 *   else if(c == "g") then "G"
 *   else if(c == "h") then "H"
 *   else if(c == "i") then "I"
 *   else if(c == "j") then "J"
 *   else if(c == "k") then "K"
 *   else if(c == "l") then "L"
 *   else if(c == "m") then "M"
 *   else if(c == "n") then "N"
 *   else if(c == "o") then "O"
 *   else if(c == "p") then "P"
 *   else if(c == "q") then "Q"
 *   else if(c == "r") then "R"
 *   else if(c == "s") then "S"
 *   else if(c == "t") then "T"
 *   else if(c == "u") then "U"
 *   else if(c == "v") then "V"
 *   else if(c == "w") then "W"
 *   else if(c == "x") then "X"
 *   else if(c == "y") then "Y"
 *   else if(c == "z") then "Z"
 *   else c;
 * let fillPeriod(x, y) = x + "." + y;
 * let isPeriod(c) = c == ".";

 * {
 *   print("input a string");
 *   let ln = scan();
 *   let up = map(ln, "toUpper");
 *   print(up);
 *   let com = inject(up, "fillPeriod");
 *   print(com);
 *   let periods = filter(com, "isPeriod");
 *   print("period = " + abs(periods));
 * };
 * </pre>
 *
 * <h4>6</h4>
 * 写像と選択.
 * <pre>
 * let map(xs, len, func) = {
 *   let i = 0;
 *   let result = "{";
 *   while(i &lt; len){
 *     let name = xs + i;
 *     let elem = refvar(name);
 *     let val = call(func, elem);
 *     let result = result + "let " + name + " = " + val + ";";
 *     let i = i + 1;
 *   };
 *   let result = result + "};"};
 *   result;
 * };
 * let filter(src, dest, lenName, pred) = {
 *   let i = 0;
 *   let count = 0;
 *   let len = refvar(lenName);
 *   let result = "{";
 *   while(i &lt; len){
 *     let inName = src + i;
 *     let elem = refvar(inName);
 *     if(call(pred, elem)) then {
 *       let result = result + "let " + dest + count + " = " + elem + ";";
 *       let count = count + 1;
 *     };
 *     let i = i + 1;
 *   };
 *   let result = result + "let " + lenName + " = " + count + ";};";
 *   result;
 * };
 * let plus1(x) = x + 1;
 * let isEven(x) = (int)x % 2 == 0;
 * let p(x) = print(x + " :: " + typeof(x));
 * let pa(xs, len) = {
 *   let i = 0;
 *   while(i &lt; len){
 *     p(refvar(xs + i));
 *     let i = i + 1;
 *   };
 * };
 *
 * {
 *   let a0 = 1;
 *   let a1 = 3;
 *   let a2 = 4;
 *   let a3 = 10;
 *   let len = 4;
 *   print("first:");
 *   pa("a", len);
 *   eval(map("a", len, "plus1"));
 *   print("second:");
 *   pa("a", len);
 *   eval(filter("a", "b", "len", "isEven"));
 *   print("third:");
 *   pa("b", len);
 * };
 * </pre>
 * @author sin
 * @version 2017.3.23
 */
package main;