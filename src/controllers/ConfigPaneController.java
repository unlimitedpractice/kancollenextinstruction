package controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * メインペインのコントローラクラス
 * @author 皇翔(Shou Sumeragi)
 */
public class ConfigPaneController extends BaseController implements Initializable {
	/**
	 * 設定ウィンドウ(仮)を表示するLabel
	 */
	public Label configLabel001;

	/**
	 * コンストラクタ。
	 */
	public ConfigPaneController() {
		// 親クラスのコンストラクタ実行
		super();
	}
}
