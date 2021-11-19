import styled from "styled-components";

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
    display: block;
    box-sizing: border-box;
    align-items: center;
    justify-content: center;
    transition: all 0.5s;
`;

const SlickList = styled.div`
    width: 100%;
    position: relative;
    display: block;
    overflow: hidden;
    margin:0;
    padding: 0;
    // background-color: pink;
`;

const SlickTrack = styled.div`
    position: relative;
    top: 0;
    left: 0;
    margin-left: auto;
    margin-right: auto;
    opacity: 1;
    width: 1932px;
    transform: translate3d(0px,0px,0px);
`;

const DateWrap = styled.li`
    border-radius: 40px;
    padding: 13px 0;
    margin: 0 3px;
    float: left;
    height: 100%;
    min-height: 1px;
    width: 132px;
    text-align: center;
    background-color: rgb(53,52,165);
    color:white;
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




export default function MainContainer() {
    return(
        <Container>
            <DateNav>
                <TabWrap>
                    
                        <SwipeTabUl>
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
                                    
                                </SlickTrack>
                            </SlickList>
                        </SwipeTabUl>
                   
                </TabWrap>
            </DateNav>
        </Container>
    )
}