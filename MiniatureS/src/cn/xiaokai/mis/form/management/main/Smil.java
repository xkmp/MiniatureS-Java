package cn.xiaokai.mis.form.management.main;

import cn.nukkit.form.response.FormResponseCustom;
import cn.xiaokai.mis.form.FormStatic;
import cn.xiaokai.mis.tool.ItemIDSunName;
/**
 * @author Winfxk
 */
public class Smil {
	private int SBFFF = 0;
	private String Path;

	/**
	 * 获取按钮贴图数据
	 * 
	 * @param data 表单数据
	 * @param ID   贴图类型的ID
	 * @param DI   贴图路径的ID
	 */
	public Smil(FormResponseCustom data, int ID, int DI) {
		SBFFF = ((String.valueOf(data.getResponse(ID)) == FormStatic.ButtonImageType[0]) ? 0
				: ((String.valueOf(data.getResponse(ID)) == FormStatic.ButtonImageType[1]) ? 1 : 2));
		Path = ((SBFFF == 0) ? null
				: ((SBFFF == 1) ? ItemIDSunName.UnknownToPath(String.valueOf(data.getResponse(DI)))
						: String.valueOf(data.getResponse(DI))));
	}

	/**
	 * 获取图片类型
	 * 
	 * @return
	 */
	public boolean getImageType() {
		return SBFFF == 1 ? true : false;
	}

	/**
	 * 获取贴图路径
	 * 
	 * @return
	 */
	public String getPath() {
		return Path;
	}
}
