package controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.PropertiesUtil;
import utils.WindowManager;

/**
 * Helloを表示するペインのコントローラとなるクラス
 * @author 皇翔(Shou Sumeragi)
 */
public class MainPaneController implements Initializable {
	/**
	 * クラス内定数。「進撃・撤退のImageViewでどの画像が表示されているか」
	 * 進撃の画像が表示されている
	 */
	private static final int ATTACKIMG_TYPE_ATTACK = 1;
	/**
	 * クラス内定数。「進撃・撤退のImageViewでどの画像が表示されているか」
	 * 撤退の画像が表示されている
	 */
	private static final int ATTACKIMG_TYPE_WITHDRAWAL = 2;

	/**
	 * クラス内定数。「夜戦突入・追撃せずのImageViewでどの画像が表示されているか」
	 * 夜戦突入の画像が表示されている
	 */
	private static final int NIGHTBATTLEIMG_TYPE_NIGHTBATTLE = 3;
	/**
	 * クラス内定数。「夜戦突入・追撃せずのImageViewでどの画像が表示されているか」
	 * 追撃せずの画像が表示されている
	 */
	private static final int NIGHTBATTLEIMG_TYPE_DONOTPURSUE = 4;

	/**
	 * このペインを呼び出しているStageを保持するプロパティ
	 */
	public Stage stage;

	/**
	 * FXMLファイルのパスが設定されたプロパティファイルを読み込むクラス
	 */
	Properties fxmlFilePathsProperties;

	/**
	 * 画像ファイルのパスが設定されたプロパティファイルを読み込むクラス
	 */
	Properties imagePathsProperties;

	/**
	 * 各種コントロールを配置するメインとなるペイン
	 */
	public Pane mainPane;

	/**
	 * 進撃・撤退の画像を表示するImageView
	 */
	public ImageView attackImageView;
	/**
	 * 進撃・撤退のImageViewに現在どちらの画像が表示されているか。
	 */
	protected int attackImageType = MainPaneController.ATTACKIMG_TYPE_ATTACK; // 初期表示されているのは進撃

	/**
	 * 夜戦突入・追撃せずの画像を表示するImageView
	 */
	public ImageView nightBattleImageView;
	/**
	 * 夜戦突入・追撃せずのImageViewに現在どちらの画像が表示されているか
	 */
	protected int nightBattleImageType = MainPaneController.NIGHTBATTLEIMG_TYPE_NIGHTBATTLE; // 初期表示されているのは夜戦突入

	/**
	 * 画像(Imageクラス)を格納するリスト
	 */
	private Map<Integer, Image> images;

	/**
	 * 設定ボタン
	 */
	public Button configButton;

	/**
	 * 設定ウィンドウの管理クラス
	 */
	public WindowManager configWindow;

	/**
	 * 初期化処理。
	 * コントローラのルート要素が完全に処理された後に、コントローラを初期化するためにコールされる。
	 * @param location ルート・オブジェクトの相対パスの解決に使用される場所、または場所が不明の場合は、null。
	 * @param resources ルート・オブジェクトのローカライズに使用されるリソース、またはルート・オブジェクトがローカライズされていない場合は、null。
	 */
	public void initialize(URL location, ResourceBundle resources) {
		// 現状特に処理なし
	}

	/**
	 * コンストラクタ。
	 */
	public MainPaneController() {
		// FXMLファイルのパスが記述されたプロパティファイルを読み込む
		this.fxmlFilePathsProperties = PropertiesUtil.loadPropertiesFile("FxmlFilePaths");

		// 画像ファイルのパスを設定するプロパティファイルを読み込む
		this.imagePathsProperties = PropertiesUtil.loadPropertiesFile("ImagePaths");

		// 画像格納リストを初期化
		this.images = new HashMap<Integer, Image>();
		// 表示する画像を画像格納リストに読み込んで即使えるようにしておく
		this.images.put(MainPaneController.ATTACKIMG_TYPE_ATTACK,           new Image(imagePathsProperties.getProperty("attackImagePath")));      // 進撃の画像読み込み
		this.images.put(MainPaneController.ATTACKIMG_TYPE_WITHDRAWAL,       new Image(imagePathsProperties.getProperty("withdrawalImagePath")));  // 撤退の画像読み込み
		this.images.put(MainPaneController.NIGHTBATTLEIMG_TYPE_NIGHTBATTLE, new Image(imagePathsProperties.getProperty("nightBattleImagePath"))); // 夜戦突入の画像読み込み
		this.images.put(MainPaneController.NIGHTBATTLEIMG_TYPE_DONOTPURSUE, new Image(imagePathsProperties.getProperty("doNotPursueImagePath"))); // 追撃せずの画像読み込み
	}

