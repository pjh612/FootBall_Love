import { ReactP5Wrapper, Sketch } from 'react-p5-wrapper';

const sketch: Sketch = (p5) => {
  p5.setup = () => p5.createCanvas(p5.windowWidth, p5.windowHeight, p5.WEBGL);

  p5.draw = () => {
    p5.background(250);
    p5.normalMaterial();
    p5.push();
    p5.rotateZ(p5.frameCount * 0.01);
    p5.rotateX(p5.frameCount * 0.01);
    p5.rotateY(p5.frameCount * 0.01);
    p5.plane(100);
    p5.pop();
    console.log('loop');
  };
};

export default function Example() {
  return <ReactP5Wrapper sketch={sketch} />;
}

// const Canvas: React.FC<ComponentProps> = (props: ComponentProps) => {
//   const setup = (p: p5Types, canvasParentRef: Element) => {
//     p.createCanvas(p.windowWidth, 500).parent(canvasParentRef);
//     p.background('green');
//   };

//   const draw = (p: p5Types) => {
//     p.stroke('white');
//     p.fill('green');
//     p.translate(p.windowWidth / 2 - 170, 0);
//     p.rect(30, 30, 340, 440); // 외곽선
//     p.rect(120, 30, 160, 50); // 위쪽 골대
//     p.rect(120, 420, 160, 50); // 아래쪽 골대
//     p.circle(200, 250, 50); // 센터볼 원
//     p.line(30, 250, 370, 250);
//   };

//   return <Sketch setup={setup} draw={draw}></Sketch>;
// };

// export default Canvas;
