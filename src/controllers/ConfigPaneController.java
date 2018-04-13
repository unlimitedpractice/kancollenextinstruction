package controllers;

import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import org.jnativehook.keyboard.NativeKeyEvent;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import utils.PropertiesUtil;

/**
 * 設定ウィンドウのルートペインのコントローラクラス
 * @author 皇翔(Shou Sumeragi)
 */
public class ConfigPaneController extends BaseController implements Initializable {
	/**
	 * キー入力に関する設定プロパティファイルを読み込むクラス
	 */
	Properties keyConfigProperties;

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
	 * 初期化処理
	 */
	public void initialize(URL location, ResourceBundle resources) {
		// デフォルトのままだと、設定ウィンドウが開いた時にTextFieldへフォーカスがいってしまうので外す(クリックしてからキーを押すことでそのキーコードが設定項目に適用されるという形を採るため自動的にフォーカスがいくのは邪魔になる)
		this.keyConfigAttackTextField.setFocusTraversable(false);
		this.keyConfigNightBattleTextField.setFocusTraversable(false);

		// キー入力に関するプロパティファイルを読み込む
		this.keyConfigProperties = PropertiesUtil.loadPropertiesFile("KeyConfig");

		// キー設定をプロパティファイルから取得
		this.keyConfigAttackValue      = Integer.parseInt(this.keyConfigProperties.getProperty("attackChangeKeyCode"));      // 進撃・撤退切替キーの設定値
		this.keyConfigNightBattleValue = Integer.parseInt(this.keyConfigProperties.getProperty("nightBattleChangeKeyCode")); // 夜戦突入・追撃せず切替キーの設定値

		// 各キーのTextField項目にプロパティファイルから読み込んだ設定値(キーコード)をもとにしたキー名を適用
		this.keyConfigAttackTextField.setText(NativeKeyEvent.getKeyText(this.keyConfigAttackValue));           // 進撃・撤退切替キー
		this.keyConfigNightBattleTextField.setText(NativeKeyEvent.getKeyText(this.keyConfigNightBattleValue)); // 夜戦突入・追撃せず切替キー
	}
}
