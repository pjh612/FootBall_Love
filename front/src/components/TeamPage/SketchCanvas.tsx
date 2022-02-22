import Sketch from "./sketch";
import Box from "@mui/material/Box";

export default function SketchCanvas() {
  return (
    <Box sx={{ display: "flex", justifyContent: "center", mt: "30px" }}>
      <Sketch></Sketch>
    </Box>
  );
}
