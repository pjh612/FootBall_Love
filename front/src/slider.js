import React, { useState, useEffect, useRef } from "react";

export default function slider(totalImgNum) {
  let index = 0;
  const DivElem = document.getElementsByClassName("img-div")[0];

  const fn = {
    clickBefore: function () {
      console.log("Before click function is working.!");
      if (index === 0) {
        index = totalImgNum - 1;
        DivElem.style.transform = `translate3d(-${600 * index}px, 0, 0)`;
        return;
      }

      index -= 1;
      DivElem.style.transform = `translate3d(-${600 * index}px, 0, 0)`;

      console.log(DivElem.style.transform);
    },

    clickNext: function () {
      console.log("Next click function is working.!");

      if (index === totalImgNum - 1) {
        index = 0;
        DivElem.style.transform = `translate3d(0px, 0, 0)`;
        return;
      }
      index += 1;
      DivElem.style.transform = `translate3d(-${600 * index}px, 0, 0)`;
    },
  };
  return fn;
}