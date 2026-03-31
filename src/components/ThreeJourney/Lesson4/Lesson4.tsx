import { useEffect, useState } from 'react'
import styles from './Lesson4.module.scss'
import * as THREE from 'three'
import { Button } from '@/components/Button'

const sceneContent = [
  {
    text: 'Mesh Position',
    url: 'https://threejs.org/docs/#Object3D.position',
  },
  {
    text: 'Vector3',
    url: 'https://threejs.org/docs/#Vector3',
  },
  {
    text: 'Axes Helper',
    url: 'https://threejs.org/docs/#AxesHelper',
  },
  {
    text: 'Scale',
    url: 'https://threejs.org/docs/#Object3D.scale',
  },
  {
    text: 'Rotation',
    url: 'https://threejs.org/docs/#Euler',
  },
  {
    text: 'Rotation Reorder',
    url: 'https://threejs.org/docs/#Euler.reorder',
  },
  {
    text: 'Quaternion',
    url: 'https://threejs.org/docs/#Quaternion',
  },
]

const FIXED_HEIGHT = 600
const PADDING = 40

export const Lesson4 = () => {
  const [showAllAxesHelper, setShowAllAxesHelper] = useState(false)

  useEffect(() => {
    const canvas = document.querySelector('canvas.Lesson3WebGl')
    const initialWidth = window.innerWidth - PADDING * 2

    const scene = new THREE.Scene()
    const geometry = new THREE.BoxGeometry(1, 1, 1)
    const material = new THREE.MeshBasicMaterial({
      color: 'red',
    })
    const mesh = new THREE.Mesh(geometry, material)
    // positoining
    // mesh.position.x = 0.7
    // mesh.position.y = -0.6
    // mesh.position.z = 1

    mesh.position.set(0.7, -0.6, 1)

    //scale
    // mesh.scale.x = 2
    // mesh.scale.y = 0.5
    // mesh.scale.z = 0.5

    mesh.scale.set(2, 0.5, 0.5)

    //rotation
    mesh.rotation.reorder('YXZ') // do this before changing the rotation
    mesh.rotation.x = Math.PI * 0.25 // Math.PI is half a rotation
    mesh.rotation.y = Math.PI * 0.25

    scene.add(mesh)

    const axesHelper = new THREE.AxesHelper()
    scene.add(axesHelper)

    // distance between the center of the scene and the object position
    console.info(
      'distance between the center of the scene and the object position',
      mesh.position.length(),
    )

    // normalize

    const camera = new THREE.PerspectiveCamera(75, initialWidth / FIXED_HEIGHT)

    if (showAllAxesHelper) {
      camera.position.set(1, 1, 3)
    }
    camera.position.z = 3

    scene.add(camera)

    console.info(
      'Distance between the camera and the object',
      mesh.position.distanceTo(camera.position),
    )

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

    return () => {
      window.removeEventListener('resize', handleResize)
      renderer.dispose()
      geometry.dispose()
      material.dispose()
      axesHelper.dispose()
    }
  }, [showAllAxesHelper])

  return (
    <div>
      <div className={styles.lessonText}>
        <h2>Lesson 4: Transforming an object (In Progress)</h2>
        <p>
          The transforms cover everything from scaling to rotation of the mesh,
          and any issues like Gimbal Lock that can occur.
          <code>mesh.position.set(x, y, z)</code>. This also covers a way to
          help you picture how the object will move with the axes helper.
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
          <strong>Mesh.position:</strong> The lesson explains that adding the
          mesh position after the render would not change the position of the
          mesh. This is because once the render is set the mesh has already been
          placed. Since I re-render the scene each time the screen width changes
          this is not true in my case. The position of the object will change if
          you resize the screen. I don't demo this here, but it was something
          that I tested when I was playing around with the positioning. Best
          practice is adding it after creating the mesh and before adding it to
          the scene.
        </p>
        <p>
          <strong>Show Axes Helper X:</strong> Activating this will help you
          visualize all the Axes in the scene. This is done by moving the camera
          in the positive y and x direction by 1 unit in order to see the z axes
          (blue).
        </p>
      </div>
      <div className={styles.canvas}>
        <Button onClick={() => setShowAllAxesHelper((x) => !x)}>
          Show Axes Helper X: {showAllAxesHelper ? 'On' : 'Off'}
        </Button>
        <canvas className="Lesson3WebGl"></canvas>
      </div>
    </div>
  )
}
