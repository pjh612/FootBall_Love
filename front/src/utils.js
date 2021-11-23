export function slider(totalImgNum) {
  let index = 0;
  const DivElem = document.getElementsByClassName("img-div")[0];

  const fn = {
    clickBefore: function () {
      if (index === 0) {
        index = totalImgNum - 1;
        DivElem.style.transform = `translate3d(-${940 * index}px, 0, 0)`;
        return;
      }

      index -= 1;
      DivElem.style.transform = `translate3d(-${940 * index}px, 0, 0)`;

      console.log(DivElem.style.transform);
    },

    clickNext: function () {
      if (index === totalImgNum - 1) {
        index = 0;
        DivElem.style.transform = `translate3d(0px, 0, 0)`;
        return;
      }
      index += 1;
      DivElem.style.transform = `translate3d(-${940 * index}px, 0, 0)`;
    },

    autoSlide: function () {
      let stopper = 0;
      const interval = setInterval(() => {
        this.clickNext();
        stopper += 1;
        if (stopper % 2 === 0) {
          console.log("stopper is working!");
          clearInterval(interval);
          setTimeout(() => {
            this.autoSlide();
          }, 10);
        }
      }, 2500);
    },
  };
  return fn;
}
