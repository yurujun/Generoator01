package com.yzr.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XMLUtil {
	@SuppressWarnings("unchecked")
	public static void modify(Map<String, Object> map) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(XMLUtil.class.getClassLoader().getResourceAsStream("generator.xml"));
			Element root = document.getRootElement();
			List<Element> entrys = root.getChildren("entry");
			for (Element element : entrys) {
				List<Attribute> list = element.getAttributes();
				for (int i = 0; i < list.size(); i++) {
					Attribute attribute = list.get(i);
					if (map.containsKey(attribute.getValue())) {
						element.setText(map.get(attribute.getValue()).toString());
					}
				}
			}
			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat().setIndent("\t"));
			String outPath = XMLUtil.class.getClassLoader().getResource("/") + "generator.xml";
			outPath = outPath.substring(outPath.indexOf("/") + 1);
			outputter.output(document, new FileWriter(outPath));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
