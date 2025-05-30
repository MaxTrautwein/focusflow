export interface Task {
  title: string
  short_description: string
  long_description: string
  due_date: Date
  user?: User
  priority?: TaskPriority
  status?: TaskStatus
}

export interface User {
  email: string
  password?: string
  firstName: string
  lastName: string
}

export const TaskPriorityOptions = {
  LOW: 'LOW',
  MEDIUM: 'MEDIUM',
  HIGH: 'HIGH',
} as const

export type TaskPriority =
  (typeof TaskPriorityOptions)[keyof typeof TaskPriorityOptions]

export const TaskStatusOptions = {
  OPEN: 'OPEN',
  PENDING: 'PENDING',
  IN_REVIEW: 'IN_REVIEW',
  CLOSED: 'CLOSED',
} as const

export type TaskStatus =
  (typeof TaskStatusOptions)[keyof typeof TaskStatusOptions]