	/**
	 * stageのゲッター
	 * @return このペインを呼び出しているStageを返す。
	 */
	public Stage getStage() {
		return this.stage;
	}

	/**
	 * stageのセッター
	 * @param stage このペインを呼び出しているステージ
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * 進撃・撤退ImageViewの画像を指定の画像に切り替える。
	 * 画像の指定は番号にて行う(番号がどの画像に対応するかはクラス内定数 ATTACKIMG_TYPE_... を参照)
	 * @param attackImageType 表示させたい画像番号
	 */
	public void changeAttackImage(int attackImageType) {
		// どの画像を表示しているかの値を更新
		this.attackImageType = attackImageType;

		// 指定の画像をImageViewにセット
		this.attackImageView.setImage(this.images.get(attackImageType));
	}

	/**
	 * 夜戦突入・追撃せずImageViewの画像を指定の画像に切り替える。
	 * 画像の指定は番号にて行う(番号がどの画像に対応するかはクラス内定数 NIGHTBATTLEIMG_TYPE_... を参照)
	 * @param nightBattleImageType 表示させたい画像番号
	 */
	public void changeNightBattleImage(int nightBattleImageType) {
		// どの画像を表示しているかの値を更新
		this.nightBattleImageType = nightBattleImageType;

		// 指定の画像をImageViewにセット
		this.nightBattleImageView.setImage(this.images.get(nightBattleImageType));
	}

	/**
	 * 進撃・撤退のImageViewに表示される画像を順に切り替える
	 */
	public void toggleAttackImageView() {
		switch (this.attackImageType) {
		// 進撃の画像が表示されていれば
		case MainPaneController.ATTACKIMG_TYPE_ATTACK:
			// 撤退の画像に切り替える
			this.changeAttackImage(MainPaneController.ATTACKIMG_TYPE_WITHDRAWAL);
			break;
		// 撤退の画像が表示されていれば
		case MainPaneController.ATTACKIMG_TYPE_WITHDRAWAL:
			// 進撃の画像に切り替える
			this.changeAttackImage(MainPaneController.ATTACKIMG_TYPE_ATTACK);
			break;
		}
	}

	/**
	 * 夜戦突入・追撃せずのImageViewに表示される画像を順切り替える
	 */
	public void toggleNightBattleImageView() {
		switch (this.nightBattleImageType) {
		// 夜戦突入の画像が表示されていれば
		case MainPaneController.NIGHTBATTLEIMG_TYPE_NIGHTBATTLE:
			// 追撃せずの画像に切り替える
			this.changeNightBattleImage(MainPaneController.NIGHTBATTLEIMG_TYPE_DONOTPURSUE);
			break;
		// 追撃せずの画像が表示されていれば
		case MainPaneController.NIGHTBATTLEIMG_TYPE_DONOTPURSUE:
			// 夜戦突入の画像に切り替える
			this.changeNightBattleImage(MainPaneController.NIGHTBATTLEIMG_TYPE_NIGHTBATTLE);
			break;
		}
	}

	/**
	 * 設定ウィンドウを開く
	 */
	public void openConfigWindow() {
		// 設定ウィンドウをFXMLから生成
		this.configWindow = new WindowManager("configWindow", this.fxmlFilePathsProperties.getProperty("ConfigPane"), null, 300, 300);
		// モーダルウィンドウとして設定(子ウィンドウで操作を完了しないともとのウィンドウが操作できない)
		this.configWindow.getStage().initModality(Modality.APPLICATION_MODAL);
		// 設定ウィンドウの親を設定
		this.configWindow.getStage().initOwner(this.stage);

		// 設定ウィンドウのタイトル設定
		this.configWindow.getStage().setTitle("設定");
		// 設定ウィンドウのリサイズができないようにする
		this.configWindow.getStage().setResizable(false);

		// 設定ウィンドウのステージを表示
		this.configWindow.showWindow();
	}

	/**
	 * attackImageViewクリック時の処理
	 * @param event イベント発生時の各種情報(クリックされた場所の座標等もここから取得できる)
	 */
	public void attackImageVIewClick(MouseEvent event) {
		// 画像を切り替える
		this.toggleAttackImageView();
	}

	/**
	 * nightBattleImageViewクリック時の処理
	 * @param event イベント発生時の各種情報(クリックされた場所の座標等もここから取得できる)
	 */
	public void nightBattleImageViewClick(MouseEvent event) {
		// 画像を切り替える
		this.toggleNightBattleImageView();
	}

	/**
	 * configButtonが押された場合の処理
	 */
	public void configButtonOnAction() {
		// 設定ウィンドウを開く
		this.openConfigWindow();
	}
}
