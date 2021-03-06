# 艦これ Next instruction
艦これにて、別の作業をしつつ、4-3で対潜レベリングをしている時

「あれ…次って進撃だっけ撤退だっけ？」

ということが多々ある、主に私に向けたGUIアプリです。<br />

![スクリーンショット](../images/ss001.jpg)

小窓内に 進撃・撤退 と 夜戦突入・追撃せず の2か所画像を表示し<br />
それぞれ、クリックもしくは、設定したキーをタップすることで画像を切り替え可能です。

このマスで戦闘したら撤退だなといった時に、戦闘開始前に 進撃・撤退 の画像表示部分を 撤退 に切り替えておけば<br />
別の作業をしていて、どのマスで戦闘していたか忘れてしまっても<br />
このアプリの画像表示で次は撤退すれば良いということが一目瞭然になるといった使い方ができます。

## 注意
このアプリケーションは、Javaを初めて触る私が勉強も兼ねて自分用に作ったものとなります。<br />
そのため、書き方も我流でJavaの作法も良くわかっていない状態で作っているため<br />
完成度のほうは全くと言って期待できません。

## 動作環境
Java8以上のランタイムがインストールされている、デスクトップ上での動作を想定しているのですが<br />
都合上、開発で使用したPCしかないため<br />
Windows8.1、Java8 での動作しか試していません。<br />
JavaFXを主に使ったGUIアプリなので<br />
恐らく、Java8のランタイムが入っているデスクトップ環境であれば<br />
LinuxやMac等でも動作するとは思うのですが、確証はまったくありません。

## ダウンロード
[リリースページ](../../releases) にてバイナリがダウンロードできます。<br />
<br />
※ダウンロードしたzipを任意の場所に解凍して使用してください。

## ビルドする際の注意
propertiesfilesディレクトリがクラス階層のルートから参照できるように配置・設定してください。<br />
このアプリケーションは、非アクティブ状態の時もキー入力を受け付けるようにするために<br />
JNativeHook というライブラリを使用させていただいています。<br />
そのため、ビルドする際は、JNativeHookのバージョン 2.1.0 を入手してプロジェクトに追加するなり<br />
パスを通すなりしてください。<br />
また、GUIにはJavaFXを使用しているため、JavaFX SDKへパスを通してください。<br />
(Eclipseで、JavaFXプロジェクトとしてプロジェクトをインポートする等)

## ライセンス
このソフトウェアは、MIT ライセンスの下で公開しています。<br />
[LICENSE.txt](/LICENSE.txt) を参照してください。

## 最後に
このような稚拙なアプリケーションをGitHubにアップしてしまい、お恥ずかしい限りですが<br />
Gitの使用経験が無いに等しい自分が、クライアントとして利用するための勉強に際し<br />
サーバを自前で立てずに環境を用意できないかと模索した結果、今回のアップに至った次第であること<br />
ご了承いただければと思います。<br />
このような極めて限られた層しか使わないようなアプリですが、奇縁にも発見し<br />
物好きにも使ってみようと思うような人が万が一いたならば幸いです。