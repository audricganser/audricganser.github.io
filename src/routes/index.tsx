import { createFileRoute, useNavigate } from '@tanstack/react-router'
import { Canvas, useFrame } from '@react-three/fiber'
import { ThreeGrid } from '@/components/Grid'
import {
  Environment,
  RoundedBox,
  PerspectiveCamera,
  useKeyboardControls,
  Html,
} from '@react-three/drei'
import { Suspense, useMemo, useRef, useState } from 'react'
import { Color, MathUtils, Mesh, MeshStandardMaterial } from 'three'
import styles from './index.module.scss'
import { KeyboardContolsComponent } from '#/components/KeyboardControls'

export const Route = createFileRoute('/')({ component: App })

interface Project {
  id: string
  name: string
  url: string
  minX: number
  maxX: number
  minZ: number
  maxZ: number
  color: string
}

const PROJECTS: Project[] = [
  {
    id: 'project-1',
    name: 'Three Journey',
    url: '/threeJourney',
    minX: 3,
    maxX: 5,
    minZ: -1,
    maxZ: 1,
    color: '#ff5e00',
  },
  {
    id: 'project-2',
    name: 'locked',
    url: '/',

    // name: 'ICON',
    minX: -5,
    maxX: -3,
    minZ: -1,
    maxZ: 1,
    color: '#ff5e00',
  },
  {
    id: 'project-3',
    name: 'locked',
    url: '/',

    // name: '[0]IDX',
    minX: -1,
    maxX: 1,
    minZ: -5,
    maxZ: -3,
    color: '#ff5e00',
  },
  {
    id: 'project-5',
    name: 'locked',
    url: '/',

    // name: 'OLD',
    minX: -1,
    maxX: 1,
    minZ: 3,
    maxZ: 5,
    color: '#ff5e00',
  },
]

const keyMap = [
  { name: 'forward', keys: ['ArrowUp', 'w', 'W'] },
  { name: 'backward', keys: ['ArrowDown', 's', 'S'] },
  { name: 'left', keys: ['ArrowLeft', 'a', 'A'] },
  { name: 'right', keys: ['ArrowRight', 'd', 'D'] },
]

function App() {
  const ref = useRef<HTMLDivElement>(null!)

  return (
    <div
      ref={ref}
      className={styles.root}
    >
      <KeyboardContolsComponent map={keyMap}>
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
          }}
        >
          <Suspense fallback={null}>
            <PerspectiveCamera
              makeDefault
              position={[0, 5, 10]}
              fov={50}
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
      </KeyboardContolsComponent>
    </div>
  )
}

const ROTATION_SPEED = 0.2
const BOUNDS = 5
const COLOR_TRANSITION_SPEED = 0.25
const MOVE_SPEED = 5
const FOLLOW_CAMERA_OFFSET = [0, 5, 10]

const Cube = () => {
  const meshRef = useRef<Mesh>(null)
  const lastFoundId = useRef<string | null>(null)
  const materialRef = useRef<MeshStandardMaterial>(null!)
  const labelRef = useRef<HTMLDivElement>(null!)
  const codeRef = useRef<HTMLElement>(null!)
  const [, get] = useKeyboardControls()
  // const navigate = useNavigate()

  const colors = useMemo(
    () => ({
      white: new Color('#eeeeee'),
      orange: new Color('#ff5e00'),
      black: new Color('#000000'),
    }),
    [],
  )

  useFrame((state, delta) => {
    const cube = meshRef.current
    const mat = materialRef.current

    if (!cube || !mat) return

    // Keyboard controls
    const { forward, backward, left, right, jump } = get()
    if (forward) cube.position.z -= MOVE_SPEED * delta
    if (backward) cube.position.z += MOVE_SPEED * delta
    if (left) cube.position.x -= MOVE_SPEED * delta
    if (right) cube.position.x += MOVE_SPEED * delta

    cube.position.x = Math.max(-BOUNDS, Math.min(BOUNDS, cube.position.x))
    cube.position.z = Math.max(-BOUNDS, Math.min(BOUNDS, cube.position.z))
    cube.position.y = 1

    const cx = cube.position.x
    const cz = cube.position.z
    const found = PROJECTS.find(
      (p) => cx >= p.minX && cx <= p.maxX && cz >= p.minZ && cz <= p.maxZ,
    )

    const foundId = found ? found.id : null

    // 3. Update DOM via Ref (Zero re-renders!)
    // if (foundId !== lastFoundId.current) {
    //   lastFoundId.current = foundId
    //   if (labelRef.current)
    //     labelRef.current.style.opacity = found ? '1' : '0.01'
    //   if (found && codeRef.current) codeRef.current.innerText = found.name
    // }

    // // 4. Navigation
    // if (jump && found) {
    //   navigate({ to: found.url })
    // }

    if (found) {
      cube.rotation.x = MathUtils.lerp(cube.rotation.x, 0, 0.1)
      cube.rotation.y = MathUtils.lerp(cube.rotation.y, 0, 0.1)
      cube.rotation.z = MathUtils.lerp(cube.rotation.z, 0, 0.1)
    } else {
      cube.rotation.x += delta * ROTATION_SPEED
      cube.rotation.y += delta * ROTATION_SPEED
    }

    state.camera.position.set(
      cube.position.x + FOLLOW_CAMERA_OFFSET[0],
      cube.position.y + FOLLOW_CAMERA_OFFSET[1],
      cube.position.z + FOLLOW_CAMERA_OFFSET[2],
    )

    state.camera.lookAt(cube.position.x, 0, cube.position.z)

    const targetColor = found ? colors.orange : colors.white
    const targetEmissive = found ? colors.orange : colors.black
    const targetMetalness = found ? 0.5 : 1
    mat.color.lerp(targetColor, COLOR_TRANSITION_SPEED)
    mat.emissive.lerp(targetEmissive, COLOR_TRANSITION_SPEED)
    mat.emissiveIntensity = MathUtils.lerp(mat.emissiveIntensity, 0, 0.1)
    mat.metalness = MathUtils.lerp(mat.metalness, targetMetalness, 0.1)
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
            ref={materialRef}
            roughness={0.1}
            color={colors.white}
          />
        </RoundedBox>
        {/* <Html
          visible={false}
          distanceFactor={10}
          position={[0, 0, 0.5]}
          center
          pointerEvents="none"
        >
          <div
            ref={labelRef}
            className={styles.cubeLabel}
            style={{
              opacity: 0.01,
              transition: 'opacity 0.1s ease-out', // Smooths the pop
              pointerEvents: 'none',
              willChange: 'opacity, transform', // Tells the browser to use the GPU
            }}
          >
            <code ref={codeRef}></code>
          </div>
        </Html> */}
      </mesh>
      {PROJECTS.map((project) => {
        const width = project.maxX - project.minX
        const height = project.maxZ - project.minZ

        return (
          <group
            key={project.id}
            position={[
              (project.minX + project.maxX) / 2,
              0.01,
              (project.minZ + project.maxZ) / 2,
            ]}
          >
            <mesh rotation={[-Math.PI / 2, 0, 0]}>
              <planeGeometry args={[width, height]} />
              <meshStandardMaterial
                color={project.color}
                transparent
                wireframe
                toneMapped={false}
              />
            </mesh>
          </group>
        )
      })}
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
