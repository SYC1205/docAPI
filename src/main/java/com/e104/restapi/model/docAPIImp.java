package com.e104.restapi.model;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.e104.ErrorHandling.DocApplicationException;
import com.e104.restapi.docAPI;
import com.e104.util.dynamoService;
import com.e104.util.tools;

public class docAPIImp implements docAPI{
	private static transient Logger Logger = org.apache.log4j.Logger.getLogger(docAPIImp.class);
	String bucketName = "e104-doc-api-file-store";
	String objectKey = "123/456/test.txt";
	tools tools = new tools();
	@Override
	public String addKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkFileSpec(String specObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String clearFileCache(String cacheSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String confirmUpload(String fileid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String copyFile(String fileObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String copyFileForMM(String fileObj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String decryptParam(String param) {
		param = tools.decode(param);
    	return param;
	}

	@Override
	public String encryptParam(String param) throws DocApplicationException {
		try{
			JSONObject obj = new JSONObject(param);
	    	
	    	param = tools.encode(obj.toString());
		}
    	catch(JSONException e){
    		throw new DocApplicationException("NotParsed", 1);
    	}
    	return param;    
	}

	@Override
	public String deleteFile(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String discardFile(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String generateFileId(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCheck() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFile(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFileDetail(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFileList(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFileUrl(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQueueLength() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String putFile(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeKey(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setExpireTimestamp(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateFile(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String doc2img(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatus(String fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setConvertStatus(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateData(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String videoConvert(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String audioConvert(String Param) {
		// TODO Auto-generated method stub
		return null;
	}

	//doing##############################################################3
		@Override
		public String getFileUrlnoRedis(String Object) {
			
			JSONArray rtn = new JSONArray(); //回傳的JSONArray
			tools tools = new tools();
			String jsonObject = tools.decode(Object);
			
			dynamoService dynamoService = new dynamoService();
			JSONObject jsonObj = new JSONObject(jsonObject);
			String returnStr ="";
			System.out.println("ok"+jsonObj.getJSONArray("getFileArr").toString());
			
			try{
			if((Object != null && !"".equals(Object.trim())) && 
				(jsonObj.has("timestamp") && 
			    !"".equals(jsonObj.getString("timestamp"))) &&
			    (jsonObj.has("getFileArr") && 
			    !"".equals(jsonObj.getJSONArray("getFileArr").toString()))){
			
				String timestamp = jsonObj.getString("timestamp");
				//JSONArray jsonarr =  jsonObj.getJSONArray("getFileArr");
				// 針對不在 cache 中的資料進行 mongo 查詢.
				JSONArray userData = jsonObj.getJSONArray("getFileArr");
				
				
				JSONArray users = new JSONArray(dynamoService.dynamoGetItems("users",userData));
				JSONObject jomongos= new JSONObject();	 // 從 mongo 中查詢到, 且未被 disable 的資料
					
				
					
					for(int i = 0; i < users.length(); i++){
						//判斷資料是否被砍
						System.out.println(users.getJSONObject(i));
						JSONObject user = users.getJSONObject(i);
						if(user.has("disabled") && user.get("disabled").toString().equals("1")) continue;
						jomongos.put(user.getString("fileid"), user);
					}		
					//將JSON轉為jsonarray
					//String msql="";
					JSONArray jsonarr1 = jsonObj.getJSONArray("getFileArr");
					
					/*replace all fileid_uuid xxxxxxaa to fileid*/
					// modify by jj on 2013-12-04 fix replace UUIDaa to fileId 
					
					JSONObject fileidUUIDaaObjMap = new JSONObject();
					JSONObject queryFileIdAndUUIDaaMap = new JSONObject();	 // 用於紀錄 fileid <-> filidaa 對應. 於 getFileUrl 解析完成後置換回來
					
					// logger.info("htmlToLink: getFileUrl replace before"+ jsonarr1.toString());
					for(int i=0;i<jsonarr1.length();i++){
						String fileid_temp = jsonarr1.getJSONObject(i).getString("fileId");
						
						
						if(!tools.isEmpty(fileid_temp)){		

							String real_fileid = null;
							
							if(fileid_temp.endsWith("aa")){
								
								String fileid_aa = fileid_temp;			// 重新正名區域變數, 避免混淆.
								
//								JSONObject fileidFileaaMapObj = new JSONObject(); 
								// fileid aa 的 key 不像 getFileUrl 的參數那麼多, 因此 key 值採用簡單處理 (不用json string 來呈現).
								String cacheKey = "fUrl:aa:" + fileid_aa;
								
								
								
								// 若 aa fileid 找不到對應的 cache, 就到 mongo 抓.
								// if(real_fileid == null){
								if(tools.isEmpty(real_fileid)){	
									JSONObject uuidaaObj = new JSONObject(tools.html_img_fileid(fileid_aa));
//									logger.info("load fileidaa [" + fileid_aa + "] => " + uuidaaObj.toString());
									
									if(!tools.isEmpty(uuidaaObj, "fileId")){
										real_fileid = uuidaaObj.getString("fileId");
									}

									// uuidaaObj 有可能有資料, 卻沒有 fileId, 因為 putFile 還在執行中就收到 getFileUrl 請求了.
									fileidUUIDaaObjMap.put(tools.isEmpty(real_fileid) ? fileid_aa : real_fileid, uuidaaObj);
//									fileidUUIDaaObjMap.put(fileid_aa, uuidaaObj);		// user always query by fileidaa.
									
//									logger.info("fileidUUIDaaObjMap => " + fileidUUIDaaObjMap.toString());
									
//									real_fileid = tools.html_img_fileid(fileid_temp);
									
//									if(!isEmpty(real_fileid)){
//										// 設置 aa fileid 對應的實際 fileid.
//										setUrlCache(cacheKey, real_fileid);
//									}
								}							
								
								// 若 real_fileid 仍是空值, 表示 fileidaa 在 htmllink 中也不存在
								if(tools.isEmpty(real_fileid)){
									// 對應的 fileid 不存在, 以 fileidaa 做為 fileid 以讓後續程序能執行.
									
									// 若轉貼連  putFile 還在執行中就收到 getFileUrl 請求了.
									// 這時有可能 real_fileid 是空的, 但 uuidaaObj 有值, 
									// 這裡將 real_fileid 換成 uuidaa 讓後續能夠呈現目前 fileidUuidaaMap 的轉檔狀態 (存放於 fileidUUIDaaObjMap 中的 uuidaaObj)
									real_fileid = fileid_temp;
								}
								
//								fileidFileaaMapObj.put(real_fileid, fileid_temp);
//								fileidUUIDaaObjMap.put(real_fileid, fileidFileaaMapObj);
								// logger.info("htmlToLink getFileUrl fileid_temp-->"+ fileid_temp+" real_fileid-->" +real_fileid);
								// logger.info("[doc debug] getFileUrl fileid_aa-->"+ fileid_temp+" real_fileid-->" +real_fileid);
							} else {
								real_fileid = fileid_temp;
							}
							jsonarr1.getJSONObject(i).put("fileId", real_fileid);
						}
					}
					
//					if(fileidFileaaMapArr.length() > 0)		// 降低 log 量.
//						logger.info("fileid_aa <--> fileid => " + fileidFileaaMapArr.toString());
					
					if(fileidUUIDaaObjMap.length() > 0)
						Logger.info("fileid <-> uuidaa map => " + fileidUUIDaaObjMap.toString());
					
					Map<String, JSONObject> cachedUrlMap = new HashMap<String, JSONObject>();	// 存放 fid <-> url data 的對應
					List<String> keys = new ArrayList<String>();
					
					
					
					// 針對不在 cache 中的資料進行 mongo 查詢.
					
					JSONArray jsonarr = new JSONArray(jsonarr1.toString());
//					
					
					StringBuilder sqlBuilder = new StringBuilder();
					boolean hasCacheUrl = false;
					
					
					// 效能考量, 查詢 mongo 時, 先濾除重覆的 fileid.
					Set<String> distinctFileIds = new HashSet<String>();
					
					// 將不在 cache 中的 fid 清單找出, 用於 search mongo.
					Iterator<String> keyObjs = cachedUrlMap.keySet().iterator();
					
					while(keyObjs.hasNext()){
						String keyObj = keyObjs.next();
						//if (deBugMode) 
						//	logger.info("keyObjs value=> "+keyObj);
						JSONObject cachedUrlResult = cachedUrlMap.get(keyObj);					
						
						if(cachedUrlResult == null){
							String fileId = new JSONObject(keyObj.replace("fUrl:", "")).getString("fileId");
							distinctFileIds.add(fileId);
						}else{
							hasCacheUrl = true;
							// System.out.println("cached url => " + cachedUrlResult.toString());
						}
					}
					
					// 僅針對 distincted file list 做查詢.
					Iterator<String> uncachedFileIds = distinctFileIds.iterator();
					while(uncachedFileIds.hasNext()){
						String uncachedFileId = uncachedFileIds.next();
						
						if (sqlBuilder.length() > 0){
							sqlBuilder.append(",");
						}				
						sqlBuilder.append("\"").append(uncachedFileId).append("\"");
					}		
					
					
					
					String mongoResult = null;
					
					//JSONObject jomongos= new JSONObject();	 // 從 mongo 中查詢到, 且未被 disable 的資料
					
//					SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DATE_FORMAT_1);
						
						
						//找不到資料
//						if(jomongos.length()==0){
						if(jomongos.length()==0 && !hasCacheUrl){	// 若 cached url 也是空的才回傳找不到資料.
//							JSONArray noDataRtn = new JSONArray(); 
//							JSONObject tmp= generateGetFileDetailErrorObject("", "fileid not found");							
//							noDataRtn.put(tmp);
							
							JSONArray noDataRtn = new JSONArray(); 
							
							//為每個 fileid 都產生 fileid not found 的訊息.
							for(int i=0;i<jsonarr1.length();i++){
								String fileid = jsonarr1.getJSONObject(i).getString("fileId");
								// JSONObject tmp = generateGetFileDetailErrorObject(fileid, "fileid not found");
								JSONObject tmp;
								// {"4ee65980bb974b3da4a586c302996f79aa":{"UUIDaa":"4ee65980bb974b3da4a586c302996f79aa","convert":"pending"}}
								if(fileidUUIDaaObjMap.has(fileid)){
									JSONObject fileidUuidMapObj = fileidUUIDaaObjMap.getJSONObject(fileid);
									String msg = fileidUuidMapObj.has("msg")?fileidUuidMapObj.getString("msg"):"";
									tmp = tools.generateGetFileDetailErrorObject(fileid, msg);
									tmp.put("convert", fileidUuidMapObj.getString("convert"));								 
								}
								else{
									tmp = tools.generateGetFileDetailErrorObject(fileid, "fileid not found");
								}
								
								noDataRtn.put(tmp);
							}
							return noDataRtn.toString();
						}
					
					
		
					
					// 輸出資料		
					for(int i=0;i<jsonarr.length();i++){
						JSONObject paramObj = jsonarr.getJSONObject(i);
						String fileId = paramObj.getString("fileId");	
						//if (deBugMode) 
						//	logger.info("fileId value =>"+fileId);
//						String fileTag = paramObj.has("fileTag") ? paramObj.getString("fileTag") : "";
//						
//						JSONObject keyObj = new JSONObject()
//						.put("fileId", paramObj.getString("fileId"))					
//						.put("fileTag", fileTag);
//						
//						JSONObject cachedUrlResult = cachedUrlMap.get(keyObj.toString());
						
						
							// 採用 mongo data
							if(jomongos.has(fileId)){
								
								JSONObject obj = jomongos.getJSONObject(fileId); 
								
								// 若在 getFileUrl 中的 timestamp 值為 0, 則回傳公開的 url.
								if(timestamp.equals("0"))
									obj.put("isP", 1);
								
								JSONObject tmp = tools.resolveSingleFileUrl(fileId, obj, paramObj, timestamp, fileidUUIDaaObjMap, queryFileIdAndUUIDaaMap);
														
								rtn.put(tmp);	
								
								// process url response cache.								
								
							}
							else{
								JSONObject tmp= tools.generateGetFileDetailErrorObject(fileId, "fileid not found");
								rtn.put(tmp);
							}
							Logger.info("rtn value =>"+rtn.toString());
						
					}
					
//					JSONObject cost = new JSONObject().put("cost", String.valueOf(System.currentTimeMillis() - cost_start) + "ms");
//					rtn.put(cost);
					
					returnStr = rtn.toString();	
				}else{
					JSONArray errorRtn = new JSONArray(); 
					JSONObject tmp= tools.generateGetFileDetailErrorObject("", "empty parameter");
					errorRtn.put(tmp);
					return errorRtn.toString();
				}								
			}catch (Exception e1) {		
				Logger.error("jsonObj=>" + jsonObj, e1);
				e1.printStackTrace();
				try{
					JSONArray errorRtn = new JSONArray(); 
					JSONObject tmp= tools.generateGetFileDetailErrorObject("", "getFileUrl Exception");
					errorRtn.put(tmp);
					return errorRtn.toString();
				}catch(Exception e){
					Logger.error("jsonObj=>" + jsonObj , e);
				};
			}	
					
			
			return returnStr;
				
			
		}

		@Override
		public String signature(String param) throws DocApplicationException {
			//SimpleDateFormat sdf = new SimpleDateFormat("E yyyy-MM-dd");
			JSONObject returnObject = new JSONObject();
			JSONObject paramObj;
			try {
			//paramVal is {"apnum":"10400","pid":"10400","content-type","image/jpeg","filename":"123","extra":"1234"}
			paramObj = new JSONObject(this.decryptParam(param));
			
			if (!paramObj.has("apnum")||"".equals(paramObj.getString("apnum")))
				throw new DocApplicationException("NotPresent",3);//erroehandler 必填欄位未填
			if (!paramObj.has("pid")||"".equals(paramObj.getString("pid")))
				throw new DocApplicationException("NotPresent",3);//erroehandler 必填欄位未填
			if (!paramObj.has("content-type")||"".equals(paramObj.getString("content-type")))
				throw new DocApplicationException("NotPresent",3);//erroehandler 必填欄位未填
			if (!paramObj.has("filename")||"".equals(paramObj.getString("filename")))
				throw new DocApplicationException("NotPresent",3);//erroehandler 必填欄位未填
			if (!paramObj.has("extraNo")||"".equals(paramObj.getString("extraNo")))
				throw new DocApplicationException("NotPresent",3);//erroehandler 必填欄位未填
			
			
				
			 String s = UUID.randomUUID().toString();
			 String objectKey ="filetemp/"+s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24)+".jpg"; 
		     //去掉“-”符号 
			String policy_document =
				      "{\"expiration\": \"2017-01-01T00:00:00Z\"," +
				        "\"conditions\": [" +
				          "{\"bucket\": \""+bucketName+"\"}," +
				          "[\"starts-with\", \"$key\", \""+objectKey+"\"]," +
				          "{\"acl\": \"public-read\"}," +
				          "{\"Content-Disposition\": \""+ paramObj.has("filename") +"},"+
				          "{\"acl\": \"public-read\"},"+
				          "[\"starts-with\", \"$Content-Type\", \"image/\"]," +
				        "]" +
				      "}";
			
			 // Calculate policy and signature values from the given policy document and AWS credentials.
			Base64 Base64 =  new Base64();
			
			
			String signature="";
			
				String policy = Base64.encodeToString(policy_document.getBytes("UTF-8")).replaceAll("\n","").replaceAll("\r","");
			
				Mac hmac = Mac.getInstance("HmacSHA1");
				
					hmac.init(new SecretKeySpec("9w21SKeTGh5NAiLsrditOv2qQKdN8lFKs9aZKU36".getBytes("UTF-8"), "HmacSHA1"));
				
				//Map<String, String> cachedUrlMap = new HashMap<String, String>();	
				
				signature = Base64.encodeToString(hmac.doFinal(policy.getBytes("UTF-8"))).replaceAll("\n", "");
				returnObject.put("policy_document", policy);
				returnObject.put("signature", signature);
				returnObject.put("objectKey", objectKey);
				returnObject.put("bucketName", bucketName);
				returnObject.put("Content-Disposition", paramObj.has("filename"));
			
			} catch (InvalidKeyException | UnsupportedEncodingException |
					NoSuchAlgorithmException | NullPointerException | JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new DocApplicationException(e,11);
			}
			return returnObject.toString();
		}

}
