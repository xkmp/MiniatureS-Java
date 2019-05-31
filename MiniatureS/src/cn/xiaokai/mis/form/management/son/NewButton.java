package cn.xiaokai.mis.form.management.son;

import java.io.File;
import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.FormStatic;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.form.openbt.HandsomeXiaoKai;
import cn.xiaokai.mis.tool.Tool;
/**
 * @author Winfxk
 */
public class NewButton {
	private Player player;
	private MiniatureS mis;
	private File file;

	/**
	 * 在主页以外的按钮创建按钮
	 * 
	 * @param player 要创建按钮得到玩家对象
	 * @param file   要创建按钮的界面配置文件的文件对象
	 */
	public NewButton(Player player, File file) {
		this.mis = MiniatureS.mis;
		this.file = file;
		this.player = player;
	}

	/**
	 * 让选择要创建的按钮的类型的UI
	 */
	public void Make() {
		Config config = new Config(file, Config.YAML);
		HandsomeXiaoKai key = new HandsomeXiaoKai();
		key.file = file;
		mis.sb.put(player.getName(), key);
		player.showFormWindow(
				new FormWindowSimple(Tool.getColorFont(mis.getName() + "-" + config.getString("Title")),
						Tool.getColorFont("请选择您需要创建的按钮类型！"), FormStatic.getButtonOpenTypeList()),
				MakeID.SonAddButtonByType.getID());
	}
}
