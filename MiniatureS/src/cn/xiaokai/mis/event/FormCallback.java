package cn.xiaokai.mis.event;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponse;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseModal;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.event.yousb.SBPlayerData;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.form.custom.TheForce;
import cn.xiaokai.mis.form.custom.sc.CustomReceive;
import cn.xiaokai.mis.form.custom.sc.ModalReceive;
import cn.xiaokai.mis.form.custom.sc.SimpleReceive;
import cn.xiaokai.mis.form.management.MakeManagFrom;
import cn.xiaokai.mis.form.management.ManagerProcessing;
import cn.xiaokai.mis.form.management.form.SonDispose;
import cn.xiaokai.mis.form.management.form.SonDisposeSwitch;
import cn.xiaokai.mis.form.openbt.DealWith;
import cn.xiaokai.mis.form.openbt.overfed.DonFiddle;
import cn.xiaokai.mis.myshop.MyShop;
import cn.xiaokai.mis.myshop.MyShopD;
import cn.xiaokai.mis.myshop.seek.BnsoxK;
import cn.xiaokai.mis.shop.DataDispose;
import cn.xiaokai.mis.shop.ItemCallback;

/**
 * @author Winfxk
 */
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
	 * 事件监听方法[P♂Y交易进行时~]
	 * 
	 * @param e 事件对象
	 */
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = false)
	public void onPlayerForm(PlayerFormRespondedEvent e) {
		Player player = e.getPlayer();
		if (MakeID.getByID(e.getFormID()) != null && e.getPlayer() != null) {
			SBPlayerData sb = mis.MakeFormTime.containsKey(player.getName())
					? mis.MakeFormTime.get(player.getName()) == null ? new SBPlayerData()
							: mis.MakeFormTime.get(player.getName())
					: new SBPlayerData();
			sb.Open = false;
			mis.MakeFormTime.put(player.getName(), sb);
			if (e.wasClosed() || e.getResponse() == null
					|| (!(e.getResponse() instanceof FormResponseCustom)
							&& !(e.getResponse() instanceof FormResponseSimple)
							&& !(e.getResponse() instanceof FormResponseModal)))
				return;
			mis.Menus = new Config(mis.getDataFolder() + "/Main.yml", 2);
			FormResponse data = e.getResponse();
			switch (MakeID.getByID(e.getFormID())) {
			case CustomTypeForm:
				(new CustomReceive(player, (FormResponseCustom) data)).startPY();
				break;
			case ModalTypeForm:
				(new ModalReceive(player, (FormResponseModal) data)).startPY();
				break;
			case SimpleTypeForm:
				(new SimpleReceive(player, (FormResponseSimple) data)).startPY();
				break;
			case MakeCustom:
				(new TheForce(player)).startPY((FormResponseCustom) data);
				break;
			case SettingConfig:
				(new ManagerProcessing(mis)).SettingConfig(player, (FormResponseCustom) data);
				break;
			case MyShopFormSeek:
				(new BnsoxK(player, (FormResponseSimple) data)).Switch();
				break;
			case startMyShopItem:
				(new MyShop(player)).Switch((FormResponseCustom) data);
				break;
			case MyShopItem:
				(new MyShopD(player)).MyShopItem((FormResponseSimple) data);
				break;
			case MyShopSeekMain:
				(new MyShopD(player)).SeekMain((FormResponseCustom) data);
				break;
			case newMyShopItem:
				(new MyShopD(player)).newMyShopItem((FormResponseCustom) data);
				break;
			case MyShopMain:
				(new MyShopD(player)).Main((FormResponseSimple) data);
				break;
			case SonAddOpenWindow:
				(new SonDisposeSwitch(mis, player)).addOpenWindow((FormResponseCustom) data);
				break;
			case SonAddOpenShow:
				(new SonDisposeSwitch(mis, player)).addOpenShop((FormResponseCustom) data);
				break;
			case SonAddTransferForm:
				(new SonDisposeSwitch(mis, player)).addTransfer((FormResponseCustom) data);
				break;
			case SonAddCommadnForm:
				(new SonDisposeSwitch(mis, player)).addCommand((FormResponseCustom) data);
				break;
			case SonAddTipForm:
				(new SonDisposeSwitch(mis, player)).addTip((FormResponseCustom) data);
				break;
			case SonAddButtonByType:
				(new SonDispose()).Switch(player, (FormResponseSimple) data);
				break;
			case HeadIntoTheWater:
				(new DonFiddle(player, (FormResponseCustom) data)).Rape();
				break;
			case ShowWindow:
				(new DealWith(player, (FormResponseSimple) data)).startPy();
				break;
			case MakeRemoveButton:
				(new MakeManagFrom(mis)).MakeIsRemoveButton(player, (FormResponseSimple) data);
				break;
			case MakeIsRemoveButton:
				(new ManagerProcessing(mis)).RemoveButton(player, (FormResponseModal) data);
				break;
			case PlayerShopInteract:
				(new ItemCallback(mis, player, (FormResponseCustom) data)).start();
				break;
			case AddItemToItem:
				shopDispose.AddItemToItem(player, (FormResponseCustom) data);
				break;
			case AddExpShop:
				shopDispose.AddExpShop(player, (FormResponseCustom) data);
				break;
			case AddExpSell:
				shopDispose.AddExpSell(player, (FormResponseCustom) data);
				break;
			case AddItemShop:
				shopDispose.AddItemShop(player, (FormResponseCustom) data);
				break;
			case AddItemSell:
				shopDispose.AddItemSell(player, (FormResponseCustom) data);
				break;
			case AddShopType:
				shopDispose.SelectShopType(player, ((FormResponseSimple) data).getClickedButtonId());
				break;
			case Shop:
				shopDispose.Shop(player, (FormResponseSimple) data);
				break;
			case ShopAddShop:
				shopDispose.addShopShow(player, (FormResponseCustom) data);
				break;
			case ShopMain:
				shopDispose.Main(player, (FormResponseSimple) data);
				break;
			case MainFormID:
				dispose.Main(player, (FormResponseSimple) data);
				break;
			default:
				break;
			}
		}
	}
}
