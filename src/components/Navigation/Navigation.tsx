import { Link } from '@tanstack/react-router'
import { Button } from '../Button'
import style from './Navigation.module.scss'
import { ThreeJsJourney, Home } from '../Icons'
import { useState } from 'react'
import clsx from 'clsx'

const NavItems = [
  {
    name: 'Home',
    image: <Home />,
    href: '/',
  },
  {
    name: 'ThreeJS Journey',
    image: <ThreeJsJourney />,
    href: '/threeJourney/$lessonId',
    params: 3,
  },
]

export const Navigation = () => {
  const [isOpen, setIsOpen] = useState(true)

  return (
    <div className={style.root}>
      <nav
        className={clsx(style.slideNav, isOpen && style.isOpen)}
        onClick={(e) => {
          e.stopPropagation()
          setIsOpen((x) => !x)
        }}
      >
        {NavItems.map((i) => (
          <Link
            to={i.href}
            params={i.params ? { lessonId: i.params } : undefined}
            key={'nav-item-' + i.name}
            className={style.navLink}
            onClick={(e) => e.stopPropagation()}
          >
            <Button
              imageOnly
              aria-label={'navigation ' + i.name}
              title={i.name}
            >
              {i.image}
            </Button>
          </Link>
        ))}
      </nav>
    </div>
  )
}
