package com.mangocity.mybatis.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * 
* @ClassName: MapCustomConverter 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author Syungen
* @date 2015年8月25日 下午2:51:04 
*
 */
public class MapCustomConverter extends AbstractCollectionConverter {

	/**
	 * @param mapper
	 */
	public MapCustomConverter(Mapper mapper) {
		super(mapper);
	}

	@SuppressWarnings("rawtypes")
	public boolean canConvert(Class type) {
		return type.equals(HashMap.class) || type.equals(Hashtable.class)
				|| type.getName().equals("java.util.LinkedHashMap")
				|| type.getName().equals("sun.font.AttributeMap") // Used by
																	// java.awt.Font
																	// in JDK 6
		;
	}

	@SuppressWarnings("rawtypes")
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		Map map = (Map) source;
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry entry = (Entry) iterator.next();
			ExtendedHierarchicalStreamWriterHelper.startNode(writer,
					"property", Entry.class);

			writer.addAttribute("key", entry.getKey().toString());
			writer.addAttribute("value", entry.getValue().toString());
			writer.endNode();
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		//Map map = (Map) createCollection(context.getRequiredType());
		Map<String,String> map = new HashMap<String, String>();
		populateMap(reader, context, map);
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void populateMap(HierarchicalStreamReader reader,
			UnmarshallingContext context, Map map) {
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			Object key = reader.getAttribute("key");
			Object value = reader.getAttribute("value");
			map.put(key, value);
			reader.moveUp();
		}
	}
}
