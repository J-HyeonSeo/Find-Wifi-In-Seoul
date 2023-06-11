function deleteBookmark(id){

    const URL = "/deletebookmark?ID=" + id;

    fetch(URL)
    .then(function(response) {
        if (response.ok) {
        return response.text();
        }
        throw new Error('Network response was not ok.');
    })
    .then(function(data) {

        alert("북마크를 삭제하였습니다.");

    })
    .catch(function(error) {
        alert("북마크를 삭제하는 도중에 오류가 발생했습니다.");
    })
    .finally(function(){
        window.location.href = "";
    });

}