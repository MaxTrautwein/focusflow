import { TextField, InputAdornment, Fab, Box } from '@mui/material'
import AddIcon from '@mui/icons-material/Add'
import SearchIcon from '@mui/icons-material/Search'

interface SearchBarProps {
  value?: string
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void
}

const SearchBar = ({ value, onChange }: SearchBarProps) => {
  return (
    <Box
      sx={{
        display: 'flex',
        alignItems: 'center',
        gap: '1rem',
        width: '100%', // neu: volle Breite
      }}
    >
      <TextField
        variant='outlined'
        placeholder='Search...'
        value={value}
        onChange={onChange}
        fullWidth
        InputProps={{
          startAdornment: (
            <InputAdornment position='start'>
              <SearchIcon />
            </InputAdornment>
          ),
        }}
        sx={{
          flex: 1,
        }}
      />
      <Fab color='primary' aria-label='add' size='small'>
        <AddIcon />
      </Fab>
    </Box>
  )
}

export default SearchBar
