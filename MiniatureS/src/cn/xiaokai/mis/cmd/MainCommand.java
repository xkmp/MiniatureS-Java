package cn.xiaokai.mis.cmd;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.tool.Tool;

/**
 * @author Winfxk
 */
public class MainCommand {
	private MiniatureS mis;

	public MainCommand(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 处理插件主命令
	 * 
	 * @param player 玩家对象
	 * @param label
	 * @param a      命令标签
	 * @return
	 */
	public boolean onCommand(CommandSender player, String label, String[] a) {
		if (a.length < 1 || a[0].toLowerCase().equals("get") || a[0].toLowerCase().equals("获取")) {
			if (player.isPlayer()) {
				String makeTool = mis.config.getString("快捷工具", null);
				if (makeTool != null && !makeTool.equals("")) {
					Inventory inventory = ((Player) player).getInventory();
					Map<Integer, Item> map = inventory.getContents();
					Item item;
					boolean Mate = false;
					for (int site : map.keySet()) {
						item = map.get(site);
						if (Mate = Tool.isMateID(item.getId() + ":" + item.getDamage(), makeTool))
							break;
					}
					if (!Mate) {
						int[] ID = Tool.IDtoFullID(makeTool);
						item = new Item(ID[0], ID[1]);
						item.addEnchantment(Enchantment.get(Enchantment.ID_SILK_TOUCH));
						item.setCustomName(
								mis.getMessage().getMessage("快捷工具名称", new String[] { "{Player}", "{Server_Name}" },
										new String[] { player.getName(), mis.getServer().getMotd() }));
						item.setLore(
								mis.getMessage().getMessage("快捷工具名称2", new String[] { "{Player}", "{Server_Name}" },
										new String[] { player.getName(), mis.getServer().getMotd() }));
						inventory.addItem(item);
						player.sendMessage(
								mis.getMessage().getMessage("进服给快捷工具", new String[] { "{Player}", "{Server_Name}" },
										new String[] { player.getName(), mis.getServer().getMotd() }));
					}
				}
				mis.makeForm.makeMain((Player) player);
			} else
				player.sendMessage(TextFormat.RED + "请在游戏内执行此命令！");
			return true;
		}
		switch (a[0].toLowerCase()) {
		case "help":
		default:

			break;
		}
		return false;
	}
}
