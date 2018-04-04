package application;

import java.io.InputStream;
import java.util.Properties;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * メインクラス。アプリケーションのエントリーを含んでいる。
 * アプリケーション起動時、mainメソッドを介してstartメソッドが実行される。
 * @author unlimitedpractice
 *
 */
public class Main extends Application {
	/**
	 * Helloを表示するペイン
	 */
	protected BorderPane helloPane;

	/**
	 * メインペイン
	 */
	protected Pane mainPane;

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
			// ウィンドウのリサイズができないようにする
			primaryStage.setResizable(false);

			// メインペインをFXMLから読み込み
			mainPane = (Pane)FXMLLoader.load(getClass().getResource(FxmlFilePaths.MainPane.getValue()));

			// メインウィンドウの設定をプロパティファイルから読み込む
			InputStream mainPanePropertiesFileInputStream = Main.class.getClassLoader().getResourceAsStream(PropertiesFilePaths.MaiinWindow.getValue());
			Properties mainPaneProperties = new Properties();
			mainPaneProperties.load(mainPanePropertiesFileInputStream);

			// シーン生成
			scene = new Scene(mainPane, Integer.parseInt(mainPaneProperties.getProperty("width")), Integer.parseInt(mainPaneProperties.getProperty("height")));
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
