package input;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import controllers.MainPaneController;
import utils.WindowManager;

/**
 * JNativeHookのキー入力のイベントリスナ
 * @author 皇翔(Shou Sumeragi)
 */
public class JNativeHookKeyListener implements NativeKeyListener {
	/**
	 * コンストラクタで渡されたメインウィンドウの管理クラスを保持するプロパティ。
	 * このクラスからメインウィンドウとその配下を操作するために使用
	 */
	protected WindowManager<MainPaneController> mainWindow;

	/**
	 * コンストラクタ。
	 * @param controllers コントローラを格納したマップ(コントローラ外からPaneの各コントロールを操作するために使う)
	 */
	public JNativeHookKeyListener(WindowManager<MainPaneController> mainWindow) {
		// 渡されたウィンドウを保持する
		this.mainWindow = mainWindow;
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
		MainPaneController mainPaneController = (MainPaneController)this.mainWindow.getController();

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
}
