
const locLoadBtn = document.getElementById("load");

locLoadBtn.addEventListener("click", getLocation);

function getLocation(){
    navigator.geolocation.getCurrentPosition(getSuccess, getError);
}



// 가져오기 성공
function getSuccess(position) {
    // 위도
    const lat = position.coords.latitude;
    // 경도
    const lnt = position.coords.longitude;

    const LATtext = document.getElementById("LAT");
    const LNTtext = document.getElementById("LNT");
	
	//위치 히스토리에 데이터 저장.
	
	const URL = "/addhistory?LAT=" + lat + "&LNT=" + lnt;

    fetch(URL)
        .then(function(response) {
            if (response.ok) {
            return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(function(data) {
    		alert("위치를 불러왔습니다.");
        })
        .catch(function(error) {
            alert("위치 정보를 서버에 저장하는 도중에 문제가 발생하였습니다.");
        }).finally(function(){
			LATtext.value = lat;
    		LNTtext.value = lnt;
		});

}

// 가지오기 실패(거부)
function getError() {
    alert('위치를 가져올 수 없습니다.');
}