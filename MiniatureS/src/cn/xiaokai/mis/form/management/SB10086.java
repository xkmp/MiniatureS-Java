package cn.xiaokai.mis.form.management;

import java.util.LinkedHashMap;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;

public class SB10086 {
	/**
	 * 设置默认属性
	 * 
	 * @param player    要设置这个玩意的玩家对象
	 * @param iD        快捷工具
	 * @param isShop    快捷打开为商店
	 * @param isMyShop  个人商店
	 * @param moneyName 货币单位
	 * @param money1    上架耗资
	 * @param money2    个人商店介绍修改费用
	 */
	public void Save(Player player, String iD, boolean isShop, boolean isMyShop, String moneyName, int money1,
			int money2) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("快捷工具", iD);
		map.put("快捷打开为商店", isShop);
		map.put("个人商店", isMyShop);
		map.put("货币单位", moneyName);
		map.put("上架耗资", money1);
		map.put("个人商店介绍修改费用", money2);
		Config config = MiniatureS.mis.config;
		config.setAll(map);
		config.save();
		player.sendMessage(TextFormat.GREEN + "设置成功！");
	}
}
