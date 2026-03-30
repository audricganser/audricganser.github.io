import { ThemeToggle } from '@/components/ThemeToggle'
import { Navigation } from '@/components/Navigation'
import { Github, LinkedIn, Mail } from '@/components/Icons'
import styles from './Header.module.scss'

export const Header = () => {
  return (
    <header className={styles.root}>
      <div className={styles.toggleWrapper}>
        <ThemeToggle />
      </div>
      <h1 className={styles.title}>Audric Ganser</h1>
      <p className={styles.title}>A software engineer based in Austin, TX</p>
      <p className={styles.contact}>
        <a
          href="//github.com/audricganser"
          title="GitHub"
        >
          <Github />
        </a>
        <a
          href="//www.linkedin.com/in/aganser"
          title="Linkedin"
        >
          <LinkedIn />
        </a>
        <a
          href="mailto:audricganser@gmail.com"
          title="Email"
        >
          <Mail />
        </a>
      </p>
      <Navigation />
    </header>
  )
}
