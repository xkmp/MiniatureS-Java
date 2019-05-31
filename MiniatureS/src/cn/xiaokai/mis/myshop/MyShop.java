package cn.xiaokai.mis.myshop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;
import me.onebone.economyapi.EconomyAPI;
/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class MyShop {
	private Player player;
	private MiniatureS mis;
	private int Count;
	private TonsFx fx;
	private HashMap<String, Object> map;
	private File file;
	private String Key;
	private int Money;
	private int ID, Meta;

	/**
	 * 个人商店开始交易物品
	 * 
	 * @param player 交易物品的忘记对象
	 */
	public MyShop(Player player) {
		this.player = player;
		mis = MiniatureS.mis;
		fx = mis.MyShopData.get(player.getName());
		map = fx.MainItem;
		file = fx.file;
		String IDs = String.valueOf(map.get("Item"));
		ID = Float.valueOf(IDs.split(":")[0]).intValue();
		Meta = Float.valueOf(IDs.split(":")[1]).intValue();
		Key = String.valueOf(map.get("Key"));
	}

	/**
	 * 交易结束后的处理
	 */
	private void end() {
		Config config = new Config(file, Config.YAML);
		HashMap<String, Object> Items = (config.get("Items") instanceof Map)
				? (HashMap<String, Object>) (config.get("Items"))
				: new HashMap<>();
		if (Float.valueOf(String.valueOf(map.get("Count"))) < 1) {
			Items.remove(Key);
			Player p2 = Server.getInstance().getPlayer(String.valueOf(map.get("Player")));
			if (p2 == null || !p2.isOnline()) {
				Config config1 = CPlayer.getPlayerConfig(String.valueOf(map.get("Player")));
				ArrayList<String> list = (config1.get("Msg") instanceof List) ? ((ArrayList<String>) config1.get("Msg"))
						: new ArrayList<>();
				list.add(TextFormat.GREEN + "您的商品" + TextFormat.WHITE + ItemIDSunName.getIDByName(ID, Meta)
						+ TextFormat.GREEN + "已"
						+ (String.valueOf(map.get("Type")).toLowerCase().equals("sell") ? "售完" : "收购结束") + "删除");
				config1.set("Msg", list);
				config1.save();
			} else {
				p2.sendMessage(TextFormat.GREEN + "您的商品" + TextFormat.WHITE + ItemIDSunName.getIDByName(ID, Meta)
						+ TextFormat.GREEN + "已"
						+ (String.valueOf(map.get("Type")).toLowerCase().equals("sell") ? "售完" : "收购结束") + "删除");
			}
		} else {
			map.put("Count", Float.valueOf(String.valueOf(map.get("Count"))).intValue() - Count);
			Items.put(Key, map);
		}
		config.set("Items", Items);
		config.save();
		if (mis.MyShopData.containsKey(player.getName()))
			mis.MyShopData.remove(player.getName());
	}

	/**
	 * 判断交易方式
	 * 
	 * @param data 交易过程中产生的数据对象
	 */
	public void Switch(FormResponseCustom data) {
		Count = Float.valueOf(String.valueOf(data.getResponse(1))).intValue();
		Money = Float.valueOf(String.valueOf(map.get("Money"))).intValue();
		switch (String.valueOf(map.get("Type")).toLowerCase()) {
		case "shop":
		case "收购":
			Shop();
			break;
		case "sell":
		case "出售":
		default:
			Sell();
			break;
		}
	}

	/**
	 * 判断的交易方式为收购物品
	 */
	private void Shop() {
		PlayerInventory inventory = player.getInventory();
		Map<Integer, Item> items = inventory.getContents();
		int Count = 0;
		for (Integer i : items.keySet()) {
			Item item = items.get(i);
			if (Tool.isMateID(item.getId() + ":" + item.getDamage(), ID + ":" + Meta))
				Count += item.getCount();
		}
		if (Count < this.Count) {
			player.sendMessage(TextFormat.RED + "您的物品数量不足！请检查！");
			return;
		}
		inventory.remove(new Item(ID, Meta, this.Count));
		EconomyAPI.getInstance().addMoney(player, (this.Count * Money));
		player.sendMessage(TextFormat.GREEN + "成功出售" + TextFormat.WHITE + this.Count + TextFormat.GREEN + "个"
				+ TextFormat.WHITE + ItemIDSunName.getIDByName(ID, Meta) + TextFormat.GREEN + "给" + TextFormat.BLUE
				+ String.valueOf(map.get("Player")) + TextFormat.GREEN + "，获利" + TextFormat.RED + (this.Count * Money)
				+ TextFormat.GREEN + mis.getMoneyName());
		Player p2 = Server.getInstance().getPlayer(String.valueOf(map.get("Player")));
		if (p2 == null || !p2.isOnline()) {
			Config config = CPlayer.getPlayerConfig(String.valueOf(map.get("Player")));
			ArrayList<HashMap<String, Object>> arrayList = (config.get("Items") instanceof List)
					? (ArrayList<HashMap<String, Object>>) (config.get("Items"))
					: new ArrayList<>();
			HashMap<String, Object> iteMap = new HashMap<>();
			iteMap.put("ID", ID);
			iteMap.put("Meta", Meta);
			iteMap.put("Count", this.Count);
			arrayList.add(iteMap);
			ArrayList<String> list = (config.get("Msg") instanceof List) ? ((ArrayList<String>) config.get("Msg"))
					: new ArrayList<>();
			list.add(TextFormat.BLUE + player.getName() + TextFormat.GREEN + "卖给你" + TextFormat.RED + this.Count
					+ TextFormat.GREEN + "个" + TextFormat.WHITE + ItemIDSunName.getIDByName(ID, Meta)
					+ (String.valueOf(map.get("Player")).equals(player.getName()) ? TextFormat.RED + " 自卖侠！牛逼！" : ""));
			config.set("Msg", list);
			config.set("Items", arrayList);
			config.save();
		} else
			p2.sendMessage(TextFormat.BLUE + player.getName() + TextFormat.GREEN + "卖给你" + TextFormat.RED + this.Count
					+ TextFormat.GREEN + "个" + TextFormat.WHITE + ItemIDSunName.getIDByName(ID, Meta)
					+ (String.valueOf(map.get("Player")).equals(player.getName()) ? TextFormat.RED + " 自卖侠！牛逼！" : ""));
		end();
	}

	/**
	 * 判断交易方式为出售物品
	 */
	private void Sell() {
		if (EconomyAPI.getInstance().myMoney(player) < (Money * Count)) {
			player.sendMessage(TextFormat.RED + mis.getMoneyName() + "不足！");
			return;
		}
		PlayerInventory inventory = player.getInventory();
		Map<Integer, Item> items = inventory.getContents();
		int NullCount = 0;
		Item item = new Item(ID, Meta, Count);
		int SB_FFF = 0;
		for (Integer i : items.keySet()) {
			SB_FFF++;
			Item it = items.get(i);
			if (it.getId() == 0)
				NullCount += item.getMaxStackSize();
			if (Tool.isMateID(it.getId() + ":" + it.getDamage(), ID + ":" + Meta))
				NullCount += (item.getMaxStackSize() - it.getCount());
		}
		NullCount += (inventory.getSize() - SB_FFF) * item.getMaxStackSize();
		if (NullCount < Count) {
			player.sendMessage(TextFormat.RED + "您的背包空间不足！请清理背包空间后重试~");
			return;
		}
		int ItemCount = Float.valueOf(String.valueOf(map.get("Count"))).intValue();
		ItemCount -= Count;
		map.put("Count", ItemCount);
		EconomyAPI.getInstance().reduceMoney(player, (Money * Count));
		inventory.addItem(item);
		player.sendMessage(TextFormat.GREEN + "您成功购买了" + TextFormat.BLUE + String.valueOf(map.get("Player"))
				+ TextFormat.GREEN + "的" + TextFormat.WHITE + ItemIDSunName.getIDByName(ID, Meta) + TextFormat.GREEN
				+ "，花费" + TextFormat.AQUA + (Money * Count) + TextFormat.GREEN + mis.getMoneyName());
		Player p2 = Server.getInstance().getPlayer(String.valueOf(map.get("Player")));
		if (p2 == null || !p2.isOnline()) {
			Config config = CPlayer.getPlayerConfig(String.valueOf(map.get("Player")));
			config.set("Money", config.getDouble("Money") + (Money * Count));
			ArrayList<String> list = (config.get("Msg") instanceof List) ? ((ArrayList<String>) config.get("Msg"))
					: new ArrayList<>();
			list.add(TextFormat.BLUE + player.getName() + TextFormat.GREEN + "购买了您的" + TextFormat.WHITE
					+ ItemIDSunName.getIDByName(ID, Meta) + TextFormat.GREEN + "，获利" + TextFormat.AQUA + (Money * Count)
					+ TextFormat.GREEN + mis.getMoneyName()
					+ (String.valueOf(map.get("Player")).equals(player.getName()) ? TextFormat.RED + " 自卖侠！牛逼！" : ""));
			config.set("Msg", list);
			config.save();
		} else {
			player.sendMessage(TextFormat.BLUE + player.getName() + TextFormat.GREEN + "购买了您的" + TextFormat.WHITE
					+ ItemIDSunName.getIDByName(ID, Meta) + TextFormat.GREEN + "，获利" + TextFormat.AQUA + (Money * Count)
					+ TextFormat.GREEN + mis.getMoneyName()
					+ (String.valueOf(map.get("Player")).equals(player.getName()) ? TextFormat.RED + " 自卖侠！牛逼！" : ""));
			EconomyAPI.getInstance().addMoney(p2, (Money * Count));
		}
		end();
	}
}
