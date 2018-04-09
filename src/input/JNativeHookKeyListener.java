package input;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import controllers.MainPaneController;
import utils.PropertiesUtil;

/**
 * JNativeHookのキー入力のイベントリスナ
 * @author unlimitedpractice
 */
public class JNativeHookKeyListener implements NativeKeyListener {
	/**
	 * コンストラクタで渡されたコントローラマップを保持するプロパティ。
	 * このクラスからペインを操作するために使用
	 */
	private Map<String, Object> controllers;

	/**
	 * コントローラマップからコントローラを取得する際の名前設定プロパティファイルを保持するプロパティ
	 */
	private Properties controllerNamesProperties;

	/**
	 * コンストラクタ。
	 * @param controllers コントローラを格納したマップ(コントローラ外からPaneの各コントロールを操作するために使う)
	 */
	public JNativeHookKeyListener(Map<String, Object> controllers) {
		// 渡されたコントローラマップを保持する
		this.controllers = new HashMap<String, Object>(); // コントローラマップを初期化
		this.setControllers(controllers);

		// コントローラマップからコントローラを取得する際の名前設定プロパティファイルを読み込む
		this.controllerNamesProperties = PropertiesUtil.loadPropertiesFile("ControllerNames");
	}

	/**
	 * キーが押された際の処理
	 * @param event イベント発生時の各種情報(どのキーが押されたか等)
	 */
	public void nativeKeyPressed(NativeKeyEvent event) {
//		System.out.println(event.paramString());
		// 現状、特に処理なし
	}

	/**
	 * キーが離された際の処理
	 * @param event イベント発生時の各種情報(どのキーが離されたか等)
	 */
	public void nativeKeyReleased(NativeKeyEvent event) {
//		System.out.println(event.paramString());

		// メインペインのコントローラ取得
		MainPaneController mainPaneController = (MainPaneController)this.getControllers().get(this.controllerNamesProperties.getProperty("MainPaneController"));

		// 離されたキーに対応する処理
		switch (event.getKeyCode()) {
			// \(バックスラッシュ)
			case NativeKeyEvent.VC_BACK_SLASH:
				// メインペインにある夜戦突入・追撃せず画像を切り替える
				mainPaneController.toggleNightBattleImageView();
				break;
			// ^
			case NativeKeyEvent.VC_QUOTE:
				// メインペインにある進撃・撤退画像を切り替える
				mainPaneController.toggleAttackImageView();
				break;
		}
	}

	/**
	 * キーがタイプされた際の処理
	 * @param event イベント発生時の各種情報(どのキーがタイプされたか等)
	 */
	public void nativeKeyTyped(NativeKeyEvent event) {
//		System.out.println(event.paramString());
		// 現状、特に処理なし
	}

	/**
	 * this.controllers のゲッター
	 * @return コントローラを格納したマップを返す
	 */
	public Map<String, Object> getControllers() {
		return this.controllers;
	}

	/**
	 * this.controllers のセッター
	 * @param controllers セットするコントローラマップ
	 */
	public void setControllers(Map<String, Object> controllers) {
		this.controllers = controllers;
	}
}
