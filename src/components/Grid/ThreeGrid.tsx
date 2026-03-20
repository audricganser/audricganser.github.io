import { useTheme } from "@/hooks/useTheme"
import { Grid } from "@react-three/drei"

export const ThreeGrid = () => {
  const { isDark } = useTheme()

  const gridOptions = {
    gridSize: [30, 16],
    cellSize: 1.0,
    cellThickness: 1.0,
    cellColor: isDark ? '#00FF41' : '#000',
    sectionSize: 5.0,
    sectionThickness: 1.5,
    sectionColor: isDark ? '#00FF41' : '#000',
    fadeStrength: 0.5,
    followCamera: false,
    infiniteGrid: true,
  }

  return <Grid {...gridOptions} position={[0, -3.0, 0.0]} side={2}  />
}
