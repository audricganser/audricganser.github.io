import { createFileRoute } from '@tanstack/react-router'
import styles from './threeJourney.module.scss'
import { Lesson3 } from '@/components/ThreeJourney/Lesson3'

export const Route = createFileRoute('/threeJourney')({
  component: RouteComponent,
})

function RouteComponent() {
  return (
    <div className={styles.root}>
      <div className={styles.info}>
        <h2>Hello ThreeJS!</h2>
        <p>
          First, a huge thank you to Bruno Simon and his course on ThreeJS{' '}
          <a href="https://threejs-journey.com">(ThreeJS Journey)</a>. This page
          is dedicated to showcasing the progress I'm making in the course, and
          any comments I have had along the way.
        </p>
      </div>
      <Lesson3 />
    </div>
  )
}
