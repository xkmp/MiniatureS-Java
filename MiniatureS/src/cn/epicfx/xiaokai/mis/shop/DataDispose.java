package cn.epicfx.xiaokai.mis.shop;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.FormStatic;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.epicfx.xiaokai.mis.tool.ItemIDSunName;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.TextFormat;

public class DataDispose {
	private MiniatureS mis;

	public DataDispose(MiniatureS shop) {
		this.mis = shop;
	}

	public void SelectShopType(Player player, int data) {
		switch (data) {
		case 0:

			break;

		default:
			break;
		}
	}

	public void Shop(Player player, FormResponseSimple data) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限这样做！");
			return;
		}
		int list_int = mis.shopList.get(player.getName()).size();
		if (!player.isOp() && data != null && list_int < (data.getClickedButtonId() + 1)) {
			MakeForm.makeTip(player, TextFormat.RED + "数据解析错误：未知的数据Key或您无权限！");
			return;
		}
		if (player.isOp() && (data == null || data.getClickedButtonId() >= list_int)) {
			mis.shopList.remove(player.getName());
			mis.shopMakeForm.SelectShopType(player);
			return;
		}

	}

	public void addShopShow(Player player, FormResponseCustom data) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限这样做！");
			return;
		}
		String ShopName;
		if (data.getResponse(0) != null && data.getResponse(0) != "") {
			ShopName = String.valueOf(data.getResponse(0));
		} else {
			MakeForm.makeTip(player, TextFormat.RED + "请输入商店分页名称");
			return;
		}
		String ShopType;
		if (data.getResponse(1) != null)
			ShopType = (String.valueOf(data.getResponse(1)) == FormStatic.shopGetTypeStrings[0]) ? "All"
					: ((String.valueOf(data.getResponse(1)) == FormStatic.shopGetTypeStrings[1]) ? "Op" : "Player");
		else
			ShopType = "All";
		String[] Back_World = {};
		if (data.getResponse(2) != null && data.getResponse(2) != "")
			Back_World = String.valueOf(data.getResponse(2)).split(";");
		String[] Back_Player = {};
		if (data.getResponse(3) != null && data.getResponse(3) != "")
			Back_Player = String.valueOf(data.getResponse(3)).split(";");
		boolean ImageType = false;
		if (data.getResponse(4) != null)
			ImageType = String.valueOf(data.getResponse(4)) == FormStatic.ShopImageType[0] ? false
					: String.valueOf(data.getResponse(4)) == FormStatic.ShopImageType[1] ? true : false;
		String imagePath = null;
		if (data.getResponse(5) != null) {
			imagePath = String.valueOf(data.getResponse(5));
			if (ImageType == true)
				imagePath = ItemIDSunName.UnknownToPath(imagePath);
			else
				imagePath = null;
		}
		(new Shop(mis)).AddShopShow(player, ShopName, ShopType, Back_World, Back_Player, ImageType, imagePath);
	}

	public void Main(Player player, FormResponseSimple data) {
		if (mis.shopList.get(player.getName()) == null) {
			MakeForm.makeTip(player, TextFormat.RED + "数据解析错误：无法获取玩家视图列表(Shop Main)！");
			return;
		}
		int list_int = mis.shopList.get(player.getName()).size();
		if (!player.isOp() && data != null && list_int < (data.getClickedButtonId() + 1)) {
			MakeForm.makeTip(player, TextFormat.RED + "数据解析错误：未知的数据Key或您无权限！");
			return;
		}
		if (player.isOp() && (data == null || data.getClickedButtonId() >= list_int)) {
			mis.shopList.remove(player.getName());
			mis.shopMakeForm.MakeAddShop(player);
			return;
		}
		(new ShopMakeForm(mis)).MakeShowShopShow(player,
				mis.shopList.get(player.getName()).get(data.getClickedButtonId()));
	}
}
