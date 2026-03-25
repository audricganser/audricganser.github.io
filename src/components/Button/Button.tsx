import type { ButtonHTMLAttributes, FC } from 'react'
import styles from './Button.module.scss'
import { clsx } from 'clsx'

export const Button: FC<ButtonHTMLAttributes<HTMLButtonElement>> = ({
  children,
  className,
  ...rest
}) => {
  return (
    <button
      className={clsx(styles.root, className)}
      {...rest}
    >
      {children}
    </button>
  )
}
