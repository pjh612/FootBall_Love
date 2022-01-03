
import React,{ useState} from "react";
import DateCarousel from './DateCarousel';
import Filter from './LocalFilter';
import styled from 'styled-components';

const TestDiv = styled.div`
    width:100vw;
    height: 50vh;
    // background: yellow;
`;

export default function MainContainer() {
    const [dateBtnInfo, setDateBtnInfo] = useState({
        btnIdx : 0,
        total : 13,
    });

    return(
        <div>
       <DateCarousel dateBtnInfo={dateBtnInfo} setDateBtnInfo={setDateBtnInfo}></DateCarousel>
       <Filter></Filter>
       <TestDiv></TestDiv>
       </div>
    )
}