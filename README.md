# make.language

簡単なスクリプト言語を作ってみました.  
実用性はありません.

## おおざっぱな仕様

### 実行方法

インタプリタとファイルを読む形式のものがあります.  
後者はファイルの先頭から順に実行します.

### データ型

* bool 論理値型
* int 整数型
* double 実数型
* string 文字列型

すべて比較可能かつキャスト可能です.  
適切な型に自動でキャストされる場合があります.
  
stringにnullはありません.

### 文

* if 分岐
* while 繰り返し
* let 変数と関数の定義

### 関数

引数の数について多重定義できます.

関数はそれ自体では外側の変数に影響を及ぼすことができません.  
関数内に登場する変数は引数も含めてすべてローカルです.

外側の変数に影響を及ぼすにはライブラリ関数evalを使います.

### 演算子

データ型それぞれについて多重定義されています.

## コンパイル

カレントディレクトリの下に

* element
* main
* parser
* visitor
* overview.html

があるとして

javac -encoding UTF-8 element\*.java parser\*.java main\*.java visitor\*.java

してください.

## ドキュメンテーション

コンパイルしたときと同じ場所で

dir /b /s *.java > list

(Win, すべての.javaファイルの名前を集める操作です)

mkdir doc

してから

javadoc -encoding UTF-8 -public -d doc -overview overview.html @list

してください.

## 実行

まずはコンパイルしたときと同じ場所で

java main.Main h

してください.