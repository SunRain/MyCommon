package libs.src;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GeneratePropertiesFile {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		generatePropertiesFile();
	}

	/**
	 * 生成 Eclipse ADT 中libs 目录下的 代码关联 properties 文件。 把 root 变量替换为项目中的 libs
	 * 目录地址即可。 另外如果你的libs 的 源代码不在同级的 libs-src 目录下，则需要自行修改 src_pro 变量的值、
	 * 
	 * @throws IOException
	 */
	private static void generatePropertiesFile() throws IOException {
		String root = "/home/gxl/ggg/home/MyDemos/library/MyAndroidCommon/libs/";
		String src_pro = "src=../libs-src/";
		File dir = new File(root);
		String[] files = dir.list();
		List<String> list = Arrays.asList(files);
		for (String string : list) {
			System.out.println("file: " + string);
			if (string.endsWith("sources.jar")) {
				continue;
			}
			if (string.endsWith(".jar")) {
				String sorceName = string.replace(".jar", "-sources.jar");
				String proName = string + ".properties";
				File proFile = new File(root + proName);
				if (proFile.exists()) {
					continue;
				}
				proFile.createNewFile();
				FileWriter fw = new FileWriter(proFile);
				fw.write(src_pro + sorceName);
				fw.flush();
				fw.close();
			}
		}
	}
}
