/**
 * 
 */
$(document)
		.ready(
				function() {
					$("#removeImage").click(function() {
						location.reload();
					});
					
					$("#submitImage")
							.click(
									function(event) {
										$(".Celebrity").show();
										$(".Face").show();
										$(".Vehicle").show();
										$(".Text").show();
										
										$('#detectedText').empty();
										$('#celebNames').empty();
										$('#faceRec').empty();
										$('#vehicleRec').empty();
										event.preventDefault();

										var form = $('#fileUploadForm')[0];

										// var file_data =
										// $("#filePhoto").prop("files")[0];
										var data = new FormData(form);
										// Jeewan:
										$("#btnSubmit").prop("disabled", true);
										// call text detection servlet
										$
												.ajax({
													url : "TextRecognitionServlet",
													enctype : 'multipart/form-data',
													type : "POST",
													cache : false,
													contentType : false,
													processData : false,
													data : data,
													success : function(data) {
														$("<tr><td>" + data + "</td></tr>")
																.appendTo(
																		"#detectedText");
													},
													error : function(e) {
														alert(e);
													}
												});

										// Call celebrity servlet
										$
												.ajax({
													url : "CelebrityRecognitionServlet",
													enctype : 'multipart/form-data',
													type : "POST",
													cache : false,
													contentType : false,
													processData : false,
													data : data,
													success : function(data) {
														var i =0;
														$
																.each(
																		JSON
																				.parse(data),
																		function(
																				idx,
																				obj) {																			
																		for (let i = 0; i < obj.length; i++) {
																			console.log(obj[i].url);
																			$("<tr><td><a href=\""
																			+ obj[i].url +"\">"
																			+  obj[i].Name +"</a></td></tr>")
																	.appendTo(
																			"#celebNames");
																			} 	
																		});
													},
													error : function(e) {
														alert(e);
													}
												});
										// call Face Recognize servlet

										$
												.ajax({

													url : "FaceRecognitionServlet",
													enctype : 'multipart/form-data',
													type : "POST",
													cache : false,
													contentType : false,
													processData : false,
													data : data,
													success : function(data) {
														$
																.each(
																		JSON
																				.parse(data),
																		function(
																				idx,
																				obj) {
																			if (idx === 'objects') {

																				$
																						.each(
																								obj,
																								function(
																										idx,
																										obj) {
																									if (obj.type === 'face') {

																										$(
																												"<tr><td>"
																														+ obj.attributes.gender
																														+ "</td><td>"
																														+ obj.attributes.age
																														+ "</td> <td>"
																														+ obj.attributes.emotion
																														+ "</td></tr>")
																												.appendTo(
																														"#faceRec");
																									}

																								});

																			}
																		});
													},

													error : function(e) {
														alert(e);
													}
												});

										$
												.ajax({

													url : "VehicleRecognitionServlet",
													enctype : 'multipart/form-data',
													type : "POST",
													cache : false,
													contentType : false,
													processData : false,
													data : data,
													success : function(data) {
														$
																.each(
																		JSON
																				.parse(data),
																		function(
																				idx,
																				obj) {

																			if (idx === 'objects') {

																				$
																						.each(
																								obj,
																								function(
																										idx,
																										obj) {
																									$(
																											"<tr><td>"
																													+ obj.vehicleAnnotation.attributes.system.make.name
																													+ "</td><td>"
																													+ obj.vehicleAnnotation.attributes.system.model.name
																													+ "</td> <td>"
																													+ obj.vehicleAnnotation.attributes.system.color.name
																													+ "</td><td>"
																													+ obj.vehicleAnnotation.attributes.system.vehicleType
																													+ "</td></tr>")
																											.appendTo(
																													"#vehicleRec");

																								});

																			}
																		});
													},

													error : function(e) {
														alert(e);
													}
												});

									});

					var panels = $('.user-infos');
					var panelsButton = $('.dropdown-user');
					panels.hide();

					// Click dropdown
					panelsButton
							.click(function() {
								// get data-for attribute
								var dataFor = $(this).attr('data-for');
								var idFor = $(dataFor);

								// current button
								var currentButton = $(this);
								idFor
										.slideToggle(
												400,
												function() {
													// Completed slidetoggle
													if (idFor.is(':visible')) {
														currentButton
																.html('<i class="glyphicon glyphicon-chevron-up text-muted"></i>');
													} else {
														currentButton
																.html('<i class="glyphicon glyphicon-chevron-down text-muted"></i>');
													}
												})
							});

					$('[data-toggle="tooltip"]').tooltip();
				});