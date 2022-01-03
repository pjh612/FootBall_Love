import styled from "styled-components";
import React,{useState, useEffect, useRef} from "react";
import {ReactComponent as RightArrow} from "../icon/rightArrow.svg";
import {ReactComponent as LeftArrow} from "../icon/leftArrow.svg";


const Container =  styled.div`
    display: block;
`;

const DateNav = styled.div`
    padding: 20px 0;
    position: sticky;
    z-index: 10;
    top:0;
    // background-color: red;
`;

const TabWrap = styled.div`
    height: 100%;
    max-width: 1024px;
    margin: 0 auto;
    // background-color: yellow;
`;

const SwipeTabUl = styled.ul`
    position: relative;
    display: flex;
    box-sizing: border-box;
    align-items: center;
    transition: all 0.5s;
`;

const SlickList = styled.div`
    width: 100%;
    position: relative;
    display:block;
    overflow: hidden;
    margin:0;
    padding: 0;
`;

const SlickTrack = styled.div`
    position: relative;
    display: flex;
    justify-content: flex-start;
    top: 0;
    left: 0;
    margin-left: auto;
    margin-right: auto;
    opacity: 1;
    width: 1932px;
    transform: translate3d(${props => props.idx * (-140.3)}px,0px, 0px);
    transition: all, 0.5s;
    // transform: translate3d(0px,0px,0px);
`;

const DateWrap = styled.li`
    border-radius: 40px;
    padding: 13px 0;
    margin: 0 4.4px;
    float: left;
    height: 100%;
    min-height: 1px;
    width: 132px;
    text-align: center;
    // background-color: rgb(53,52,165);
    color:  ${props => props.color};
    cursor: pointer;
    `;

const P = styled.p`
font-size: 18px;
line-height: 14px;
padding-top: 8px;
font-weight: 400;
margin:0;
word-break: break-all;
`;


const Span = styled.span`
    font-size: 0.763888vw;
    font-family: 'Nanum Gothic', sans-serif;
`;

export default function DateCarousel(props) {
    const [dateComponents, setDateComponents] = useState(null);
    const dateBtnInfo = props.dateBtnInfo;
    const setDateBtnInfo = props.setDateBtnInfo;

    const leftArrowColor = useRef("rgba(53,64,165,0.4)")
    const rightArrowColor = useRef("rgba(53,64,165,1)")

    const event = {
        nextClick : function() {
        if (dateBtnInfo.btnIdx === 6) {
            return ;
        }
        else {
            if (dateBtnInfo.btnIdx === 5) {
                rightArrowColor.current = "rgba(53,64,165,0.4)";
            } else {
                rightArrowColor.current = "rgba(53,64,165,1)"
            }
            leftArrowColor.current = "rgba(53,64,165,1)"
            setDateBtnInfo((prevState) => ({
                total: prevState.total,
                btnIdx: prevState.btnIdx + 1,
            }));
        }
        },
        beforeClick : function() {
            if ((dateBtnInfo.btnIdx === 0)) {
                return ;
            } else {
                if (dateBtnInfo.btnIdx === 1) {
                    leftArrowColor.current = "rgba(53,64,165,0.4)";
                } else {
                    leftArrowColor.current = "rgba(53,64,165,1)"
                }
                rightArrowColor.current = "rgba(53,64,165,1)"
                setDateBtnInfo((prevState) => ({
                    total: prevState.total,
                    btnIdx: prevState.btnIdx - 1,
                }))
            }
        }    
    }

    useEffect(() => {
        setDateComponents(makeDateBlock(13))
    }, [])

    function makeDateBlock(blockNums) {
        let week = ['일', '월', '화', '수', '목', '금', '토'];
        let thirtyOne = [1,0,1,0,1,0,1,1,0,1,0,1]; 
        let todayIdx = new Date().getDay();
        let todayDate = new Date().getDate();
        let month = new Date().getMonth();
        const components = [];

        const utils = {
            parseDate : function(i) {
                const isThirtyOne = thirtyOne[month];
                let date = todayDate + i;
                if (isThirtyOne) {
                    if (date > 31) {
                        date -= 31;
                    }
                } else {
                    if (month === 1) {
                        if (date > 28) {
                            date -= 28;
                        }
                    } else {
                        if (date > 30) {
                            date -= 30;
                        }
                    }
                }
                return date;
            },
            getToday : function(i) {
                let today = (todayIdx + i) % 7;
                return week[today];
            }

        }

        for(let i = 0; i < blockNums; i++) {
            let date = utils.parseDate(i);
            let today = utils.getToday(i);
            let fontColor = 'black';
            if (today === "일") {
                fontColor = 'red';
            }
            if (today === "토") {
                fontColor = 'rgb(53,52,165)'; // blue
            }
            components.push(
            <DateWrap color={fontColor} key={i - 400}> 
                <P key={i}>{date}</P>
                <Span key={i + 500}>{today}</Span>
                </DateWrap>)
        }
        return components;
    }

        return(
            <Container>
            <DateNav>
                <TabWrap>
                        <SwipeTabUl>
                        <LeftArrow style={{cursor: "pointer"}} fill={leftArrowColor.current} onClick={() => event.beforeClick()}></LeftArrow>
                            <SlickList>
                                <SlickTrack idx={dateBtnInfo.btnIdx}>                
                                    {dateComponents ? dateComponents : null}
                                </SlickTrack>
                            </SlickList>
                            <RightArrow style={{cursor: "pointer"}} fill={rightArrowColor.current} onClick={() => event.nextClick()}></RightArrow>
                        </SwipeTabUl>
                </TabWrap>
            </DateNav>
        </Container>
        )
}