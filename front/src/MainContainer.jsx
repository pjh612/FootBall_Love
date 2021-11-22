import styled from "styled-components";
import React,{useEffect, useState} from "react";

const Container =  styled.div`
display: block;
`;

const DateNav = styled.div`
    padding: 20px 0;
    position: sticky;
    z-index: 2;
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
    transform: translate3d(-139.5px,0px,0px);
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
    background-color: rgb(53,52,165);
    color:white;
    // color: black;
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
    font-size: 11px;
    font-family: 'Nanum Gothic', sans-serif;
`;

const BeforeButton = styled.button`
    width: 20px;
    height: 20px;
    cursor: pointer;
    display:inline-block;
`;

const NextButton = styled.button`
width: 20px;
    height: 20px;
    cursor: pointer;
    display:inline-block;
`;




export default function MainContainer() {
    const [dateBtnInfo, setDateBtnInfo] = useState({
        btnIdx : 0,
        total : 8,
    });

    function NextClick() {
        // 버튼 disable 하고 버튼 색깔 회색으로 바꾸기
        if ((dateBtnInfo.total - dateBtnInfo.btnIdx) === 7) {
            return ;
        }
        else {
            setDateBtnInfo((prevState) => ({
                ...prevState,
                btnIdx: prevState.btnIdx + 1,
            }))
        }

    }
    return(
        <Container>
            <DateNav>
                <TabWrap>
                        <SwipeTabUl>
                        <BeforeButton></BeforeButton>
                            <SlickList>
                                <SlickTrack>                
                                    <DateWrap>
                                        <P>19</P>
                                        <Span>금</Span>
                                        </DateWrap>
                                    <DateWrap>
                                    <P>20</P>
                                    <Span>토</Span>
                                    </DateWrap>
                                    <DateWrap>
                                    <P>21</P>
                                    <Span>일</Span>
                                    </DateWrap>
                                    <DateWrap>
                                    <P>22</P>
                                    <Span>월</Span>
                                    </DateWrap>
                                    <DateWrap>
                                    <P>23</P>
                                    <Span>화</Span>
                                    </DateWrap>
                                    <DateWrap>
                                    <P>24</P>
                                    <Span>수</Span>
                                    </DateWrap>
                                    <DateWrap>
                                    <P>25</P>
                                    <Span>목</Span>
                                    </DateWrap>
                                    <DateWrap>
                                    <P>26</P>
                                    <Span>금</Span>
                                    </DateWrap>
                                </SlickTrack>
                            </SlickList>
                            <NextButton></NextButton>
                        </SwipeTabUl>
                </TabWrap>
            </DateNav>
        </Container>
    )
}