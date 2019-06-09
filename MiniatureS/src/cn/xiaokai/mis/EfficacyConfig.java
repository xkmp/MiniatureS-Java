package cn.xiaokai.mis;

import java.io.File;

import cn.nukkit.utils.Config;

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
						MiniatureS.mis.getLogger().info("§e文件§6" + dirFile.getName() + "/" + fileName + "§e效验成功！");
					} catch (Exception e) {
						MiniatureS.mis.getLogger().info(
								"§4文件§6" + dirFile.getName() + "§4/§6" + fileName + "§4效验不通过！请检查！§f" + e.getMessage());
					}
				}
		}
	}
}
