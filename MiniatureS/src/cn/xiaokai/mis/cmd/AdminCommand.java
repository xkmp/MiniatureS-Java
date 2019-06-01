package cn.xiaokai.mis.cmd;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.management.MakeManagFrom;

public class AdminCommand {
	private MiniatureS mis;

	public AdminCommand(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 管理命令的处理
	 * 
	 * @param player 触发命令的玩家对象
	 * @param label
	 * @param a      命令内容
	 * @return
	 */
	public boolean onCommand(CommandSender player, String label, String[] a) {
		if (a.length < 1) {
			if (player.isPlayer()) {
				(new MakeManagFrom(mis)).MakeSettingConfig((Player) player);
			} else
				player.sendMessage(mis.getMessage().getSon("Admin", "NotPlayer"));
			return true;
		}
		switch (a[0].toLowerCase()) {
		case "help":
			player.sendMessage(TextFormat.RED + "/admis ui 打开配置界面");
			if (!player.isPlayer())
				player.sendMessage(mis.getMessage().getSon("Admin", "NotPlayer"));
			return true;
		case "ui":
		case "shop":
		default:
			if (player.isPlayer()) {
				(new MakeManagFrom(mis)).MakeSettingConfig((Player) player);
			} else
				player.sendMessage(mis.getMessage().getSon("Admin", "NotPlayer"));
			return true;
		}
	}
}
