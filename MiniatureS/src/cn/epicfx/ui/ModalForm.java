package cn.epicfx.ui;

import cn.nukkit.utils.TextFormat;

public class ModalForm extends FormBase {

	public ModalForm(int ID, String Title, String Bt1, String Bt2) {
		super(ID, Title, "modal_form");
		data.put("button1", Bt1);
		data.put("button2", Bt2);
	}

	public ModalForm(int ID) {
		super(ID, "", "modal_form");
		data.put("button1", TextFormat.GREEN + "确定");
		data.put("button2", TextFormat.RED + "取消");
	}

	public ModalForm(int ID, String Title) {
		super(ID, Title, "modal_form");
		data.put("button1", TextFormat.GREEN + "确定");
		data.put("button2", TextFormat.RED + "取消");
	}

	public void setContent(String text) {
		data.put("content", text);
	}

	public void setButton1(String text) {
		data.put("button1", text);
	}

	public void setButton2(String text) {
		data.put("button2", text);
	}
}
