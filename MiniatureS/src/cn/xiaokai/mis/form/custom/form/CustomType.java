package cn.xiaokai.mis.form.custom.form;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.form.custom.CustomData;
import cn.xiaokai.mis.form.custom.form.get.SBForm;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class CustomType extends SB10000 {
	/**
	 * 当玩家点击的按钮指向的表单为多样型表单
	 * 
	 * @param player 触发这个事件的玩家对象
	 * @param file   表单文件得文件对象
	 * @param config 表单配置文件对西安
	 */
	public CustomType(Player player, File file, Config config) {
		super(player, file, config);
	}

	/**
	 * 开始PY
	 */
	public void startPY() {
		if (!config.getBoolean("Enabled")) {
			MakeForm.makeTip(player, getMessage().getSurname("UI", "Click", "NotEnabled"));
			return;
		}
		String Title = getMessage().getText(config.getString("Title"));
		String Icon = config.getString("Icon");
		boolean IconType = true;
		try {
			IconType = Boolean.valueOf(config.getString("IconType"));
		} catch (Exception e) {
			IconType = true;
			mis.getLogger().info(TextFormat.RED + "数据类型错误：此处只能为布尔值！\nTitle：" + Title + "\n" + e.getMessage());
		}
		HashMap<String, Object> Items = (config.get("Items") instanceof Map)
				? (HashMap<String, Object>) config.get("Items")
				: new HashMap<>();
		CustomData data = new CustomData();
		data.FormType = "CustomType";
		HashMap<String, Object> item;
		List<Element> list = new ArrayList<>();
		ArrayList<String> keyList = new ArrayList<>();
		ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
		for (String ike : Items.keySet()) {
			item = (HashMap<String, Object>) Items.get(ike);
			keyList.add(ike);
			item.put("Key", ike);
			list.add(SBForm.Switch(item));
			itemList.add(item);
		}
		data.Keys = keyList;
		data.AllData = Items;
		data.Items = itemList;
		data.file = file;
		data.OverallCommadn = (config.getString("Command") == null || config.getString("Command").isEmpty()) ? null
				: config.getString("Command");
		data.OverallCommander=(config.getString("Commander") == null || config.getString("Commander").isEmpty()) ? null
				: config.getString("Commander");
		mis.Custom.put(player.getName(), data);
		ElementButtonImageData iconData;
		if (Icon != null && !Icon.isEmpty()) {
			iconData = new ElementButtonImageData(Icon, IconType ? ElementButtonImageData.IMAGE_DATA_TYPE_PATH
					: ElementButtonImageData.IMAGE_DATA_TYPE_URL);
		} else
			iconData = null;
		player.showFormWindow(new FormWindowCustom(Title, list, iconData), MakeID.CustomTypeForm.getID());
	}
}
