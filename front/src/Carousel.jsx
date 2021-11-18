import React,{useEffect, useState} from "react";
import styled from "styled-components";
import slider from "./slider.js";

const CarouselContainer = styled.div`
    background-color : #fafafa;
    margin: 0 auto;
    border-bottom: 1px solid #ddd;
`;

const ParentDiv = styled.div`
    overflow: hidden;
    height: 366px;
    
`;

const LeftButton = styled.button`
    position: absolute;
    top: 185px;
    left: 350px;
    width: 30px;
    height: 30px;
    z-index: 6;
`;

const RightButton = styled.button`
    position: absolute;
    top: 185px;
    left: 1060px;
    width: 30px;
    height: 30px;
    z-index: 6;
`;

const Block1 = styled.div`
    position: absolute;
    width: 420px;
    height: 366px;
    background-color:#fafafa;
    z-index: 5;
    border-bottom: 1px solid #ddd;
`;
const Block2 = styled.div`
    position:absolute;
    right: 0px;
    width: 420px;
    height: 366px;
    background-color: #fafafa;
    z-index: 5;
    border-bottom: 1px solid #ddd;
`;

export default function Carousel() {
    const [fn, setFn] = useState({});

    useEffect(() => {
        const fnfn = slider(imgsrc.length);
        setFn(fnfn);
    }, [])

    const imgsrc = ['img/football.jpg', 'img/football2.jpg', 'img/football3.jpg', 'img/football4.jpg','img/football5.jpg'];
    const imgStyle = {
        width: 600,
        height: 366,
    }
    const imgDivStyle = {
        position: 'absolute',
        left: '420px',
        display: 'flex',
        transfrom: 'translate3d(0,0,0)',
        transition: 'transform 0.6s',
    }

    return(
        <CarouselContainer>
           <ParentDiv>
               <Block1></Block1>
               <Block2></Block2>
               <div className="img-div" style={imgDivStyle}>
                   {imgsrc.map((src) => {
              return <img alt="footBall related img" src={src} style={imgStyle}></img>;
            })}
                </div>
                <LeftButton onClick={() => fn.clickNext()}></LeftButton>
                <RightButton onClick={() => fn.clickBefore()}></RightButton>
           </ParentDiv>
        </CarouselContainer>
    )
}