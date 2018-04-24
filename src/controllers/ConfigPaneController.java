package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.jnativehook.keyboard.NativeKeyEvent;

import input.JNativeHookKeyListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.WindowEvent;
import utils.PropertiesUtil;

/**
 * 設定ウィンドウのルートペインのコントローラクラス
 * @author 皇翔(Shou Sumeragi)
 */
public class ConfigPaneController extends BaseController implements Initializable {
	/**
	 * 設定ウィンドウに関する設定プロパティファイルを読み込むクラス
	 */
	protected PropertiesUtil configWindowProperties;

	/**
	 * キー入力に関する設定プロパティファイルを読み込むクラス
	 */
	private PropertiesUtil keyConfigProperties;

	/**
	 * 設定ウィンドウのルートペイン
	 */
	public Pane configPane;

	/**
	 * キー設定の見出しを表示するLabel
	 */
	public Label keyConfigHeadlineLabel;

	/**
	 * キー設定見出しと設定項目の間にある区切り線
	 */
	public Separator keyConfigSeparator;

	/**
	 * キー設定「進撃・撤退切替」項目のLabel
	 */
	public Label keyConfigAttackLabel;

	/**
	 * キー設定「進撃・撤退切替」項目のTextFields
	 */
	public TextField keyConfigAttackTextField;

	/**
	 * キー設定「進撃・撤退切替」項目の設定値
	 */
	protected int keyConfigAttackValue;

	/**
	 * キー設定「夜戦突入・追撃せず切替」項目のLabel
	 */
	public Label keyConfigNightBattleLabel;

	/**
	 * キー設定「夜戦突入・追撃せず切替」項目のTextField
	 */
	public TextField keyConfigNightBattleTextField;

	/**
	 * キー設定「夜戦突入・追撃せず切替」項目の設定値
	 */
	protected int keyConfigNightBattleValue;

	/**
	 * キー設定中かどうかのフラグ。
	 */
	protected boolean nowKeyConfiging = false;

	/**
	 * キー設定中の対象項目となるTextField
	 */
	protected TextField textFieldOfConfigTarget;

	/**
	 * キー設定中の対象項目と対応するキー設定プロパティファイル上でのキー名
	 */
	protected String propertiesKeyNameOfConfigTarget;

	/**
	 * 説明テキストのLabel
	 */
	public Label descriptionLabel;

	/**
	 * 完了ボタン
	 */
	public Button completeButton;

	/**
	 * 初期化処理
	 */
	public void initialize(URL location, ResourceBundle resources) {
		// 設定ウィンドウに関する設定プロパティファイルを読み込む
		this.configWindowProperties = new PropertiesUtil("ConfigWindow");

		// デフォルトのままだと、設定ウィンドウが開いた時にTextFieldへフォーカスがいってしまうので外す(クリックしてからキーを押すことでそのキーコードが設定項目に適用されるという形を採るため自動的にフォーカスがいくのは邪魔になる)
		this.keyConfigAttackTextField.setFocusTraversable(false);
		this.keyConfigNightBattleTextField.setFocusTraversable(false);

		// キー入力に関するプロパティファイルを読み込む
		this.keyConfigProperties = new PropertiesUtil("KeyConfig");

		// キー設定をプロパティファイルから取得
		this.keyConfigAttackValue      = Integer.parseInt(this.keyConfigProperties.getProperty("attackChangeKeyCode"));      // 進撃・撤退切替キーの設定値
		this.keyConfigNightBattleValue = Integer.parseInt(this.keyConfigProperties.getProperty("nightBattleChangeKeyCode")); // 夜戦突入・追撃せず切替キーの設定値

		// 各キーのTextField項目にプロパティファイルから読み込んだ設定値(キーコード)をもとにしたキー名を適用
		this.keyConfigAttackTextField.setText(NativeKeyEvent.getKeyText(this.keyConfigAttackValue));           // 進撃・撤退切替キー
		this.keyConfigNightBattleTextField.setText(NativeKeyEvent.getKeyText(this.keyConfigNightBattleValue)); // 夜戦突入・追撃せず切替キー

		// 説明ラベルにデフォルトの文言をセット
		this.descriptionLabel.setText(this.configWindowProperties.getProperty("descriptionLabelWordingDefault"));
		this.descriptionLabel.setStyle(this.configWindowProperties.getProperty("descriptionLabelWordingDefaultColor"));

		// thisを保持(ChangeListenerの中でこの時点でのthisを使うため)
		ConfigPaneController configPaneController = this;

		// 進撃・撤退切替のTextFieldのフォーカスが変わる際のイベントを登録
		this.keyConfigAttackTextField.focusedProperty().addListener(new ChangeListener<Boolean>(){
			// フォーカスが変わる際の処理
			public void changed(ObservableValue<? extends Boolean> focused, Boolean oldFocused, Boolean newFocused) {
				configPaneController.textFieldFocusChange(configPaneController.keyConfigAttackTextField, oldFocused);
			}
		});

		// 夜戦突入・追撃せず切替のTextFieldのフォーカスが変わる際のイベントを登録
		this.keyConfigNightBattleTextField.focusedProperty().addListener(new ChangeListener<Boolean>(){
			// フォーカスが変わる際の処理
			public void changed(ObservableValue<? extends Boolean> focused, Boolean oldFocused, Boolean newFocused) {
				configPaneController.textFieldFocusChange(configPaneController.keyConfigNightBattleTextField, oldFocused);
			}
		});
	}

