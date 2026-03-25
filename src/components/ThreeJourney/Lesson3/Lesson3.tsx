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

const FIXED_HEIGHT = 600
const PADDING = 40

export const Lesson3 = () => {
  const [isWireFrame, setIsWireFrame] = useState(false)

  useEffect(() => {
    const canvas = document.querySelector('canvas.Lesson3WebGl')
    const initialWidth = window.innerWidth - PADDING * 2

    const scene = new THREE.Scene()
    const geometry = new THREE.BoxGeometry(1, 1, 1)
    const material = new THREE.MeshBasicMaterial({
      color: 'red',
      wireframe: isWireFrame,
    })
    const mesh = new THREE.Mesh(geometry, material)
    scene.add(mesh)

    const camera = new THREE.PerspectiveCamera(75, initialWidth / FIXED_HEIGHT)
    camera.position.z = 3
    scene.add(camera)

    if (!canvas) {
      return console.error('canvas missing')
    }
    const renderer = new THREE.WebGLRenderer({
      canvas: canvas,
      alpha: true,
    })

    renderer.setSize(initialWidth, FIXED_HEIGHT)

    const handleResize = () => {
      const width = window.innerWidth - PADDING * 2
      camera.aspect = width / FIXED_HEIGHT
      camera.updateProjectionMatrix()
      renderer.setSize(width, FIXED_HEIGHT)
      renderer.render(scene, camera)
    }

    window.addEventListener('resize', handleResize)

    renderer.render(scene, camera)

    console.log(renderer.info.programs?.length)

    return () => {
      window.removeEventListener('resize', handleResize)
      renderer.dispose()
      geometry.dispose()
      material.dispose()
    }
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
        <h3>Extras:</h3>
        <p>
          <strong>Wireframe Toggle:</strong> Toggle to show that this is a
          BoxGeometry and not just a square.
        </p>
        <p>
          <strong>Resize Width:</strong> The lesson provided an object with set
          height and width sizes. This didnt look good on mobile so I decided to
          add an event listener to resize based off of{' '}
          <code>window.innerWidth</code> and padding. These changes resulted in
          some camera updates as well{' '}
          <code>camera.updateProjectionMatrix()</code>. Calling the function
          allows the camera to re-calculate its field of view.
        </p>
        <p>
          <strong>Resource Management:</strong> Since WebGL doesn't
          automatically garbage-collect GPU assets, I added the{' '}
          <code>dispose()</code> method in the <code>useEffect</code> cleanup.
          This manually frees up VRAM and prevents memory leaks when the
          component unmounts or updates.
          <br />
          <a href="https://discourse.threejs.org/t/is-dispose-even-needed/3482">
            "But you don't have to take my word for it" - LeVar Burton
          </a>
        </p>
        <p>
          <strong>Renderer Alpha:</strong> Set the renderer Alpha prop to true
          so that my canvas scss color would come through. Setting it to true
          allows the GPU to not set its default color on the canvas.
        </p>
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
