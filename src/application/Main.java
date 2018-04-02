package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * メインクラス。アプリケーションのエントリーを含んでいる。
 * アプリケーション起動時、mainメソッドを介してstartメソッドが実行される。
 * @author unlimitedpractice
 *
 */
public class Main extends Application {
	/**
	 * ルートペイン
	 */
	// protected BorderPane root;
	protected Hello helloObj;

	/**
	 * メインシーン
	 */
	protected Scene scene;

	/**
	 * スタートメソッド。
	 * このメソッドが実質的なアプリケーションのエントリーとなる(アプリケーション起動時に実行される)
	 * @param primaryStage アプリケーションのGUIを配置するベースとなるステージ。このステージにSceneを配置してそのSceneにPaneを配置して…といった形で構成する
	 */
	public void start(Stage primaryStage) {
		try {
			// タイトル設定
			primaryStage.setTitle("Next instruction");

			// Helloクラス生成(Helloを表示するペインを含んでいる)
			helloObj = new Hello();

			// シーン生成
			scene = new Scene(helloObj.pane, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// ステージにシーンをセット
			primaryStage.setScene(scene);
			// ステージを表示
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * メインメソッド。アプリケーションのエントリー(アプリケーション起動時に実行される)
	 * ここから start メソッドが実行されるので実質的なエントリーは start メソッドとなる。
	 * @param args コマンドライン引数がString配列に展開されて渡される
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
