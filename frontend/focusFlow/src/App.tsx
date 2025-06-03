import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
  useLocation,
} from 'react-router-dom'
import SignIn from './pages/signIn'
import Dashboard from './pages/dashboard'
import SignUp from './pages/signUp'
import Root from './pages/root'
import { AuthProvider } from './context/authProvider'
import { useAuth } from './hooks/useAuth'
import React from 'react'
import { CircularProgress, Box } from '@mui/material'
import Navbar from './components/navbar'
import NotFound from './pages/notFound'

const ProtectedRoute: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const { user, loading } = useAuth()

  if (loading) {
    return (
      <Box
        sx={{
          width: '100%',
          height: '100vh',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <CircularProgress />
      </Box>
    )
  }

  if (!user) return <Navigate to='/signIn' />
  return <>{children}</>
}

const PublicRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { user, loading } = useAuth()

  if (loading) {
    return (
      <Box
        sx={{
          width: '100%',
          height: '100vh',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <CircularProgress />
      </Box>
    )
  }

  if (user) return <Navigate to='/dashboard' />
  return <>{children}</>
}

const RootRedirect: React.FC = () => {
  const { user, loading } = useAuth()

  if (loading) {
    return (
      <Box
        sx={{
          width: '100%',
          height: '100vh',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <CircularProgress />
      </Box>
    )
  }

  if (user) {
    return <Navigate to='/dashboard' />
  } else {
    return <Navigate to='/signIn' />
  }
}

const Layout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const location = useLocation()
  const hideNavbarPaths = ['/signIn', '/signUp']

  const shouldHideNavbar = hideNavbarPaths.includes(location.pathname)

  return (
    <>
      {!shouldHideNavbar && <Navbar />}
      {children}
    </>
  )
}

function App() {
  return (
    <AuthProvider>
      <Router>
        <Layout>
          <Routes>
            <Route path='/' element={<RootRedirect />} />
            <Route
              path='/dashboard'
              element={
                <ProtectedRoute>
                  <Dashboard />
                </ProtectedRoute>
              }
            />
            <Route
              path='/root'
              element={
                <ProtectedRoute>
                  <Root />
                </ProtectedRoute>
              }
            />
            <Route
              path='/signIn'
              element={
                <PublicRoute>
                  <SignIn />
                </PublicRoute>
              }
            />
            <Route
              path='/signUp'
              element={
                <PublicRoute>
                  <SignUp />
                </PublicRoute>
              }
            />
            <Route path='*' element={<NotFound />} />
          </Routes>
        </Layout>
      </Router>
    </AuthProvider>
  )
}

export default App
