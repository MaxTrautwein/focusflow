import React, { useEffect, useState, type ReactNode } from 'react'
import { AuthContext } from './authContext'
import type { User } from '../lib/types'
import api from '../lib/api'
import axios from 'axios'

export const AuthProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [user, setUser] = useState<User | null>(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const storedUser = localStorage.getItem('user')
    if (storedUser) {
      setUser(JSON.parse(storedUser))
    }
    setLoading(false)
  }, [])

  const login = async (email: string, password: string) => {
    try {
      const response = await api.post<User>('/users/login', {
        email,
        password,
      })
      console.log('Login backend = ', response)
      const user = response.data
      setUser(user)
      localStorage.setItem('user', JSON.stringify(user))
    } catch (error) {
      if (axios.isAxiosError(error)) {
        const apiMessage = error.response?.data?.message || 'Login failed'
        throw new Error(apiMessage)
      } else {
        throw new Error('Login failed')
      }
    }
  }

  const logout = () => {
    setUser(null)
    localStorage.removeItem('user')
    window.location.href = '/signIn'
  }

  const getCurrentUser = async () => {
    try {
      const response = await api.get<User>('/users/me')
      const data = response.data
      setUser(data)
      localStorage.setItem('user', JSON.stringify(data))
    } catch (error) {
      if (axios.isAxiosError(error)) {
        const apiMessage = error.response?.data?.message || 'Not authenticated'
        throw new Error(apiMessage)
      } else {
        throw new Error('Not authenticated')
      }
    }
  }

  const register = async (
    firstName: string,
    lastName: string,
    email: string,
    password: string
  ): Promise<string> => {
    try {
      const response = await api.post(
        '/users/register',
        {
          email,
          password,
          firstName,
          lastName,
        },
        {
          responseType: 'text', // IMMER als Text behandeln
        }
      )

      const message = response.data
      return message
    } catch (error) {
      if (axios.isAxiosError(error)) {
        const data = error.response?.data

        let apiMessage = 'Internal Server Error: Registration failed'
        if (typeof data === 'string') {
          try {
            const parsed = JSON.parse(data)
            if (parsed.message) {
              apiMessage = parsed.message
            } else {
              apiMessage = data // Fallback: Original-Text
            }
          } catch {
            // Kein JSON → einfach als Text übernehmen
            apiMessage = data
          }
        }

        throw new Error(apiMessage)
      } else {
        console.error('Unexpected Error:', error)
        throw new Error('Internal Server Error: Registration failed')
      }
    }
  }

  return (
    <AuthContext.Provider
      value={{
        user,
        setUser,
        loading,
        login,
        logout,
        getCurrentUser,
        register,
      }}
    >
      {children}
    </AuthContext.Provider>
  )
}
