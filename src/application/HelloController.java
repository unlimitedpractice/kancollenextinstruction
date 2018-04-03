package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * Helloを表示するペインのコントローラとなるクラス
 * @author unlimitedpractice
 */
public class HelloController implements Initializable {
	/**
	 * メインとなるペイン
	 */
	public BorderPane pane;

	/**
	 * Helloを表示する中央のラベル
	 */
	public Label helloLabel;

	/**
	 * Helloを表示する上部のラベル
	 */
	public Label topHelloLabel;

	/**
	 * 初期化処理。
	 * コントローラのルート要素が完全に処理された後に、コントローラを初期化するためにコールされる。
	 * @param location ルート・オブジェクトの相対パスの解決に使用される場所、または場所が不明の場合は、null。
	 * @param resources ルート・オブジェクトのローカライズに使用されるリソース、またはルート・オブジェクトがローカライズされていない場合は、null。
	 */
	public void initialize(URL location, ResourceBundle resources) {
		// 現状とくになし
		System.out.println(location);
		System.out.println(resources);
	}

	/**
	 * コンストラクタ。
	 */
	public HelloController() {
		// 現業特に処理なし
	}

	/**
	 * helloLabelクリック時の処理
	 * @param event イベント情報
	 */
	public void helloLabelOnClick(MouseEvent event) {
		helloLabel.setText("クリックされました。");
	}
}
