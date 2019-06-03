package cn.xiaokai.mis.form.custom.form;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.form.custom.CustomData;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class ModalType extends SB10000 {
	/**
	 * 当玩家点击的按钮指向的表单为选择型表单
	 * 
	 * @param player 触发这个事件的玩家对象
	 * @param file   表单文件得文件对象
	 * @param config 表单配置文件对西安
	 */
	public ModalType(Player player, File file, Config config) {
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
		String Content = getMessage().getText(config.getString("Content"));
		HashMap<String, Object> Items = (config.get("Items") instanceof Map)
				? (HashMap<String, Object>) config.get("Items")
				: new HashMap<>();
		String Button1 = getMessage().getText(Items.get("Button1") == null ? "确定" : (String) Items.get("Button1"));
		String Button2 = getMessage().getText(Items.get("Button2") == null ? "确定" : (String) Items.get("Button2"));
		CustomData data = new CustomData();
		data.file = file;
		data.AllData = Items;
		data.FormType = "ModalType";
		mis.Custom.put(player.getName(), data);
		player.showFormWindow(new FormWindowModal(Title, Content, Button1, Button2), MakeID.ModalTypeForm.getID());
	}
}
