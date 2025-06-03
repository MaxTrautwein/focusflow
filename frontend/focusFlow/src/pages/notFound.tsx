import React from 'react'
import { Box, Typography, Button } from '@mui/material'
import { useNavigate } from 'react-router-dom'

const NotFound: React.FC = () => {
  const navigate = useNavigate()

  return (
    <Box
      sx={{
        width: '100%',
        height: '100vh',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <Typography variant='h3' gutterBottom>
        404 - Page Not Found
      </Typography>
      <Typography variant='body1' gutterBottom>
        The page you are looking for does not exist.
      </Typography>
      <Button
        variant='contained'
        onClick={() => navigate('/dashboard')}
        sx={{ mt: 2 }}
      >
        Go to Home
      </Button>
    </Box>
  )
}

export default NotFound
