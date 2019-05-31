package cn.xiaokai.mis.myshop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.MiniatureS;
/**
 * @author Winfxk
 */
public class CPlayer {
	/**
	 * 默认的玩家配置文件都有那些东西
	 * 
	 * @return
	 */
	public static LinkedHashMap<String, Object> getConfig() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
		map.put("Items", arrayList);
		map.put("Money", 0);
		map.put("Msg", new ArrayList<String>());
		return map;
	}

	/**
	 * 获取玩家配置文件
	 * 
	 * @param Player 要获取配置文件的玩家名
	 * @return
	 */
	public static Config getPlayerConfig(String Player) {
		return new Config(new File(MiniatureS.mis.getDataFolder() + MiniatureS.PlayerConfigPath, Player + ".yml"),
				Config.YAML);
	}

	/**
	 * 获取玩家配置文件对象
	 * 
	 * @param Player 玩家对象
	 * @return
	 */
	public static Config getPlayerConfig(Player Player) {
		return getPlayerConfig(Player.getName());
	}

	/**
	 * 判断玩家存不存在配置文件
	 * 
	 * @param Player 玩家名字
	 * @return
	 */
	public static boolean isPlayerConfig(String Player) {
		File file = new File(MiniatureS.mis.getDataFolder() + MiniatureS.PlayerConfigPath, Player + ".yml");
		return file.exists();
	}

	/**
	 * 判断玩家存不存在配置文件
	 * 
	 * @param Player 玩家对象
	 * @return
	 */
	public static boolean isPlayerConfig(Player Player) {
		return isPlayerConfig(Player.getName());
	}
}
