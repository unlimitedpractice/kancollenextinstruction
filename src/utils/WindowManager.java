package utils;

import java.io.InputStream;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * ウィンドウを扱うクラス
 * @author 皇翔(Shou Sumeragi)
 */
public class WindowManager<T> {
	/**
	 * ウィンドウID。
	 * (ウィンドウを複数格納したマップから取り出す時に使うID)
	 */
	protected String windowId = null;

	/**
	 * ウィンドウ本体となるステージ
	 */
	protected Stage stage;

	/**
	 * ウィンドウのシーン
	 */
	protected Scene scene;

	/**
	 * ルートペイン。
	 * Pane型やBorderPane型等、様々な型のものが入る可能性があるためObject型のプロパティに保持しておき、使用時にキャストする
	 */
	protected Parent rootPane;

	/**
	 * コントローラ(正確にはルートペインのコントローラ)
	 * コントローラはそれぞれ別のクラスによって作られる(全て型が違う)ので、Object型のプロパティに保持しておき、使用時にキャストする
	 */
	protected T controller;

	/**
	 * コンストラクタ(根元のウィンドウを開く場合)
	 * fxmlPathで指定されたFXMLを読み込む
	 * @param windowId ウィンドウID(ウィンドウを複数格納したマップから取り出す時に使うID)
	 * @param fxmlPath 読み込むFXMLのパス
	 * @param primaryStage 根元のウィンドウを扱う場合、startメソッドの引数primaryStageをこの引数に渡す。nullを渡した場合は子ウィンドウを扱うものとして新しいStageを生成する
	 * @param width ウィンドウ横幅
	 * @param height ウィンドウ縦幅
	 */
	public WindowManager(String windowId, String fxmlPath, Stage primaryStage, int width, int height) {
		try {
			// primaryStageが渡されていれば
			if (primaryStage != null) {
				// primaryStageをこのウィンドウのステージとしてセット
				this.stage = primaryStage;
			} else {
				// 指定されていない場合は、新たにStageを生成する
				this.stage = new Stage();
			}

			// FXMLを読み込む
			FXMLLoader fxmlLoader = new FXMLLoader();
			InputStream paneInputStream = Main.class.getClassLoader().getResourceAsStream(fxmlPath);
			this.rootPane = fxmlLoader.load(paneInputStream);

			// ルートペインのコントローラ取得
			this.controller = fxmlLoader.getController();

			// シーン生成
			this.scene = new Scene(this.rootPane, width, height);
			this.scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());

			// ステージにシーンをセット
			this.stage.setScene(this.scene);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * this.windowIdのゲッター
	 * @return ウィンドウIDを返す。
	 */
	public String getWindowId() {
		return this.windowId;
	}

	/**
	 * this.stageのゲッター
	 * @return Stageのインスタンスを返す。
	 */
	public Stage getStage() {
		return this.stage;
	}

	/**
	 * this.sceneのゲッター
	 * @return Sceneのインスタンスを返す。
	 */
	public Scene getScene() {
		return this.scene;
	}

	/**
	 * this.controllerのゲッター
	 * @return コントローラクラスのインスタンスを返す。
	 */
	public T getController() {
		return this.controller;
	}

	/**
	 * ウィンドウのタイトルをセットする。
	 * @param windowTitle ウィンドウのタイトル
	 */
	public void setWindowTitle(String windowTitle) {
		this.stage.setTitle(windowTitle);
	}

	/**
	 * ウィンドウを表示する
	 */
	public void showWindow() {
		this.stage.show();
	}
}
