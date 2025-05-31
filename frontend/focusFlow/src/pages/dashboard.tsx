import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Box,
  Typography,
} from '@mui/material'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore'
import { useEffect, useState } from 'react'
import type { Task } from '../lib/types'
import ChipWithMenu from '../components/chipWithMenu'
import SearchBar from '../components/searchbar'

const Dashboard = () => {
  const [tasks, setTasks] = useState<Task[]>([])
  const [priorities, setPriorities] = useState<(string | null)[]>(
    Array(tasks.length).fill(null)
  )
  const [status, setStatus] = useState<(string | null)[]>(
    Array(tasks.length).fill(null)
  )
  const handlePriorityChange = (index: number, newPriority: string | null) => {
    const updated = [...priorities]
    updated[index] = newPriority
    setPriorities(updated)
  }

  const handleStatusChange = (index: number, newStatus: string | null) => {
    const updated = [...status]
    updated[index] = newStatus
    setStatus(updated)
  }

  useEffect(() => {
    setTasks([
      {
        title: 'Test Task',
        short_description: 'short description',
        long_description: 'long description',
        due_date: new Date(),
        priority: 'HIGH',
        status: 'OPEN',
        task_id: 1,
      },
    ])
  }, [])

  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        mt: '3rem',
        gap: '2rem',
        width: '100%',
        maxWidth: '600px',
        mx: 'auto',
      }}
    >
      <SearchBar />
      <Box
        sx={{
          width: '100%',
        }}
      >
        {tasks.map((task, index) => (
          <Accordion key={index}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls='panel1-content'
              id={`panel1-header-${index}`}
            >
              <Box
                sx={{
                  display: 'flex',
                  alignItems: 'center',
                  justifyContent: 'space-between',
                  width: '100%',
                }}
              >
                <Box sx={{ display: 'flex', flexDirection: 'column' }}>
                  <Typography component='span'>{task.title}</Typography>
                  <Typography variant='body2'>
                    {task.short_description}
                  </Typography>
                </Box>
                <Box
                  onClick={(event) => event.stopPropagation()}
                  sx={{ display: 'flex', gap: 1, marginRight: 1 }}
                >
                  <ChipWithMenu
                    customLabel='Select Priority'
                    options={['High', 'Medium', 'Low']}
                    selectedOption={priorities[index]}
                    onSelect={(option) => handlePriorityChange(index, option)}
                  />
                  <ChipWithMenu
                    customLabel='Select Status'
                    options={['OPEN', 'PENDING', 'IN_REVIEW', 'CLOSED']}
                    selectedOption={status[index]}
                    onSelect={(option) => handleStatusChange(index, option)}
                  />
                </Box>
              </Box>
            </AccordionSummary>
            <AccordionDetails>{task.long_description}</AccordionDetails>
          </Accordion>
        ))}
      </Box>
    </Box>
  )
}

export default Dashboard
