package utils;

/**
 * 各プロパティファイルのパスを設定した定数を定義するためのENUMクラス
 * @author 皇翔(Shou Sumeragi)
 */
public enum PropertiesFilePaths {
	/**
	 * ユーザ定義のメソッドを呼び出すためのダミー
	 */
	Dummy("dummy"),

	/**
	 * 各FXMLファイルのパスを記述したプロパティファイルのパス
	 *
	 */
	FxmlFilePaths("propertiesfiles/FxmlFilePaths.properties"),

	/**
	 * メインウィンドウの各種設定値等を記述したプロパティファイルのパス
	 */
	MainWindow("propertiesfiles/MainWindow.properties"),

	/**
	 * コンフィグウィンドウの各種設定値等を記述したプロパティファイルのパス
	 */
	ConfigWindow("propertiesfiles/ConfigWindow.properties"),

	/**
	 * 画像ファイルパスを設定するプロパティファイルのパス
	 */
	ImagePaths("propertiesfiles/ImagePaths.properties"),

	/**
	 * キー入力に関する設定プロパティファイルのパス
	 */
	KeyConfig("propertiesfiles/KeyConfig.properties");


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
	private PropertiesFilePaths(String value) {
		// 列挙子に対応する値をフィールドに取得
		this.value = value;
	}

	/**
	 * EnumeratorNameで指定された列挙子名の列挙子に付与された値を返す
	 * @param EnumeratorName 値を取り出したい列挙子の列挙子名
	 * @return String EnumeratorNameで指定された列挙子名の列挙子に付与された値
	 */
	public String getValueForEnumeratorName(String EnumeratorName) {
		for (PropertiesFilePaths pFP: values()) {
			// 指定の列挙子名と一致する列挙子なら
			if (pFP.name() == EnumeratorName) {
				return pFP.getValue();
			}
		}


		// 指定された列挙子名の列挙子が見つからなければnullを返す
		 return null;
	}

	// 以下、ゲッターとセッター---------------------------------------------------------------------------------------//

	/**
	 * 列挙子に付与された値を返す
	 * @return String 列挙子に付与された値
	 */
	public String getValue() {
		return this.value;
	}
}
