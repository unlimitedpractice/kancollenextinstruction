package input;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import controllers.ConfigPaneController;
import controllers.MainPaneController;
import javafx.application.Platform;
import utils.PropertiesUtil;
import utils.WindowManager;

/**
 * JNativeHookのキー入力のイベントリスナ
 * @author 皇翔(Shou Sumeragi)
 */
public class JNativeHookKeyListener implements NativeKeyListener {
	/**
	 * クラス内定数 キー入力検知のモード「入力検知をしないモード」。
	 */
	public static final int DETECT_INPUT_MODE_STOP = 0;

	/**
	 * クラス内定数 キー入力検知のモード「画像切替のための検知モード」。
	 */
	public static final int DETECT_INPUT_MODE_ACTIVE = 1;

	/**
	 * クラス内定数 キー入力の検知モード「キー設定のための検知モード」
	 */
	public static final int DETECT_INPUT_MODE_KEYCONFIG = 2;

	/**
	 * キー入力に関するプロパティファイルを読み込むクラス
	 */
	protected PropertiesUtil keyConfigProperties;

	/**
	 * コンストラクタで渡されたメインウィンドウの管理クラスを保持するプロパティ。
	 * このクラスからメインウィンドウとその配下を操作するために使用
	 */
	protected WindowManager<MainPaneController> mainWindow;

	/**
	 * コンフィグウィンドウの管理クラスを保持するプロパティ。
	 * (コンフィグウィンドウが開いた時に渡される)
	 */
	protected WindowManager<ConfigPaneController> configWindow;

	/**
	 * キー入力の検知モード。
	 * ※設定される値とその意味については クラス内定数 DETECT_INPUT_MODE_...を参照。
	 */
	protected int detectInputMode = JNativeHookKeyListener.DETECT_INPUT_MODE_ACTIVE;

	/**
	 * コンストラクタ。
	 * @param mainWindow メインウィンドウのウィンドウ管理クラス
	 */
	public JNativeHookKeyListener(WindowManager<MainPaneController> mainWindow) {
		// キー入力に関するプロパティファイルを読み込む
		this.keyConfigProperties = new PropertiesUtil("KeyConfig");

		// 渡されたウィンドウを保持する
		this.mainWindow = mainWindow;
	}

	/**
	 * キーが押された際の処理
	 * @param event イベント発生時の各種情報(どのキーが押されたか等)
	 */
	public void nativeKeyPressed(NativeKeyEvent event) {
		// 現状、特に処理なし
	}

	/**
	 * キーが離された際の処理
	 * @param event イベント発生時の各種情報(どのキーが離されたか等)
	 */
	public void nativeKeyReleased(NativeKeyEvent event) {
		// キー入力の検知モードに対応する処理を実行
		switch (this.detectInputMode) {
		// 画像切替のための検知モードなら
		case JNativeHookKeyListener.DETECT_INPUT_MODE_ACTIVE:
			this.modeActiveProcessing(event);
			break;

		// キー設定のための検知モードなら
		case JNativeHookKeyListener.DETECT_INPUT_MODE_KEYCONFIG:
			this.modeKeyconfigProcessing(event);
			break;
		}
	}

	/**
	 * キーがタイプされた際の処理
	 * @param event イベント発生時の各種情報(どのキーがタイプされたか等)
	 */
	public void nativeKeyTyped(NativeKeyEvent event) {
		// 現状、特に処理なし
	}

	/**
	 * キー入力の検知モードが画像切替のための検知モードになっている場合の処理
	 * @param event キー入力イベント発生時の各種情報
	 */
	protected void modeActiveProcessing(NativeKeyEvent event) {
		// メインウィンドウのルートペインのコントローラ取得
		MainPaneController mainPaneController = (MainPaneController)this.mainWindow.getController();

		// 進撃・撤退切替キーが離されたら
		if (event.getKeyCode() == Integer.parseInt(this.keyConfigProperties.getProperty("attackChangeKeyCode"))) {
			// メインペインにある夜戦突入・追撃せず画像を切り替える
			mainPaneController.toggleAttackImageView();
		} else if (event.getKeyCode() == Integer.parseInt(this.keyConfigProperties.getProperty("nightBattleChangeKeyCode"))) {
			// 夜戦突入・追撃せず切替キーが離されたら

			// メインペインにある夜戦突入・追撃せず画像を切り替える
			mainPaneController.toggleNightBattleImageView();
		}
	}

