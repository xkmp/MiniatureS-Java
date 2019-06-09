package cn.xiaokai.mis.event;

import java.time.Duration;
import java.time.Instant;

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
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.event.yousb.FFFSB;
import cn.xiaokai.mis.event.yousb.Fuck;
import cn.xiaokai.mis.event.yousb.SBPlayerData;
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
		Player player = e.getPlayer();
		SBPlayerData data = mis.MakeFormTime.containsKey(player.getName())
				? mis.MakeFormTime.get(player.getName()) == null ? new SBPlayerData()
						: mis.MakeFormTime.get(player.getName())
				: new SBPlayerData();
		if (item != null && Tool.isMateID(item.getId() + ":" + item.getDamage(), makeTool)
				&& (data == null || data.Open != true
						|| ((float) (Duration.between(data.OpenTime, Instant.now()).toMillis()) / 1000) > 1)) {
			e.setCancelled();
			data.Open = true;
			data.OpenTime = Instant.now();
			mis.MakeFormTime.put(player.getName(), data);
			if (!mis.config.getBoolean("快捷打开为商店"))
				mis.makeForm.makeMain(player);
			else
				mis.shopMakeForm.MakeMain(player);
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
		CompoundTag Tag = item.getNamedTag();
		Action action = e.getAction();
		Player player = e.getPlayer();
		SBPlayerData data = mis.MakeFormTime.containsKey(player.getName())
				? mis.MakeFormTime.get(player.getName()) == null ? new SBPlayerData()
						: mis.MakeFormTime.get(player.getName())
				: new SBPlayerData();
		if (item != null && Tool.isMateID(makeTool, item.getId() + ":" + item.getDamage())
				&& Tag.getString("快捷工具名称").equals(mis.getMessage().getMessage("快捷工具名称")) && action != null
				&& (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK
						|| action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
				&& (data == null || data.Open != true
						|| ((float) (Duration.between(data.OpenTime, Instant.now()).toMillis()) / 1000) > 1)) {
			e.setCancelled();
			data.Open = true;
			data.OpenTime = Instant.now();
			mis.MakeFormTime.put(player.getName(), data);
			if (!mis.config.getBoolean("快捷打开为商店"))
				mis.makeForm.makeMain(player);
			else
				mis.shopMakeForm.MakeMain(player);
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
		FFFSB sb = new FFFSB(e.getPlayer());
		sb.getMis();
	}

	/**
	 * 监听玩家加入服务器♂事件
	 * 
	 * @param e
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		String makeTool = mis.config.getString("快捷工具", null);
		(new Fuck()).Switch(player);
		if (makeTool == null || makeTool.equals(""))
			return;
		FFFSB sb = new FFFSB(player);
		sb.getMis();
	}
}
