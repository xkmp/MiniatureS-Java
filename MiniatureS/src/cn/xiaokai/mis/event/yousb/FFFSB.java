package cn.xiaokai.mis.event.yousb;

import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;

/**
 * @author Winfxk
 */
public class FFFSB {
	private Player player;
	private MiniatureS mis;
	private PlayerInventory inventory;

	/**
	 * 玩家快捷工具检查类
	 * 
	 * @param player
	 */
	public FFFSB(Player player) {
		this.player = player;
		mis = MiniatureS.mis;
		inventory = player.getInventory();
	}

	/**
	 * 检测并判断玩家是否有快捷工具，没有的话给与一个
	 */
	public void getMis() {
		if (!isSB()) {
			inventory.addItem(getItem());
			player.sendMessage(mis.getMessage().getMessage("进服给快捷工具"));
		}
	}

	/**
	 * 获取一个配置完毕的快捷工具的物品对象
	 * 
	 * @return
	 */
	public static Item getItem() {
		String idString = ItemIDSunName.UnknownToID(MiniatureS.mis.config.getString("快捷工具"));
		String[] strings = idString.split(":");
		int ID = Integer.valueOf(strings[0]);
		int Meta = Integer.valueOf(strings[1]);
		Item item = new Item(ID, Meta);
		item.setCustomName(MiniatureS.mis.getMessage().getMessage("快捷工具名称"));
		item.setLore(MiniatureS.mis.getMessage().getMessage("快捷工具名称2"));
		CompoundTag Tag = item.getNamedTag() == null ? new CompoundTag() : item.getNamedTag();
		Tag.putString("快捷工具名称", MiniatureS.mis.getMessage().getMessage("快捷工具名称"));
		item.setNamedTag(Tag);
		item.addEnchantment(Enchantment.get(Enchantment.ID_SILK_TOUCH));
		return item;
	}

	/**
	 * 判断玩家背包是否有快捷工具
	 * 
	 * @return
	 */
	public boolean isSB() {
		Map<Integer, Item> items = inventory.getContents();
		MiniatureS mis = MiniatureS.mis;
		String ID = ItemIDSunName.UnknownToID(mis.config.getString("快捷工具"));
		for (Integer ike : items.keySet()) {
			Item item = items.get(ike);
			if (Tool.isMateID(ID, item.getId() + ":" + item.getDamage())) {
				CompoundTag Tag = item.getNamedTag();
				if (Tag != null) {
					String string = Tag.getString("快捷工具名称");
					if (string != null && string.equals(mis.getMessage().getMessage("快捷工具名称")))
						return true;
				}
			}
		}
		return false;
	}

}