	/**
	 * 進撃・撤退切替のTextFieldのフォーカスが変わる際に実行されるメソッド
	 * @param textFieldOfConfigTarget 設定対象のテキストフィールド
	 * @param oldFocused フォーカスが変わる前のフォーカスがあるかどうかの値(true=ある、false=ない)
	 */
	public void textFieldFocusChange(TextField textFieldOfConfigTarget, Boolean oldFocused) {
		// フォーカスが ない 状態から ある 状態になるとき、かつ、キー設定中でなければ
		if (oldFocused == false && this.nowKeyConfiging == false) {
			// キー設定中であるとフラグを立てる
			this.nowKeyConfiging = true;

			// キー設定中の対象項目となるTextFieldをプロパティに保持する
			this.textFieldOfConfigTarget = textFieldOfConfigTarget;

			// キー設定中の項目に対応するキー設定プロパティファイル上でのキー名を保持する
			if (textFieldOfConfigTarget.getId().equals("keyConfigAttackTextField")) {
				this.propertiesKeyNameOfConfigTarget = "attackChangeKeyCode";
			} else if (textFieldOfConfigTarget.getId().equals("keyConfigNightBattleTextField")) {
				this.propertiesKeyNameOfConfigTarget = "nightBattleChangeKeyCode";
			}

			// 割り当てたいキーをタップしろという旨を文字色:黒で表示する
			this.descriptionLabel.setText(this.configWindowProperties.getProperty("descriptionLabelWordingKeyConfiging"));
			this.descriptionLabel.setStyle(this.configWindowProperties.getProperty("descriptionLabelWordingKeyConfigingColor"));

			// 完了ボタンを押せないようにする
			this.completeButton.setDisable(true);

			// JNativeHookのキー入力の検知モードをキー設定のための検知モードに変える
			this.window.getJNativeHookKeyListener().setDetectInputMode(JNativeHookKeyListener.DETECT_INPUT_MODE_KEYCONFIG);
		}

		// フォーカスが ある 状態から ない 状態になるとき、かつ、キー設定中なら
		if (oldFocused == true && this.nowKeyConfiging == true) {
			// キー設定が完了するまで設定対象項目のTextFieldからフォーカスを外さないようにする
			textFieldOfConfigTarget.requestFocus();
		}
	}

	/**
	 * 完了ボタンが押された際の処理
	 */
	public void completeButtonOnAction() {
		// キー設定中でなければ
		if (this.nowKeyConfiging == false) {
			// Stage.close()でウィンドウを閉じる場合、onCloseReqestイベントが発生しないので、ここで明示的に発生させる
			this.window.getStage().fireEvent(new WindowEvent(this.window.getStage(), WindowEvent.WINDOW_CLOSE_REQUEST));

			// 設定ウィンドウを閉じる
			this.window.getStage().close();
		} else {
			// キー設定中なら、フォーカスを完了ボタンに移す
			this.completeButton.requestFocus();
		}
	}

	// 以下、ゲッターとセッター---------------------------------------------------------------------------------------//

	/**
	 * configWindowPropertiesのゲッター
	 * @return 設定ウィンドウに関する設定プロパティファイルを読み込んだクラスインスタンスを返す
	 */
	public PropertiesUtil getConfigWindowProperties() {
		return this.configWindowProperties;
	}

	/**
	 * nowKeyConfigingのセッター
	 * @param nowKeyConfiging キー設定中かどうかのフラグ(true=キー設定中、false=キー設定中ではない)
	 */
	public void setNowKeyConfiging(boolean nowKeyConfiging) {
		this.nowKeyConfiging = nowKeyConfiging;
	}

	/**
	 * textFieldOfConfigTargetのゲッター
	 * @return キー設定中の対象項目となるTextField
	 */
	public TextField getTextFieldOfConfigTarget() {
		return this.textFieldOfConfigTarget;
	}

	/**
	 * textFieldOfConfigTargetのセッター
	 * @param textFieldOfConfigTarget キー設定中の対象項目となるTextField
	 */
	public void setTextFieldOfConfigTarget(TextField textFieldOfConfigTarget) {
		this.textFieldOfConfigTarget = textFieldOfConfigTarget;
	}

	/**
	 * propertiesKeyNameOfConfigTargetのゲッター
	 * @return キー設定中の対象項目に対応するキー設定プロパティファイル上でのキー名
	 */
	public String getPropertiesKeyNameOfConfigTarget() {
		return this.propertiesKeyNameOfConfigTarget;
	}

	/**
	 * propertiesKeyNameOfConfigTargetのセッター
	 * @param propertiesKeyNameOfConfigTarget キー設定中の対象項目に対応するキー設定プロパティファイル上でのキー名
	 */
	public void setPropertiesKeyNameOfConfigTarget(String propertiesKeyNameOfConfigTarget) {
		this.propertiesKeyNameOfConfigTarget = propertiesKeyNameOfConfigTarget;
	}
}
