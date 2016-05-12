package com.mangocity.mybatis.util;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Jsons {

	public static void main(String[] args) {
		String jsonStr2 = "{\"passList\":[{\"BIRTHDAY\":\"2012-11-12\",\"CHI_NAME\":\"刘春元\",\"GENDER\":\"11\",\"MBR_ID\":\"2867168\","
				+ "\"MOBILE_NO\":\"18576773226\",\"PASS_ID\":\"42\",\"certificateList\":[{\"CER_NO\":\"43252419888899\",\"CER_TYPE\":\"IDC\"}]},"
				+ "{\"BIRTHDAY\":\"2012-11-12\",\"CHI_NAME\":\"刘春元2\",\"GENDER\":\"11\",\"MBR_ID\":\"2867168\","
				+ "\"MOBILE_NO\":\"18576773226\",\"PASS_ID\":\"42\",\"certificateList\":[{\"CER_NO\":\"43252419887766\",\"CER_TYPE\":\"IDC\"}]}]}";
		JSONObject jsonObj = JSON.parseObject(jsonStr2);
		String str = "passList.certificateList.CER_NO";
		String[] strArr = str.split("\\.");
		List<String> resultList = New.list();
		AtomicLong count = new AtomicLong(0);
		resultList = recursiveJSON(jsonObj, strArr,resultList,count);
		System.out.println("resultList: " + resultList + " ,count: " + count.get());
	}

	/**
	 */
	/**
	 * 用于递归取出所有指定层级的JSON对应值
	 * such as: 
		     {"passList": [
		            {
		                "BIRTHDAY": "2012-11-12", 
		                "CHI_NAME": "刘春元", 
		                "GENDER": "11", 
		                "MBR_ID": "2867168", 
		                "MOBILE_NO": "18576773226", 
		                "PASS_ID": "42", 
		                "certificateList": [
		                    {
		                        "CER_NO": "43252419887777", 
		                        "CER_TYPE": "IDC"
		                    }
		                ]
		            }
		        ]
		    }
		strArr[]: ("passList.certificateList.CER_NO").split("\\.");	
	 * @param jsonObj
	 * @param strArr
	 * @param resultList  存储值
	 * @param nullValCounter 对应层级空值的记录数  用于校验是否参数必填
	 * @return
	 */
	public static List<String> recursiveJSON(JSONObject jsonObj, String[] strArr,List<String> resultList,AtomicLong nullValCounter) {
		Object val = jsonObj.get(strArr[0]);
		JSONArray jsonArr = null;
		String[] subArr = null;
		if (strArr.length != 1) {
			subArr = new String[strArr.length - 1];
			System.arraycopy(strArr, 1, subArr, 0, strArr.length - 1);
		}
		if (val instanceof JSONArray) {
			jsonArr = (JSONArray) val;
			for (int i = 0, len = jsonArr.size(); i < len; i++) {
				recursiveJSON(jsonArr.getJSONObject(i), subArr,resultList,nullValCounter);
			}
		} else if (val instanceof JSONObject) {
			recursiveJSON((JSONObject) val, subArr,resultList,nullValCounter);
		} else if (null == val) {
			nullValCounter.incrementAndGet();
		} else {
			resultList.add(String.valueOf(val));
		}
		return resultList;
	}

}
