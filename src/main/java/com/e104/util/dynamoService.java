package com.e104.util;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.e104.ErrorHandling.DocApplicationException;



public class dynamoService {
	
	public static DynamoDB dynamoinit(){
		BasicAWSCredentials myCredentials = new BasicAWSCredentials("AKIAJXGZ6BOIVQHUNNAQ", "9w21SKeTGh5NAiLsrditOv2qQKdN8lFKs9aZKU36");
		
		DynamoDB dynamoDB = new DynamoDB((AmazonDynamoDB) new AmazonDynamoDBClient(
				myCredentials).withRegion(Regions.AP_NORTHEAST_1)
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
	

	public static void main(String args[]) {
		dynamoService dynamoService = new dynamoService();
		JSONArray userData = new JSONArray();
		userData.put(new JSONObject("{\"fileid\":\"1e411903e05b4456bcfe01c7288dcde511\"},{\"fileid\":\"906eb1c4667544219607c522fe5332e811\"}"));
		System.out.println(dynamoService.dynamoGetItems("users",userData));
		}
	
}
