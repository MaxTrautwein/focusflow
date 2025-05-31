import {
  Box,
  FormControl,
  TextField,
  Typography,
  Button,
  Alert,
  Divider,
} from '@mui/material'
import { useState } from 'react'
import { Link, useNavigate, useLocation } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth'

const SignIn: React.FC = () => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState<string | null>(null)

  const { login } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()

  const queryParams = new URLSearchParams(location.search)
  const registrationSuccess = queryParams.get('registration') === 'success'

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)
    try {
      await login(email, password)
      navigate('/dashboard')
    } catch (err: unknown) {
      if (err instanceof Error) {
        console.log('Error in SignIn.tsx:', err)
        setError(err.message || 'An unknown error occurred. Please try again.')
      } else {
        setError('An unknown error occurred. Please try again.')
      }
    }
  }

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
        <Typography variant='h4' align='center' gutterBottom>
          Sign In
        </Typography>

        {registrationSuccess && (
          <Alert severity='success' sx={{ mb: 2 }}>
            Registration successful! You can now sign in.
          </Alert>
        )}

        {error && (
          <Alert severity='error' sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        <FormControl fullWidth component='form' onSubmit={handleSubmit}>
          <TextField
            label='E-Mail'
            placeholder='E-Mail'
            fullWidth
            required
            margin='normal'
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <TextField
            label='Password'
            placeholder='Password'
            type='password'
            required
            margin='normal'
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <Button
              type='submit'
              variant='contained'
              sx={{ marginTop: '1rem' }}
              fullWidth
            >
              Sign in
            </Button>
            <Divider />
            <Link to='/signUp'>
              <Button type='button' variant='contained' fullWidth>
                Sign Up
              </Button>
            </Link>
          </Box>
        </FormControl>
      </Box>
    </Box>
  )
}

export default SignIn
