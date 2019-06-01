package cn.xiaokai.mis.myshop;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.FormStatic;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.myshop.seek.MySeek;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;
import me.onebone.economyapi.EconomyAPI;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class MyShopD {
	private Player player;
	private MiniatureS mis;

	/**
	 * 处理个人商店的数据
	 * 
	 * @param player 触发这些事件的玩家对象
	 */
	public MyShopD(Player player) {
		this.player = player;
		this.mis = MiniatureS.mis;
	}

	/**
	 * 处理查看玩家项目列表的时候发回的数据
	 * 
	 * @param data 发挥的数据
	 */
	public void MyShopItem(FormResponseSimple data) {
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "MyShopOK"));
			return;
		}
		TonsFx fx = mis.MyShopData.get(player.getName());
		HashMap<String, Object> map = fx.ShopItems.get(data.getClickedButtonId());
		int Count = Float.valueOf(String.valueOf(map.get("Count"))).intValue();
		if (Count < 1) {
			Config config = new Config(fx.file, Config.YAML);
			if (!(config.get("Items") instanceof Map)) {
				MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "NotDataList"));
			}
			HashMap<String, Object> items = (HashMap<String, Object>) config.get("Items");
			items.remove(String.valueOf(map.get("Key")));
			config.set("Items", items);
			config.save();
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "ItemOKRemove"));
			return;
		}
		(new MakeMyShopForm(player)).startPyItem(map, fx.file);
	}

	/**
	 * 处理搜索主页传回的数据
	 * 
	 * @param data 传回的数据
	 */
	public void SeekMain(FormResponseCustom data) {
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "MyShopOK"));
			return;
		}
		String ID = String.valueOf(data.getResponse(0));
		if (data.getResponse(0) == null || ID.isEmpty()) {
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "InputItemSB"));
			return;
		}
		ID = ItemIDSunName.UnknownToID(ID);
		String ShopType = String.valueOf(data.getResponse(1)) == FormStatic.SeekMyShopType[0] ? "All"
				: (String.valueOf(data.getResponse(1)) == FormStatic.SeekMyShopType[1] ? "Sell" : "Shop");
		(new MySeek(player)).FormSeek(ID, ShopType);
	}

	/**
	 * 处理在个人商店添加项目的数据
	 * 
	 * @param data 传回的数据
	 */
	public void newMyShopItem(FormResponseCustom data) {
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "MyShopOK"));
			return;
		}
		String ID = String.valueOf(data.getResponse(0));
		if (data.getResponse(0) == null || ID.isEmpty()) {
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "InputItemSB"));
			return;
		}
		ID = ItemIDSunName.UnknownToID(ID);
		String countString = String.valueOf(data.getResponse(1));
		if (data.getResponse(1) == null || countString.isEmpty()) {
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "InputItemCountSB"));
			return;
		}
		if (!Tool.isInteger(countString)) {
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "InputItemCountSBNotInt"));
			return;
		}
		int Count = Float.valueOf(countString).intValue();
		if (Count <= 0) {
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "InputItemCountSBNotInt0"));
			return;
		}
		String MoneytString = String.valueOf(data.getResponse(2));
		if (data.getResponse(2) == null || MoneytString.isEmpty()) {
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "InputItemMoneySB"));
			return;
		}
		if (!Tool.isInteger(MoneytString)) {
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "InputItemMoneySBNotInt"));
			return;
		}
		int Money = Float.valueOf(MoneytString).intValue();
		if (Money <= 0) {
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "InputItemMoneySBNotInt0"));
			return;
		}
		PlayerInventory inventory = player.getInventory();
		String ShopType = String.valueOf(data.getResponse(3)) == FormStatic.MyShopType[0] ? "Sell" : "Shop";
		if (ShopType == "Sell") {
			int ItemCount = 0;
			Map<Integer, Item> items = inventory.getContents();
			for (Integer i : items.keySet()) {
				Item item = items.get(i);
				if (Tool.isMateID(item.getId() + ":" + item.getDamage(), ID) && item.getEnchantments().length == 0)
					ItemCount += item.getCount();
			}
			if (ItemCount < Count) {
				MakeForm.makeTip(player,
						mis.getMessage().getSon("MyShop", "NotItems", new String[] { "{ItemName}", "{Count}" },
								new Object[] { ItemIDSunName.getIDByName(ID), (Count - ItemCount) }));
				return;
			}
		} else {
			if (EconomyAPI.getInstance().myMoney(player) < (Count * Money)) {
				MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "SBPlayerNotMoney"));
				return;
			}
		}
		int xxMoney = mis.config.getInt("上架耗资");
		String Sb_you = "";
		if (ShopType == "Sell")
			if (!Tool.isInteger(ID.split(":")[1]) || !Tool.isInteger(ID.split(":")[0])) {
				MakeForm.makeTip(player,  mis.getMessage().getSon("MyShop", "getIDError"));
				return;
			}
		if (xxMoney > 1) {
			if (EconomyAPI.getInstance().myMoney(player) < xxMoney * Count) {
				MakeForm.makeTip(player,
						mis.getMessage().getSon("MyShop", "MyShopItemSB", new String[] { "{Money}", "{isMoney}" },
								new Object[] { (xxMoney * Count),
										((xxMoney * Count) - EconomyAPI.getInstance().myMoney(player)) }));
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, xxMoney * Count);
			Sb_you = mis.getMessage().getSon("MyShop", "reMoney", new String[] { "{Money}" },
					new Object[] { (xxMoney * Count) });
		}
		if (ShopType == "Sell") {
			String[] sb = ID.split(":");
			int IDD = Float.valueOf(sb[0]).intValue();
			int DD = Float.valueOf(sb[1]).intValue();
			inventory.remove(new Item(IDD, DD, Count));
		} else
			EconomyAPI.getInstance().reduceMoney(player, (Count * Money));
		(new MakeMyShop(player, ID, Count, Money, ShopType, Sb_you)).newItemSwitch();
	}

	/**
	 * 处理个人商店主页传回的数据
	 * 
	 * @param data 传回的数据
	 */
	public void Main(FormResponseSimple data) {
		int ID = data.getClickedButtonId();
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, mis.getMessage().getSon("MyShop", "MyShopOK"));
			return;
		}
		TonsFx fx = mis.MyShopData.get(player.getName());
		if (ID >= fx.files.size()) {
			if (ID == fx.files.size())
				(new MakeMyShopForm(player)).addItem();
			else
				(new MakeMyShopForm(player)).Seek();
			return;
		}
		(new MakeMyShopForm(player)).ShowPlayer(fx.files.get(ID));
	}
}
