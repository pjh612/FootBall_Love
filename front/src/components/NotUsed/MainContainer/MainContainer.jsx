import React, { useState } from "react";
import DateCarousel from "./DateCarousel";
import Filter from "./LocalFilter";

export default function MainContainer() {
  const [dateBtnInfo, setDateBtnInfo] = useState({
    btnIdx: 0,
    total: 13,
  });

  return (
    <div>
      <DateCarousel
        dateBtnInfo={dateBtnInfo}
        setDateBtnInfo={setDateBtnInfo}
      ></DateCarousel>
      <Filter></Filter>
    </div>
  );
}
