package cn.xiaokai.mis.event;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerInteractEvent.Action;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerRespawnEvent;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.event.yousb.Fuck;
import cn.xiaokai.mis.myshop.CPlayer;
import cn.xiaokai.mis.tool.Tool;

/**
 * @author Winfxk
 */
public class PlayerEvent implements Listener {
	private MiniatureS mis;

	/**
	 * 监听玩家事件
	 * 
	 * @param mis 插件主类对象
	 */
	public PlayerEvent(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 玩家滚蛋事件
	 * 
	 * @param e
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (mis.sb.containsKey(e.getPlayer().getName()))
			mis.sb.remove(e.getPlayer().getName());
		if (mis.PlayerMenuData.containsKey(e.getPlayer().getName()))
			mis.PlayerMenuData.remove(e.getPlayer().getName());
		if (mis.PlayerAddButtonByOpenShop.containsKey(e.getPlayer().getName()))
			mis.PlayerAddButtonByOpenShop.remove(e.getPlayer().getName());
		if (mis.PlayerMenuBack.containsKey(e.getPlayer().getName()))
			mis.PlayerMenuBack.remove(e.getPlayer().getName());
		if (mis.PlayerMenu.containsKey(e.getPlayer().getName()))
			mis.PlayerMenu.remove(e.getPlayer().getName());
		if (mis.PlayerShopInteract.containsKey(e.getPlayer().getName()))
			mis.PlayerShopInteract.remove(e.getPlayer().getName());
		if (mis.PlayerShopItemData.containsKey(e.getPlayer().getName()))
			mis.PlayerShopItemData.remove(e.getPlayer().getName());
		if (mis.RemoveButtonFile.containsKey(e.getPlayer().getName()))
			mis.RemoveButtonFile.remove(e.getPlayer().getName());
		if (mis.RemoveButtonKeyList.containsKey(e.getPlayer().getName()))
			mis.RemoveButtonKeyList.remove(e.getPlayer().getName());
		if (mis.RemoveButtonKeyID.containsKey(e.getPlayer().getName()))
			mis.RemoveButtonKeyID.remove(e.getPlayer().getName());
	}

	/**
	 * 监听玩家吃多了撑着去破坏方块的事件
	 * 
	 * @param e
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerBreak(BlockBreakEvent e) {
		String makeTool = mis.config.getString("快捷工具", null);
		if (makeTool == null || makeTool.equals(""))
			return;
		Item item = e.getItem();
		if (item != null && Tool.isMateID(item.getId() + ":" + item.getDamage(), makeTool)
				&& e.getPlayer().getGamemode() == 1) {
			e.setCancelled();
			if (!mis.config.getBoolean("快捷打开为商店"))
				mis.makeForm.makeMain(e.getPlayer());
			else
				mis.shopMakeForm.MakeMain(e.getPlayer());
		}
	}

	/**
	 * 监听玩家瞎几把点瞎几把搞事件事件
	 * 
	 * @param e
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerInteract(PlayerInteractEvent e) {
		String makeTool = mis.config.getString("快捷工具", null);
		if (makeTool == null || makeTool.equals(""))
			return;
		Item item = e.getItem();
		if (item != null && e.getAction() != null
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& Tool.isMateID(item.getId() + ":" + item.getDamage(), makeTool) && e.getPlayer().getGamemode() != 1) {
			e.setCancelled();
			if (!mis.config.getBoolean("快捷打开为商店"))
				mis.makeForm.makeMain(e.getPlayer());
			else
				mis.shopMakeForm.MakeMain(e.getPlayer());
		}
	}

	/**
	 * 监听玩家嗝了屁重新破壳的事件
	 * 
	 * @param e
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerSpawn(PlayerRespawnEvent e) {
		String makeTool = mis.config.getString("快捷工具", null);
		if (makeTool == null || makeTool.equals(""))
			return;
		Player player = e.getPlayer();
		Inventory inventory = player.getInventory();
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
			item.setCustomName(mis.getMessage().getMessage("快捷工具名称", new String[] { "{Player}", "{Server_Name}" },
					new String[] { player.getName(), mis.getServer().getMotd() }));
			item.setLore(mis.getMessage().getMessage("快捷工具名称2", new String[] { "{Player}", "{Server_Name}" },
					new String[] { player.getName(), mis.getServer().getMotd() }));
			inventory.addItem(item);
			player.sendMessage(mis.getMessage().getMessage("进服给快捷工具", new String[] { "{Player}", "{Server_Name}" },
					new String[] { player.getName(), mis.getServer().getMotd() }));
		}
	}

	/**
	 * 监听玩家加入服务器♂事件
	 * 
	 * @param e
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onJoin(PlayerJoinEvent e) {
		String makeTool = mis.config.getString("快捷工具", null);
		if (makeTool == null || makeTool.equals(""))
			return;
		Player player = e.getPlayer();
		Inventory inventory = player.getInventory();
		Map<Integer, Item> map = inventory.getContents();
		if (!CPlayer.isPlayerConfig(player)) {
			Config cp = CPlayer.getPlayerConfig(player);
			player.sendMessage(mis.getMessage().getMessage("玩家加入提示1"));
			cp.setAll(CPlayer.getConfig());
			cp.save();
		}
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
			item.setCustomName(mis.getMessage().getMessage("快捷工具名称", new String[] { "{Player}", "{Server_Name}" },
					new String[] { player.getName(), mis.getServer().getMotd() }));
			item.setLore(mis.getMessage().getMessage("快捷工具名称2", new String[] { "{Player}", "{Server_Name}" },
					new String[] { player.getName(), mis.getServer().getMotd() }));
			inventory.addItem(item);
			player.sendMessage(mis.getMessage().getMessage("进服给快捷工具", new String[] { "{Player}", "{Server_Name}" },
					new String[] { player.getName(), mis.getServer().getMotd() }));
		}
		(new Fuck()).Switch(player);
	}
}
