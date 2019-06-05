package cn.xiaokai.mis.msg;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Utils;
import cn.xiaokai.mis.MiniatureS;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class ReloadConfig {
	public static final String V = "2019-6-5";
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
			if (config.getString(V_Key) != V) {
				file.delete();
				try {
					if (!file.exists()) {
						mis.getServer().getLogger().info(TextFormat.RED + "更新资源：" + TextFormat.GREEN + name);
						Utils.writeFile(file, mis.getClass().getResourceAsStream("/resources/" + name));
						file = new File(mis.getDataFolder(), name);
						config = new Config(file, Config.YAML);
						config.set(V_Key, V);
						config.save();
						for (String key : map.keySet()) {
							if (!key.equals(V_Key) && !(map.get(key) instanceof Map) && !(map.get(key) instanceof List)
									&& config.get(key) != null
									&& config.get(key).getClass().equals(map.get(key).getClass())
									&& !map.get(key).equals(config.get(key))) {
								config.set(key, map.get(key));
								mis.getServer().getLogger().info(TextFormat.RED + "正在还原数据：" + key);
							}
							if ((map.get(key) instanceof Map) && !map.get(key).equals(config.get(key))
									&& config.get(key) != null) {
								mis.getServer().getLogger().info(TextFormat.RED + "正在还原数据列：" + key);
								config.set(key, reloadMap(config.getSection(key), map));
							}
						}
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

	/**
	 * 还原配置
	 * 
	 * @param config_map 新的配置数据
	 * @param l_map      原来的配置数据
	 * @return
	 */
	public static Map<String, Object> reloadMap(Map<String, Object> config_map, Map<String, Object> l_map) {
		for (String key : l_map.keySet()) {
			if (!(config_map.get(key) instanceof Map) && l_map.get(key) != null) {
				if (config_map.get(key) != null && config_map.get(key).getClass().equals(l_map.get(key).getClass())
						&& !l_map.get(key).equals(config_map.get(key))) {
					config_map.put(key, l_map.get(key));
					MiniatureS.mis.getServer().getLogger().info(TextFormat.RED + "正在还原数据：" + key);
				}
			} else if ((l_map.get(key) instanceof Map) && !l_map.get(key).equals(config_map.get(key))) {
				MiniatureS.mis.getServer().getLogger().info(TextFormat.RED + "正在还原数据列：" + key);
				config_map.put(key,
						reloadMap((Map<String, Object>) config_map.get(key), (Map<String, Object>) l_map.get(key)));
			}
		}
		return config_map;
	}
}
