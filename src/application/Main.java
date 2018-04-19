package application;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import controllers.MainPaneController;
import input.JNativeHookKeyListener;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.PropertiesUtil;
import utils.WindowManager;

/**
 * メインクラス。アプリケーションのエントリーを含んでいる。
 * アプリケーション起動時、mainメソッドを介してstartメソッドが実行される。
 * @author 皇翔(Shou Sumeragi)
 *
 */
public class Main extends Application {
	/**
	 * メインウィンドウ
	 */
	protected WindowManager<MainPaneController> mainWindow;

	/**
	 * スタートメソッド。
	 * このメソッドが実質的なアプリケーションのエントリーとなる(アプリケーション起動時に実行される)
	 * @param primaryStage アプリケーションのGUIを配置するベースとなるステージ。このステージにSceneを配置してそのSceneにPaneを配置して…といった形で構成する
	 */
	public void start(Stage primaryStage) {
		try {
			// FXMLファイルのパスが記述されたプロパティファイルを読み込む
			Properties fxmlFilePathsProperties = PropertiesUtil.loadPropertiesFile("FxmlFilePaths");

			// メインウィンドウの設定をプロパティファイルから読み込む
			Properties mainWindowProperties = PropertiesUtil.loadPropertiesFile("MainWindow");

			// メインウィンドウをFXMLから生成
			this.mainWindow = new WindowManager<MainPaneController>("mainWindow", fxmlFilePathsProperties.getProperty("MainPane"), primaryStage, Integer.parseInt(mainWindowProperties.getProperty("width")), Integer.parseInt(mainWindowProperties.getProperty("height")));

			// メインウィンドウのコントローラにウィンドウを保持する
			this.mainWindow.getController().setWindow(this.mainWindow);

			// メインウィンドウのタイトル設定
			this.mainWindow.getStage().setTitle(mainWindowProperties.getProperty("title"));

			// ウィンドウのリサイズができないようにする
			this.mainWindow.getStage().setResizable(false);

			// 終了時の処理をイベント登録
			this.mainWindow.getStage().setOnCloseRequest((WindowEvent event) -> {
				onClose(event);
			});

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
			JNativeHookKeyListener jNativeHookKeyListener = new JNativeHookKeyListener(this.mainWindow);
			GlobalScreen.addNativeKeyListener(jNativeHookKeyListener);
			// リスナをウィンドウに保持
			this.mainWindow.setJNativeHookKeyListener(jNativeHookKeyListener);

			// メインウィンドウを表示
			this.mainWindow.showWindow();
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

	/**
	 * 終了時の処理
	 * @param event イベント発生時の各種情報
	 */
	public void onClose(WindowEvent event) {
		// 明示的に終了する(これをしないとバックグラウンドでアプリが動いたままになる)
		System.exit(0);
	}
}
