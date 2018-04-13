package controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * 設定ウィンドウのルートペインのコントローラクラス
 * @author 皇翔(Shou Sumeragi)
 */
public class ConfigPaneController extends BaseController implements Initializable {
	/**
	 * 設定ウィンドウのルートペイン
	 */
	public Pane configPane;

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
