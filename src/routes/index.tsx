import { createFileRoute } from '@tanstack/react-router'
import { Canvas, useFrame } from '@react-three/fiber'
import { OrbitControls, Environment, RoundedBox, Center, Text3D, View, PerspectiveCamera } from '@react-three/drei'
import { useRef } from 'react'
import { Mesh } from 'three'
import styles from './index.module.scss'

export const Route = createFileRoute('/')({ component: App })

function App() {
  const ref = useRef<HTMLDivElement>(null!)

  return (
    <div ref={ref} className={styles.root} >
        <View index={1} className={styles.view1}>
          <Text />
          <PerspectiveCamera makeDefault position={[0, 0, 5]} />
          <Environment preset="warehouse" />
        </View>

        <View index={2} className={styles.view2}>
          <Cube />
          <OrbitControls
            enablePan={false}
            enableZoom={false}
          />
          <PerspectiveCamera makeDefault position={[0, -1, 3]} />
          <Environment preset="warehouse" />
        </View>


        <Canvas
          eventSource={ref}
          gl={{
            antialias: true,
            powerPreference: "high-performance",
            failIfMajorPerformanceCaveat: false
          }}
          dpr={[1, 2]}
          style={{
            position: 'absolute',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            pointerEvents: 'none'
          }}
        >
          <View.Port />
        </Canvas>
    </div>
  )
}

const Cube = () => {
  const meshRef = useRef<Mesh>(null)

  useFrame((_state, delta) => {
    if (meshRef.current) {
      const speed = 0.2
      meshRef.current.rotation.x += delta * speed;
      meshRef.current.rotation.y += delta * speed;
    }
  });

  return (
    <>
      <mesh ref={meshRef}>
        <RoundedBox
          args={[1, 1, 1]}
          position={[0, 0, 0]}
          radius={0.1}
          smoothness={4}
        >
          <meshStandardMaterial metalness={1} roughness={0.1} color="white" />
        </RoundedBox>
      </mesh>
      <ambientLight intensity={Math.PI / 2} />
      <spotLight position={[10, 10, 10]} angle={0.15} penumbra={1} decay={0} intensity={Math.PI} />
      <pointLight position={[-10, -10, -10]} decay={0} intensity={Math.PI} />
    </>
  )
}

const Text = () => {
  const fontUrl = 'https://raw.githubusercontent.com/mrdoob/three.js/master/examples/fonts/helvetiker_bold.typeface.json'
  return (
    <>
      <Center>
        <Text3D
          font={fontUrl}
          size={1}
          height={0.05}
        >
          New Portfolio Coming Soon
          <meshStandardMaterial metalness={1} roughness={0.15} color="white" />
        </Text3D>
      </Center>
    </>
  )
}
