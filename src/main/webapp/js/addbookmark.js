const addBookmarkBtn = document.getElementById("addbookmark-btn");

addBookmarkBtn.addEventListener("click", function(){

    const comboBox = document.getElementById("group-combo");
    var groupid = comboBox.options[comboBox.selectedIndex].value;

    const urlParams = new URL(location.href).searchParams;
    var wifiid = urlParams.get('ID');

    const url = "/addbookmark?GROUPID=" + groupid + "&WIFIID=" + wifiid;


    fetch(url)
        .then(function(response) {
            if (response.ok) {
            return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(function(data) {
            alert("북마크를 추가하였습니다.");
        })
        .catch(function(error) {
            alert("북마크를 추가하는 도중에 오류가 발생하였습니다.");
        });

});