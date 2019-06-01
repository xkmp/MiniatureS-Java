package cn.xiaokai.mis.msg;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Utils;
import cn.xiaokai.mis.MiniatureS;

/**
 * @author Winfxk
 */
public class ReloadConfig {
	public static final int V = 1;
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
			HashMap<String, Object> map = (config.getAll() instanceof Map) ? (HashMap<String, Object>) config.getAll()
					: new HashMap<>();
			if (config.getInt(V_Key) != V) {
				file.delete();
				try {
					if (!file.exists()) {
						mis.getServer().getLogger().info(TextFormat.RED + "更新资源：" + TextFormat.GREEN + name);
						Utils.writeFile(file, mis.getClass().getResourceAsStream("/resources/" + name));
						file = new File(mis.getDataFolder(), name);
						config = new Config(file, Config.YAML);
						for (String key : map.keySet())
							if (!key.equals(V_Key))
								config.set(key, map.get(key));
						config.save();
					}
				} catch (IOException e) {
					mis.getServer().getLogger().info(TextFormat.RED + "资源：" + TextFormat.GREEN + name + TextFormat.RED
							+ "加载错误！\n" + TextFormat.WHITE + "错误详情：" + e.getMessage());
					mis.getServer().getPluginManager().disablePlugin(mis);
				}
			}
		}
	}
}
