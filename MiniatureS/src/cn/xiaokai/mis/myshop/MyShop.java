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
				list.add(mis.getMessage()
						.getSon("MyShop", "ItemOK", new String[] { "{ItemName}", "{ShopType}" }, new Object[] {
								ItemIDSunName.getIDByName(ID, Meta),
								(String.valueOf(map.get("Type")).toLowerCase().equals("sell") ? "售完" : "收购结束") }));
				config1.set("Msg", list);
				config1.save();
			} else {
				p2.sendMessage(mis.getMessage()
						.getSon("MyShop", "ItemOK", new String[] { "{ItemName}", "{ShopType}" }, new Object[] {
								ItemIDSunName.getIDByName(ID, Meta),
								(String.valueOf(map.get("Type")).toLowerCase().equals("sell") ? "售完" : "收购结束") }));
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
			player.sendMessage(mis.getMessage().getSon("MyShop", "ItemDeficiency"));
			return;
		}
		inventory.remove(new Item(ID, Meta, this.Count));
		EconomyAPI.getInstance().addMoney(player, (this.Count * Money));
		player.sendMessage(mis.getMessage().getSon("MyShop", "ItemShopIsOK",
				new String[] { "{Count}", "{ItemName}", "{ByPlayer}", "{Money}", "{MoneyName}" }, new Object[] {
						this.Count, ItemIDSunName.getIDByName(ID, Meta), map.get("Player"), (this.Count * Money) }));
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
			list.add(mis.getMessage().getSon("MyShop", "ItemSellPYOK",
					new String[] { "{Name}", "{Count}", "{ItemName}", "{isPlayerTxt}" },
					new Object[] { player.getName(), this.Count, ItemIDSunName.getIDByName(ID, Meta),
							(String.valueOf(map.get("Player")).equals(player.getName())
									? mis.getMessage().getSon("MyShop", "ItemSellPYOK_isPlayerTxt")
									: "") }));
			config.set("Msg", list);
			config.set("Items", arrayList);
			config.save();
		} else
			p2.sendMessage(mis.getMessage().getSon("MyShop", "ItemSellPYOK",
					new String[] { "{Name}", "{Count}", "{ItemName}", "{isPlayerTxt}" },
					new Object[] { player.getName(), this.Count, ItemIDSunName.getIDByName(ID, Meta),
							(String.valueOf(map.get("Player")).equals(player.getName())
									? mis.getMessage().getSon("MyShop", "ItemSellPYOK_isPlayerTxt")
									: "") }));
		int SB_Ic = mis.MyShopPlayerMoneyConfig.getInt(player.getName());
		SB_Ic += this.Count;
		mis.MyShopPlayerMoneyConfig.set(player.getName(), SB_Ic);
		mis.MyShopPlayerMoneyConfig.save();
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
			player.sendMessage(mis.getMessage().getSon("MyShop", "LackOfBackpackSpace"));
			return;
		}
		int ItemCount = Float.valueOf(String.valueOf(map.get("Count"))).intValue();
		ItemCount -= Count;
		map.put("Count", ItemCount);
		EconomyAPI.getInstance().reduceMoney(player, (Money * Count));
		inventory.addItem(item);
		player.sendMessage(mis.getMessage().getSon("MyShop", "ItemShopPYOK",
				new String[] { "{ByPlayer}", "{ItemName}", "{Money}", "{MoneyName}" },
				new Object[] { map.get("Player"), ItemIDSunName.getIDByName(ID, Meta), (Money * Count) }));
		Player p2 = Server.getInstance().getPlayer(String.valueOf(map.get("Player")));
		if (p2 == null || !p2.isOnline()) {
			Config config = CPlayer.getPlayerConfig(String.valueOf(map.get("Player")));
			config.set("Money", config.getDouble("Money") + (Money * Count));
			ArrayList<String> list = (config.get("Msg") instanceof List) ? ((ArrayList<String>) config.get("Msg"))
					: new ArrayList<>();
			list.add(mis.getMessage().getSon("MyShop", "ItemShopOK",
					new String[] { "{Name}", "{ItemName}", "{Money}", "{isPlayerTxt}" },
					new Object[] { player.getName(), ItemIDSunName.getIDByName(ID, Meta), (Money * Count),
							(String.valueOf(map.get("Player")).equals(player.getName())
									? mis.getMessage().getSon("MyShop", "ItemSellPYOK_isPlayerTxt")
									: "") }));
			config.set("Msg", list);
			config.save();
		} else {
			player.sendMessage(mis.getMessage().getSon("MyShop", "ItemShopOK",
					new String[] { "{Name}", "{ItemName}", "{Money}", "{isPlayerTxt}" },
					new Object[] { player.getName(), ItemIDSunName.getIDByName(ID, Meta), (Money * Count),
							(String.valueOf(map.get("Player")).equals(player.getName())
									? mis.getMessage().getSon("MyShop", "ItemSellPYOK_isPlayerTxt")
									: "") }));
			EconomyAPI.getInstance().addMoney(p2, (Money * Count));
		}
		int SB_Ic = mis.MyShopPlayerMoneyConfig.getInt(player.getName());
		SB_Ic += this.Count + Money;
		mis.MyShopPlayerMoneyConfig.set(player.getName(), SB_Ic);
		mis.MyShopPlayerMoneyConfig.save();
		end();
	}
}
