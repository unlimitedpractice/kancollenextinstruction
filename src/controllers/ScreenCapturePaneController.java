package controllers;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import utils.PropertiesUtil;

/**
 * 画面キャプチャウィンドウのルートペインのコントローラクラス
 * @author 皇翔(Shou Sumeragi)
 */
public class ScreenCapturePaneController extends BaseController implements Initializable {
	/**
	 * 画面キャプチャウィンドウに関する設定プロパティファイルを読み込むクラス
	 */
	protected PropertiesUtil screenCaptureWindowProperties;

	/**
	 * 画面キャプチャに関する設定プロパティファイルを読み込むクラス
	 */
	protected PropertiesUtil screenCaptureConfigProperties;

	/**
	 * 画面キャプチャウィンドウのルートペイン
	 */
	public Pane screenCapturePane;

	/**
	 * 画像保存先を表示するTextField
	 */
	public TextField imageSaveDestinationTextField;

	/**
	 * 画像保存先のフォルダを選択するダイアログを開くボタン
	 */
	public Button imageSaveDestinationButton;

	/**
	 * 画像保存先選択ダイアログを扱うクラス
	 */
	protected DirectoryChooser directoryChooser;

	/**
	 * キャプチャ実行ボタン
	 */
	public Button captureButton;

	/**
	 * 画面キャプチャをするためのRobotクラス
	 */
	protected Robot screenCaptureRobot;

	/**
	 * 初期化処理
	 */
	public void initialize(URL location, ResourceBundle resources) {
		// 画像保存先を表示するラベルからフォーカスを外す(ウィンドウを開いた時にプロンプトが見えるようにするため)
		this.imageSaveDestinationTextField.setFocusTraversable(false);

		// 設定ウィンドウに関する設定プロパティファイルを読み込む
		this.screenCaptureWindowProperties = new PropertiesUtil("ScreenCaptureWindow");

		// 画面キャプチャに関する設定プロパティファイルを読み込む
		this.screenCaptureConfigProperties = new PropertiesUtil("ScreenCaptureConfig");

		// 画像保存先ディレクトリ選択ダイアログがすぐに開けるよう準備
		// 画像保存先ディレクトリ選択ダイアログを扱うクラス生成
		this.directoryChooser = new DirectoryChooser();
		// 画像保存先ディレクトリ選択ダイアログのタイトルを設定
		directoryChooser.setTitle(this.screenCaptureWindowProperties.getProperty("imageSaveDestinationDialogWording"));

		// プロパティファイルに保存された、画像保存先パスをTextFieldに反映
		this.imageSaveDestinationTextField.setText(this.screenCaptureConfigProperties.getProperty("imageSaveDestinationPath"));

		// 画面キャプチャがすぐ実行できるように準備
		try {
			// 画面キャプチャをするためのRobotクラス生成
			this.screenCaptureRobot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 参照ボタンが押された際の処理
	 */
	public void imageSaveDestinationButtonOnAction() {
		// 画像保存先選択ダイアログを開く(選択結果は戻り値で取得)
		File dialogResult = this.directoryChooser.showDialog(this.getWindow().getStage());

		// 画像保存先を表示するTextFieldに選択結果を反映
		this.imageSaveDestinationTextField.setText(dialogResult.getPath().toString());

		// 選択結果をプロパティファイルに保存
		this.screenCaptureConfigProperties.setProperty("imageSaveDestinationPath", dialogResult.getPath().toString());
		this.screenCaptureConfigProperties.store(this.screenCaptureWindowProperties.getProperty("ScreenCaptureConfigComment"));
	}

	/**
	 * キャプチャ実行ボタンが押された際の処理
	 */
	public void captureButtonOnAction() {
		try {
			// キャプチャした画像の保存先ディレクトリのパスを取得
			String imageSaveDestination = this.imageSaveDestinationTextField.getText();
			// 保存先がデフォルトでなければ(imageSaveDestinationが空でなければ)
			if (!imageSaveDestination.equals("")) {
				// '\'は、'/'に変換
				imageSaveDestination = imageSaveDestination.replace("\\", "/") + "/";
			}

			// キャプチャ画像のファイル名接頭語をプロパティファイルから取得
			String captureImageFilePathPrefix = this.screenCaptureWindowProperties.getProperty("captureImageFileNamePrefix");

			// 現在日時をyyyyMMdd-hhmmss形式で文字列として取得
			Date nowDate = new Date();
			String nowDateString = new SimpleDateFormat("yyyyMMdd-hhmmss").format(nowDate);

			// キャプチャ画像のファイルパス(拡張子抜き)を組み立て
			String captureImageFilePathWithOutExtension = imageSaveDestination + captureImageFilePathPrefix + nowDateString;

			// 組み立てたファイル名のファイルがが既に存在しているかどうかチェック
			String captureImageFileNameExtension = this.screenCaptureWindowProperties.getProperty("captureImageFileNameExtension");
			File file = new File(captureImageFilePathWithOutExtension + "." + captureImageFileNameExtension);
			// 存在している場合は、連番を付与する(ファイル名が被らなくなるまで連番に1足してチェックするループ)
			Integer i = 0;
			while (file.exists()) {
				i += 1;
				file = new File(captureImageFilePathWithOutExtension + "-" + i.toString() + "." + captureImageFileNameExtension);
			}

			// キャプチャ画像ファイルパス組み立て
			// 連番の付与をしないと既存のファイルとファイル名が被ってしまう場合
			if (i != 0) {
				// ファイル名に連番を付与
				captureImageFilePathWithOutExtension += '-' + i.toString();
			}
			String captureImageFilePath = captureImageFilePathWithOutExtension + '.' + captureImageFileNameExtension;

			// キャプチャ範囲設定
			// TODO キャプチャ範囲設定矩形で、範囲を設定できるようにする
			Rectangle captureRange = new Rectangle(0, 0, 640, 360);

			// 画面をキャプチャして、ファイルに保存
			ImageIO.write(this.screenCaptureRobot.createScreenCapture(captureRange), this.screenCaptureWindowProperties.getProperty("captureImageFileNameExtension"), new File(captureImageFilePath));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	// 以下、ゲッターとセッター---------------------------------------------------------------------------------------//

	/**
	 * screenCaptureWindowPropertiesのゲッター
	 * @return 画面キャプチャウィンドウに関する設定プロパティファイルを読み込んだクラスインスタンスを返す
	 */
	public PropertiesUtil getScreenCaptureWindowProperties() {
		return this.screenCaptureWindowProperties;
	}

	/**
	 * screenCaptureConfigPropertiesのゲッター
	 * @return 画面キャプチャに関する設定プロパティファイルを読み込んだクラスインスタンスを返す
	 */
	public PropertiesUtil getScreenCaptureConfigProperties() {
		return this.screenCaptureConfigProperties;
	}
}
