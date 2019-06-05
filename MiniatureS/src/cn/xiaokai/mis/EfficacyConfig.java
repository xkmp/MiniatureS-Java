package cn.xiaokai.mis;

import java.io.File;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

/**
 * @author Winfxk
 */
public class EfficacyConfig {
	public static final String[] Dir = { MiniatureS.CustomConfigPath, MiniatureS.MenuConfigPath,
			MiniatureS.MyShopConfigPath, MiniatureS.PlayerConfigPath, MiniatureS.ShopConfigPath };

	/**
	 * 效验系统配置
	 */
	public static void startPY() {
		File dirFile;
		for (String dir : Dir) {
			dirFile = new File(MiniatureS.mis.getDataFolder() + dir);
			if (!dirFile.exists())
				dirFile.mkdirs();
			if (dirFile.list().length > 0)
				for (String fileName : dirFile.list()) {
					File file = new File(dirFile, fileName);
					try {
						new Config(file, Config.YAML);
						MiniatureS.mis.getLogger().info(MiniatureS.Title + TextFormat.YELLOW + "文件" + TextFormat.GREEN
								+ dirFile.getName() + "/" + fileName + TextFormat.YELLOW + "效验成功！");
					} catch (Exception e) {
						MiniatureS.mis.getLogger()
								.info(MiniatureS.Title + TextFormat.RED + "文件" + TextFormat.GREEN + dirFile.getName()
										+ "/" + fileName + TextFormat.RED + "效验不通过！请检查！" + TextFormat.WHITE
										+ e.getMessage());
					}
				}
		}
	}
}
