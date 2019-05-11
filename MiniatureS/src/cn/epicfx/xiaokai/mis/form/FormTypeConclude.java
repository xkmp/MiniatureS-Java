package cn.epicfx.xiaokai.mis.form;

import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.nukkit.Player;

public class FormTypeConclude {
	private MiniatureS mis;
	private Map<String, Object> map;
	private String BackForm;

	public FormTypeConclude(MiniatureS miS, Map<String, Object> map) {
		this(miS, map, null);
	}

	public FormTypeConclude(MiniatureS miS, Map<String, Object> map, String BackForm) {
		this.map = map;
		this.mis = miS;
		this.BackForm = BackForm;
	}

	public void toMain(Player player) {
		switch ((String) map.get("Type")) {
		case "make":
			
			break;
		default:
			break;
		}
	}
}
