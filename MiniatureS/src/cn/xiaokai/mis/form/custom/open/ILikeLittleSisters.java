package cn.xiaokai.mis.form.custom.open;

import java.io.File;
import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.custom.form.CustomType;
import cn.xiaokai.mis.form.custom.form.ModalType;
import cn.xiaokai.mis.form.custom.form.SimpleType;

/**
 * @author Winfxk
 */
public class ILikeLittleSisters {
	private Player player;
	private File file;
	private Config config;

	/**
	 * 判断点击的按钮对应的表单类型是什么
	 * 
	 * @param player 点击按钮的玩家对象
	 * @param file   表单配置文件的文件对象
	 */
	public ILikeLittleSisters(Player player, File file) {
		this.file = file;
		this.player = player;
	}

	/**
	 * 开始PY
	 */
	public void startPY() {
		try {
			this.config = new Config(file, Config.YAML);
		} catch (Exception e) {
			MiniatureS.mis.getLogger().info("\n§e" + MiniatureS.Title + e.getMessage() + MiniatureS.Title
					+ file.getName() + "§4表单文件格式配置错误！请检查");
			return;
		}
		switch (config.getString("FormType").toLowerCase()) {
		case "modal":
		case "modaltype":
		case "mod":
		case "mo":
		case "m":
			(new ModalType(player, file, config)).startPY();
			break;
		case "custom":
		case "customtype":
		case "cu":
		case "c":
			(new CustomType(player, file, config)).startPY();
			break;
		case "simple":
		case "s":
		case "buttons":
		default:
			(new SimpleType(player, file, config)).startPY();
			break;
		}
	}
}
