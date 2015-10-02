<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Auteurs : Corinne & Laura -->
<html>
<head>
<!-- <meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
<link href="resources/stylesheets/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="resources/stylesheets/css/style.css" rel="stylesheet">
<link
	href="https://developers.google.com/maps/documentation/javascript/examples/default.css"
	rel="stylesheet">
<script src="resources/stylesheets/jquery-2.1.3.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>

<!-- Fonctions de localisation pour la carte -->
<script src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script>
	var geocoder;
	var map;
	var mapOptions = {
		zoom : 6,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	}
	var marker;
	function initialize(pays, capital) {
		geocoder = new google.maps.Geocoder();
		map = new google.maps.Map(document.getElementById('map_canvas'),
				mapOptions);
		codeAddress(pays, capital);
	}
	function codeAddress(pays, capital) {
		var address = pays + ' ' + capital;
		var infoWindow = new google.maps.InfoWindow();
		
		geocoder.geocode({
			'address' : address
		}, function(results, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				map.setCenter(results[0].geometry.location);
				if (marker)
					marker.setMap(null);
				marker = new google.maps.Marker({
					map : map,
					position : results[0].geometry.location,
					draggable : false,
					animation:google.maps.Animation.BOUNCE,
					//icon:'pinkball.png'
				});
				
				
				var nbHabitSelect = "<c:out value='${pays.nbHabitants}' />";
				var infowindow = new google.maps.InfoWindow({
				    content: "<div id='boxcontent' style='font-family:Calibri'><strong style='color:green'>"
            		+ "<h3>" +capital + "</h3>"
                    + "<h4></strong><span style='font-size:12px;color:#333'>Nombre d'habitants : "+nbHabitSelect+"</span></h4></div>"
				  });

				/*marker.addListener('click', function() {
				    infowindow.open(map, marker);
				  });*/

				  infowindow.open(map, marker);
                
			} else {
				alert('Geocode was not successful for the following reason: '
						+ status);
			}
		});
	}
</script>

<script type="text/javascript">
	function Chargement() {
		var obj = document.getElementById("id_erreur");
		if (obj.value != '')
			alert('Erreur signalée  : "' + obj.value + "'");
	}

	$(document).ready(function() {

		// Selection des listes déroulantes
		var paysSelect = "<c:out value='${pays.nom}' />";
		var capitalSelect = "<c:out value='${pays.nomCapital}' />";

		if (paysSelect != "") {
			$('#country').val(paysSelect);
			initialize(paysSelect, capitalSelect);
		}
	});
</script>




</head>
<body>
	<!-- Conteneur principal -->
	<div class="container">
		<h1>La population d'un pays</h1>

		<!-- 		Contenu -->
		<section class="row">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 contenu">
			<input type="hidden" name="uneErreur" value="${MesErreurs}"
				id="id_erreur">
			<form class="form-horizontal" method="post" action="home.htm"
				onsubmit="">
				<input type="hidden" name="type" value="home" id="type" /> <input
					type="hidden" name="action" value="home.htm" />

				<!-- input fields -->
				<div class="form-group">
					<label class="control-label">Sélectionnez le pays que vous
						souhaitez parmi la liste suivante :</label><select name="country"
						id="country">
						<c:forEach items="${listPays}" var="paysItem">
							<option value="${paysItem.nom}">${paysItem.nom}</option>
						</c:forEach>
					</select>
				</div>

				<!-- Boutons Ajouter/Reset -->
				<div class="form-group">
					<div class="col-sm-6 col-sm-offset-4 col-md-4 col-md-offset-5">
						<button type="submit" name="valider"
							class="btn btn-default btn-primary">Valider</button>
					</div>
				</div>
			</form>
		</div>
		</section>

		<c:if test="${pays != null}">
			<section class="row">
			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 contenu">
				<h2>${pays.nom}</h2>
				<table class="table table-bordered">
					<!-- <CAPTION>Tableau des Stages</CAPTION> -->
					<thead>
						<tr>
							<th class="col-md-6">Capitale</th>
							<th class="col-md-6">Nombre d'habitants</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${pays.nomCapital}</td>
							<td>${pays.nbHabitants}</td>
						</tr>
					</tbody>
				</table>
			</div>

			<!--  Carte google  -->
			<div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 contenu">
				<div id="map_canvas" style="width: 500px; height: 380px;"></div>
			</div>
		</c:if>
		</section>

	</div>

</body>
</html>