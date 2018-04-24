package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Properties;

/**
 * プロパティファイルの扱いを容易にするためのクラス
 * @author 皇翔(Shou Sumeragi)
 */
public class PropertiesUtil {
	/**
	 * プロパティファイルを扱う本体となるクラス
	 */
	protected Properties properties;

	/**
	 * 扱うプロパティファイルのutils.PropertiesFilePaths上でファイルパスが定義された列挙子名(列挙子はutils.PropertiesFilePathsに定義されている。)
	 */
	protected String enumeratorName;

	/**
	 * コンストラクタ。
	 * EnumeratorNameで指定された列挙子名の列挙子に付与された値(ファイルパス)でプロパティファイルを同クラスのpropertiesプロパティに読み込む。
	 * ※列挙子はutils.PropertiesFilePathsに定義されている。
	 * ※プロパティファイルの文字コードはUTF-8であること前提
	 * @param EnumeratorName 列挙子名。読み込みたいプロパティファイルのパスが付与された列挙子の列挙子名を指定する
	 * @see utils.PropertiesFilePaths
	 */
	public PropertiesUtil(String EnumeratorName) {
		try {
			// 列挙子名を保持
			this.enumeratorName = EnumeratorName;

			// プロパティファイルのInputStreamを取得
			InputStream propertiesFileInputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PropertiesFilePaths.Dummy.getValueForEnumeratorName(EnumeratorName));

			// UTF-8指定したリーダーを生成
			InputStreamReader inputStreamReaderUTF8 = new InputStreamReader(propertiesFileInputStream, "UTF-8");
            BufferedReader    readerUTF8            = new BufferedReader(inputStreamReaderUTF8);

			// プロパティファイルのUTF-8指定したリーダーを基にプロパティファイルを読み込む
			this.properties = new Properties();
			this.properties.load(readerUTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * プロパティファイルからkeyで指定されたプロパティの値を返す
	 * @param key プロパティのキー
	 * @return keyで指定されたプロパティの値
	 */
	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}

	/**
	 * keyで指定されたプロパティの値をvalueでセットする
	 * @param key セットしたいプロパティのキー
	 * @param value セットしたいプロパティの値
	 */
	public void setProperty(String key, String value) {
		this.properties.setProperty(key, value);
	}

	/**
	 * プロパティファイルを保存(上書き)する。
	 * ※書き込むプロパティファイルには先頭の2行を除いて、コメントが書かれていないことを前提とする。
	 * @param comment プロパティファイルの先頭に記載されるコメント
	 */
	public void store(String comment) {
		try {
			File propertiesFile;
			try {
				// まず、toURIでパス指定してFileを開く
				propertiesFile = new File(PropertiesUtil.class.getClassLoader().getResource(PropertiesFilePaths.Dummy.getValueForEnumeratorName(this.enumeratorName)).toURI());
			} catch(URISyntaxException e) {
				// toURIで開けない場合は、getPathでパス指定してFileを開く
				propertiesFile = new File(PropertiesUtil.class.getClassLoader().getResource(PropertiesFilePaths.Dummy.getValueForEnumeratorName(this.enumeratorName)).getPath());
			}

			this.properties.store(new FileOutputStream(propertiesFile), comment);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 以下、ゲッターとセッター---------------------------------------------------------------------------------------//
}
