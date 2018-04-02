package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

/**
 * Helloを表示するペインを扱うクラス
 * @author unlimitedpractice
 */
public class Hello {
	/**
	 * メインとなるペイン
	 */
	protected BorderPane pane;

	/**
	 * コンストラクタ。
	 * ペインの生成等
	 */
	public Hello() {
		try {
			// ペインをFXMLから読み込む
			this.pane = (BorderPane)FXMLLoader.load(getClass().getResource("../fxmls/HelloBorderPane.fxml"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
