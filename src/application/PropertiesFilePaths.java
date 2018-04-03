package application;

/**
 * 各プロパティファイルのパスを設定した定数を定義するためのENUMクラス
 * @author unlimitedpractice
 */
public enum PropertiesFilePaths {
	/**
	 * メインウィンドウの各種設定値等を記述したプロパティファイルのパス
	 */
	MaiinWindow("application/propertiesfiles/MainWindow.properties"),
	SubWindow("application/propertiesFiles/SubWindow.properties");

	/**
	 * フィールドの定義。
	 * 列挙子に値を付与する場合に必要なため定義
	 */
	private String value;

	/**
	 * コンストラクタ定義。
	 * 列挙子に値を付与する場合に必要なため定義
	 * @param value 列挙子に値を付与するために定義
	 */
	private PropertiesFilePaths(String value) {
		// 特に意味のない処理(何も処理を書かないとエラーになるため)
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