	/**
	 * キー入力の検知モードがキー設定のための検知モードになっている場合の処理
	 * @param event キー入力イベント発生時の各種情報
	 */
	protected void modeKeyconfigProcessing(NativeKeyEvent event) {
		// 有効なキーが入力されたら
		if (event.getKeyCode() != 0) {
			// 入力されたキー設定を反映して、プロパティファイルを保存
			this.keyConfigProperties.setProperty(this.configWindow.getController().getPropertiesKeyNameOfConfigTarget(), Integer.toString(event.getKeyCode()));
			this.keyConfigProperties.store("Key config.");

			// キー設定完了なので、設定中のフラグを下げる
			this.configWindow.getController().setNowKeyConfiging(false);

			// JNativeHookのキー入力検知モードを検知しないモードに変える
			this.detectInputMode = JNativeHookKeyListener.DETECT_INPUT_MODE_STOP;


			// 外部スレッドからのUI操作となるものは、Platform.runLaterを介して実行する
			JNativeHookKeyListener jNativeHookKeyListener = this;
			Platform.runLater(() -> {
				// 入力されたキーのキー名を設定対象のTextFieldに反映する
				jNativeHookKeyListener.configWindow.getController().getTextFieldOfConfigTarget().setText(NativeKeyEvent.getKeyText(event.getKeyCode()));

				// 設定対象のTextFiledを保持しているプロパティをクリア
				jNativeHookKeyListener.configWindow.getController().setTextFieldOfConfigTarget(null);

				// 設定対象のプロパティファイル上でのキー名を保持しているプロパティをクリア
				jNativeHookKeyListener.configWindow.getController().setPropertiesKeyNameOfConfigTarget(null);

				// 完了ボタンを押せるようにする
				jNativeHookKeyListener.configWindow.getController().completeButton.setDisable(false);

				// 設定が完了した旨を文字色:青で表示する
				jNativeHookKeyListener.configWindow.getController().descriptionLabel.setText(jNativeHookKeyListener.configWindow.getController().getConfigWindowProperties().getProperty("descriptionLabelWordingComplete"));
				jNativeHookKeyListener.configWindow.getController().descriptionLabel.setStyle("-fx-text-fill: #0000ff;");

				// キー設定対象項目のTextFieldからフォーカスを外す(正確には、TextFieldから外すことはできないので、完了ボタンにフォーカスを移す)
				jNativeHookKeyListener.configWindow.getController().completeButton.requestFocus();
			});
		} else {
			// 無効なキーが入力されたら

			// 外部スレッドからのUI操作となるものは、Platform.runLaterを介して実行する
			JNativeHookKeyListener jNativeHookKeyListener = this;
			Platform.runLater(() -> {
				// そのキーが使えない旨を文字色:赤で表示する
				jNativeHookKeyListener.configWindow.getController().descriptionLabel.setText(jNativeHookKeyListener.configWindow.getController().getConfigWindowProperties().getProperty("descriptionLabelWordingInvalidKey"));
				jNativeHookKeyListener.configWindow.getController().descriptionLabel.setStyle("-fx-text-fill: #ff0000;");
			});
		}
	}

	// 以下、ゲッターとセッター---------------------------------------------------------------------------------------//

	/**
	 * detectInputModeのゲッター
	 * @return キー入力の検知モード(値の意味についてはクラス内定数 DETECT_INPUT_MODE_...を参照)
	 */
	public int getDetectInputMode() {
		return this.detectInputMode;
	}

	/**
	 * detectInputModeのセッター
	 * @param detectInputMode キー入力の検知モード(値の意味についてはクラス内定数 DETECT_INPUT_MODE_...を参照)
	 */
	public void setDetectInputMode(int detectInputMode) {
		this.detectInputMode = detectInputMode;
	}

	/**
	 * confingWindowのゲッター
	 * @return 設定ウィンドウのウィンドウ管理クラスを返す
	 */
	public WindowManager<ConfigPaneController> getConfigWindow() {
		return this.configWindow;
	}

	/**
	 * configWindowのセッター
	 * @param configWindow 設定ウィンドウのウィンドウ管理クラス
	 */
	public void setConfigWindow(WindowManager<ConfigPaneController> configWindow) {
		this.configWindow = configWindow;
	}
}
