package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * プロパティファイルの扱いを容易にするためのクラス
 * @author 皇翔(Shou Sumeragi)
 */
public class PropertiesUtil {
	/**
	 * EnumeratorNameで指定された列挙子名の列挙子に付与された値(ファイルパス)でプロパティファイルを読み込む。
	 * ※列挙子はutils.PropertiesFilePathsに定義されている。
	 * ※プロパティファイルの文字コードはUTF-8であること前提
	 * @param EnumeratorName 列挙子名。読み込みたいプロパティファイルのパスが付与された列挙子の列挙子名を指定する
	 * @return Properties プロパティファイルの読み込みが完了したPropertiesオブジェクト
	 * @see utils.PropertiesFilePaths
	 */
	public static Properties loadPropertiesFile(String EnumeratorName) {
		try {
			// プロパティファイルのInputStreamを取得
			InputStream propertiesFileInputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(PropertiesFilePaths.Dummy.getValueForEnumeratorName(EnumeratorName));

			// UTF-8指定したリーダーを生成
			InputStreamReader inputStreamReaderUTF8 = new InputStreamReader(propertiesFileInputStream, "UTF-8");
            BufferedReader    readerUTF8            = new BufferedReader(inputStreamReaderUTF8);

			// プロパティファイルのUTF-8指定したリーダーを基にプロパティファイルを読み込む
			Properties properties = new Properties();
			properties.load(readerUTF8);

			// プロパティファイルの読み込みが完了したPropertiesオブジェクトを返す
			return properties;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 処理が失敗した場合nullを返す
		return null;
	}
}
