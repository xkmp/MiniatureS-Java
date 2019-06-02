package cn.xiaokai.mis.form.custom.form;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.form.custom.CustomData;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class SimpleType extends SB10000 {

	/**
	 * 如果自定义表单文件为简单类型
	 * 
	 * @param player 触发这些事件的玩家对象
	 * @param file   表单配置文件的文件对象
	 * @param config 表单的配置文件对象
	 */
	public SimpleType(Player player, File file, Config config) {
		super(player, file, config);
	}

	/**
	 * 开始....
	 */
	public void startPY() {
		if (!config.getBoolean("Enabled")) {
			MakeForm.makeTip(player, getMessage().getSurname("UI", "Click", "NotEnabled"));
			return;
		}
		HashMap<String, Object> Items = (config.get("Items") instanceof Map)
				? (HashMap<String, Object>) config.get("Items")
				: new HashMap<>();
		String Title = getMessage().getText(config.getString("Title"));
		String Content = getMessage().getText(config.getString("Content") + (Items.size() > 0 ? ""
				: getMessage().getSurname("UI", "Click", "NotList", new String[] { "{OP?}" },
						new String[] { (player.isOp() ? "" : "找管理员") })));
		List<ElementButton> list = new ArrayList<ElementButton>();
		HashMap<String, Object> item;
		ArrayList<HashMap<String, Object>> itemList = new ArrayList<>();
		ArrayList<String> keyList = new ArrayList<>();
		for (String ike : Items.keySet()) {
			item = (HashMap<String, Object>) Items.get(ike);
			if (item.get("Image") != null && String.valueOf(item.get("Image")).isEmpty()
					&& item.get("ImageType") != null && (item.get("ImageType") instanceof Boolean)) {
				list.add(new ElementButton(String.valueOf(item.get("Text")),
						new ElementButtonImageData(
								Boolean.valueOf(String.valueOf(item.get("ImageType")))
										? ElementButtonImageData.IMAGE_DATA_TYPE_PATH
										: ElementButtonImageData.IMAGE_DATA_TYPE_URL,
								String.valueOf(item.get("Image")))));
			} else
				list.add(new ElementButton(String.valueOf(item.get("Text"))));
			keyList.add(String.valueOf(item.get("ID")));
			itemList.add(item);
		}
		CustomData data = new CustomData();
		data.file = file;
		data.AllData = Items;
		data.Items = itemList;
		data.Keys = keyList;
		mis.Custom.put(player.getName(), data);
		player.showFormWindow(new FormWindowSimple(Title, Content, list), MakeID.SimpleTypeForm.getID());
	}
}
