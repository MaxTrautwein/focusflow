import { Box } from '@mui/material'
import type { ReactNode } from 'react'

const authForm = ({ children }: { children: ReactNode }) => {
  return (
    <Box
      sx={{
        width: '100%',
        minHeight: '90vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        p: 2,
        boxSizing: 'border-box',
        overflow: 'hidden',
      }}
    >
      <Box
        sx={{
          width: '100%',
          maxWidth: 400,
          padding: 3,
          boxShadow: 3,
          borderRadius: 2,
          backgroundColor: 'background.paper',
        }}
      >
        {children}
      </Box>
    </Box>
  )
}

export default authForm
