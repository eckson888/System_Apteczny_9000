// In the following example, markers appear when the user clicks on the map.
// The markers are stored in an array.
// The user can then click an option to hide, show or delete the markers.

//NIE, CHAT TEGO NEI ROBIŁ, KOD ZOSTAŁ ZAJEBANY BEZPOSREDNIO OD GOOGLE™
let map;
let markers = [];
var geocoder;

function initMap() {
    const lubLeanCenter = { lat: 51.250, lng: 22.574 };

    geocoder = new google.maps.Geocoder();

    map = new google.maps.Map(document.getElementById("map"), {
        zoom: 12,
        center: lubLeanCenter,
        mapTypeId: "roadmap",
        mapTypeControl: false,
        streetViewControl: false,
    });
}

function codeAddress(address) {
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == 'OK') {
            var marker = new google.maps.Marker({
            position: results[0].geometry.location,
                map: map,
            });
            markers.push(marker);
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}

function setHomeMarker(address) {
    geocoder.geocode( { 'address': address}, function(results, status) {
        if (status == 'OK') {
            // NIEBIESKI KOLOREK DLA AKTUALNEJ APTEKI
            var markerIcon = {
                url: "http://maps.google.com/mapfiles/ms/icons/blue-dot.png",
            };
            var marker = new google.maps.Marker({
                position: results[0].geometry.location,
                map: map,
                icon: markerIcon,
            });
            markers.push(marker);
            map.setCenter(results[0].geometry.location);
        } else {
            alert('Geocode was not successful for the following reason: ' + status);
        }
    });
}

// Sets the map on all markers in the array.
function setMapOnAll(map) {
    for (let i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}

// Removes the markers from the map, but keeps them in the array.
function hideMarkers() {
    setMapOnAll(null);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
    hideMarkers();
    markers = [];
}

window.initMap = initMap;