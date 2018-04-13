package input;

import java.util.Properties;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import controllers.MainPaneController;
import utils.PropertiesUtil;
import utils.WindowManager;

/**
 * JNativeHookのキー入力のイベントリスナ
 * @author 皇翔(Shou Sumeragi)
 */
public class JNativeHookKeyListener implements NativeKeyListener {
	/**
	 * キー入力に関するプロパティファイルを読み込むクラス
	 */
	protected Properties keyConfigProperties;

	/**
	 * コンストラクタで渡されたメインウィンドウの管理クラスを保持するプロパティ。
	 * このクラスからメインウィンドウとその配下を操作するために使用
	 */
	protected WindowManager<MainPaneController> mainWindow;

	/**
	 * キー入力を検知するかどうかのフラグ。
	 * true=入力を検知する、false=入力を検知しない
	 * 設定ウィンドウ等のモーダルウィンドウを開いてもJNativeHookのキー入力イベントは発生してしまうため、
	 * 一時的に入力に対する処理をしたくない時に使用。
	 */
	protected boolean isDetectInput = true;

	/**
	 * コンストラクタ。
	 * @param controllers コントローラを格納したマップ(コントローラ外からPaneの各コントロールを操作するために使う)
	 */
	public JNativeHookKeyListener(WindowManager<MainPaneController> mainWindow) {
		// キー入力に関するプロパティファイルを読み込む
		this.keyConfigProperties = PropertiesUtil.loadPropertiesFile("KeyConfig");

		// 渡されたウィンドウを保持する
		this.mainWindow = mainWindow;
	}

	/**
	 * isDetectInputのゲッター
	 * @return キー入力を検知するかどうかのフラグ(true=入力を検知する、false=入力を検知しない)
	 */
	public boolean getIsDetectInput() {
		return this.isDetectInput;
	}

	/**
	 * isDetectInputのセッター
	 * @param isDetectInput キー入力を検知するかどうかのフラグ(true=入力を検知する、false=入力を検知しない)
	 */
	public void setIsDetectInput(boolean isDetectInput) {
		this.isDetectInput = isDetectInput;
	}

	/**
	 * キーが押された際の処理
	 * @param event イベント発生時の各種情報(どのキーが押されたか等)
	 */
	public void nativeKeyPressed(NativeKeyEvent event) {
		// キー入力検知フラグが立っていれば
		if (this.isDetectInput) {
//			System.out.println(event.paramString());
			// 現状、特に処理なし
		}
	}

	/**
	 * キーが離された際の処理
	 * @param event イベント発生時の各種情報(どのキーが離されたか等)
	 */
	public void nativeKeyReleased(NativeKeyEvent event) {
		// キー入力検知フラグが立っていれば
		if (this.isDetectInput) {
//			System.out.println(event.paramString());

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
	}

	/**
	 * キーがタイプされた際の処理
	 * @param event イベント発生時の各種情報(どのキーがタイプされたか等)
	 */
	public void nativeKeyTyped(NativeKeyEvent event) {
		// キー入力検知フラグが立っていれば
		if (this.isDetectInput) {
//			System.out.println(event.paramString());
			// 現状、特に処理なし
		}
	}
}
