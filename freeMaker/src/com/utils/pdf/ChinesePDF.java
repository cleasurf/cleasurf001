package com.utils.pdf;

import java.awt.Color;
import java.io.FileOutputStream;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;



public class ChinesePDF {
	/** 
	 * @ClassName: ChinesePDF 
	 * @Description: TODO(这里用一句话描述这个类的作用) 
	 * @author 梁雷
	 * @date Sep 7, 2012 10:06:23 AM 
	 *  
	 */
	public static void main(String[] args)throws Exception{
		// 创建一个对中文字符集支持的基础字体
		BaseFont bfChinese = BaseFont.createFont("STSong-Light",
				"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font font = new Font(bfChinese, 12, Font.BOLD, Color.RED); // 使用基础字体对象创建新字体对像，粗体12号红色字
		Document document = new Document(PageSize.A4); // 创建document对象
		PdfWriter.getInstance(document, new FileOutputStream("d://梁雷.pdf"));// 创建书写器
		document.open(); // 打开文档
		String context = "woainilalalallaeeeeeeeeeeeeeeew中文可以些好吗！"; // 文档内容
		Paragraph paragraph = new Paragraph(context, font); // 创建段落，并设置字体
		paragraph.setAlignment(Paragraph.ALIGN_CENTER); // 设置段落居中
		document.add(paragraph); // 将段落添加到文档中
		document.close();
		System.out.print("pdf文档创建完成！");
	}
}
