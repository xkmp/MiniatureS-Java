package cn.xiaokai.mis.form.custom.sc;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;

/**
 * @author Winfxk
 */
public class SendCommand {
	/**
	 * 执行命令
	 * 
	 * @param player    可能会执行的玩家对象
	 * @param Command   命令的内容
	 * @param Commander 执行命令的对象 Console|Player
	 */
	public static void toCommand(Player player, String Command, String Commander) {
		switch (Commander.toLowerCase()) {
		case "console":
		case "c":
		case "控制台":
			Server.getInstance().dispatchCommand(new ConsoleCommandSender(), Command);
			break;
		case "player":
		case "p":
		case "玩家":
		default:
			Server.getInstance().dispatchCommand(player, Command);
			break;
		}
	}
}
