import React from "react";
import styled from "styled-components";

const CarouselContainer = styled.div`
    background-color : #fafafa;
    margin: 0 auto;
    border-bottom: 1px solid #ddd;
`;

const ParentDiv = styled.div`
  background-color: #fafafa;
  margin: 0 auto;
  border-bottom: 1px solid #ddd;
  position: relative;
  display: flex;
  justify-content: center;
`;

const ImageDiv = styled.div`
  width: 600px;
  height: 400px;
  margin: 20 auto;
  overflow: hidden;
  display: flex;
`;

// 이어서 할것: 버튼 배치하고 슬라이드
export default function Carousel() {
    const imgsrc = ['img/football.jpg', 'img/football2.jpg'];
    const imgStyle = {
        width: 600,
        height: 400,
    }
    const buttonNum = imgsrc.length;
    const buttonStyle = {
    }

    return(
        <CarouselContainer>
           <ParentDiv>
               <ImageDiv>
                   {imgsrc.map((src) => {
              return <img src={src} style={imgStyle}></img>;
            })}
                {[...Array(buttonNum)].map((n, index) => {
                    return <button style={buttonStyle}>{index + 1}</button>   
                })
                }
               </ImageDiv>
           </ParentDiv>
        </CarouselContainer>
    )
}