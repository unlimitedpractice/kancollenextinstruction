package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import utils.WindowManager;

/**
 * コントローラクラスのベース(各コントローラはこのクラスを継承して使う)
 * @author 皇翔(Shou Sumeragi)
 */
public class BaseController implements Initializable {
	/**
	 * コントローラを持っているWindowManagerを保持するプロパティ
	 */
	public WindowManager<? extends BaseController> window;

	/**
	 * 初期化処理。
	 * コントローラのルート要素が完全に処理された後に、コントローラを初期化するためにコールされる。
	 * @param location ルート・オブジェクトの相対パスの解決に使用される場所、または場所が不明の場合は、null。
	 * @param resources ルート・オブジェクトのローカライズに使用されるリソース、またはルート・オブジェクトがローカライズされていない場合は、null。
	 */
	public void initialize(URL location, ResourceBundle resources) {
		// 現状、特に処理なし
	}

	/**
	 * コンストラクタ。
	 */
	public BaseController() {
		// 現状、特に処理なし
	}

	// 以下、ゲッターとセッター---------------------------------------------------------------------------------------//

	/**
	 * windowのゲッター。
	 * @return コントローラを持っているウィンドウのウィンドウ管理クラス
	 */
	public WindowManager<? extends BaseController> getWindow() {
		return this.window;
	}

	/**
	 * windowのセッター
	 * @param window コントローラを持っているウィンドウのウィンドウ管理クラス
	 */
	public void setWindow(WindowManager<? extends BaseController> window) {
		this.window = window;
	}
}
