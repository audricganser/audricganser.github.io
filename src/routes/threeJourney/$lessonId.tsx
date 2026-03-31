import { createFileRoute } from '@tanstack/react-router'
import styles from './threeJourney.module.scss'
import { Lesson3 } from '@/components/ThreeJourney/Lesson3'
import type React from 'react'
import { Lesson4 } from '#/components/ThreeJourney/Lesson4'

const LESSONS: Record<string, React.ComponentType> = {
  '3': Lesson3,
  '4': Lesson4,
} as const

export const Route = createFileRoute('/threeJourney/$lessonId')({
  component: RouteComponent,
  parseParams: (params) => ({
    lessonId: Number(params.lessonId),
  }),
})

function RouteComponent() {
  const { lessonId } = Route.useParams()

  const isFirstLesson = lessonId === 3
  const SelectedLesson = LESSONS[lessonId]

  if (!SelectedLesson) {
    return <div>Lesson {lessonId} not found!</div>
  }

  return (
    <div className={styles.root}>
      {isFirstLesson && (
        <div className={styles.info}>
          <h1>Hello ThreeJS!</h1>
          <p>
            First, a huge thank you to{' '}
            <a href="https://bruno-simon.com/">Bruno Simon</a> and his course on
            ThreeJS <a href="https://threejs-journey.com">(ThreeJS Journey)</a>.
            This page is dedicated to showcasing the progress I'm making in the
            course, and any comments I have had along the way.
          </p>
        </div>
      )}
      <SelectedLesson />
    </div>
  )
}
