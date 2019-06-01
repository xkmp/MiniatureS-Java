package cn.xiaokai.mis.event.yousb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.myshop.CPlayer;
import cn.xiaokai.mis.tool.ItemIDSunName;
import me.onebone.economyapi.EconomyAPI;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class Fuck {
	/**
	 * 设置玩家配置文件
	 * 
	 * @param player 玩家对象
	 */
	public void Switch(Player player) {
		Config config = CPlayer.getPlayerConfig(player);
		HashMap<String, Object> map = (config.get("Items") instanceof Map)
				? (HashMap<String, Object>) config.get("Items")
				: new HashMap<>();
		ArrayList<String> list = (map.get("Msg") instanceof List) ? (ArrayList<String>) map.get("Msg")
				: new ArrayList<>();
		double Money = config.getDouble("Money");
		if (list.size() > 0)
			player.sendMessage(MiniatureS.mis.getMessage().getSurname("MyShop", "Join", "Join1"));
		for (String msg : list)
			player.sendMessage(msg);
		if (Money > 0) {
			player.sendMessage(MiniatureS.mis.getMessage().getSurname("MyShop", "Join", "MoneyAdd",
					new String[] { "{Money}" }, new String[] { Money + "" }));
			EconomyAPI.getInstance().addMoney(player, Money);
		}
		PlayerInventory inventory = player.getInventory();
		if (map.size() > 0) {
			player.sendMessage(MiniatureS.mis.getMessage().getSurname("MyShop", "Join", "ItemAdd"));
			for (String key : map.keySet()) {
				HashMap<String, String> item = (HashMap<String, String>) map.get(key);
				int ID = Float.valueOf(item.get("ID")).intValue();
				int Meta = Float.valueOf(item.get("Meta")).intValue();
				int Count = Float.valueOf(item.get("Count")).intValue();
				Item i = new Item(ID, Meta, Count);
				inventory.addItem(i);
				player.sendMessage(MiniatureS.mis.getMessage().getSurname("MyShop", "Join", "ItemAdd",
						new String[] { "{Count}", "{ItemName}" },
						new String[] { Count + "", ItemIDSunName.getIDByName(ID, Meta) }));
			}
		}
	}
}
