package com.e104.util;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

public class mediaUtil {  
	tools tools =new tools();
	
	public String getUrl(JSONObject file_obj, int Second, String quality) throws JSONException{
		String rr="";
		String path = "";	
		try{
			switch(file_obj.getInt("contenttype")){
			case 3:
				path = FileID_Path(file_obj,"video", quality);
				break;
			case 4:
				path = FileID_Path(file_obj,"audio", null);
				break;
			case 5:
				path = FileID_Path(file_obj,"video", quality);
				break;
			}
			if (path.charAt(0)=='/') path=path.substring(1);			
			
			/** 9. 2013-11-20 modify by jj
	          		A:fix rtmpt 不加密  只回傳trmpt file path**/
			
			//rr=EncryptUrl(path,Second);//原本加密規則
			//回傳filepath 不加密 2013-11-21 
			JSONObject retFilePathOnly = new JSONObject();
			retFilePathOnly.put("url",  Config.DFRTMPT_URL+"/"+path);
			rr=retFilePathOnly.toString();
		} catch	(Exception e) {
			System.out.println("com.v104.ws.Video Video_Converter() Exception:"+e);
			rr="";
		}			
		//System.out.println("getUrl out -----> "+ rr);
		return rr;
	}	
  
	public String EncryptUrl(String pathfile,int div){
		MD5Util mu=new MD5Util();
		String rr="{url:\"\"}";
		try{
			pathfile=pathfile.trim();
			if(!pathfile.substring(0, 1).equals("/")) pathfile = "/"+pathfile;
			java.util.Date dt = new Date();
			long ldt=dt.getTime()/1000;
			String dif=String.valueOf(ldt%div);
			String filename=pathfile;
			int p= filename.lastIndexOf("/");
			if (p!=-1){
				filename=filename.substring(p+1);
			}	
			//System.out.println("convert filename: "+filename);
			String key=filename+String.valueOf(ldt/div)+String.valueOf(div)+dif;
			//System.out.println("key: " +key);
			rr=Config.DFRTMPT_URL+pathfile+"?security="+mu.getMD5(key.getBytes("UTF-8"))+","+String.valueOf(div)+","+dif;
			rr="{url:\""+rr+"\"}";
		} catch (Exception e) {
			System.out.println("org.red5.FileMd5 EncryptUrl() Exception :"+e);
			return rr;
		}		
		return rr;
	} 
	
	/*
	   public String FileID_Path(JSONObject file_obj,String ftype){		   	
	    	String rr="";
	    	try{
		    	String fileId = file_obj.getString("fileid");
		    	String contentType =file_obj.get("contenttype").toString();
	    		String filePath = tools.generateFilePathForMount(file_obj.getString("filepath"));
				rr=filePath;
				String videoDefinition = null;
				String mediaType = null;
				rr=rr.substring(0,rr.lastIndexOf("."));
				//System.out.println("contentType: "+contentType);
				String convert_flag = file_obj.getString("convert").toLowerCase();
				if (!contentType.equals("5")) { // 除了contentType不是白板的以外，都return下列url
					if (ftype.equals("video")) {
						// 判斷db有沒有videoDefinition Y=>videoDefinition(720p...)  N=>480p(預設)
						videoDefinition = (file_obj.has("videoDefinition") && !file_obj.getJSONArray("videoDefinition").getString(0).equals(""))? file_obj.getJSONArray("videoDefinition").getString(0):"480p";
						// 判斷convert=success and 判斷db有沒有mediaType Y=>mediaType(mp4...)  N=>flv(預設)
						if (convert_flag.equals("success")) { 
							mediaType = (file_obj.has("mediaType") && !file_obj.getString("mediaType").equals(""))? file_obj.getString("mediaType"):"flv";
							rr=rr+Config.VIDEO_VERSION+videoDefinition+"."+mediaType;
						}
						else { //pending
							rr=rr+Config.VIDEO_VERSION+videoDefinition+".mp4";
						}
					}else { //audio
						// 判斷db有沒有videoDefinition Y=>videoDefinition(320k...)  N=>192k(預設)
						videoDefinition = (file_obj.has("videoDefinition") && !file_obj.getJSONArray("videoDefinition").getString(0).equals(""))? file_obj.getJSONArray("videoDefinition").getString(0):"192k";
						// 判斷db有沒有mediaType Y=>mediaType(m4a...)  N=>mp3(預設)
						if (convert_flag.equals("success")) { 
							mediaType = (file_obj.has("mediaType") && !file_obj.getString("mediaType").equals(""))? file_obj.getString("mediaType"):"mp3";
							rr=rr+Config.VIDEO_VERSION+videoDefinition+"."+mediaType;
						}else { //pending
							rr=rr+Config.VIDEO_VERSION+videoDefinition+".m4a";
						}
					}
				}else {  //contentType=5白板，2013/5/14後有送轉檔且成功
						if (file_obj.has("videoDefinition")) { // 判斷db有沒有videoDefinition 有值表示有轉檔也有mediaType
						videoDefinition = file_obj.getJSONArray("videoDefinition").getString(0);
						mediaType = file_obj.getString("mediaType");
						rr=rr+Config.VIDEO_VERSION+videoDefinition+"."+mediaType;
					}else { //舊的壓成功但並未真的轉檔
						rr=rr+".flv";
					}
				}
			} catch (Exception e) {
	    		System.out.println("mediaUtil FileID_Path() Exception:"+e);
	    		return rr;
	    	}
	    	return rr;
	    }		
	    */
	
