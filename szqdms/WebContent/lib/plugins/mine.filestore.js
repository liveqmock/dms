/**
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2013-02
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
*/
(function($) {
    $.filestore = {
        /**
         * 查看附件列表 
         */
        view:function(sPk){
            var _op = $.extend({max:false,width:600,height:380,mask:true,mixable:true,minable:true,resizable:true,drawable:true},{pk:sPk});
            $.pdialog.open(webApps + "/jsp/jQFrameWork/dialog/viewAttachment.jsp", "dialog-viewAttachment", "查看附件", _op);
        },
        /**
         * 打开附件上传组件
         * @param {Object<string>} sPk：业务主键
         * @param {Object<json>} sOp:参数配置
         */
        open:function(sPk,sOp){
            var _op = $.extend({max:false,width:550,height:350,mask:true,mixable:true,minable:true,resizable:true,drawable:true},{uploadify:sOp,pk:sPk});
            $.pdialog.open(webApps + "/jsp/jQFrameWork/dialog/uploadify.jsp", "dialog-uploadify", "附件上传", _op);
        },
        /**
         * 下载附件
         * @param {Object<json>} 
         */
        download:function(op){
            var _op = $.extend({fjid:'',wjjbs:'',blwjm:'',fjmc:''},op);
            var url = webApps + '/FileStoreAction/downloadFile.do?fjid='+_op.fjid+'&fjmc='+_op.fjmc+'&wjjbs=' + _op.wjjbs+"&blwjm="+_op.blwjm;
            var fm = $("#dialog-fm-download");
            //$("#dialog-in-fjid").val(_op.fjid);
            fm.attr("action",url);
            fm.attr("method","post");
            fm.submit();
            //sendPost(url,"","","");
            //fm.attr("src",url);      
        },
        /**
         * 删除附件
         * @param {Object<json>} 
         */
        remove:function(op){
            var _op = $.extend({fjid:'',fileId:'',wjjbs:'',callback:'',blwjm:'',fjmc:''},op);
            var url = webApps + '/FileStoreAction/removeFile.ajax?fjid=' + _op.fjid +'&fjmc='+_op.fjmc+ "&fileId="+_op.fileId+"&wjjbs=" + _op.wjjbs+"&blwjm="+_op.blwjm;
            sendPost(url,"","",_op.callback,"true");
        },
        /**
         * 控件初始化 
         * @param {Object<json>} userInfo:用户信息对象，为解决flash上传session丢失问题
         * @param {Object<json>} op:配置参数对象
         */
        init:function(userInfo,op){
            var kSwf = webApps + '/uploadify/scripts/uploadify.swf';
            var kButtonImage = webApps + "/uploadify/img/add.png";
            var _op = op["uploadify"];
            if(!_op) _op = {};
            var holdName = "false";
            if(op.uploadify.holdName == "true")
                holdName = "true";
            var folder = "false";
            if(op.uploadify.folder == "true")
                folder = "true";
            //var url = webApps + '/FileStoreAction/uploadFile.ajax?key='+op.pk+"&jsessionid="+userInfo.sessionid+"&userid="+userInfo.userid+"&holdname="+holdName+"&folder="+folder;
            var url = webApps + '/FileStoreAction/uploadFile.ajax';
            var uploaderOption = $.extend({
                    swf:kSwf,//固定
                    uploader:url,//固定
                    formData:{'key':op.pk,'jsessionid':userInfo.sessionid,'userid':userInfo.userid,'holdname':holdName,'folder':folder},//jsessionid:session id;key:业务主键
                    queueID:'fileQueue',
                    fileObjName:'filedata',
                    buttonImage:kButtonImage,
                    buttonClass:'my-uploadify-button',
                    width:102,
                    height:28,
                    auto:false,
                    uploadLimit:10,     //设置一次最多上传文件数量
                    fileSizeLimit:'5000KB',    //设置文件大小限制（单位为KB），0为不限制文件大小
                    fileTypeDesc:'All Files',   //设置文件格式描述，如是图片，则：'*.jpg;*.jpeg;*.gif;*.png;'
                    fileTypeExts: '*.*',    //设置文件格式，如是图片，则：'*.jpg;*.jpeg;*.gif;*.png;',
                    multi:true,  //设置是否允许多文件批量上传,
                    requeueErrors:false,
                    removeCompleted:false,
                    removeTimeout:3,
                    successTimeout  : 300, //上传超时
                    onUploadSuccess:function(file, xdata, respons){
                        swfuploadify = this;
                        if(xdata)
                        {
                        	var xDoc;
                        	if(bOs == 1)
                			{
                				xDoc = createDOMDocument();
                				try{xDoc.loadXML(xdata);}catch(e){xDoc = loadFromStr(xdata);}
                			}
            				else
            				{
                				xDoc = loadFromStr(xdata);
            				}
                        	if(xDoc.getElementsByTagName("NOALERTE").length >0)
                        	{
                        		var data = getNodeText(xDoc.getElementsByTagName("RESULT").item(0));
                        		var s = data.split("#");
	                            if(s[0] == "1") //成功
	                            {
	                               $('#' + file.id).find('.data').html('<div >成功'+' - ' + s[1]+'</div>');
	                               try{
	                                   fjUpCallBack(s[2]);
	                               }catch(e){}
	                               
	                               /**
	                               setTimeout(function() {
	                                if ($('#' + file.id)) {
	                                    swfuploadify.queueData.queueSize   -= file.size;
	                                    swfuploadify.queueData.queueLength -= 1;
	                                    delete swfuploadify.queueData.files[file.id]
	                                    $('#' + file.id).fadeOut(500, function() {
	                                        $(this).remove();
	                                    });
	                                }
	                                }, swfuploadify.settings.removeTimeout * 1000);
	                                */
	                            }else
	                            {
	                                $('#' + file.id).find('.data').html('<div style="color:red;">失败'+' - ' + s[1]+'</div>');
	                            }
                        	}else if(xDoc.getElementsByTagName("ERRMESSAGE").length > 0)
                        	{
                        		var data = getNodeText(xDoc.getElementsByTagName("ERRMESSAGE").item(0));
                        		var s = data.split("#");
                        		$('#' + file.id).find('.data').html('<div style="color:red;">失败'+' - ' + s[1]+'</div>');
                        	}
                        }else
                        {
                        	$('#' + file.id).find('.data').html('<div style="color:red;">失败</div>');
                        }
                    },
                    onUploadComplete:function(file){
                        //alert("complete:"+file.filestatus);
                    },
                    onUploadError:function(file, errorCode, errorMsg, errorString){
                        
                    }
            }, _op);
            $("#uploadify-impl").attr("uploaderOption",DWZ.obj2str(uploaderOption));
        }
    };
})(jQuery);