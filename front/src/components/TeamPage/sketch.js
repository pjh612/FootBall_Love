import Sketch from "react-p5";

// let xPos = [];
// let yPos = [];

const sketch = () => {
  const setup = (p, canvasParentRef) => {
    p.createCanvas(p.windowWidth, 500).parent(canvasParentRef);
    p.background("green");
  };

  const draw = (p) => {
    p.stroke("white");
    p.fill("green");
    p.translate(p.windowWidth / 2 - 170, 0);
    p.rect(30, 30, 340, 440); // 외곽선
    p.rect(120, 30, 160, 50); // 위쪽 골대
    p.rect(120, 420, 160, 50); // 아래쪽 골대
    p.circle(200, 250, 50); // 센터볼 원
    p.line(30, 250, 370, 250);
  };

  return (
    <>
      <Sketch
        setup={setup}
        draw={draw}
        // mouseReleased={mouseReleased}
        // mouseDragged={mouseDragged}
        // mousePressed={mousePressed}
      />
    </>
  );
};

export default sketch;
