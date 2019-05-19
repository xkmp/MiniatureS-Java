package cn.epicfx.xiaokai.mis.event;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.MakeID;
import cn.epicfx.xiaokai.mis.shop.DataDispose;
import cn.epicfx.xiaokai.mis.shop.ItemCallback;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;

public class FormCallback implements Listener {
	private FormDispose dispose;
	private DataDispose shopDispose;
	private MiniatureS mis;

	/**
	 * UI数据包传回事件
	 * 
	 * @param mis 插件主类对象
	 */
	public FormCallback(MiniatureS mis) {
		dispose = new FormDispose(mis);
		shopDispose = new DataDispose(mis);
		this.mis = mis;
	}

	/**
	 * 事件监听方法
	 * 
	 * @param e 事件对象
	 */
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerForm(PlayerFormRespondedEvent e) {
		if (e.wasClosed() || e.getResponse() == null
				|| (!(e.getResponse() instanceof FormResponseCustom) && !(e.getResponse() instanceof FormResponseSimple)
						&& !(e.getResponse() instanceof FormResponseModal))
				|| e.getPlayer() == null || MakeID.getByID(e.getFormID()) == null)
			return;
		Player player = e.getPlayer();
		switch (MakeID.getByID(e.getFormID())) {
		case PlayerShopInteract:
			(new ItemCallback(mis, player, (FormResponseCustom) e.getResponse())).start();
			break;
		case AddItemToItem:
			shopDispose.AddItemToItem(player, (FormResponseCustom) e.getResponse());
			break;
		case AddExpShop:
			shopDispose.AddExpShop(player, (FormResponseCustom) e.getResponse());
			break;
		case AddExpSell:
			shopDispose.AddExpSell(player, (FormResponseCustom) e.getResponse());
			break;
		case AddItemShop:
			shopDispose.AddItemShop(player, (FormResponseCustom) e.getResponse());
			break;
		case AddItemSell:
			shopDispose.AddItemSell(player, (FormResponseCustom) e.getResponse());
			break;
		case AddShopType:
			shopDispose.SelectShopType(player, ((FormResponseSimple) e.getResponse()).getClickedButtonId());
			break;
		case Shop:
			shopDispose.Shop(player, (FormResponseSimple) e.getResponse());
			break;
		case ShopAddShop:
			shopDispose.addShopShow(player, (FormResponseCustom) e.getResponse());
			break;
		case ShopMain:
			shopDispose.Main(player, (FormResponseSimple) e.getResponse());
			break;
		case AddButtonType:

			break;
		case MainFormID:
		default:
			dispose.Main(player, (FormResponseSimple) e.getResponse());
			break;
		}
	}
}
