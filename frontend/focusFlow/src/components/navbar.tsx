import React from 'react'
import { AppBar, Box, Toolbar, Typography, IconButton } from '@mui/material'
import LogoutIcon from '@mui/icons-material/Logout'
import { useAuth } from '../hooks/useAuth'

const Navbar: React.FC = () => {
  const { logout } = useAuth()

  const handleLogout = () => {
    logout()
  }

  return (
    <AppBar position='static' color='primary'>
      <Toolbar>
        <Box sx={{ flexGrow: 1, textAlign: 'center' }}>
          <Typography variant='h6' component='div'>
            Focusflow
          </Typography>
        </Box>
        <IconButton
          edge='end'
          color='inherit'
          aria-label='logout'
          onClick={handleLogout}
          data-cy='navbar-logout-button'
        >
          <LogoutIcon />
        </IconButton>
      </Toolbar>
    </AppBar>
  )
}

export default Navbar
