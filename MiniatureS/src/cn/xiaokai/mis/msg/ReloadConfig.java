package cn.xiaokai.mis.msg;

import java.io.File;
import java.io.IOException;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Utils;
import cn.xiaokai.mis.MiniatureS;

/**
 * @author Winfxk
 */
public class ReloadConfig {
	public static final String V = "2019-6-9";
	public static final String[] Files = { "Config.yml", "Message.yml" };
	public static final String V_Key = "文件版本";

	/**
	 * 检查资源合法性，是否为适当的版本
	 */
	public static void start() {
		File file;
		MiniatureS mis = MiniatureS.mis;
		for (String name : Files) {
			file = new File(mis.getDataFolder(), name);
			Config config = new Config(file, Config.YAML);
			if (!config.getString(V_Key).equals(V)) {
				file.delete();
				try {
					mis.getServer().getLogger().info(TextFormat.RED + "更新资源：" + TextFormat.GREEN + name);
					Utils.writeFile(file, mis.getClass().getResourceAsStream("/resources/" + name));
				} catch (IOException e) {
					mis.getServer().getLogger().info(TextFormat.RED + "资源：" + TextFormat.GREEN + name + TextFormat.RED
							+ "加载错误！\n" + TextFormat.WHITE + "错误详情：" + e.getMessage());
					mis.getServer().getPluginManager().disablePlugin(mis);
				}
			}
		}
	}
}
