package application;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import controllers.MainPaneController;
import input.JNativeHookKeyListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utils.PropertiesUtil;

/**
 * メインクラス。アプリケーションのエントリーを含んでいる。
 * アプリケーション起動時、mainメソッドを介してstartメソッドが実行される。
 * @author unlimitedpractice
 *
 */
public class Main extends Application {
	/**
	 * メインペイン
	 */
	protected Pane mainPane;
	/**
	 * メインペインのコントローラ
	 */
	protected MainPaneController mainPaneController;

	/**
	 * メインシーン
	 */
	protected Scene scene;

	/**
	 * 各ペインのコントローラを保持するためのマップ。
	 * (コントローラ外から、ペインを操作するのに使う)
	 */
	public Map<String, Object> controllers;

	/**
	 * スタートメソッド。
	 * このメソッドが実質的なアプリケーションのエントリーとなる(アプリケーション起動時に実行される)
	 * @param primaryStage アプリケーションのGUIを配置するベースとなるステージ。このステージにSceneを配置してそのSceneにPaneを配置して…といった形で構成する
	 */
	public void start(Stage primaryStage) {
		try {
			// コントローラを保持するマップを初期化
			this.controllers = new HashMap<String, Object>();

			// タイトル設定
			primaryStage.setTitle("艦これ Next instruction");
			// ウィンドウのリサイズができないようにする
			primaryStage.setResizable(false);

			// FXMLファイルのパスが記述されたプロパティファイルを読み込む
			Properties fxmlFilePathsProperties = PropertiesUtil.loadPropertiesFile("FxmlFilePaths");

			// メインペインをFXMLから読み込み
			FXMLLoader fxmlLoader = new FXMLLoader();
			InputStream mainPaneFxmlInputStream = Main.class.getClassLoader().getResourceAsStream(fxmlFilePathsProperties.getProperty("MainPane"));
			mainPane = (Pane)fxmlLoader.load(mainPaneFxmlInputStream);

			// コントローラマップからコントローラを取得する際の名前設定プロパティファイルを読み込む
			Properties controllerNamesProperties = PropertiesUtil.loadPropertiesFile("ControllerNames");
			// メインペインのコントローラを保持(コントローラ外から、ペインを操作するのに使うため)
			this.controllers.put(controllerNamesProperties.getProperty("MainPaneController"), fxmlLoader.getController());

			// メインウィンドウの設定をプロパティファイルから読み込む
			Properties mainPaneProperties = PropertiesUtil.loadPropertiesFile("MainWindow");

			// シーン生成
			scene = new Scene(mainPane, Integer.parseInt(mainPaneProperties.getProperty("width")), Integer.parseInt(mainPaneProperties.getProperty("height")));
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			// ステージにシーンをセット
			primaryStage.setScene(scene);
			// ステージを表示
			primaryStage.show();

			try {
				// JNativeHookのログレベルをOFFに変えて、通常ログが大量に出ないようにする
				Logger jNativeHookLogger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
				jNativeHookLogger.setLevel(Level.OFF);

				// 非アクティブ時にもキー入力によるイベントを発生させるためJNativeHookを登録する
				GlobalScreen.registerNativeHook();
			} catch (NativeHookException e) {
				System.err.println("NativeHookの登録で問題が発生しました。");
				System.err.println(e.getMessage());
				System.exit(1);
			}

			// JNativeHookのキー入力に対するリスナを登録
			GlobalScreen.addNativeKeyListener(new JNativeHookKeyListener(this.controllers));
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
