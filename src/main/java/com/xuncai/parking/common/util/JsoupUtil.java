/**
* @Title: JsoupUtil.java
* @Package com.haikan.iptv.common.util
* @Description: TODO
* @author mayi
* @date 2020年1月16日
* @version V1.0
*/
package com.xuncai.parking.common.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
* @ClassName: JsoupUtil
* @Description: 过滤html标签
* @author
* @date 2020年1月16日
*
*/
public class JsoupUtil {

	/**
	 * 使用自带的basicWithImages 白名单
	 * 允许的便签有a,b,blockquote,br,cite,code,dd,dl,dt,em,i,li,ol,p,pre,q,small,span,
	 * strike,strong,sub,sup,u,ul,img
	 * 以及a标签的href,img标签的src,align,alt,height,width,title属性
	 */
	private static final Whitelist whitelist = Whitelist.relaxed();//.basicWithImages();
	/** 配置过滤化参数,不对代码进行格式化 */
	private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
	static {
		// 富文本编辑时一些样式是使用style来进行实现的
		// 比如红色字体 style="color:red;"
		// 所以需要给所有标签添加style属性
		whitelist.addTags("div");
		whitelist.addAttributes(":all", "style","class","id","name");
		whitelist.addAttributes("img", "data-cke-realelement","data-cke-real-node-type","alt","title","align","src","data-cke-real-element-type");
	}

	public static String clean(String content) {
		return Jsoup.clean(content, "", whitelist, outputSettings);
	}
	
}
