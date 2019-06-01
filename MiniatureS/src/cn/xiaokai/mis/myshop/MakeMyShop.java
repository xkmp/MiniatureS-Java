package cn.xiaokai.mis.myshop;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class MakeMyShop {
	private MiniatureS mis;
	private Player player;
	private String ID;
	private int Count;
	private int Money;
	private String Type;
	private Config config;
	private File file;
	private String IsMoney;

	/**
	 * 个人商店添加项目
	 * 
	 * @param player 要添加项目的玩家对象
	 * @param ID     要上架的物品ID
	 * @param Count  要上架的物品数量
	 * @param Money  要上架的的物品单价
	 * @param Type   要上架的项目类型
	 */
	public MakeMyShop(Player player, String ID, int Count, int Money, String Type) {
		this(player, ID, Count, Money, Type, "");
	}

	/**
	 * 个人商店添加项目
	 * 
	 * @param player  要添加项目的玩家对象
	 * @param ID      要上架的物品ID
	 * @param Count   要上架的物品数量
	 * @param Money   要上架的的物品单价
	 * @param Type    要上架的项目类型
	 * @param IsMoney 若上架需要扣除金币，则这个选项为要扣除的金币的文本说明
	 */
	public MakeMyShop(Player player, String ID, int Count, int Money, String Type, String IsMoney) {
		this.player = player;
		this.mis = MiniatureS.mis;
		this.ID = ID;
		this.Count = Count;
		this.Money = Money;
		this.Type = Type;
		file = new File(mis.getDataFolder() + MiniatureS.MyShopConfigPath, player.getName() + ".yml");
		config = new Config(file, Config.YAML);
		this.IsMoney = IsMoney;
	}

	/**
	 * 开始处理数据
	 */
	public void newItemSwitch() {
		String Key = getKey();
		HashMap<String, Object> Items = (config.get("Items") instanceof Map)
				? ((HashMap<String, Object>) config.get("Items"))
				: new HashMap<>();
		HashMap<String, Object> map = new HashMap<>();
		map.put("Player", player.getName());
		map.put("Item", ID);
		map.put("Time", Tool.getDate() + " " + Tool.getTime());
		map.put("Count", Count);
		map.put("Type", Type);
		map.put("Money", Money);
		map.put("Key", Key);
		Items.put(Key, map);
		config.set("Items", Items);
		if (config.getString("Player") == null || config.getString("Player").isEmpty())
			config.set("Player", player.getName());
		if (config.getString("Contxt") == null || config.getString("Contxt").isEmpty())
			config.set("Contxt", Tool.getColorFont("这个人很懒！什么都没留下！"));
		config.save();
		player.sendMessage(TextFormat.GREEN + "上架物品成功！" + IsMoney + "\n" + TextFormat.WHITE + "每个" + TextFormat.RED
				+ ItemIDSunName.getIDByName(ID) + TextFormat.AQUA + Money + TextFormat.WHITE + mis.getMoneyName() + "\n"
				+ Tool.getRandColor() + "Key:" + Tool.getRandColor() + Key);
		int SB_Ic = mis.MyShopPlayerMoneyConfig.getInt(player.getName());
		SB_Ic += this.Count;
		mis.MyShopPlayerMoneyConfig.set(player.getName(), SB_Ic);
		mis.MyShopPlayerMoneyConfig.save();
	}

	/**
	 * 获取一个不重复的项目Key
	 * 
	 * @return
	 */
	private String getKey() {
		String Key = "";
		int JJleng = Tool.getRand(5, 25);
		for (int i = 0; i < JJleng; i++)
			Key += Tool.getRandString();
		HashMap<String, Object> map = (config.get("Items") instanceof Map)
				? ((HashMap<String, Object>) config.get("Items"))
				: new HashMap<>();
		if (map.containsKey(Key))
			return getKey();
		return Key;
	}
}
