import React from "react";
import styled from "styled-components";

import slider from './slider';

const CarouselContainer = styled.div`
    background-color : #fafafa;
    margin: 0 auto;
    border-bottom: 1px solid #ddd;
`;

const Test = styled.div`
    height: 100px;
    background-color: yellow;
`;



export default function Carousel() {
    const imgsrc = ['img/football.jpg', 'img/football2.jpg'];
    const newDiv = slider(imgsrc, 500, 500);
    console.log(newDiv);
    return(
        <CarouselContainer>
            <Test></Test>
        </CarouselContainer>
    )
}