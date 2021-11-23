
import React,{ useState} from "react";
import DateCarousel from './DateCarousel';


export default function MainContainer() {
    const [dateBtnInfo, setDateBtnInfo] = useState({
        btnIdx : 0,
        total : 13,
    });

    return(
       <DateCarousel dateBtnInfo={dateBtnInfo} setDateBtnInfo={setDateBtnInfo}></DateCarousel>
    )
}