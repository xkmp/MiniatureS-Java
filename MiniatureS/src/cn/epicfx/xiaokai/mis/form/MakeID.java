package cn.epicfx.xiaokai.mis.form;

import java.util.HashMap;
import java.util.Map;

public enum MakeID {
	MainFormID(97005423), SetConfig(165412354), AddButtonType(1563165423), ShopMain(755453486), ShopAddShop(168854321),
	Shop(45435453), AddShopType(231635415), AddItemSell(564357421), AddItemShop(456123156);
	private int code;

	private static final Map<Integer, MakeID> MAP = new HashMap<>();

	static {
		for (MakeID item : MakeID.values()) {
			MAP.put(item.code, item);
		}
	}

	public static MakeID getByString(String code) {
		return MAP.get(Integer.valueOf(code));
	}

	public static MakeID getByID(int code) {
		return MAP.get(code);
	}

	public int getID() {
		return code;
	}

	private MakeID(int code) {
		this.code = code;
	}
}
