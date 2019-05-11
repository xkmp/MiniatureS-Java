package cn.epicfx.ui;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.ModalFormRequestPacket;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;

public abstract class FormBase {
	public Map<String, Object> data = new LinkedHashMap<String, Object>();
	public int ID;

	public FormBase(int id, String Title, String Type) {
		ID = id;
		setTitle(Title);
		data.put("type", Type);
	}

	public void setTitle(String title) {
		data.put("title", title);
	}

	public void sendPlayer(Player player) {
		ModalFormRequestPacket packet = new ModalFormRequestPacket();
		packet.data = new GsonBuilder().setPrettyPrinting().create().toJson(data);
		packet.formId = ID;
		player.dataPacket(packet);
	}
}
