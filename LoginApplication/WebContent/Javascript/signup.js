var index = 0;
var imageArray = [];
imageArray[0] = "/profile.jpg";
imageArray[1] = "/cat.jpg";
imageArray[2] = "/cpy.png";
imageArray[3] = "/cpy1.png";

function closeModalWindow() {
    document.getElementById('modal_window').style.display = 'none';
}
function openModalWindow() {
    document.getElementById('modal_window').style.display = 'block';
}

function onClickNext() {
    index++;
    if (index > (imageArray.length - 1)) {
	index = 0;
    }
    var pathToImage = document.getElementById("profile_image").src;
    var url = pathToImage.substring(0, pathToImage.lastIndexOf("/"))
	    + imageArray[index];
    document.getElementById("profile_image").src = url;
}

function onClickPrevious() {
    index--;
    if (index < 0) {
	index = imageArray.length - 1;
    }
    var pathToImage = document.getElementById("profile_image").src;
    var url = pathToImage.substring(0, pathToImage.lastIndexOf("/"))
	    + imageArray[index];
    document.getElementById("profile_image").src = url;
}