	public String FileID_Path(JSONObject file_obj,String ftype, String videoQuality){		   	
    	String _filepath="";
    	try{
	    	String fileId = file_obj.getString("fileid");
	    	String contentType =file_obj.get("contenttype").toString();
    		String filePath = tools.generateFilePathForMount(file_obj.getString("filepath"));
			_filepath=filePath;
//			String videoDefinition = null;
//			String mediaType = null;
			_filepath=_filepath.substring(0,_filepath.lastIndexOf("."));
			//System.out.println("contentType: "+contentType);
			String convert_flag = file_obj.getString("convert").toLowerCase();
			
			String videoDefinition = file_obj.has("videoDefinition") ? file_obj.getJSONArray("videoDefinition").getString(0) : "";
			String mediaType = file_obj.has("mediaType") ? file_obj.getString("mediaType") : "";
			
			if (!contentType.equals("5")) { // 除了contentType不是白板的以外，都return下列url
				if (ftype.equals("video")) {
					// 判斷db有沒有videoDefinition Y=>videoDefinition(720p...)  N=>480p(預設)
//					videoDefinition = (file_obj.has("videoDefinition") && !file_obj.getJSONArray("videoDefinition").getString(0).equals(""))? file_obj.getJSONArray("videoDefinition").getString(0):"480p";
					
//					if(videoDefinition.equals("")) 
//						videoDefinition = "480p";
					
					// 判斷convert=success and 判斷db有沒有mediaType Y=>mediaType(mp4...)  N=>flv(預設)
					if(convert_flag.equals("success") && mediaType.equals(""))
						mediaType = "flv";
					
					if (convert_flag.equals("success")) { 
						if(mediaType.equals("")) 
							mediaType = "flv";
					}
					else
						mediaType = "mp4";
					
//					_filepath=_filepath+Config.VIDEO_VERSION+videoDefinition+"."+mediaType;
					_filepath=_filepath+Config.VIDEO_VERSION+videoQuality+"."+mediaType;
					
				}else { //audio
					// 判斷db有沒有videoDefinition Y=>videoDefinition(320k...)  N=>192k(預設)
//					if(videoDefinition.equals("")) 
//						videoDefinition = "192k";
					
//					String audioQuality = (
//							isEmpty(videoDefinition) && 
//							isEmpty(file_obj.optString("audioQuality")))?
//									"192k" : videoDefinition;
					
					String audioQuality = file_obj.optString("audioQuality"); 
					if(isEmpty(audioQuality))
						audioQuality = isEmpty(videoDefinition) ? "192k" : videoDefinition;
					
					// 判斷db有沒有mediaType Y=>mediaType(m4a...)  N=>mp3(預設)
					if (convert_flag.equals("success")) { 
						if(mediaType.equals("")) 
							mediaType = "mp3";
					}
					else
						mediaType = ".m4a";
					
//					_filepath=_filepath+Config.VIDEO_VERSION+videoDefinition+"."+mediaType;
					_filepath=_filepath+Config.VIDEO_VERSION+audioQuality+"."+mediaType;
				}
			}else {  //contentType=5白板，2013/5/14後有送轉檔且成功
//				if (file_obj.has("videoDefinition")) { // 判斷db有沒有videoDefinition 有值表示有轉檔也有mediaType
//					videoDefinition = file_obj.getJSONArray("videoDefinition").getString(0);
//					mediaType = file_obj.getString("mediaType");
//					_filepath=_filepath+Config.VIDEO_VERSION+videoDefinition+"."+mediaType;
//				}else { //舊的壓成功但並未真的轉檔
//					_filepath=_filepath+".flv";
//				}
				
				String audioQuality = file_obj.optString("audioQuality");
				if(isEmpty(audioQuality))
					audioQuality = videoDefinition;
				
				if(!isEmpty(audioQuality)){
					_filepath=_filepath+Config.VIDEO_VERSION+audioQuality+"."+mediaType;
				}
				else{
					_filepath=_filepath+".flv";
				}
				
			}
		} catch (Exception e) {
    		System.out.println("mediaUtil FileID_Path() Exception:"+e);
    		return _filepath;
    	}
    	return _filepath;
    }		
	
	private boolean isEmpty(String str){
		return str == null || str.trim().equals("");
	}
}  