import { TextField, InputAdornment } from '@mui/material'
import SearchIcon from '@mui/icons-material/Search'

interface SearchBarProps {
  value?: string
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void
}

const SearchBar = ({ value, onChange }: SearchBarProps) => {
  return (
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
  )
}

export default SearchBar
