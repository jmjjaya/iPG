/**
 * 
 */
$(document).ready(function() {
	$( "#removeImage" ).click(function() {
		$('.uploader').hide();
		$("#submitImage").hide();
		$("#removeImage").hide();
	});
	
	
	$("#submitImage").click(function(event) {
		event.preventDefault();
		
		var form = $('#fileUploadForm')[0];
		
		//var file_data = $("#filePhoto").prop("files")[0];
		var data = new FormData(form);
		
		$("#btnSubmit").prop("disabled", true);
		//call text detection servlet
        $.ajax({
            url: "TextRecognitionServlet",
            enctype: 'multipart/form-data',
            type : "POST",
            cache: false,
            contentType: false,
            processData: false,
            data: data,
            success: function(data){
                $("<p>"+ data+ "</p>").appendTo("#detectedText");
            },
            error: function(e){
            	alert(e);
            }
        });
        
        //Call celebrity servlet
        $.ajax({
            url: "CelebrityRecognitionServlet",
            enctype: 'multipart/form-data',
            type : "POST",
            cache: false,
            contentType: false,
            processData: false,
            data: data,
            success: function(data){
            	$("<tr><td>" + data + "</td></tr>").appendTo("#celebNames");
            },
            error: function(e){
            	alert(e);
            }
        });
	});
	
	var panels = $('.user-infos');
    var panelsButton = $('.dropdown-user');
    panels.hide();

    //Click dropdown
    panelsButton.click(function() {
        //get data-for attribute
        var dataFor = $(this).attr('data-for');
        var idFor = $(dataFor);

        //current button
        var currentButton = $(this);
        idFor.slideToggle(400, function() {
            //Completed slidetoggle
            if(idFor.is(':visible'))
            {
                currentButton.html('<i class="glyphicon glyphicon-chevron-up text-muted"></i>');
            }
            else
            {
                currentButton.html('<i class="glyphicon glyphicon-chevron-down text-muted"></i>');
            }
        })
    });


    $('[data-toggle="tooltip"]').tooltip();
});