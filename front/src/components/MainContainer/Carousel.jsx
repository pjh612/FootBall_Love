import React,{useEffect} from "react";
import styled from "styled-components";
import {slider}from "../utils";

const CarouselContainer = styled.div`
    background-color : #fafafa;
    margin: 0 auto;
    border-bottom: 1px solid #ddd;
`;

const ParentDiv = styled.div`
    overflow: hidden;
    height: 366px;
    
`;

const Block1 = styled.div`
    position: absolute;
    width: 17.36vw;
    height: 360px;
    background-color:#fafafa;
    z-index: 5;
    
`;
const Block2 = styled.div`
    position:absolute;
    right: 0px;
    width: 17.36vw;
    height: 360px;
    background-color: #fafafa;
    z-index: 5;
    
`;

export default function Carousel() {
    
    const imgsrc = ['img/football.jpg', 'img/football2.jpg', 'img/football3.jpg', 'img/football4.jpg','img/football5.jpg'];

    useEffect(() => {
        const fnfn = slider(imgsrc.length);
        fnfn.autoSlide();
    },[imgsrc.length])

    
    const imgStyle = {
        width: '65.28vw',
        height: 320,
        borderRadius: '5px',
        marginTop: '20px',
    }
    const imgDivStyle = {
        position: 'absolute',
        left: '17.36vw',
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
                   {imgsrc.map((src,index) => {
              return <img alt="footBall related img" src={src} key={index} style={imgStyle}></img>;
            })}
                </div> 
           </ParentDiv>
        </CarouselContainer>
    )
}