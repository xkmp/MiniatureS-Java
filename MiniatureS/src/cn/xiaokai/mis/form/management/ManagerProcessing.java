package cn.xiaokai.mis.form.management;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class ManagerProcessing {
	private MiniatureS mis;

	/**
	 * 处理相关数据，如添加项目、删除项目等
	 * 
	 * @param mis 插件主类对象
	 */
	public ManagerProcessing(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 配置数据
	 * 
	 * @param player 触发这个事件的玩家对象
	 * @param data   处罚的数据
	 */

	public void SettingConfig(Player player, FormResponseCustom data) {
		if (!player.isOp()) {
			player.sendMessage(TextFormat.RED + "您没有这样做的权限");
			return;
		}
		if (data.getResponse(0) == null || String.valueOf(data.getResponse(0)).isEmpty()) {
			player.sendMessage(TextFormat.RED + "请输入物品ID或名称");
			return;
		}
		String ID = ItemIDSunName.UnknownToID(String.valueOf(data.getResponse(0)));
		boolean isShop = Boolean.valueOf(String.valueOf(data.getResponse(1)));
		if (data.getResponse(2) == null || String.valueOf(data.getResponse(2)).isEmpty()) {
			player.sendMessage(TextFormat.RED + "请输入货币名称");
			return;
		}
		String MoneyName = String.valueOf(data.getResponse(2));
		boolean isMyShop = Boolean.valueOf(String.valueOf(data.getResponse(3)));
		if (data.getResponse(4) == null || String.valueOf(data.getResponse(4)).isEmpty()) {
			player.sendMessage(TextFormat.RED + "请输入个人商店上架耗资");
			return;
		}
		String moneyString = String.valueOf(data.getResponse(4));
		if (!Tool.isInteger(moneyString)) {
			player.sendMessage(TextFormat.RED + "请输入正确的个人商店上架耗资(纯整数)");
			return;
		}
		int Money1 = Float.valueOf(moneyString).intValue();
		if (Money1 < 1) {
			player.sendMessage(TextFormat.RED + "请输入正确的个人商店上架耗资(正整数)");
			return;
		}
		if (data.getResponse(5) == null || String.valueOf(data.getResponse(5)).isEmpty()) {
			player.sendMessage(TextFormat.RED + "请输入个人商店介绍修改费用");
			return;
		}
		moneyString = String.valueOf(data.getResponse(5));
		if (!Tool.isInteger(moneyString)) {
			player.sendMessage(TextFormat.RED + "请输入正确的个人商店介绍修改费用(纯整数)");
			return;
		}
		int Money2 = Float.valueOf(moneyString).intValue();
		if (Money1 < 1) {
			player.sendMessage(TextFormat.RED + "请输入正确的个人商店介绍修改费用(正整数)");
			return;
		}
		(new SB10086()).Save(player, ID, isShop, isMyShop, MoneyName, Money1, Money2);
	}

	/**
	 * 处理删除按钮的数据
	 * 
	 * @param player 触发事件的玩家对象
	 * @param data   返回的数据
	 */
	public void RemoveButton(Player player, FormResponseModal data) {
		File file = mis.RemoveButtonFile.get(player.getName());
		ArrayList<String> keyList = mis.RemoveButtonKeyList.get(player.getName());
		Config config = new Config(file, Config.YAML);
		HashMap<String, Object> Buttons = (HashMap<String, Object>) config.get("Buttons");
		String Key = keyList.get(mis.RemoveButtonKeyID.get(player.getName()));
		String FileName = String.valueOf(((HashMap<String, Object>) Buttons.get(Key)).get("FileName"));
		Buttons.remove(Key);
		config.set("Buttons", Buttons);
		config.save();
		File file2 = new File(mis.getDataFolder() + MiniatureS.MenuConfigPath, FileName);
		if (file2.exists())
			file2.delete();
		String s = "";
		if (data.getClickedButtonId() == 0) {
			ArrayList<String> list = delConfig(FileName);
			for (String sb_fff : list)
				if (sb_fff != null)
					s += (!sb_fff.isEmpty()) ? " 、 " : "" + sb_fff;
			if (!s.isEmpty())
				s = "并删除以下文件：\n" + s;
		}
		MakeForm.makeTip(player, TextFormat.GREEN + "删除成功！" + s);
	}

	/**
	 * 循环删除配置文件，也就是把它以及他名下的所有配置文件全部删掉，你能理解吗？
	 * 
	 * @param string 配置文件名
	 */
	public ArrayList<String> delConfig(String string) {
		ArrayList<String> list = new ArrayList<>();
		list.add(string);
		File file = new File(mis.getDataFolder() + MiniatureS.MenuConfigPath, string);
		if (!file.exists())
			return list;
		Config config = new Config(file, Config.YAML);
		file.delete();
		if (!(config.get("Buttons") instanceof Map))
			return list;
		HashMap<String, Object> map = (HashMap<String, Object>) config.get("Buttons");
		for (String Key : map.keySet()) {
			HashMap<String, Object> ks = (HashMap<String, Object>) map.get(Key);
			if (String.valueOf(ks.get("Type")).toLowerCase() == "openwindow")
				list.addAll(delConfig(String.valueOf(ks.get("FileName"))));
		}
		return list;
	}
}
