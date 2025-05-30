import React from 'react'
import Chip from '@mui/material/Chip'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import Divider from '@mui/material/Divider'

interface ChipWithMenuProps {
  options: string[]
  selectedOption: string | null
  customLabel: string
  onSelect: (option: string | null) => void // angepasst, um auch null zu erlauben
}

const ChipWithMenu: React.FC<ChipWithMenuProps> = ({
  options,
  selectedOption,
  onSelect,
  customLabel,
}) => {
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null)

  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget)
  }

  const handleClose = () => {
    setAnchorEl(null)
  }

  const handleSelectOption = (option: string) => {
    onSelect(option)
    handleClose()
  }

  const handleClearSelection = () => {
    onSelect(null)
    handleClose()
  }

  return (
    <div>
      <Chip
        label={selectedOption ?? customLabel}
        onClick={handleClick}
        variant='outlined'
        style={{ cursor: 'pointer' }}
      />
      <Menu anchorEl={anchorEl} open={Boolean(anchorEl)} onClose={handleClose}>
        {options.map((option) => (
          <MenuItem
            key={option}
            selected={option === selectedOption}
            onClick={() => handleSelectOption(option)}
          >
            {option}
          </MenuItem>
        ))}

        {selectedOption && (
          <>
            <Divider />
            <MenuItem onClick={handleClearSelection} sx={{ color: 'red' }}>
              Delete
            </MenuItem>
          </>
        )}
      </Menu>
    </div>
  )
}

export default ChipWithMenu
