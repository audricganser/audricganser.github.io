import { useEffect, useState } from 'react'
import styles from './Lesson3.module.scss'
import * as THREE from 'three'
import { Button } from '@/components/Button'

const sceneContent = [
  {
    text: 'Canvas',
    url: 'https://developer.mozilla.org/en-US/docs/Web/API/Canvas_API',
  },
  { text: 'Scene', url: 'https://threejs.org/docs/#Scene' },
  { text: 'Geometry', url: 'https://threejs.org/docs/#BoxGeometry' },
  { text: 'Material', url: 'https://threejs.org/docs/#Material' },
  { text: 'Mesh', url: 'https://threejs.org/docs/#Mesh' },
  {
    text: 'Camera',
    url: 'https://threejs.org/docs/PerspectiveCamera',
  },
  { text: 'Renderer', url: 'https://threejs.org/docs/#Renderer' },
]

export const Lesson3 = () => {
  const [isWireFrame, setIsWireFrame] = useState(false)

  useEffect(() => {
    const canvas = document.querySelector('canvas.Lesson3WebGl')

    const scene = new THREE.Scene()
    const geometry = new THREE.BoxGeometry(1, 1, 1)
    const material = new THREE.MeshBasicMaterial({
      color: 'red',
      wireframe: isWireFrame,
    })
    const mesh = new THREE.Mesh(geometry, material)
    scene.add(mesh)

    const sizes = {
      width: 800,
      height: 600,
    }

    const camera = new THREE.PerspectiveCamera(75, sizes.width / sizes.height)
    camera.position.z = 3
    scene.add(camera)

    if (!canvas) {
      return console.error('canvas missing')
    }
    const renderer = new THREE.WebGLRenderer({
      canvas: canvas,
    })

    renderer.setSize(sizes.width, sizes.height)

    renderer.render(scene, camera)
  }, [isWireFrame])

  return (
    <div>
      <div className={styles.lessonText}>
        <h2>Lesson 3: Vite and other build tools + first Scene</h2>
        <p>
          This lesson covers the initial development of a vite project, but
          since I have already completed that by building my personal site in
          vite and Tanstack I ended up skipping to the first scene creation
          using pure ThreeJS.
        </p>
        {sceneContent.map((t, idx) => (
									<li key={t.text + idx}>
            <a
              href={t.url}
              target="_blank"
            >
              {t.text}
            </a>
          </li>
        ))}
      </div>
      <div className={styles.canvas}>
        <Button onClick={() => setIsWireFrame((x) => !x)}>
          Wireframe: {isWireFrame ? 'On' : 'Off'}
        </Button>
        <canvas className="Lesson3WebGl"></canvas>
      </div>
    </div>
  )
}
