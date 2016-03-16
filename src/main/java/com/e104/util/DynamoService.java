package com.e104.util;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.e104.ErrorHandling.DocApplicationException;



public class DynamoService {
	
	public static DynamoDB  dynamoinit(){

		DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) new AmazonDynamoDBClient(
				new ProfileCredentialsProvider()).withRegion(Regions.AP_NORTHEAST_1)
			    .withEndpoint("dynamodb.ap-northeast-1.amazonaws.com"));
			    
			    
		
		return dynamoDB;
	}
	
	public String dynamoGetItem(String tableName,String fileId) throws DocApplicationException{
		DynamoDB dynamoDB = dynamoinit();
		/*Map<String, String> map = new HashMap<String, String>();
		map.put(key, value)
		dynamoDB.getItem(new GetItemRequest().withTableName("mytable").withKey((
	            new Key().withHashKeyElement(new AttributeValue().withS("1"))));
		
		GetItemSpec spec = new GetItemSpec()
		 .withKeyConditionExpression("Id = :v_id")
		    .withValueMap(new ValueMap()
		        .withString(":v_id", "Amazon DynamoDB#DynamoDB Thread 1"));
	    .withPrimaryKey("Id", 206).withNameMap(new Map<"", "">)
	    .withProjectionExpression("Id, Title, RelatedItems[0], Reviews.FiveStar")
	    .withConsistentRead(true);

	Item item = table.getItem(spec);*/
		String userData=null;
		try{
			userData = dynamoDB.getTable(tableName).getItem("fileid", fileId).toJSON();
		
		}catch(NullPointerException e){
			e.printStackTrace();
			throw new DocApplicationException(e,11);
		}
		return userData;
	}
	
public String dynamoGetItems(String tableName,JSONArray fileIds){
	//DynamoDB dynamoDB = dynamoinit();
	JSONArray userData = new JSONArray();
	
	
	for (int i=1;i<fileIds.length();i++){
		try {
			System.out.println(dynamoGetItem(tableName,fileIds.getJSONObject(i).getString("fileId")));
			userData.put(new JSONObject(dynamoGetItem(tableName,fileIds.getJSONObject(i).getString("fileId"))));
		} catch (JSONException | DocApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	return userData.toString();
	}
	

public String GetItemRequest(String fileid,int isp){
	DynamoDB  dynamoDB = dynamoinit();
	
     // item.put("fileid", new AttributeValue(fileid));
      //item.put("fileid", new AttributeValue("906eb1c4667544219607c522fe5332e811"));
      //item.put("convert", new AttributeValue("success"));
      //Map<String, AttributeValue> item1 = new HashMap<String, AttributeValue>();
      //item.put("convert", new AttributeValue("success"));
      //item.put("isP", new AttributeValue(Integer.toString(isp)));

		//Map<String, String> item = new HashMap<String, String>();
		
		//item.put("isP", String.valueOf(isp));
		
     // GetItemSpec spec = new GetItemSpec().withPrimaryKey("fileid",fileid).withNameMap(item);
		//GetItemRequest getItemRequest = new GetItemRequest().withTableName("users").withKey(item);
				//.withExpressionAttributeNames(expressionAttributeNames);
	//	 System.out.println(dynamoDB.geti.getItem(getItemRequest));
		
     // System.out.println(dynamoDB.getTable("users").getItem("fileid","1e411903e05b4456bcfe01c7288dcde511")
    		//  .a);
      
      //GetItemResult item = dynamoDBClient.getItem(getItemRequest);
      return "";
}

	public void putItem(String tableName, Item putItem){
		DynamoDB dynamoDB = dynamoinit();
		/*AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(
				new ProfileCredentialsProvider()).withRegion(Regions.AP_NORTHEAST_1)
			    .withEndpoint("dynamodb.ap-northeast-1.amazonaws.com");
		*/
		System.out.println(dynamoDB.getTable(tableName).putItem(putItem).getPutItemResult());
		
		
	} 

	public static void main(String args[]) {
		System.out.println("Start");
		DynamoService dynamoService = new DynamoService();
		JSONArray userData = new JSONArray();
		//userData.put(new JSONObject("{\"fileid\":\"1e411903e05b4456bcfe01c7288dcde511\"},{\"fileid\":\"906eb1c4667544219607c522fe5332e811\"}"));
		//System.out.println(dynamoService.dynamoGetItems("users",userData));
		dynamoService.GetItemRequest("1e411903e05b4456bcfe01c7288dcde511", 1);
		}
	
}
