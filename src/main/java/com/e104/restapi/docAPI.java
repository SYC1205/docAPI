package com.e104.restapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.e104.ErrorHandling.DocApplicationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

@Api(value = "/")
public interface docAPI {
	  @PUT
	   @Path("/addKey")
	   @ApiOperation(value = "Update user collection key & value")
	   /**request String {"fileid":"123456789","key":"{"value"}"}
	    * respone jsonobject string {"txid":"uuid","status":"Success"}
	    * */
	   public String addKey();
	   
	   @POST
	   @Path("/checkFileSpec")
	   @ApiOperation(value = "Check upload spec", httpMethod = "POST")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   /**request String 
	    * {"filePath":"https://s3-ap-northeast-1.amazonaws.com/awssyslogs03/docUpload.jpg",
	    * 	"extraNo":"2cdds-asdsad-asdas-adssad",
	    * 	"specObj":"{
						"maxwidth" : "9999",
						"description" : "NonSns",
						"source" : "104pro",
						"title" : "NonSns",
						"contenttype" : "4",
						"extensions" : "wav,mp3,wma,m4a",
						"maxheight" : "9999",
						"minwidth" : "0",
						"extra" : {
						"convert" : "true"
						},
						"maxsize" : "500",
						"minheight" : "0"
						}"}
	    * 
	    * 
	    * 
	    * respone jsonobject string {"status":"Success"}
	    * */
	   public String checkFileSpec(@ApiParam(value = "check upload spec", required = true)@PathParam("specObj") String specObj);
	   
	   
	   @DELETE
	   @Path("/clearFileCache/{cacheSize}")
	   @ApiOperation(value = "clear redis cache", httpMethod = "DELETE")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String clearFileCache(@ApiParam(value = "clear redis Size", required = true) @PathParam("cacheSize") String cacheSize);
	   
	   
	   @GET
	   @Path("/confirmUpload/{fileid}")
	   @ApiOperation(value = "clear redis cache", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String confirmUpload(@ApiParam(value = "check Upload file is exist,if exist remove expireTimestamp", required = true) @PathParam("fileid") String fileid);
	  
	   
	   @POST
	   @Path("/copyFile")
	   @ApiOperation(value = "copy file by user config & use putfile method", httpMethod = "POST")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String copyFile(@ApiParam(value = "{fileId:123,apNum:10400,pid:104,jsonObj:{},title:hello,description:hello word,filename:hello.jpg}", required = true) @FormParam("fileObj") String fileObj);
	   
	   @POST
	   @Path("/copyFileForMM")
	   @ApiOperation(value = "copy file by inputstream", httpMethod = "POST")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String copyFileForMM(@ApiParam(value = "{fileid:123442,cnt:1,contenttype:1}", required = true) @FormParam("fileObj") String fileObj);
	   
	   @GET
	   @Path("/decryptParam/{param}")
	   @ApiOperation(value = "decrypt", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String decryptParam(@ApiParam(value = "decrypt data", required = true) @PathParam("param") String param);
	   
	   @GET
	   @Path("/encryptParam/{param}")
	   @ApiOperation(value = "encrypt", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String encryptParam(@ApiParam(value = "encrypt data", required = true) @PathParam("param") String param) throws DocApplicationException;
	   
	   
	   @DELETE
	   @Path("/deleteFile/{Param}")
	   @ApiOperation(value = "delete file", httpMethod = "DELETE")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String deleteFile(@ApiParam(value = "param is decode,need fileid & fileTag & delExtend ", required = true) @PathParam("Param") String Param);
	   
	   @DELETE
	   @Path("/discardFile/{fileId}")
	   @ApiOperation(value = "delete files by fileId", httpMethod = "DELETE")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String discardFile(@ApiParam(value = "fileId", required = true) @PathParam("fileId") String fileId);
	   
	   @GET
	   @Path("/generateFileId/(Param)")
	   @ApiOperation(value = "generate fileId", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String generateFileId(@ApiParam(value = "Param is decode,need extraNo & contenttype & isP", required = true) @PathParam("Param") String Param);
	   
	   @GET
	   @Path("/getCheck")
	   @ApiOperation(value = "Get a value", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String getCheck();
	   
	   @GET
	   @Path("/getFileCache/(Param)")
	   @ApiOperation(value = "Get redis cache url", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String getFile(@ApiParam(value = "Param is decode,need pattern & limit", required = true) @PathParam("Param") String Param);
	   
	   @GET
	   @Path("/getFileDetail/(Param)")
	   @ApiOperation(value = "Get file meta by fileId", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String getFileDetail(@ApiParam(value = "Param is decode,need FileId & tag", required = true) @PathParam("Param") String Param);
	   
	   @GET
	   @Path("/getFileList/(Param)")
	   @ApiOperation(value = "Get file List by Pid", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String getFileList(@ApiParam(value = "Param is decode,need pid & contenttype & apnum", required = true) @PathParam("Param") String Param);
	   
	   @GET
	   @Path("/getFileUrl/(Param)")
	   @ApiOperation(value = "Get file Url", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String getFileUrl(@ApiParam(value = "Param is decode,need jsonObj & timestamp", required = true) @PathParam("Param") String Param);
	   
	   @GET
	   @Path("/getQueueLength")
	   @ApiOperation(value = "Get Quere Length", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String getQueueLength();
	   
	   @GET
	   @Path("/getVersion")
	   @ApiOperation(value = "Get Version", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String getVersion();
	   
	   @POST
	   @Path("/putfile")
	   @ApiOperation(value = "putfile ", httpMethod = "POST")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String putfile(String jsonData) throws DocApplicationException;
	   
	   @DELETE
	   @Path("/removeKey")
	   @ApiOperation(value = "remove user collection Key ", httpMethod = "DELETE")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String removeKey(@ApiParam(value = "Param is decode,need fileId & key", required = true) @PathParam("Param") String Param);
	   
	   @PUT
	   @Path("/setExpireTimestamp")
	   @ApiOperation(value = "set user collection ExpireTimestamp", httpMethod = "PUT")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String setExpireTimestamp(@ApiParam(value = "Param is decode,need fileId & key", required = true) @PathParam("Param") String Param);
	   
	   @PUT
	   @Path("/updateFile")
	   @ApiOperation(value = "update user collection title & description", httpMethod = "PUT")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String updateFile(@ApiParam(value = "Param is decode,need fileId & title & description & fileTag", required = true) @PathParam("Param") String Param);
	   
	   
	   @POST
	   @Path("/doc2img")
	   @ApiOperation(value = "doc2img", httpMethod = "POST")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String doc2img(@ApiParam(value = "{fileId,page,height,width,tag,isSave,isGetURL}", required = true) @PathParam("Param") String Param);
	   
	   @GET
	   @Path("/getStatus/{fileId}")
	   @ApiOperation(value = "Get convert status", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String getStatus(@ApiParam(value = "fileId", required = true) @PathParam("fileId") String fileId);
	   
	   @PUT
	   @Path("/setConvertStatus")
	   @ApiOperation(value = "Set convert status", httpMethod = "PUT")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String setConvertStatus(@ApiParam(value = "{fileId,progress}", required = true) @PathParam("Param") String Param);
	   
	   @PUT
	   @Path("/updateData")
	   @ApiOperation(value = "add user collection key & value ", httpMethod = "PUT")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String updateData(@ApiParam(value = "{fileId,jsonobject}", required = true) @PathParam("Param") String Param);
	   
	   @POST
	   @Path("/videoConvert")
	   @ApiOperation(value = "video task send to quere", httpMethod = "POST")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String videoConvert(@ApiParam(value = "{fileId}", required = true) @PathParam("Param") String Param);
	   
	   @POST
	   @Path("/audioConvert")
	   @ApiOperation(value = "audio task send to quere", httpMethod = "POST")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String audioConvert(@ApiParam(value = "{fileId}", required = true) @PathParam("Param") String Param);
	   
	   
	   @GET
	   @Path("/signatureByExtraNo/{param}")
	   @ApiOperation(value = "parme is {\"apnum\":\"10400\",\"pid\":\"10400\",\"content-type\",\"image/jpeg\",\"filename\":\"123\",\"extra\":\"1234\"}")
	   public String signatureByExtraNo(@ApiParam(value = "Param is decode,need jsonObj & timestamp", required = true) @PathParam("param") String param) throws DocApplicationException; 
	   
	 //doing##########################################################
	   
	   @GET
	   @Path("/getfileurlnoeedis/{Param}")
	   @ApiOperation(value = "Get file Url", httpMethod = "GET")
	   @ApiResponses(value = { @ApiResponse(code = 200, message = "http/1.1 200 OK{\"error\":\"\",\"data\":\"\",\"success\":\"true\"}")})
	   public String getFileUrlnoRedis(@ApiParam(value = "Param is decode,need jsonObj & timestamp", required = true) @PathParam("Param") String Param);
	   
}
