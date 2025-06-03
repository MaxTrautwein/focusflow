import type { ReactNode } from 'react'
import {
  Button as MuiButton,
  type ButtonProps as MuiButtonProps,
} from '@mui/material'

type CustomColor = 'green' | 'red' | 'orange'

interface CustomButtonProps extends Omit<MuiButtonProps, 'color'> {
  color?: CustomColor
  children?: ReactNode
}

const colorMap: Record<CustomColor, string> = {
  green: '#4caf50',
  red: '#f44336',
  orange: '#ff9800',
}

const Button = ({ color, children, sx, ...rest }: CustomButtonProps) => {
  const customStyles = color
    ? {
        backgroundColor: colorMap[color],
        color: '#fff',
        '&:hover': {
          backgroundColor: colorMap[color],
          opacity: 0.9,
        },
      }
    : {}

  return (
    <MuiButton
      variant='contained'
      {...rest}
      sx={{
        ...customStyles,
        ...sx,
      }}
    >
      {children}
    </MuiButton>
  )
}

export default Button
