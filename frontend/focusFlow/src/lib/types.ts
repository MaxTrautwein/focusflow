export interface User {
  id: number
  email: string
  firstName: string
  lastName: string
  teamId: string | null
}

export type POST_User = Omit<User, 'id' | 'teamId'> & {
  password: string
}

export type POST_Login_User = Pick<User, 'email'> & {
  password: string
}

export interface Task {
  task_id: number
  title: string
  short_description: string
  long_description: string
  due_date: Date
  assignee?: User
  priority: TaskPriority
  status: TaskStatus
}

export type POST_Task = Omit<Task, 'task_id' | 'assignee'> & {
  assignee: { id: number }
}

export type TaskPriority = 'LOW' | 'MEDIUM' | 'HIGH'

export type TaskStatus = 'OPEN' | 'PENDING' | 'IN_REVIEW' | 'CLOSED'

export interface AuthContextType {
  user: User | null
  setUser: React.Dispatch<React.SetStateAction<User | null>>
  loading: boolean
  login: (email: string, password: string) => Promise<void>
  logout: () => void
  getCurrentUser: () => Promise<void>
  register: (
    firstName: string,
    lastName: string,
    email: string,
    password: string
  ) => Promise<void>
}
