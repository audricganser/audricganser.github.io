import type { ButtonHTMLAttributes, FC } from 'react'
import styles from './Button.module.scss'
import { clsx } from 'clsx'

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  iconOnly?: boolean
  imageOnly?: boolean
}

export const Button: FC<ButtonProps> = ({
  children,
  className,
  iconOnly,
  imageOnly,
  ...rest
}) => {
  return (
    <button
      className={clsx(styles.root, iconOnly && styles.iconOnly, imageOnly && styles.imageOnly, className)}
      {...rest}
    >
      {children}
    </button>
  )
}
