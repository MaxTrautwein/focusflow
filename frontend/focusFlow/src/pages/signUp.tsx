import {
  Box,
  FormControl,
  TextField,
  Typography,
  Button,
  Alert,
} from '@mui/material'
import Divider from '@mui/material/Divider'
import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../hooks/useAuth'

const SignUp: React.FC = () => {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [firstName, setFirstName] = useState('')
  const [lastName, setLastName] = useState('')
  const [error, setError] = useState<string | null>(null)

  const { register } = useAuth()
  const navigate = useNavigate()

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)

    try {
      await register(firstName, lastName, email, password)
      navigate('/signIn?registration=success')
    } catch (err) {
      console.error(err)
      if (err instanceof Error) {
        setError(err.message)
      } else {
        setError('Unkown Error')
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
          Sign Up
        </Typography>

        {/* Fehleranzeige */}
        {error && (
          <Alert severity='error' sx={{ mb: 2 }}>
            {error}
          </Alert>
        )}

        <form onSubmit={handleSubmit}>
          <FormControl fullWidth>
            <Box sx={{ display: 'flex', gap: 1 }}>
              <TextField
                label='First Name'
                placeholder='First Name'
                required
                margin='normal'
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
              />
              <TextField
                label='Last Name'
                placeholder='Last Name'
                required
                margin='normal'
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
              />
            </Box>
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
                Sign Up
              </Button>
              <Divider />
              <Link to='/signIn'>
                <Button type='button' variant='contained' fullWidth>
                  Sign In
                </Button>
              </Link>
            </Box>
          </FormControl>
        </form>
      </Box>
    </Box>
  )
}

export default SignUp
