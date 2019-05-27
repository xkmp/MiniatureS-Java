package cn.epicfx.xiaokai.mis.form.management;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

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
			s += "并删除以下文件：\n";
			ArrayList<String> list = delConfig(FileName);
			for (String sb_fff : list)
				s += sb_fff + " 、 ";
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
