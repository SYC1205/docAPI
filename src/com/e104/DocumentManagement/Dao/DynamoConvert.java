package com.e104.DocumentManagement.Dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;





import org.json.JSONException;
import org.json.JSONObject;






import com.amazonaws.services.dynamodbv2.document.Item;
import com.e104.ErrorHandling.DocApplicationException;
import com.e104.util.DynamoService;
import com.e104.util.tools;

public class DynamoConvert {
	private String fileid;
	private int contenttype;
	private String apnum;
	private String filepath;
	private String insertDate;
	private String triggerDate;
	private Map<String, String> status = new HashMap<String, String>();
	private List<String> convertItems = new ArrayList<>();
	private Map<String, String> videoQuality;
	tools tools = new tools();
	public void insertDynamo(JSONObject convert) throws DocApplicationException{
		try{
		fileid = convert.getString("fileid");
		contenttype = convert.getInt("contenttype");
		apnum = convert.getString("apnum");
		filepath = convert.getString("filepath");
		insertDate = convert.getString("insertDate");
		triggerDate = convert.getString("triggerDate");
		
		
		for (int i=0;i<convert.getJSONArray("convertItems").length();i++ ){
			convertItems.add(convert.getJSONArray("convertItems").getJSONObject(i).toString());
		}
		
		status = tools.json2Map(convert.getJSONObject("status"));
		if (convert.has("videoQuality"))
			videoQuality = new HashMap<String,String>(tools.json2Map(convert.getJSONObject("videoQuality")));
		}catch(JSONException e){
			throw new DocApplicationException("NotPresent",3);//erroehandler 必填欄位未填
		}
		
		this.doInsertDb();
		
	}
	private void doInsertDb() throws DocApplicationException{
		DynamoService dynamoService = new DynamoService();
		try{
		Item putItem = new Item().withPrimaryKey("fileid",fileid).
				withNumber("contenttype", contenttype).
				withString("apnum", apnum).
				withString("filepath",filepath).
				withString("insertDate", insertDate).
				withString("triggerDate", triggerDate).
				withMap("status", status).
				withList("convertItems",convertItems);
		//非必填項目
		if(videoQuality!=null) 
			putItem.withMap("videoQuality", videoQuality);
			
		
		dynamoService.putItem("convert", putItem);
		}catch(Exception e){
			throw new DocApplicationException("NotPresent",3);//erroehandler 必填欄位未填
		}
	}
	
	
}
