const loadBtn = document.getElementById("open-api");
const modal = document.getElementById("myModal");

const loadText = document.getElementById("load-text");

const closeBtn = document.getElementById("close-modal");

loadBtn.addEventListener("click", function(){
    
    modal.style.display = "block";
    loadFromAPI();

});

closeBtn.addEventListener("click", function(){
	
	modal.style.display = "none";
	closeBtn.style.display = "none";
	
});


function loadFromAPI(){

    const URL = "/openAPI";

    fetch(URL)
        .then(function(response) {
            if (response.ok) {
            return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(function(data) {

            loadText.textContent = data;
            closeBtn.style.display = "block";

        })
        .catch(function(error) {
            alert("데이터를 가져오는 중에 문제가 발생하였습니다. 다시 시도해주세요");
            modal.style.display = "none";
        });

}