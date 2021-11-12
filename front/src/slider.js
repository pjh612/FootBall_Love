import "./slider.css";
// 이미지 소스 배열

export default function slider(imgSrcArray, width, height) {
  const imgElements = [];
  const ParentDiv = document.createElement("div");
  ParentDiv.className = "parent-div";

  for (let i = 0; i < imgSrcArray.length; i++) {
    let img = new Image(width, height);
    img.src = imgSrcArray[i];
    // img.style.display = 'none';

    imgElements.push(img);
    ParentDiv.appendChild(img);
  }

  return ParentDiv;
}
