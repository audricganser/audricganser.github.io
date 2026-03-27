import { KeyboardControls, type KeyboardControlsProps } from '@react-three/drei'
import type { FC } from 'react'

export const KeyboardContolsComponent: FC<KeyboardControlsProps> = ({
  children,
  ...rest
}) => {
  return <KeyboardControls {...rest}>{children}</KeyboardControls>
}
