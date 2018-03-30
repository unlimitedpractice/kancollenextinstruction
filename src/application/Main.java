package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * メインクラス。アプリケーションのエントリーを含んでいる。
 * アプリケーション起動時、mainメソッドを介してstartメソッドが実行される。
 * @author unlimitedpractice
 *
 */
public class Main extends Application {
	/**
	 * スタートメソッド。
	 * このメソッドが実質的なアプリケーションのエントリーとなる(アプリケーション起動時に実行される)
	 * @param primatyStage アプリケーションのGUIを配置するベースとなるステージ。このステージにSceneを配置してそのSceneにPaneを配置して…といった形で構成する
	 */
	public void start(Stage primaryStage) {
		try {
			// タイトル設定
			primaryStage.setTitle("Next instruction");

			// ルートペイン生成(BorderPane)
			BorderPane root = new BorderPane();

			// シーン生成
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());


			// Helloラベル生成
			Label helloLabel = new Label("Hello, JavaFX.");
			// ラベルのスタイルを設定
			helloLabel.setStyle("-fx-font-size: 32pt;"); // フォントサイズ設定
			// ラベルをルートペインに配置
			root.setCenter(helloLabel);

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
