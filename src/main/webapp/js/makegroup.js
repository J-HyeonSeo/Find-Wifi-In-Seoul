const groupOpenBtn = document.getElementById("make-bookmark");
const groupModal = document.getElementById("group-modal");
const updateGroupModal = document.getElementById("group-update-modal");

groupOpenBtn.addEventListener("click", function(){

    groupModal.style.display = "block";

});

document.body.addEventListener("click", function(event) {
    if (event.target == groupModal || event.target == updateGroupModal) {
        groupModal.style.display = "none";
        updateGroupModal.style.display = "none";
    }
});

//inputdata를 기반으로 북마크 설정하기.
const bookmarkCommit = document.getElementById("create-group-btn");

bookmarkCommit.addEventListener("click", function(){

    var name = document.getElementById("bookmark-name").value;
    var order = document.getElementById("order").value;

    const URL = "/createbookmarkGroup?NAME=" + name + "&ORDER=" + order;

    let result = 1;

    fetch(URL)
        .then(function(response) {
            if (response.ok) {
            return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(function(data) {
            alert("북마크그룹을 추가하였습니다.");
        })
        .catch(function(error) {
            alert("북마크그룹을 추가하는 도중에 오류가 발생하였습니다.");
        })
        .finally(function(){
            window.location.href = "/bookmark";
        });

});


let updateGroupId = 0;
//북마크 그룹 update 수행하기.
function updateOpen(id){

    updateGroupId = id;
    
    updateGroupModal.style.display = "block";

}

const updateGroupBtn = document.getElementById("update-group-btn");

updateGroupBtn.addEventListener("click", updateGroupSend);

//북마크 수정 내용 서버로 전송.
function updateGroupSend(){

    var name = document.getElementById("bookmark-update-name").value;
    var order = document.getElementById("update-order").value;

    const URL = "/updatebookmarkGroup?ID=" + updateGroupId + "&NAME=" + name + "&ORDER=" + order;
	
    fetch(URL)
        .then(function(response) {
            if (response.ok) {
            return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(function(data) {
            alert("북마크그룹을 수정하였습니다.");
        })
        .catch(function(error) {
            alert("북마크그룹을 수정하는 도중에 오류가 발생하였습니다.");
        })
        .finally(function(){
            window.location.href = "/bookmark";
        });
}


//북마크 그룹 delete 수행하기.
function deleteOpen(id){

    const URL = "/deletebookmarkGroup?ID=" + id;

    fetch(URL)
        .then(function(response) {
            if (response.ok) {
            return response.text();
            }
            throw new Error('Network response was not ok.');
        })
        .then(function(data) {
            alert("북마크그룹을 삭제하였습니다.");
        })
        .catch(function(error) {
            alert("북마크그룹을 삭제하는 도중에 오류가 발생하였습니다.");
        })
        .finally(function(){
            window.location.href = "/bookmark";
        });
    
}