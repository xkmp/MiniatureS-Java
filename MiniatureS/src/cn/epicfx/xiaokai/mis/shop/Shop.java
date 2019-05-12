package cn.epicfx.xiaokai.mis.shop;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

public class Shop {
	private MiniatureS mis;

	public Shop(MiniatureS mis) {
		this.mis = mis;
	}

	public void AddShopShow(Player player, String ShopName, String ShopType, String[] Back_world, String[] Back_Player,
			Boolean imageType, String imagePath) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		String ConfigName = getShopConfigName();
		map.put("Text", ShopName);
		map.put("Image", imagePath);
		map.put("ImageType", true);
		map.put("Player", player.getName());
		map.put("File", ConfigName);
		map.put("Type", ShopType);
		map.put("Back_world", Back_world == null ? new String[] {} : Back_world);
		map.put("Back_Player", Back_Player == null ? new String[] {} : Back_Player);
		Map<String, Object> list = mis.ShopListConfig.get("Buttons", new HashMap<>());
		LinkedHashMap<String, Object> Con = new LinkedHashMap<>();
		Con.put("Content", null);
		Con.put("Name", ShopName);
		Con.put("Player", player.getName());
		Con.put("Back_world", Back_world == null ? new String[] {} : Back_world);
		Con.put("Back_Player", Back_Player == null ? new String[] {} : Back_Player);
		Con.put("Buttons", new HashMap<String, Object>());
		Config Config = new Config(mis.getDataFolder() + MiniatureS.ShopConfigPath + ConfigName, 2);
		Config.setAll(Con);
		Config.save();
		String BtKey = ShopMakeForm.getShopName(0);
		list.put(BtKey, map);
		mis.ShopListConfig.set("Buttons", list);
		mis.ShopListConfig.save();
		player.sendMessage(TextFormat.GREEN + "创建成功!"
				+ (imageType == true ? (imagePath != null ? "\n设置贴图为：" + imagePath : "") : ""));
	}

	private String getShopConfigName() {
		String nameString = "";
		int length = Tool.getRand(5, 20);
		for (int i = 0; i < length; i++)
			nameString += Tool.getRandString();
		nameString = nameString + ".yml";
		File file = new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, nameString);
		if (file.exists())
			return getShopConfigName();
		return nameString;
	}
}
