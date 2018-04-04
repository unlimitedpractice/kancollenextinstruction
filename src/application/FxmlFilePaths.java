package application;

/**
 * 各FXMLファイルのパスを設定した定数を定義するためのENUMクラス
 * @author unlimitedpractice
 */
public enum FxmlFilePaths {
	/**
	 * メインペインのFSXMLファイルパス
	 */
	MainPane("/resource/fxmls/main/MainPane.fxml");

	/**
	 * フィールドの定義。
	 * 列挙子に付与する値を保持するためのフィールド
	 */
	private String value;

	/**
	 * コンストラクタ定義。
	 * 列挙子に値を付与する場合に必要なため定義
	 * @param value 列挙子に付与する値
	 */
	private FxmlFilePaths(String value) {
		// 列挙子に対応する値をフィールドに取得
		this.value = value;
	}

	/**
	 * 列挙子に付与された値を返す
	 * @return
	 */
	public String getValue() {
		return this.value;
	}
}
