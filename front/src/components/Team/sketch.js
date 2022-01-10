import Sketch from "react-p5";

let x = 0;
let y = 0;
let rgb;
let xPos = [];
let yPos = [];

const sketch = () => {
  const setup = (p, canvasParentRef) => {
    p.createCanvas(400, 300).parent(canvasParentRef);
  };
  const mouseReleased = () => {
    console.log("mouseRelease");
    console.log(JSON.stringify(xPos));
    console.log(JSON.stringify(yPos));
  };
  const mouseDragged = (p) => {
    p.stroke("black");
    p.line(p.pmouseX, p.pmouseY, p.mouseX, p.mouseY);
    xPos.push(p.mouseX);
    yPos.push(p.mouseY);
    console.log("mousepressed");
  };
  const mousePressed = () => {
    xPos = [];
    yPos = [];
  };
  const draw = (p) => {
    if (y < 400) {
      rgb = [255, p.random(75, 200), p.random(5, 75)];
      p.stroke(rgb);
      p.fill(rgb);
      p.circle(x + p.random(-5, 5), y + p.random(-5, 5), p.random(40, 75));
      x += 10;
      if (x > 400) {
        x = 0;
        y += 30;
      }
    }
  };

  return (
    <>
      <Sketch
        setup={setup}
        draw={draw}
        mouseReleased={mouseReleased}
        mouseDragged={mouseDragged}
        mousePressed={mousePressed}
      />
    </>
  );
};

export default sketch;
