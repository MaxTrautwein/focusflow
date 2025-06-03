import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Box,
  Typography,
  Fab,
} from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import { useEffect, useState } from 'react'
import type { POST_Task, Task, TaskPriority, TaskStatus } from '../lib/types'
import ChipWithMenu from '../components/chipWithMenu'
import SearchBar from '../components/searchbar'
import { useAuth } from '../hooks/useAuth'
import CreateTaskModal from '../components/taskModal'
import api from '../lib/api'

const Dashboard = () => {
  const { user } = useAuth()
  const [tasks, setTasks] = useState<Task[]>([])
  const [open, setOpen] = useState(false)

  const handlePriorityChange = async (
    task: Task,
    newPriority: string | null
  ) => {
    if (!newPriority || !user?.userId) return
    const updatedTask: POST_Task = {
      title: task.title,
      short_description: task.short_description,
      long_description: task.long_description,
      due_date: task.due_date,
      priority: newPriority.toUpperCase() as TaskPriority,
      status: task.status,
      assignee: { id: user.userId },
    }
    try {
      await api.put(`/tasks/${task.task_id}`, updatedTask)
      await fetchTasks()
    } catch (error) {
      console.error('Error updating priority:', error)
    }
  }

  const handleStatusChange = async (task: Task, newStatus: string | null) => {
    if (!newStatus || !user?.userId) return
    const updatedTask: POST_Task = {
      title: task.title,
      short_description: task.short_description,
      long_description: task.long_description,
      due_date: task.due_date,
      priority: task.priority,
      status: newStatus as TaskStatus,
      assignee: { id: user.userId },
    }
    try {
      await api.put(`/tasks/${task.task_id}`, updatedTask)
      await fetchTasks()
    } catch (error) {
      console.error('Error updating status:', error)
    }
  }

  const handleAddTask = async (task: Omit<Task, 'task_id'>) => {
    if (!user?.userId) return
    const payload: POST_Task = {
      ...task,
      assignee: { id: user.userId },
    }
    try {
      await api.post('/tasks', payload)
      setOpen(false)
      await fetchTasks()
    } catch (error) {
      console.error('Error adding task:', error)
    }
  }

  const fetchTasks = async () => {
    try {
      const response = await api.get<Task[]>('/tasks')
      if (response.status === 200) {
        setTasks(response.data)
      }
    } catch (error) {
      console.error('Error fetching tasks:', error)
    }
  }

  useEffect(() => {
    fetchTasks()
  }, [])

  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        mt: 3,
        gap: 2,
        width: '100%',
        maxWidth: 600,
        mx: 'auto',
      }}
    >
      <Typography variant='h6'>
        Welcome {user?.firstName ?? ''} {user?.lastName ?? ''}
      </Typography>

      <Box
        sx={{
          display: 'flex',
          alignItems: 'center',
          gap: 1,
          width: '100%',
        }}
      >
        <SearchBar />
        <Fab
          color='primary'
          aria-label='add'
          size='small'
          onClick={() => setOpen(true)}
        >
          <AddIcon />
        </Fab>
      </Box>

      <Box
        sx={{ width: '100%', display: 'flex', gap: 2, flexDirection: 'column' }}
      >
        {tasks.map((task, index) => (
          <Accordion key={task.task_id ?? index}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls={`panel-content-${index}`}
              id={`panel-header-${index}`}
            >
              <Box
                sx={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  width: '100%',
                  alignItems: 'center',
                }}
              >
                <Box>
                  <Typography>{task.title}</Typography>
                  <Typography variant='body2'>
                    {task.short_description}
                  </Typography>
                </Box>
                <Box
                  onClick={(e) => e.stopPropagation()}
                  sx={{ display: 'flex', gap: 1, mr: 1 }}
                >
                  <ChipWithMenu
                    customLabel='Select Priority'
                    options={['LOW', 'MEDIUM', 'HIGH']}
                    selectedOption={task.priority}
                    onSelect={(option) => handlePriorityChange(task, option)}
                  />
                  <ChipWithMenu
                    customLabel='Select Status'
                    options={['OPEN', 'PENDING', 'IN_REVIEW', 'CLOSED']}
                    selectedOption={task.status}
                    onSelect={(option) => handleStatusChange(task, option)}
                  />
                </Box>
              </Box>
            </AccordionSummary>
            <AccordionDetails>{task.long_description}</AccordionDetails>
          </Accordion>
        ))}
      </Box>

      {open && (
        <CreateTaskModal
          open={open}
          onClose={() => setOpen(false)}
          onCreate={handleAddTask}
        />
      )}
    </Box>
  )
}

export default Dashboard
