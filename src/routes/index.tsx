import { createFileRoute, Link } from '@tanstack/react-router'
import { Canvas, useFrame } from '@react-three/fiber'
import { ThreeGrid } from '@/components/Grid'
import {
  OrbitControls,
  Environment,
  RoundedBox,
  PerspectiveCamera,
  Html,
} from '@react-three/drei'
import { Suspense, useRef } from 'react'
import { Mesh } from 'three'
import styles from './index.module.scss'

export const Route = createFileRoute('/')({ component: App })

function App() {
  const ref = useRef<HTMLDivElement>(null!)

  return (
    <div
      ref={ref}
      className={styles.root}
    >
      <Canvas
        eventSource={ref}
        gl={{
          antialias: true,
          powerPreference: 'high-performance',
          failIfMajorPerformanceCaveat: false,
        }}
        dpr={1}
        style={{
          position: 'absolute',
          top: 0,
          left: 0,
          width: '100%',
          height: '100%',
          pointerEvents: 'none',
        }}
      >
        <Suspense fallback={null}>
          <PerspectiveCamera
            makeDefault
            position={[0, 5, 12]}
            fov={50}
          />
          <OrbitControls
            makeDefault
            enablePan={false}
            minDistance={5}
            maxDistance={20}
          />

          <ThreeGrid />
          <Environment preset="city" />

          <group position={[0, 0, 0]}>
            <Cube />
          </group>

          <ambientLight intensity={0.5} />
          <spotLight
            position={[10, 10, 10]}
            angle={0.15}
            penumbra={1}
            intensity={Math.PI}
          />
        </Suspense>
      </Canvas>
    </div>
  )
}

const Cube = () => {
  const meshRef = useRef<Mesh>(null)

  useFrame((_state, delta) => {
    if (meshRef.current) {
      const speed = 0.2
      meshRef.current.rotation.x += delta * speed
      meshRef.current.rotation.y += delta * speed
    }
  })

  return (
    <>
      <mesh ref={meshRef}>
        <RoundedBox
          args={[1, 1, 1]}
          position={[0, 0, 0]}
          radius={0.1}
          smoothness={4}
        >
          <meshStandardMaterial
            metalness={1}
            roughness={0.1}
            color="white"
          />
        </RoundedBox>
      </mesh>
      <ambientLight intensity={Math.PI / 2} />
      <spotLight
        position={[10, 10, 10]}
        angle={0.15}
        penumbra={1}
        decay={0}
        intensity={Math.PI}
      />
      <pointLight
        position={[-10, -10, -10]}
        decay={0}
        intensity={Math.PI}
      />
    </>
  )
}
