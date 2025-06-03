import React, { useState } from 'react'
import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
  MenuItem,
} from '@mui/material'
import type { Task, TaskPriority, TaskStatus } from '../lib/types'

interface CreateTaskModalProps {
  open: boolean
  onClose: () => void
  onCreate: (task: Omit<Task, 'task_id'>) => void
}

const CreateTaskModal: React.FC<CreateTaskModalProps> = ({
  open,
  onClose,
  onCreate,
}) => {
  const [title, setTitle] = useState('')
  const [shortDescription, setShortDescription] = useState('')
  const [longDescription, setLongDescription] = useState('')
  const [dueDate, setDueDate] = useState('')
  const [priority, setPriority] = useState<TaskPriority>('LOW')
  const [status, setStatus] = useState<TaskStatus>('OPEN')

  const handleCreate = () => {
    if (!title || !shortDescription || !longDescription || !dueDate) {
      alert('Please fill in all required fields!')
      return
    }
    onCreate({
      title,
      short_description: shortDescription,
      long_description: longDescription,
      due_date: new Date(dueDate),
      priority,
      status,
    })
    onClose()
  }

  return (
    <Modal open={open} onClose={onClose}>
      <Box
        sx={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: '90%',
          maxWidth: 500,
          bgcolor: 'background.paper',
          boxShadow: 24,
          borderRadius: 2,
          p: 3,
          display: 'flex',
          flexDirection: 'column',
          gap: 2,
        }}
      >
        <Typography variant='h6' textAlign='center'>
          Create New Task
        </Typography>

        <TextField
          label='Title'
          size='small'
          fullWidth
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <TextField
          label='Short Description'
          size='small'
          fullWidth
          value={shortDescription}
          onChange={(e) => setShortDescription(e.target.value)}
        />
        <TextField
          label='Long Description'
          size='small'
          fullWidth
          multiline
          rows={4}
          value={longDescription}
          onChange={(e) => setLongDescription(e.target.value)}
        />
        <TextField
          label='Due Date'
          type='date'
          size='small'
          fullWidth
          value={dueDate}
          onChange={(e) => setDueDate(e.target.value)}
          InputLabelProps={{ shrink: true }}
        />
        <TextField
          label='Priority'
          select
          size='small'
          fullWidth
          value={priority}
          onChange={(e) => setPriority(e.target.value as TaskPriority)}
        >
          <MenuItem value='LOW'>Low</MenuItem>
          <MenuItem value='MEDIUM'>Medium</MenuItem>
          <MenuItem value='HIGH'>High</MenuItem>
        </TextField>
        <TextField
          label='Status'
          select
          size='small'
          fullWidth
          value={status}
          onChange={(e) => setStatus(e.target.value as TaskStatus)}
        >
          <MenuItem value='OPEN'>Open</MenuItem>
          <MenuItem value='IN_PROGRESS'>In Progress</MenuItem>
          <MenuItem value='DONE'>Done</MenuItem>
        </TextField>

        <Box
          sx={{ display: 'flex', justifyContent: 'flex-end', gap: 1, mt: 1 }}
        >
          <Button onClick={onClose} variant='outlined' size='small'>
            Cancel
          </Button>
          <Button variant='contained' size='small' onClick={handleCreate}>
            Create
          </Button>
        </Box>
      </Box>
    </Modal>
  )
}

export default CreateTaskModal
