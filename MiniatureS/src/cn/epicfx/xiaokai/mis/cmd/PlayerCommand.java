package cn.epicfx.xiaokai.mis.cmd;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

public class PlayerCommand {
	private MiniatureS mis;

	public PlayerCommand(MiniatureS mis) {
		this.mis = mis;
	}

	public boolean onCommand(CommandSender player, Command command, String label, String[] a) {
		switch (command.getName()) {
		case "shop":
			if (a.length < 1) {
				if (player.isPlayer()) {
					mis.shopMakeForm.MakeMain((Player) player);
					return true;
				} else
					return onCommand(player, command, label, new String[] { "help" });
			}
			break;
		default:
			break;
		}
		return false;
	}
}
